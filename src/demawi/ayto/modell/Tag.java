package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import demawi.ayto.events.Event;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.events.NewPerson;

public class Tag {

   private List<Event> events = new ArrayList<>();

   // TODO: Die nachfolgenden Attribute können entfernt werden, wenn auf Eventverarbeitung umgestellt wurde..
   public List<MatchBoxResult> boxResults = new ArrayList<>();
   public MatchingNight matchingNight;
   public Map<Pair, Boolean> implicits = new LinkedHashMap<>();
   public Person newExtraPerson;

   private Tag() {
   }

   public static Tag create() {
      return new Tag();
   }

   public Tag implicitDerived(boolean b, Pair pair) {
      implicits.put(pair, b);
      return this;
   }

   public Tag addNew(Person person) {
      this.newExtraPerson = person;
      events.add(new NewPerson(person));
      return this;
   }

   public Tag matchBox(Pair matchingPair, Boolean perfectMatch) {
      if (matchingPair != null) {
         MatchBoxResult matchBoxResult = new MatchBoxResult(matchingPair, perfectMatch);
         boxResults.add(matchBoxResult);
         events.add(matchBoxResult);
      }
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
}
