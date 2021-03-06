package demawi.ayto.modell;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MatchingNight {

  public int lights;
  public Set<Pair> constellation;

  public MatchingNight(int lights, Pair... constellation) {
    this(lights, Arrays.asList(constellation));
  }

  public MatchingNight(int lights, Collection<Pair> constellation) {
    if (lights < 0 || lights > 10) {
      throw new RuntimeException("Falsche Anzahl an Lichtern: " + lights);
    }
    if (constellation.size() != 10) {
      throw new RuntimeException("Falsche Anzahl an Paaren: " + constellation.size());
    }
    validatePairs(constellation);
    this.lights = lights;
    this.constellation = new HashSet<>(constellation);
  }

  public static void validatePairs(Pair... constellation) {
    Set<Frau> frauen = new HashSet<>();
    Set<Mann> maenner = new HashSet<>();
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

  public static void validatePairs(Collection<Pair> constellation) {
    Set<Frau> frauen = new HashSet<>();
    Set<Mann> maenner = new HashSet<>();
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

}
