package demawi.ayto.events;

import java.util.Collection;

import demawi.ayto.modell.Pair;

public class MatchBoxResult
      implements Event {

   public final Pair pair;
   public final Boolean result;

   public MatchBoxResult(Pair pair, Boolean result) {
      this.pair = pair;
      this.result = result;
   }

   /**
    * Gibt true zurück sofern es keinen Widerspruch gibt und
    * die Konstellation somit weiterhin als valide eingestuft
    * werden kann.
    */
   public boolean test(Collection<Pair> constellation) {
      if (result == null)
         return true;
      return result == constellation.contains(pair);
   }
}
