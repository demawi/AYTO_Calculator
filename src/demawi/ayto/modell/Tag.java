package demawi.ayto.modell;

public class Tag {

   public Pair matchingPair;
   public Boolean perfectMatch;
   public MatchingNight matchingNight;

   public Tag(Pair matchingPair, Boolean perfectMatch, MatchingNight matchingNight) {
      this.matchingPair = matchingPair;
      this.perfectMatch = perfectMatch;
      this.matchingNight = matchingNight;
   }

}
