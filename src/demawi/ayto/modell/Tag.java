package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Tag {

   public List<MatchBoxResult> boxPairs = new ArrayList<>();
   public MatchingNight matchingNight;
   public Map<Pair, Boolean> implicits = new LinkedHashMap<>();

   public Tag() {
   }

   public static Tag create() {
      return new Tag();
   }

   public Tag(Pair boxPair, Boolean boxResult, MatchingNight matchingNight) {
      matchBox(boxPair, boxResult);
      this.matchingNight = matchingNight;
   }

   public Tag implicit(boolean b, Pair pair) {
      implicits.put(pair, b);
      return this;
   }

   public Tag matchBox(Pair matchingPair, Boolean perfectMatch) {
      if (matchingPair != null) {
         boxPairs.add(new MatchBoxResult(matchingPair, perfectMatch));
      }
      return this;
   }

   public Tag matchNight(int lights, Pair... pairs) {
      this.matchingNight = new MatchingNight(lights, pairs);
      return this;
   }
}
