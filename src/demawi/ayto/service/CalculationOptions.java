package demawi.ayto.service;

import java.util.Collection;
import java.util.List;

import demawi.ayto.events.ConstellationValidation;
import demawi.ayto.events.Event;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.events.NewPerson;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.AYTO_Pair;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.modell.Tag;
import demawi.ayto.permutation.AYTO_Permutator;

public class CalculationOptions
      implements PermutationOptions, ConstellationValidation {

   private final StaffelData data;
   private final int tagNr;
   private final int eventCount;

   // abgeleitete Informationen aus den Events
   private final int anzahlNeuerPersonen;
   private final int anzahlMatchBoxen;
   private final boolean mitMatchingNight;

   public CalculationOptions(StaffelData data, int tagNr, int eventCount) {
      this.data = data;
      this.tagNr = tagNr;
      this.eventCount = eventCount;

      // lookup for anzahlNeuerPersonen, anzahlMatchBoxen, mitMatchingNight
      int matchBoxCount = 0;
      int matchingNightCount = 0;
      int neuePersonenCount = 0;
      List<Event> events = getTag().getEvents();
      for (int i = 0; i < eventCount; i++) {
         Event evt = events.get(i);
         if (evt instanceof MatchBoxResult) {
            matchBoxCount++;
         }
         else if (evt instanceof MatchingNight) {
            matchingNightCount++;
         }
         else if (evt instanceof NewPerson) {
            neuePersonenCount++;
         }
      }
      anzahlMatchBoxen = matchBoxCount;
      mitMatchingNight = matchingNightCount > 0;
      anzahlNeuerPersonen = neuePersonenCount;
   }

   public StaffelData getData() {
      return data;
   }

   public int getAnzahlMatchBoxen() {
      return anzahlMatchBoxen;
   }

   public int getAnzahlNeuePersonen() {
      return anzahlNeuerPersonen;
   }

   public boolean isMitMatchingNight() {
      return mitMatchingNight;
   }

   public int getTagNr() {
      return tagNr;
   }

   public int getEventCount() {
      return eventCount;
   }

   public Event getEvent() {
      if(eventCount == 0) return null;
      return getTag().getEvent(eventCount);
   }

   public Tag getTag() {
      return data.getTag(tagNr);
   }

   public List<Frau> getFrauen() {
      return data.getFrauen(tagNr, eventCount);
   }

   public List<Mann> getMaenner() {
      return data.getMaenner(tagNr, eventCount);
   }

   public MatchingNight getMatchingNight() {
      if (tagNr == 0)
         return null;
      return getTag().getMatchingNight();
   }

   public AYTO_Permutator.ZUSATZTYPE getZusatztype() {
      return data.getZusatztype();
   }

   public List<Event> getEvents() {
      return getData().getAllEventsTill(tagNr, eventCount);
   }

   public boolean isValid(Collection<AYTO_Pair> constellation) {
      for (Event event : getEvents()) {
         if (!event.isValid(constellation)) {
            return false;
         }
      }
      return true;
   }
}
