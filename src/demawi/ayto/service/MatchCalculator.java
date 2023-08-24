package demawi.ayto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import demawi.ayto.events.ConstellationValidation;
import demawi.ayto.events.Event;
import demawi.ayto.events.NewPerson;
import demawi.ayto.modell.AYTO_Pair;
import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.CalculationOptions;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.Person;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.modell.Zeitpunkt;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.Formatter;
import demawi.ayto.util.Pair;

public class MatchCalculator {

   private final List<CalculationOptions> calcOptions = new ArrayList<>();

   public MatchCalculator(StaffelData data, int fromTag, int fromEventCount, int toTag, int toEventCount) {
      for (int curTag = fromTag; curTag <= toTag; curTag++) {
         int curEventFrom = 0;
         int curEventTo = 0;

         if (curTag == fromTag) { // fromEventCount - size()
            curEventFrom = fromEventCount;
            curEventTo = data.getTag(curTag)
                  .getEvents()
                  .size();
         }
         else if (curTag == toTag) { // 0 - toEventCount
            curEventTo = toEventCount;
         }
         else { // 0 - size()
            curEventTo = data.getTag(curTag)
                  .getEvents()
                  .size();
         }

         for (int curEvent = curEventFrom; curEvent <= curEventTo; curEvent++) {
            calcOptions.add(new CalculationOptions(data, new Zeitpunkt(curTag, curEvent)));
         }
      }
   }

   /**
    * Suche nach NeuePerson Events um entsprechende Permutation-Cuts zu setzen.
    */
   public List<AYTO_Result> calculate(Consumer<String> out) {
      List<List<CalculationOptions>> realCalcOptions = new ArrayList<>();
      List<CalculationOptions> curOptions = new ArrayList<>();
      realCalcOptions.add(curOptions);
      for (CalculationOptions opts : calcOptions) {
         Event evt = opts.getEvent();
         if (evt instanceof NewPerson) {
            curOptions = new ArrayList<>();
            realCalcOptions.add(curOptions);
         }
         curOptions.add(opts);
      }

      return realCalcOptions.stream()
            .flatMap(opts -> calculateSafe(opts, out).stream())
            .collect(Collectors.toList());
   }

   /**
    * Events dürfen nur zu Beginn ein Event NeuePerson enthalten.
    */
   private static List<AYTO_Result> calculateSafe(List<CalculationOptions> calcOptions, Consumer<String> out) {
      long start = System.currentTimeMillis();

      CalculationOptions calcPrimaryOptions = calcOptions.get(0);
      StaffelData data = calcPrimaryOptions.getData();
      data.ensureDataIsClosed();
      AYTO_Permutator<Person, Person, AYTO_Pair> permutator = AYTO_Permutator.create(calcPrimaryOptions.getFrauen(),
            calcPrimaryOptions.getMaenner(), calcPrimaryOptions.getZusatztype(), AYTO_Pair::pair);

      List<List<Pair<ConstellationValidation, AYTO_Result>>> results = new ArrayList<>();
      permutator.permutate(() -> {
         List<Pair<ConstellationValidation, AYTO_Result>> eventResults = new ArrayList<>();
         results.add(eventResults);
         for (CalculationOptions opt : calcOptions) {
            if (eventResults.size() == 0) {
               // Die erste Validation ist alles von Anfang an bis zu diesem Ereignis
               eventResults.add(Pair.pair(opt, new AYTO_Result(opt)));
            }
            else {
               // Fortführend sind es nur noch Einzelereignisse
               eventResults.add(Pair.pair(opt.getEvent(), new AYTO_Result(opt)));
            }
         }
         final AtomicInteger totalConstellations = new AtomicInteger(0);
         return constellation -> {
            for (Pair<ConstellationValidation, AYTO_Result> entry : eventResults) {
               if (entry.getFirst()
                     .isValid(constellation)) {
                  entry.getSecond()
                        .addResult(constellation, true);
               }
               else {
                  break;
               }
            }
            eventResults.get(0)
                  .getSecond()
                  .fixTotalPossibilities(totalConstellations.incrementAndGet());
         };
      });

      if (out != null) {
         System.out.println(" Calculation time: " + Formatter.minSecs(System.currentTimeMillis() - start));
      }

      return sum(calcOptions, results);
   }

   private static List<AYTO_Result> sum(List<CalculationOptions> calcOptions,
         List<List<Pair<ConstellationValidation, AYTO_Result>>> results) {
      List<AYTO_Result> finalResultList = new ArrayList<>();
      int totalConstellations = 0;
      for (int i = 0, l = calcOptions.size(); i < l; i++) {
         CalculationOptions opt = calcOptions.get(i);
         AYTO_Result curResult = new AYTO_Result(opt);
         finalResultList.add(curResult);
         for (List<Pair<ConstellationValidation, AYTO_Result>> entryList : results) {
            curResult.add(entryList.get(i)
                  .getSecond());
         }
         if (i == 0) {
            totalConstellations = curResult.totalConstellations;
         }
         else {
            curResult.fixTotalPossibilities(totalConstellations);
         }
      }
      return finalResultList;
   }

}
