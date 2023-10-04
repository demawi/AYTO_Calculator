package demawi.ayto.events;

import java.util.Collection;

import demawi.ayto.modell.AYTO_Pair;
import demawi.ayto.modell.Person;

public class SameMatch
      implements Event {

   private Person first;
   private Person second;

   public SameMatch(Person first, Person second) {
      this.first = first;
      this.second = second;
   }

   @Override
   public boolean isValid(Collection<AYTO_Pair> constellation) {
      return false;
   }
}
