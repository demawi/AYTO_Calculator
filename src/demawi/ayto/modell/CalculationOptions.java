package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import demawi.ayto.events.*;
import demawi.ayto.permutation.AYTO_Permutator;

public class CalculationOptions
      implements PermutationOptions, ConstellationValidation {

   private final StaffelData data;
   private final Zeitpunkt zeitpunkt;

   // abgeleitete Informationen aus den Events
   private final int anzahlNeuerPersonen;
   private final int anzahlMatchBoxen;
   private final boolean mitMatchingNight;

   private final List<SameMatch> sameMatches = new ArrayList<>();

   public CalculationOptions(StaffelData data, Zeitpunkt zeitpunkt) {
      this.data = data;
      this.zeitpunkt = zeitpunkt;

      // lookup for anzahlNeuerPersonen, anzahlMatchBoxen, mitMatchingNight
      int matchBoxCount = 0;
      int matchingNightCount = 0;
      int neuePersonenCount = 0;
      List<Event> events = getTag().getEvents();
      for (int i = 0; i < zeitpunkt.getEventCount(); i++) {
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

   public Zeitpunkt getZeitpunkt() {
      return zeitpunkt;
   }

   /**
    * At the beginning of the day we have event=null
    */
   public Event getEvent() {
      if (zeitpunkt.getEventCount() == 0)
         return null;
      return getTag().getEvent(zeitpunkt.getEventCount());
   }

   public Tag getTag() {
      return data.getTag(zeitpunkt.getTagNr());
   }

   public List<Person> getFrauen() {
      return data.getFrauen(zeitpunkt.getTagNr(), zeitpunkt.getEventCount());
   }

   public List<Person> getMaenner() {
      return data.getMaenner(zeitpunkt.getTagNr(), zeitpunkt.getEventCount());
   }

   public MatchingNight getMatchingNight() {
      if (zeitpunkt.getTagNr() == 0)
         return null;
      return getTag().getMatchingNight();
   }

   public AYTO_Permutator.ZUSATZTYPE getZusatztype() {
      return data.getZusatztype();
   }

   public List<Event> getEvents() {
      return getData().getAllEventsTill(zeitpunkt.getTagNr(), zeitpunkt.getEventCount());
   }

   public boolean isValid(Collection<AYTO_Pair> constellation, PairInterpreter lookup) {
      for (Event event : getEvents()) {
         if (!event.isValid(constellation, lookup)) {
            return false;
         }
      }
      return true;
   }

   public List<Tag> getTageBisher() {
      return data.getTage()
            .subList(0, zeitpunkt.getTagNr());
   }

   public PairInterpreter getLookup() {
      Optional<PairInterpreter> first = getEvents().stream()
            .filter(evt -> evt instanceof PairInterpreter)
            .map(evt -> (PairInterpreter) evt)
            .findFirst();
      return first.orElseGet(PairInterpreter::create);
   }
}
