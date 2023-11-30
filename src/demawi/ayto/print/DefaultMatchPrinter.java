package demawi.ayto.print;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import demawi.ayto.modell.*;
import demawi.ayto.modell.events.*;
import demawi.ayto.util.Language;

public class DefaultMatchPrinter
      extends MatchPrinter {

   /**
    * For each event but MatchingNight. If the Matching Night has taken place, it's the last event but every day prints out the table anyway.
    */
   private static final boolean PRINT_TABLE_AFTER_EACH_EVENT = false;
   private static final boolean WITH_CALCULATON_SUMMARY = true;

   private final Map<Integer, List<AYTO_Result>> results = new LinkedHashMap<>();

   public DefaultMatchPrinter(SeasonData data) {
      super(data);
   }

   /**
    * Calculate and cache day-results.
    */
   public List<AYTO_Result> getResults(int tagNr) {
      List<AYTO_Result> dayResults = results.get(tagNr);
      if (dayResults == null) {
         Day tag = data.getTag(tagNr);
         dayResults = calculate(tagNr, 0, tagNr, tag.getEvents()
               .size());
         results.put(tagNr, dayResults);
      }
      return dayResults;
   }

   public Map<Integer, List<AYTO_Result>> getAllCalculatedResults() {
      return results;
   }

   @Override
   public void printLastDayResults(int tagNr) {
      // Pre-calculate everything
      Day tag = data.getTag(tagNr);
      List<AYTO_Result> results = getResults(tagNr);

      printCalculationSummary(results.get(0));

      for (int eventCount = 1, l = tag.getEvents()
            .size(); eventCount <= l; eventCount++) {
         printEvent(tagNr, eventCount, results.get(eventCount - 1), results.get(eventCount));
      }
      if (tag.getMatchingNight() == null) {
         print("");
         print("Es hat keine Matching Night stattgefunden!", "No matchup ceremony took place!");
      }
      printPossibilitiesAsTable(results.get(results.size() - 1));
   }

   private void printEvent(int tagNr, int eventCount, AYTO_Result beforeResult, AYTO_Result afterResult) {
      Event event = beforeResult.getData()
            .getTag(tagNr)
            .getEvent(eventCount);
      if (event instanceof NewPerson) {
         printNewPerson((NewPerson) event, tagNr, eventCount, beforeResult, afterResult);
         if (PRINT_TABLE_AFTER_EACH_EVENT)
            printPossibilitiesAsTable(afterResult);
      }
      else if (event instanceof MatchBoxResult) {
         printMatchBox((MatchBoxResult) event, tagNr, eventCount, beforeResult, afterResult);
         if (PRINT_TABLE_AFTER_EACH_EVENT)
            printPossibilitiesAsTable(afterResult);
      }
      else if (event instanceof MatchingNight) {
         printMatchNight((MatchingNight) event, tagNr, eventCount, beforeResult, afterResult);
      }
      else if (event instanceof SameMatch) {
         printSameMatch((SameMatch) event, tagNr, eventCount, beforeResult, afterResult);
         if (PRINT_TABLE_AFTER_EACH_EVENT)
            printPossibilitiesAsTable(afterResult);
      }
      else {
         throw new IllegalArgumentException("Event-Typ '" + event.getClass()
               .getSimpleName() + "'kann noch nicht verarbeitet werden!");
      }
   }

   private void printSameMatch(SameMatch event, int tagNr, int eventCount, AYTO_Result previousResult,
         AYTO_Result afterResult) {
      print("Ereignis: %s hat %s rausgeworfen!", "Event: %s kicked %s out!", event.getFirst()
            .getNameWithoutMark(), event.getSecond()
            .getNameWithoutMark());
      printCalculationSummary(afterResult);
      printCombinationChange(previousResult.getPossibleConstellationSize(), afterResult.getPossibleConstellationSize());
   }

   private void printNewPerson(NewPerson event, int tagNr, int eventCount, AYTO_Result previousResult,
         AYTO_Result afterResult) {
      if (event.person instanceof Woman) {
         print("Eine Frau ist hinzugekommen: %s", "A woman joined: %s", event.person.getName());
      }
      else {
         print("Ein Mann ist hinzugekommen: %s", "A man joined: %s", event.person.getName());
      }
      checkPrintImplicit(event);
      printCalculationSummary(afterResult);
      printCombinationChange(previousResult.getPossibleConstellationSize(), afterResult.getPossibleConstellationSize());
   }

   private void printMatchBox(MatchBoxResult event, int tagNr, int eventCount, AYTO_Result previousResult,
         AYTO_Result afterResult) {
      AYTO_Pair boxPair = event.pair;
      Boolean boxResult = event.result;
      print("");
      print("MatchBox: " + boxPair + (boxResult == null ? " verkauft." : "...")
                  + " (Wahrscheinlichkeit für das Ausgang des Ergebnisses)",
            "TrueBooth: " + boxPair + (boxResult == null ? " sold." : "...") + " (Probability of outcome)");
      String yesMarker = "";
      String noMarker = "";
      int yesCombinations = previousResult.getPossibleCount(boxPair);
      int noCombinations = previousResult.getPossibleConstellationSize() - previousResult.getPossibleCount(boxPair);
      int newCombinationCount = 0;
      if (boxResult != null) {
         if (boxResult) {
            yesMarker = " <==";
            newCombinationCount = yesCombinations;
         }
         else {
            noMarker = " <==";
            newCombinationCount = noCombinations;
         }
      }
      print("Ja   => %s", "Yes => %s",
            Formatter.prozent(previousResult.getPossibleCount(boxPair), previousResult.getPossibleConstellationSize())
                  + "% [" + yesCombinations + "]" + yesMarker);
      print("Nein => %s", "No  => %s",
            Formatter.prozent(previousResult.getPossibleConstellationSize() - previousResult.getPossibleCount(boxPair),
                  previousResult.getPossibleConstellationSize()) + "% [" + noCombinations + "]" + noMarker);
      if (boxResult != null) {
         printCombinationChange(previousResult.getPossibleConstellationSize(), newCombinationCount);
         printCalculationSummary(afterResult);
         checkPrintImplicit(event);
         if (!event.implicits.isEmpty()) {
            printCombinationChange(newCombinationCount, afterResult.getPossibleConstellationSize());
         }
      }
   }

   private void checkPrintImplicit(MatchBoxResult event) {
      if (event.isTrue()) {
         if (event.pairWeitererAuszug == null) {
            print("Kein weiteres Paar zieht aus!", "No other couple moves out!");
         }
         else {
            print("Zusätzlich zieht auch das Perfect Match %s aus!", "Additionally the perfect match %s moves out!",
                  "" + event.pairWeitererAuszug);
         }
      }

      for (MatchBoxResult implicit : event.implicits) {
         if (!implicit.result) {
            print("Implizite Annahme aufgrund Nicht-Auszug: %s => No Match!",
                  "Implicit assumption because no one else moves out: %s => No Match!", "" + implicit.pair);
         }
      }
   }

   private void checkPrintImplicit(NewPerson event) {
      for (MatchBoxResult implicit : event.implicits) {
         if (!implicit.result) {
            print(
                  "Implizite Annahme für die neue Person durch vorherige Auszüge: " + implicit.pair + " => No Match!");
         }
      }
   }

   private void printMatchNight(MatchingNight event, int tagNr, int eventCount, AYTO_Result previousResult,
         AYTO_Result afterResult) {
      printLightChances(previousResult, event.constellation, event.lights);
      printCalculationSummary(afterResult);
   }

   private void printCalculationSummary(AYTO_Result result) {
      if (!WITH_CALCULATON_SUMMARY)
         return;
      print("");
      breakLine();
      SeasonData data = result.getData();
      CalculationOptions calcOptions = result.getCalcOptions();
      if (lang == Language.DE) {
         print("-- Berechne " + data.name + " - Nacht " + calcOptions.getTimepoint()
               .getDayNr() + (
               calcOptions.getAnzahlNeuePersonen() > 0 ?
                     " inkl. " + calcOptions.getAnzahlNeuePersonen() + " neuer Person(en)" : "") + (
               calcOptions.getAnzahlMatchBoxen() > 0 ?
                     " inkl. " + calcOptions.getAnzahlMatchBoxen() + " Matchbox(en)" : "")
               + (calcOptions.isMitMatchingNight() ? " inkl. Matchingnight" : "") + "..." + " [Ereignis#"
               + calcOptions.getTimepoint()
               .getEventCount() + "]");
      }
      else {
         print("-- Calculate " + data.name + " - Night " + calcOptions.getTimepoint()
               .getDayNr() + (
               calcOptions.getAnzahlNeuePersonen() > 0 ?
                     " with " + calcOptions.getAnzahlNeuePersonen() + " new person(s)" : "") + (
               calcOptions.getAnzahlMatchBoxen() > 0 ?
                     " with " + calcOptions.getAnzahlMatchBoxen() + " true booth(s)" : "")
               + (calcOptions.isMitMatchingNight() ? " with matchup ceremony" : "") + "..." + " [Event#"
               + calcOptions.getTimepoint()
               .getEventCount() + "]");
      }

      String fortschritt = " Fortschritt: " + Formatter.prozent(
            Math.pow(1.0 * result.notPossible / (result.totalConstellations - 1), 100));
      if (lang == Language.DE) {
         print("-- Combinations: " + result.totalConstellations + " Possible: " + result.possible + " Not possible: "
               + result.notPossible);
      }
      else {
         print("-- Combinations: " + result.totalConstellations + " Possible: " + result.possible + " Not possible: "
               + result.notPossible);
      }
      breakLine2();
   }

   public static void main(String[] args) {
      System.out.println(Math.pow(0.9889, 100));
   }

   public void breakLine() {
      print(
            "==================================================================================================================================================");
   }

   public void breakLine2() {
      print(
            "--------------------------------------------------------------------------------------------------------------------------------------------------");
   }

}
