package demawi.ayto.modell.events;

import demawi.ayto.modell.AYTO_Pair;
import demawi.ayto.modell.Person;

/**
 * Can be used to rewrite user-given pairs. E.g. for the SameMatch-Event:
 * all previous pairs have to be re-interpreted.
 * <p>
 * A PairInterpreter-Event has to be: needsRecalculation=true
 */
public interface PairInterpreter {

   default AYTO_Pair lookup(AYTO_Pair pair) {
      return pair;
   }

   default void lookup(Person pair) {
   }

   /**
    * Returns the default nothing has changed lookup.
    */
   static PairInterpreter create() {
      return new PairInterpreter() {
      };
   }

}
