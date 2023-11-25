package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import demawi.ayto.modell.events.*;
import demawi.ayto.permutation.AYTO_Permutator;

public class CalculationOptions
      implements PermutationOptions, ConstellationValidation {

   private final SeasonData seasonData;
   private final Timepoint timepoint;

   // abgeleitete Informationen aus den Events
   private final int anzahlNeuerPersonen;
   private final int anzahlMatchBoxen;
   private final boolean mitMatchingNight;

   private final List<SameMatch> sameMatches = new ArrayList<>();

   public CalculationOptions(SeasonData seasonData, Timepoint timepoint) {
      this.seasonData = seasonData;
      this.timepoint = timepoint;

      // lookup for anzahlNeuerPersonen, anzahlMatchBoxen, mitMatchingNight
      int matchBoxCount = 0;
      int matchingNightCount = 0;
      int neuePersonenCount = 0;
      List<Event> events = getTag().getEvents();
      for (int i = 0; i < timepoint.getEventCount(); i++) {
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

   public SeasonData getSeasonData() {
      return seasonData;
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

   public Timepoint getTimepoint() {
      return timepoint;
   }

   /**
    * At the beginning of the day we have event=null
    */
   public Event getEvent() {
      if (timepoint.getEventCount() == 0)
         return null;
      return getTag().getEvent(timepoint.getEventCount());
   }

   public Day getTag() {
      return seasonData.getTag(timepoint.getDayNr());
   }

   public List<Person> getFrauen() {
      return seasonData.getFrauen(timepoint.getDayNr(), timepoint.getEventCount());
   }

   public List<Person> getMaenner() {
      return seasonData.getMaenner(timepoint.getDayNr(), timepoint.getEventCount());
   }

   public MatchingNight getMatchingNight() {
      if (timepoint.getDayNr() == 0)
         return null;
      return getTag().getMatchingNight();
   }

   public AYTO_Permutator.MODE getZusatztype() {
      return seasonData.getZusatztype();
   }

   public List<Event> getEvents() {
      return getSeasonData().getAllEventsTill(timepoint.getDayNr(), timepoint.getEventCount());
   }

   public boolean isValid(Collection<AYTO_Pair> constellation, PairInterpreter lookup) {
      for (Event event : getEvents()) {
         if (!event.isValid(constellation, lookup)) {
            return false;
         }
      }
      return true;
   }

   public List<Day> getTageBisher() {
      return seasonData.getTage()
            .subList(0, timepoint.getDayNr());
   }

   public PairInterpreter getLookup() {
      Optional<PairInterpreter> first = getEvents().stream()
            .filter(evt -> evt instanceof PairInterpreter)
            .map(evt -> (PairInterpreter) evt)
            .findFirst();
      return first.orElseGet(PairInterpreter::create);
   }
}
