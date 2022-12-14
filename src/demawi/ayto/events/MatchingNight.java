package demawi.ayto.events;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import demawi.ayto.modell.AYTO_Pair;

public class MatchingNight
      implements Event {

  public final Integer lights;
  public final Set<AYTO_Pair> constellation;

  public MatchingNight(Integer lights, AYTO_Pair... constellation) {
    this(lights, Arrays.asList(constellation));
  }

  private MatchingNight(Integer lights, Collection<AYTO_Pair> constellation) {
    if (lights != null && (lights < 0 || lights > 10)) {
      throw new RuntimeException("Falsche Anzahl an Lichtern: " + lights);
    }
    if (constellation.size() != 10) {
      throw new RuntimeException("Falsche Anzahl an Paaren: " + constellation.size());
    }
    this.lights = lights;
    this.constellation = new LinkedHashSet<>(constellation);
  }

  public int getLights(Collection<AYTO_Pair> assumptionModell) {
    return MatchingNight.getLights(assumptionModell, this.constellation);
  }

  private static int getLights(Collection<AYTO_Pair> assumptionModell, Collection<AYTO_Pair> testConstellation) {
    int lights = 0;
    for (AYTO_Pair pair : testConstellation) {
      if (assumptionModell.contains(pair)) {
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
  public boolean isValid(Collection<AYTO_Pair> constellation) {
    if (lights == null) // Ergebnis steht noch nicht fest
      return true;
    return lights == getLights(constellation);
  }

}
