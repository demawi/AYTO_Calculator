package demawi.ayto.events;

import java.util.Collection;

import demawi.ayto.modell.AYTO_Pair;

public class MatchBoxResult
      extends EventWithImplicits
      implements Event {

   public final AYTO_Pair pair;
   public final Boolean result;
   public AYTO_Pair pairWeitererAuszug;

   public MatchBoxResult(AYTO_Pair pair, Boolean result) {
      this.pair = pair;
      this.result = result;
   }

   public MatchBoxResult(AYTO_Pair pair, Boolean result, AYTO_Pair pairWeitererAuszug) {
      this(pair, result);
      assert result; // Wenn es einen weiteren Auszug gibt, muss das erste ein Perfect Match sein
      this.pairWeitererAuszug = pairWeitererAuszug;
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
   public boolean isValid(Collection<AYTO_Pair> constellation) {
      if (!super.isValid(constellation))
         return false;
      if (result == null)
         return true;
      if (pairWeitererAuszug != null && result != constellation.contains(pair)) {
         return false;
      }
      return result == constellation.contains(pair);
   }
}
