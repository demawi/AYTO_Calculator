package demawi.ayto.print;

import java.util.List;
import java.util.function.Consumer;

import demawi.ayto.events.Event;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.events.NewPerson;
import demawi.ayto.modell.AYTO_Pair;
import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.modell.Tag;
import demawi.ayto.service.CalculationOptions;

public class MultiMatchPrinter
      extends MatchPrinter {

   public static boolean withPrintCalculationFake = true;

   @Override
   public void printDayResults(StaffelData data, int tagNr) {
      // Pre-calculate everything
      Tag tag = data.getTag(tagNr);
      List<AYTO_Result> results = calculateMulti(data, tagNr, 0, tagNr, tag.getEvents()
            .size());

      printCalculationFake(results.get(0));

      for (int eventCount = 1, l = tag.getEvents()
            .size(); eventCount <= l; eventCount++) {
         printEvent(tagNr, eventCount, results.get(eventCount - 1), results.get(eventCount));
      }
      if (tag.getMatchingNight() == null) {
         out.accept("");
         out.accept("Es hat keine Matching Night stattgefunden!");
      }
      printPossibilitiesAsTable(results.get(results.size() - 1));
   }

   private void printEvent(int tagNr, int eventCount, AYTO_Result beforeResult, AYTO_Result afterResult) {
      Event event = beforeResult.getData()
            .getTag(tagNr)
            .getEvent(eventCount);
      if (event instanceof NewPerson) {
         printNewPerson((NewPerson) event, tagNr, eventCount, beforeResult, afterResult);
      }
      else if (event instanceof MatchBoxResult) {
         printMatchBox((MatchBoxResult) event, tagNr, eventCount, beforeResult, afterResult);
      }
      else if (event instanceof MatchingNight) {
         printMatchNight((MatchingNight) event, tagNr, eventCount, beforeResult, afterResult);
      }
      else {
         throw new IllegalArgumentException("Event-Klasse kann noch nicht verarbeitet werden: " + event.getClass()
               .getSimpleName());
      }
   }

   private void printNewPerson(NewPerson event, int tagNr, int eventCount, AYTO_Result previousResult,
         AYTO_Result afterResult) {
      if (event.person instanceof Frau) {
         out.accept("Eine Frau ist hinzugekommen: " + event.person.getName()
               + ". Die Anzahl der Kombinationen erhöht sich damit!");
      }
      else {
         out.accept("Ein Mann ist hinzugekommen: " + event.person.getName()
               + ". Die Anzahl der Kombinationen erhöht sich damit!");
      }
      checkPrintImplicit(event);
      printCalculationFake(afterResult);
      out.accept(
            "Die Anzahl der Kombinationen hat sich verändert: " + previousResult.getPossibleConstellationSize() + " => "
                  + afterResult.getPossibleConstellationSize());
   }

   private void printMatchBox(MatchBoxResult event, int tagNr, int eventCount, AYTO_Result previousResult,
         AYTO_Result afterResult) {
      AYTO_Pair boxPair = event.pair;
      Boolean boxResult = event.result;
      out.accept("");
      out.accept("MatchBox: " + boxPair + (boxResult == null ? " verkauft." : "...")
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
      out.accept("Ja   => " + Formatter.prozent(previousResult.getPossibleCount(boxPair),
            previousResult.getPossibleConstellationSize()) + "% [" + yesCombinations + "]" + yesMarker);
      out.accept("Nein => " + Formatter.prozent(
            previousResult.getPossibleConstellationSize() - previousResult.getPossibleCount(boxPair),
            previousResult.getPossibleConstellationSize()) + "% [" + noCombinations + "]" + noMarker);
      if (boxResult != null) {
         out.accept("Die Kombinationen reduzieren sich: " + previousResult.getPossibleConstellationSize() + " => "
               + newCombinationCount);
         printCalculationFake(afterResult);
         checkPrintImplicit(event);
         if (!event.implicits.isEmpty()) {
            out.accept("Die Kombinationen reduzieren sich: " + newCombinationCount + " => "
                  + afterResult.getPossibleConstellationSize());
         }
      }
   }

   private void checkPrintImplicit(MatchBoxResult event) {
      if (event.isTrue()) {
         if (event.pairWeitererAuszug == null) {
            out.accept("Kein weiteres Paar zieht aus!");
         }
         else {
            out.accept("Zusätzlich zieht auch das Perfect Match " + event.pairWeitererAuszug + " aus!");
         }
      }

      for (MatchBoxResult implicit : event.implicits) {
         if (!implicit.result) {
            out.accept("Implizite Annahme aufgrund Nicht-Auszug: " + implicit.pair + " => No Match!");
         }
      }
   }

   private void checkPrintImplicit(NewPerson event) {
      for (MatchBoxResult implicit : event.implicits) {
         if (!implicit.result) {
            out.accept(
                  "Implizite Annahme für die neue Person durch vorherige Auszüge: " + implicit.pair + " => No Match!");
         }
      }
   }

   private void printMatchNight(MatchingNight event, int tagNr, int eventCount, AYTO_Result previousResult,
         AYTO_Result afterResult) {
      printLightChances(previousResult, event.constellation, event.lights);
      printCalculationFake(afterResult);
   }

   private void printCalculationFake(AYTO_Result result) {
      if (!withPrintCalculationFake)
         return;
      out.accept("");
      breakLine(out);
      StaffelData data = result.getData();
      CalculationOptions calcOptions = result.getCalcOptions();
      out.accept("-- Berechne " + data.name + " - Nacht " + calcOptions.getTagNr() + (
            calcOptions.getAnzahlNeuePersonen() > 0 ?
                  " inkl. " + calcOptions.getAnzahlNeuePersonen() + " neuer Person(en)" : "") + (
            calcOptions.getAnzahlMatchBoxen() > 0 ?
                  " inkl. " + calcOptions.getAnzahlMatchBoxen() + " Matchbox(en)" : "")
            + (calcOptions.isMitMatchingNight() ? " inkl. Matchingnight" : "") + "..." + " [Ereignis#"
            + calcOptions.getEventCount() + "]");
      out.accept("-- Combinations: " + result.totalConstellations + " Possible: " + result.possible + " Not possible: "
            + result.notPossible);
      breakLine2(out);
   }

   public void breakLine(Consumer<String> out) {
      out.accept(
            "==================================================================================================================================================");
   }

   public void breakLine2(Consumer<String> out) {
      out.accept(
            "--------------------------------------------------------------------------------------------------------------------------------------------------");
   }

}
