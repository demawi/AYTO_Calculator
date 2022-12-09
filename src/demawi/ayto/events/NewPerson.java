package demawi.ayto.events;

import demawi.ayto.modell.Person;

public class NewPerson
      implements Event {

   private Person person;

   public NewPerson(Person person) {
      this.person = person;
   }
}
