package demawi.ayto.events;

import demawi.ayto.modell.Pair;

public class MatchBoxResult implements Event {

   public final Pair pair;
   public final Boolean result;

   public MatchBoxResult(Pair pair, Boolean result) {
      this.pair = pair;
      this.result = result;
   }
}
