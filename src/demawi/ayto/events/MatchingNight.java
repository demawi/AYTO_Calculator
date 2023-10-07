package demawi.ayto.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import demawi.ayto.modell.AYTO_Pair;

public class MatchingNight
      implements Event {

  public final Integer lights;
  public final Set<AYTO_Pair> constellation;

  public MatchingNight(Integer lights, int matchingNightCount, AYTO_Pair... constellation) {
    this(lights, matchingNightCount, Arrays.asList(constellation));
  }

  private MatchingNight(Integer lights, int matchingNightCount, Collection<AYTO_Pair> constellation) {
    constellation = cleanDoubles(constellation);
    if (lights != null && (lights < 0 || lights > matchingNightCount)) {
      throw new RuntimeException("Falsche Anzahl an Lichtern: " + lights + " Max: " + matchingNightCount);
    }
    if (constellation.size() != matchingNightCount) {
      throw new RuntimeException("Falsche Anzahl an Paaren: " + constellation.size());
    }
    this.lights = lights;
    this.constellation = new LinkedHashSet<>(constellation);
  }

  private Collection<AYTO_Pair> cleanDoubles(Collection<AYTO_Pair> constellation) {
    Collection<AYTO_Pair> result = new ArrayList<>();
    constellation.forEach(pair -> {
      if (!result.contains(pair) && !result.contains(AYTO_Pair.pair(pair.mann, pair.frau))) {
        result.add(pair);
      }
    });
    return result;
  }

  public int getLights(Collection<AYTO_Pair> assumptionModell, PairInterpreter lookup) {
    return MatchingNight.getLights(assumptionModell, this.constellation, lookup);
  }

  private static int getLights(Collection<AYTO_Pair> assumptionModell, Collection<AYTO_Pair> testConstellation, PairInterpreter lookup) {
    int lights = 0;
    for (AYTO_Pair pair : testConstellation) {
      if (assumptionModell.contains(lookup.lookup(pair))) {
        lights++;
      }
    }
    return lights;
  }

  /**
   * Gibt true zurück sofern es keinen Widerspruch gibt und
   * die Konstellation somit weiterhin als valide eingestuft
   * werden kann.
   */
  public boolean isValid(Collection<AYTO_Pair> constellation, PairInterpreter lookup) {
    if (lights == null) // Ergebnis steht noch nicht fest
      return true;
    return lights == getLights(constellation, lookup);
  }

}
