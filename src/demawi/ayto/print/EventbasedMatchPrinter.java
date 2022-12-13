package demawi.ayto.print;

import java.util.List;

import demawi.ayto.events.Event;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.events.NewPerson;
import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Pair;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.modell.Tag;

public class EventbasedMatchPrinter
      extends MatchPrinter {

   @Override
   public void printDayResults(StaffelData data, int tagNr) {
      data.checkForImplicits(tagNr);
      AYTO_Result result = calculateSingle(data, tagNr, 0);
      Tag tag = data.getTag(tagNr);
      List<Event> events = tag.getEvents();
      for (int i = 0, l = events.size(); i < l; i++) {
         Event event = events.get(i);
         AYTO_Result newResult;
         if (event instanceof NewPerson) {
            newResult = printNewPerson((NewPerson) event, tagNr, i + 1, result);
         }
         else if (event instanceof MatchBoxResult) {
            newResult = printMatchBox((MatchBoxResult) event, tagNr, i + 1, result);
         }
         else if (event instanceof MatchingNight) {
            newResult = printMatchNight((MatchingNight) event, tagNr, i + 1, result);
         }
         else {
            throw new IllegalArgumentException("Event-Klasse kann noch nicht verarbeitet werden: " + event.getClass()
                  .getSimpleName());
         }
         result = newResult;
      }
      if (tag.getMatchingNight() == null) {
         out.accept("");
         out.accept("Es hat keine Matching Night stattgefunden!");
      }
      printPossibilitiesAsTable(result);
   }

   private AYTO_Result printNewPerson(NewPerson event, int tagNr, int eventCount, AYTO_Result previousResult) {
      if (event.person instanceof Frau) {
         out.accept("Eine Frau ist hinzugekommen: " + event.person.getName()
               + ". Die Anzahl der Kombinationen erhöht sich damit!");
      }
      else {
         out.accept("Ein Mann ist hinzugekommen: " + event.person.getName()
               + ". Die Anzahl der Kombinationen erhöht sich damit!");
      }
      checkPrintImplicit(event);
      AYTO_Result newResult = calculateSingle(previousResult.getData(), tagNr, eventCount);
      out.accept(
            "Die Anzahl der Kombinationen hat sich verändert: " + previousResult.getPossibleConstellationSize() + " => "
                  + newResult.getPossibleConstellationSize());
      return newResult;
   }

   private AYTO_Result printMatchBox(MatchBoxResult event, int tagNr, int eventCount, AYTO_Result previousResult) {
      Pair boxPair = event.pair;
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
         AYTO_Result result = calculateSingle(previousResult.getData(), tagNr, eventCount);
         checkPrintImplicit(event);
         if (!event.implicits.isEmpty()) {
            out.accept("Die Kombinationen reduzieren sich: " + newCombinationCount + " => "
                  + result.getPossibleConstellationSize());
         }
         return result;
      }
      // Ansonsten keine Neuberechnung erforderlich
      return previousResult;
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

   private AYTO_Result printMatchNight(MatchingNight event, int tagNr, int eventCount, AYTO_Result previousResult) {
      printLightChances(previousResult, event.constellation, event.lights);
      return calculateSingle(previousResult.getData(), tagNr, eventCount);
   }

}
