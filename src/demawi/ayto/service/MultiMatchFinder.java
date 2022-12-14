package demawi.ayto.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import demawi.ayto.events.Event;
import demawi.ayto.events.NewPerson;
import demawi.ayto.modell.AYTO_Pair;
import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.Pair;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.Formatter;

/**
 * In der Selektion darf ein NewPerson-Event nur zu Beginn enthalten sein. Da sich durch ein solches Event die zugrundeliegende Permutation ändern würde
 * und wir hier aber seriell Filtern wollen.
 */
public class MultiMatchFinder {

   private final List<CalculationOptions> calcOptions = new ArrayList<>();

   public MultiMatchFinder(StaffelData data, int fromTag, int fromEventCount, int toTag, int toEventCount) {
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
            calcOptions.add(new CalculationOptions(data, curTag, curEvent));
         }
      }
   }

   /**
    * Suche nach NeuePerson Events um entsprechende Cuts zu setzen
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
      AYTO_Permutator<Frau, Mann, AYTO_Pair> permutator = AYTO_Permutator.create(calcPrimaryOptions.getFrauen(),
            calcPrimaryOptions.getMaenner(), calcPrimaryOptions.getZusatztype(), AYTO_Pair::pair);

      List<List<Pair<Event, AYTO_Result>>> results = new ArrayList<>();
      permutator.permutate(() -> {
         List<Pair<Event, AYTO_Result>> eventResults = new ArrayList<>();
         results.add(eventResults);
         for (CalculationOptions opt : calcOptions) {
            if (eventResults.size() == 0) {
               eventResults.add(Pair.pair(new NewDayEvent(opt), new AYTO_Result(opt)));
            }
            else {
               eventResults.add(Pair.pair(opt.getEvent(), new AYTO_Result(opt)));
            }
         }
         final AtomicInteger totalConstellations = new AtomicInteger(0);
         return constellation -> {
            for (Pair<Event, AYTO_Result> entry : eventResults) {
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

   private static class NewDayEvent
         implements Event {

      private CalculationOptions options;

      public NewDayEvent(CalculationOptions options) {
         this.options = options;
      }

      public boolean isValid(Collection<AYTO_Pair> constellation) {
         return options.isValid(constellation);
      }

   }

   private static List<AYTO_Result> sum(List<CalculationOptions> calcOptions,
         List<List<Pair<Event, AYTO_Result>>> results) {
      List<AYTO_Result> finalResultList = new ArrayList<>();
      int totalConstellations = 0;
      for (int i = 0, l = calcOptions.size(); i < l; i++) {
         CalculationOptions opt = calcOptions.get(i);
         AYTO_Result curResult = new AYTO_Result(opt);
         finalResultList.add(curResult);
         for (List<Pair<Event, AYTO_Result>> entryList : results) {
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
