package demawi.ayto.events;

import java.util.Collection;

import demawi.ayto.modell.Pair;

public class MatchBoxResult
      extends EventWithImplicits
      implements Event {

   public final Pair pair;
   public final Boolean result;
   public Pair pairWeitererAuszug;

   public MatchBoxResult(Pair pair, Boolean result) {
      this.pair = pair;
      this.result = result;
   }

   public MatchBoxResult(Pair pair, Boolean result, Pair pairWeitererAuszug) {
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
   public boolean isValid(Collection<Pair> constellation) {
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
