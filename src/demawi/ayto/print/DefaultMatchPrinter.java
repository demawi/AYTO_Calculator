package demawi.ayto.print;

import java.util.List;

import demawi.ayto.modell.*;
import demawi.ayto.modell.events.*;

public class DefaultMatchPrinter
      extends MatchPrinter {

   /**
    * For each event but MatchingNight. If the Matching Night has taken place, it's the last event but every day prints out the table anyway.
    */
   private static final boolean PRINT_TABLE_AFTER_EACH_EVENT = false;
   private static final boolean WITH_CALCULATON_SUMMARY = true;

   @Override
   public void printLastDayResults(SeasonData data, int tagNr) {
      // Pre-calculate everything
      Day tag = data.getTag(tagNr);
      List<AYTO_Result> results = calculate(data, tagNr, 0, tagNr, tag.getEvents()
            .size());

      printCalculationSummary(results.get(0));

      for (int eventCount = 1, l = tag.getEvents()
            .size(); eventCount <= l; eventCount++) {
         printEvent(tagNr, eventCount, results.get(eventCount - 1), results.get(eventCount));
      }
      if (tag.getMatchingNight() == null) {
         print("");
         print("Es hat keine Matching Night stattgefunden!");
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
      print("Ereignis: " + event.getFirst()
            .getNameWithoutMark() + " hat " + event.getSecond()
            .getNameWithoutMark() + " rausgeworfen!");
      printCalculationSummary(afterResult);
      printCombinationChange(previousResult.getPossibleConstellationSize(), afterResult.getPossibleConstellationSize());
   }

   private void printNewPerson(NewPerson event, int tagNr, int eventCount, AYTO_Result previousResult,
         AYTO_Result afterResult) {
      if (event.person instanceof Woman) {
         print("Eine Frau ist hinzugekommen: " + event.person.getName());
      }
      else {
         print("Ein Mann ist hinzugekommen: " + event.person.getName());
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
            + " (Wahrscheinlichkeit für das Ausgang des Ergebnisses)");
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
      print("Ja   => " + Formatter.prozent(previousResult.getPossibleCount(boxPair),
            previousResult.getPossibleConstellationSize()) + "% [" + yesCombinations + "]" + yesMarker);
      print("Nein => " + Formatter.prozent(
            previousResult.getPossibleConstellationSize() - previousResult.getPossibleCount(boxPair),
            previousResult.getPossibleConstellationSize()) + "% [" + noCombinations + "]" + noMarker);
      if (boxResult != null) {
         print("Die Kombinationen reduzieren sich: " + previousResult.getPossibleConstellationSize() + " => "
               + newCombinationCount);
         printCalculationSummary(afterResult);
         checkPrintImplicit(event);
         if (!event.implicits.isEmpty()) {
            print("Die Kombinationen reduzieren sich: " + newCombinationCount + " => "
                  + afterResult.getPossibleConstellationSize());
         }
      }
   }

   private void checkPrintImplicit(MatchBoxResult event) {
      if (event.isTrue()) {
         if (event.pairWeitererAuszug == null) {
            print("Kein weiteres Paar zieht aus!");
         }
         else {
            print("Zusätzlich zieht auch das Perfect Match " + event.pairWeitererAuszug + " aus!");
         }
      }

      for (MatchBoxResult implicit : event.implicits) {
         if (!implicit.result) {
            print("Implizite Annahme aufgrund Nicht-Auszug: " + implicit.pair + " => No Match!");
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

   private void printCombinationChange(int from, int to) {
      if (from < to) {
         print("Die Anzahl der Kombinationen hat sich erhöht: " + from + " => " + to);
      }
      else if (to < from) {
         print("Die Anzahl der Kombinationen hat sich reduziert: " + from + " => " + to);
      }
      else {
         print("Die Anzahl der Kombinationen bleibt unverändert: " + from + " => " + to);
      }
   }

   private void printCalculationSummary(AYTO_Result result) {
      if (!WITH_CALCULATON_SUMMARY)
         return;
      print("");
      breakLine();
      SeasonData data = result.getData();
      CalculationOptions calcOptions = result.getCalcOptions();
      print("-- Berechne " + data.name + " - Nacht " + calcOptions.getTimepoint()
            .getDayNr() + (
            calcOptions.getAnzahlNeuePersonen() > 0 ?
                  " inkl. " + calcOptions.getAnzahlNeuePersonen() + " neuer Person(en)" : "") + (
            calcOptions.getAnzahlMatchBoxen() > 0 ?
                  " inkl. " + calcOptions.getAnzahlMatchBoxen() + " Matchbox(en)" : "")
            + (calcOptions.isMitMatchingNight() ? " inkl. Matchingnight" : "") + "..." + " [Ereignis#"
            + calcOptions.getTimepoint()
            .getEventCount() + "]");
      String fortschritt = " Fortschritt: " + Formatter.prozent(
            Math.pow(1.0 * result.notPossible / (result.totalConstellations - 1), 100));
      print("-- Combinations: " + result.totalConstellations + " Possible: " + result.possible + " Not possible: "
            + result.notPossible);
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
