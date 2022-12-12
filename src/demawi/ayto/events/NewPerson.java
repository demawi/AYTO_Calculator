package demawi.ayto.events;

import java.util.Collection;

import demawi.ayto.modell.Pair;
import demawi.ayto.modell.Person;

public class NewPerson
      implements Event {

   public final Person person;

   public NewPerson(Person person) {
      this.person = person;
   }

   @Override
   public boolean test(Collection<Pair> constellation) {
      return true;
   }
}
