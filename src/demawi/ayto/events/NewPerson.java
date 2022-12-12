package demawi.ayto.events;

import demawi.ayto.modell.Person;

public class NewPerson
      extends EventWithImplicits
      implements Event {

   public final Person person;

   public NewPerson(Person person) {
      this.person = person;
   }

}
