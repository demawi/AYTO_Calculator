package demawi.ayto.modell.events;

import demawi.ayto.modell.Person;

public class NewPerson
      extends EventWithImplicits
      implements Event {

   public final Person person;
   public boolean zusatzperson;

   public NewPerson(Person person) {
      this(person, true);
   }

   public NewPerson(Person person, boolean zusatzperson) {
      this.person = person;
      this.zusatzperson = zusatzperson;
   }

   public boolean needsRecalculation() {
      return true;
   }

}
