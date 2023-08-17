package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.List;

import demawi.ayto.events.Event;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.events.NewPerson;

public class Tag {

   private final List<Event> events = new ArrayList<>();
   private MatchingNight matchingNight;
   private int matchingNightCount;

   private Tag(int matchingNightCount) {
      this.matchingNightCount = matchingNightCount;
   }

   public int getMatchingNightCount() {
      return matchingNightCount;
   }

   public static Tag create(int matchingNightCount) {
      return new Tag(matchingNightCount);
   }

   public Tag addNew(Person person) {
      events.add(new NewPerson(person));
      return this;
   }

   public Tag matchBox(AYTO_Pair matchingPair, Boolean perfectMatch) {
      events.add(new MatchBoxResult(matchingPair, perfectMatch));
      return this;
   }

   public Tag matchBox(AYTO_Pair matchingPair, Boolean perfectMatch, AYTO_Pair weitererAuszug) {
      events.add(new MatchBoxResult(matchingPair, perfectMatch, weitererAuszug));
      return this;
   }

   /**
    * Schließt den Tag ab, insofern keine this-Rückgabe.
    */
   public void matchNight(Integer lights, AYTO_Pair... pairs) {
      matchingNight = new MatchingNight(lights, matchingNightCount, pairs);
      events.add(matchingNight);
   }

   public MatchingNight getMatchingNight() {
      return matchingNight;
   }

   public List<Event> getEvents() {
      return events;
   }

   public Event getEvent(int eventNr) {
      return events.get(eventNr - 1);
   }
}
