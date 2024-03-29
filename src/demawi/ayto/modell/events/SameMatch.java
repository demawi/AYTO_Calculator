package demawi.ayto.modell.events;

import java.util.Collection;

import demawi.ayto.modell.AYTO_Pair;
import demawi.ayto.modell.Person;
import demawi.ayto.permutation.Mark;

public class SameMatch
      implements Event, PairInterpreter {

   private final Person first;
   private final Person second;

   public SameMatch(Person first, Person second) {
      this.first = first;
      this.second = second;
   }

   @Override
   public boolean isValid(Collection<AYTO_Pair> constellation, PairInterpreter lookup) {
      return true;
   }

   public Person getFirst() {
      return first;
   }

   public Person getSecond() {
      return second;
   }

   public boolean needsRecalculation() {
      return true;
   }

   public void lookup(Person person) {
      if (person.equals(first)) {
         String extraMark = "/" + second.getNamePlusMark();
         if (person.marks.stream()
               .noneMatch(m -> extraMark.equals(m.toString()))) {
            person.mark(new Mark(extraMark));
         }
      }
   }

   public AYTO_Pair lookup(AYTO_Pair pair) {
      if (pair.mann.equals(second)) {
         return AYTO_Pair.pair(first, pair.frau);
      }
      else if (pair.frau.equals(second)) {
         return AYTO_Pair.pair(pair.mann, first);
      }
      return pair;
   }

}
