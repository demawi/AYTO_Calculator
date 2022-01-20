package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AYTO_Data {

  public Set<Pair> noMatches = new HashSet<Pair>();
  public Set<Pair> matches = new HashSet<Pair>();
  public List<MatchingNight> matchingNights = new ArrayList<>();
  public List<Frau> frauen = new ArrayList<>();
  public List<Mann> maenner = new ArrayList<>();

  public void add(MatchingNight matchingNight) {
    matchingNights.add(matchingNight);
    for (Pair pair : matchingNight.constellation) {
      if (!frauen.contains(pair.frau)) {
        frauen.add(pair.frau);
      }
      if (!maenner.contains(pair.mann)) {
        maenner.add(pair.mann);
      }
    }
  }

  public void add(boolean b, Pair pair) {
    if (b) {
      matches.add(pair);
    }
    else {
      noMatches.add(pair);
    }
  }

}
