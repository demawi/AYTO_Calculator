package demawi.ayto.print;

import java.util.List;

import demawi.ayto.events.Event;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.events.NewPerson;
import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.Tag;

public class EventbasedMatchPrinter
      extends MatchPrinter {

   @Override
   public void printDayResults(AYTO_Data data, int tagNr) {
      AYTO_Result result = calculateSingle(data, tagNr - 1, Integer.MAX_VALUE, true);
      Tag tag = data.getTag(tagNr);
      List<Event> events;
      for (Event event : tag.getEvents()) {
         if (event instanceof NewPerson) {
            result = printNewPerson((NewPerson) event, result);
         }
         else if (event instanceof MatchBoxResult) {
            result = printMatchBox((MatchBoxResult) event, result);
         }
         else if (event instanceof MatchingNight) {
            result = printMatchNight((MatchingNight) event, result);
         }
         else {
            throw new IllegalArgumentException("Event-Klasse kann noch nicht verarbeitet werden: " + event.getClass()
                  .getSimpleName());
         }
      }
   }

   private AYTO_Result printMatchNight(MatchingNight event, AYTO_Result result) {
      return null;
   }

   private AYTO_Result printMatchBox(MatchBoxResult event, AYTO_Result result) {
      return null;
   }

   private AYTO_Result printNewPerson(NewPerson event, AYTO_Result result) {
      return null;
   }

}
