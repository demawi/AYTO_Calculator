package demawi.ayto.modell.events;

import java.util.Collection;

import demawi.ayto.modell.AYTO_Pair;

public class MatchBoxResult
      extends EventWithImplicits
      implements Event {

   public final AYTO_Pair pair;
   public final Boolean result;
   public AYTO_Pair[] additionalMoveouts;

   public MatchBoxResult(AYTO_Pair pair, Boolean result) {
      this.pair = pair;
      this.result = result;
   }

   public MatchBoxResult(AYTO_Pair pair, Boolean result, AYTO_Pair[] additionalMoveouts) {
      this(pair, result);
      if (!result) { // Wenn es einen weiteren Auszug gibt, muss das erste ein Perfect Match sein
         throw new RuntimeException("Pair has to be a perfect match. Otherwise there aren't any following move outs");
      }
      this.additionalMoveouts = additionalMoveouts;
   }

   public boolean isTrue() {
      return result != null && result;
   }

   public boolean isFalse() {
      return result != null && !result;
   }

   /**
    * Gibt true zurück sofern es keinen Widerspruch gibt und
    * die Konstellation somit weiterhin als valide eingestuft
    * werden kann.
    */
   public boolean isValid(Collection<AYTO_Pair> constellation, PairInterpreter lookup) {
      if (!super.isValid(constellation, lookup))
         return false;
      AYTO_Pair lookupPair = lookup.lookup(pair);
      if (result == null)
         return true;
      if (additionalMoveouts != null && result != constellation.contains(lookupPair)) {
         return false;
      }
      return result == constellation.contains(lookupPair);
   }
}
