package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.List;

import demawi.ayto.events.Event;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.events.NewPerson;

public class Tag {

   private List<Event> events = new ArrayList<>();

   // TODO: Die nachfolgenden Attribute können entfernt werden, wenn auf Eventverarbeitung umgestellt wurde..
   public List<MatchBoxResult> boxResults = new ArrayList<>();
   public MatchingNight matchingNight;

   private Tag() {
   }

   public static Tag create() {
      return new Tag();
   }

   public Tag addNew(Person person) {
      events.add(new NewPerson(person));
      return this;
   }

   public Tag matchBox(Pair matchingPair, Boolean perfectMatch) {
      MatchBoxResult matchBoxResult = new MatchBoxResult(matchingPair, perfectMatch);
      boxResults.add(matchBoxResult);
      events.add(matchBoxResult);
      return this;
   }

   public Tag matchBox(Pair matchingPair, Boolean perfectMatch, Pair weitererAuszug) {
      MatchBoxResult matchBoxResult = new MatchBoxResult(matchingPair, perfectMatch, weitererAuszug);
      boxResults.add(matchBoxResult);
      events.add(matchBoxResult);
      return this;
   }

   public Tag matchBox(Boolean perfectMatch, Pair matchingPair) {
      return matchBox(matchingPair, perfectMatch);
   }

   /**
    * Schließt den Tag ab, insofern keine this-Rückgabe.
    */
   public void matchNight(Integer lights, Pair... pairs) {
      matchingNight = new MatchingNight(lights, pairs);
      events.add(matchingNight);
   }

   public List<Event> getEvents() {
      return events;
   }

   public Event getEvent(int eventNr) {
      return events.get(eventNr - 1);
   }
}
