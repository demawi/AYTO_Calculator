package demawi.ayto.events;

import demawi.ayto.modell.Pair;

public class MatchBoxResult implements Event {

   public Pair pair;
   public Boolean result;

   public MatchBoxResult(Pair pair, Boolean result) {
      this.pair = pair;
      this.result = result;
   }
}
