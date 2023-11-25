package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.List;

import demawi.ayto.modell.events.*;

public class Day {

   private final List<Event> events = new ArrayList<>();
   private final int matchingNightCount;
   private MatchingNight matchingNight;

   private Day(int matchingNightCount) {
      this.matchingNightCount = matchingNightCount;
   }

   public int getMatchingNightCount() {
      return matchingNightCount;
   }

   public static Day create(int matchingNightCount) {
      return new Day(matchingNightCount);
   }

   public Day addNew(Person person) {
      events.add(new NewPerson(person));
      return this;
   }

   public Day addNew(Person person, boolean zusatzperson) {
      events.add(new NewPerson(person, zusatzperson));
      return this;
   }

   public Day matchBox(AYTO_Pair matchingPair, Boolean perfectMatch) {
      events.add(new MatchBoxResult(matchingPair, perfectMatch));
      return this;
   }

   public Day matchBox(AYTO_Pair matchingPair, Boolean perfectMatch, AYTO_Pair weitererAuszug) {
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

   public Day sameMatch(Person person, Person person2) {
      events.add(new SameMatch(person, person2));
      return this;
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
