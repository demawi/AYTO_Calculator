package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.Collection;
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

  public void add(int i, Pair... pairs) {
    add(new MatchingNight(i, pairs));
  }

  /**
   * Testet ob die übergebene Paar-Konstellation zu einem Widerspruch führt.
   */
  public boolean test(Collection<Pair> constellation, boolean debug) {
    for (Pair pair : noMatches) {
      if (constellation.contains(pair)) {
        return false;
      }
    }
    for (Pair pair : matches) {
      if (!constellation.contains(pair)) {
        return false;
      }
    }
    for (int i = 0, l = matchingNights.size(); i < l; i++) {
      MatchingNight night = matchingNights.get(i);
      int lights = getLights(constellation, night.constellation);
      if (lights != night.lights) {
        if (debug) {
          System.out.println("Falsch aufgrund von Matching Night Nr. " + (i + 1) + " Erwartete Lichter: " + lights
            + ". Es waren aber: " + night.lights);
        }
        return false;
      }
    }
    return true;
  }

  public int getLights(Collection<Pair> assumptionModell, Collection<Pair> testConstellation) {
    int lights = 0;
    for (Pair pair : testConstellation) {
      if (assumptionModell.contains(pair)) {
        lights++;
      }
    }
    return lights;
  }

}
