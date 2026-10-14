package demawi.ayto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import demawi.ayto.modell.events.ConstellationValidation;
import demawi.ayto.modell.events.Event;
import demawi.ayto.modell.events.PairInterpreter;
import demawi.ayto.modell.*;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.Formatter;
import demawi.ayto.util.Pair;

public class MatchCalculator {

   private final List<CalculationOptions> calcOptions = new ArrayList<>();

   public MatchCalculator(SeasonData data, int fromTag, int fromEventCount, int toTag, int toEventCount) {
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
            calcOptions.add(new CalculationOptions(data, new Timepoint(curTag, curEvent)));
         }
      }
   }

   /**
    * Calculates all results.
    */
   public List<AYTO_Result> calculate(Consumer<String> out) {
      return createSafeCalculationOptionsLists().stream()
            .flatMap(opts -> calculateSafe(opts, out).stream())
            .collect(Collectors.toList());
   }

   /**
    * This method ensures that recalculation events (like NewPerson/SameMatch) are always in the first place of a CalculationOptions-List.
    */
   private List<List<CalculationOptions>> createSafeCalculationOptionsLists() {
      List<List<CalculationOptions>> result = new ArrayList<>();
      List<CalculationOptions> curOptions = new ArrayList<>();
      result.add(curOptions);
      for (CalculationOptions opts : calcOptions) {
         Event evt = opts.getEvent();
         if (evt != null && evt.needsRecalculation()) {
            curOptions = new ArrayList<>();
            result.add(curOptions);
         }
         curOptions.add(opts);
      }
      return result;
   }

   /**
    * Precondition: NeedRecalculation-Events (like NewPerson/SameMatch) have to be the first event.
    * Appropriate cuts must be made beforehand (that's why the method is called "safe", only safe constellations regarding the preconditions are allowed.)
    */
   private static List<AYTO_Result> calculateSafe(List<CalculationOptions> calcOptions, Consumer<String> out) {
      long start = System.currentTimeMillis();

      CalculationOptions calcPrimaryOptions = calcOptions.get(0);
      SeasonData data = calcPrimaryOptions.getSeasonData();
      data.ensureDataIsClosed();
      PairInterpreter lookup = calcPrimaryOptions.getLookup();
      for(Person mann : calcPrimaryOptions.getMaenner()) {
         lookup.lookup(mann);
      }
      for(Person frau : calcPrimaryOptions.getFrauen()) {
         lookup.lookup(frau);
      }
      AYTO_Permutator<Person, Person, AYTO_Pair> permutator = AYTO_Permutator.create(calcPrimaryOptions.getFrauen(),
            calcPrimaryOptions.getMaenner(), calcPrimaryOptions.getPermMode(), AYTO_Pair::pair);

      List<List<Pair<ConstellationValidation, AYTO_Result>>> results =
            java.util.Collections.synchronizedList(new ArrayList<>());
      ProgressReporter progress = new ProgressReporter(out, start, permutator.estimateConstellations(),
            permutator.hasExactConstellationEstimate());
      permutator.permutate(() -> {
         List<Pair<ConstellationValidation, AYTO_Result>> eventResults = new ArrayList<>();
         results.add(eventResults);
         for (CalculationOptions opt : calcOptions) {
            if (eventResults.size() == 0) {
               // Die erste Validierung ist alles von Anfang an bis zu diesem Ereignis
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
                     .isValid(constellation, lookup)) {
                  entry.getSecond()
                        .addResult(constellation, true, lookup);
               }
               else {
                  break;
               }
            }
            eventResults.get(0)
                  .getSecond()
                  .fixTotalPossibilities(totalConstellations.incrementAndGet());
            long completed = progress.increment();
            progress.report(completed);
         };
      });
      progress.reportFinal();

      if (out != null) {
         out.accept(" Calculation time: " + Formatter.minSecs(System.currentTimeMillis() - start));
      }

      return sum(calcOptions, results);
   }

   private static List<AYTO_Result> sum(List<CalculationOptions> calcOptions,
         List<List<Pair<ConstellationValidation, AYTO_Result>>> results) {
      List<AYTO_Result> finalResultList = new ArrayList<>();
      long totalConstellations = 0;
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

   private static final class ProgressReporter {
      private static final long REPORT_INTERVAL_MILLIS = 10_000;

      private final Consumer<String> out;
      private final long start;
      private final long expectedTotal;
      private final boolean exactTotal;
      private final AtomicLong generated = new AtomicLong();
      private final AtomicLong nextReport = new AtomicLong();

      private ProgressReporter(Consumer<String> out, long start, long expectedTotal, boolean exactTotal) {
         this.out = out;
         this.start = start;
         this.expectedTotal = expectedTotal;
         this.exactTotal = exactTotal;
         nextReport.set(start + REPORT_INTERVAL_MILLIS);
      }

      private long increment() {
         return generated.incrementAndGet();
      }

      private void report(long current) {
         report(current, false);
      }

      private void reportFinal() {
         report(generated.get(), true);
      }

      private void report(long current, boolean force) {
         if (out == null) {
            return;
         }
         long now = System.currentTimeMillis();
         long reportAt = nextReport.get();
         if (!force && (now < reportAt || !nextReport.compareAndSet(reportAt, now + REPORT_INTERVAL_MILLIS))) {
            return;
         }
         long elapsed = now - start;
         long perSecond = elapsed == 0 ? 0 : current * 1000 / elapsed;
         String estimateText = "Schätzung nicht verfügbar";
         String etaText = "ETA nicht verfügbar";
         if (expectedTotal > 0) {
            double percent = Math.min(100d, current * 100d / expectedTotal);
            estimateText = exactTotal
                  ? String.format("%.2f%% (Gesamtzahl: %d)", percent, expectedTotal)
                  : String.format("%.2f%% (obere Schranke: ca. %d)", percent, expectedTotal);
            if (perSecond > 0 && current < expectedTotal) {
               String etaLabel = exactTotal ? "Voraussichtliche Restzeit " : "Worst-Case-ETA ";
               etaText = etaLabel + Formatter.minSecs((long) ((expectedTotal - current) * 1000d / perSecond));
            }
         }
         out.accept(" Progress: " + current + " Konstellationen (" + estimateText + "), "
               + perSecond + "/s, " + etaText + ", Laufzeit " + Formatter.minSecs(elapsed));
      }
   }

}
