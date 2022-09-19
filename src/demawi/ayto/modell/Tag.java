package demawi.ayto.modell;

import java.util.LinkedHashMap;
import java.util.Map;

public class Tag {

   public Pair matchingPair;
   public Boolean perfectMatch;
   public MatchingNight matchingNight;
   public Map<Pair, Boolean> implicits = new LinkedHashMap<>();

   public Tag(Pair matchingPair, Boolean perfectMatch, MatchingNight matchingNight) {
      this.matchingPair = matchingPair;
      this.perfectMatch = perfectMatch;
      this.matchingNight = matchingNight;
   }

   public Tag addImplicit(boolean b, Pair pair) {
      implicits.put(pair, b);
      return this;
   }
}
