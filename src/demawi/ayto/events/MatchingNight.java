package demawi.ayto.events;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.Pair;

public class MatchingNight
      implements Event {

  public final Integer lights;
  public final Set<Pair> constellation;

  public MatchingNight(Integer lights, Pair... constellation) {
    this(lights, Arrays.asList(constellation));
  }

  public MatchingNight(Integer lights, Collection<Pair> constellation) {
    if (lights != null && (lights < 0 || lights > 10)) {
      throw new RuntimeException("Falsche Anzahl an Lichtern: " + lights);
    }
    if (constellation.size() != 10) {
      throw new RuntimeException("Falsche Anzahl an Paaren: " + constellation.size());
    }
    validatePairs(constellation);
    this.lights = lights;
    this.constellation = new LinkedHashSet<>(constellation);
  }

  public static void validatePairs(Collection<Pair> constellation) {
    Set<Frau> frauen = new LinkedHashSet<>();
    Set<Mann> maenner = new LinkedHashSet<>();
    for (Pair pair : constellation) {
      frauen.add(pair.frau);
      maenner.add(pair.mann);
    }
    if (frauen.size() != 10) {
      throw new RuntimeException("Falsche Anzahl an Frauen: " + frauen.size());
    }
    if (maenner.size() != 10) {
      throw new RuntimeException("Falsche Anzahl an Männern: " + maenner.size());
    }
  }

  public int getLights(Collection<Pair> assumptionModell) {
    return MatchingNight.getLights(assumptionModell, this.constellation);
  }

  private static int getLights(Collection<Pair> assumptionModell, Collection<Pair> testConstellation) {
    int lights = 0;
    for (Pair pair : testConstellation) {
      if (assumptionModell.contains(pair)) {
        lights++;
      }
    }
    return lights;
  }

}
