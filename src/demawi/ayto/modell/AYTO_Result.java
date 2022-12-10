package demawi.ayto.modell;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.service.CalculationOptions;

public class AYTO_Result {

  private final CalculationOptions calcOptions;
  public final Map<Pair, Integer> pairCount = new HashMap<>();
  public final Map<Frau, Set<Mann>> frauCount = new HashMap<>();
  public final Map<Mann, Set<Frau>> mannCount = new HashMap<>();
  public int totalConstellations = 0;
  public int possible = 0;
  public int notPossible = 0;
  private Set<Pair> matchingNightConstellation;
  private int[] lightResults;

  public AYTO_Result(CalculationOptions calcOptions) {
    this.calcOptions = calcOptions;
    MatchingNight matchingNight = calcOptions.getMatchingNight();
    if (matchingNight != null) {
      matchingNightConstellation = matchingNight.constellation;
      lightResults = new int[11];
      for (int i = 0, l = 11; i < l; i++) {
        lightResults[i] = 0;
      }
    }
  }

  public AYTO_Data getData() {
    return calcOptions.getData();
  }

  public int getPossibleConstellationSize() {
    return possible;
  }

  public List<Set<Pair>> getAllPossibleConstellations() {
    throw new IllegalStateException("Wird nicht mehr geliefert!");
  }

  public Integer getCount(Frau frau, Mann mann) {
    return getCount(Pair.pair(frau, mann));
  }

  public Integer getCount(Pair pair) {
    Integer result = pairCount.get(pair);
    return result == null ? 0 : result;
  }

  public double getPossibility(Mann mann, Frau frauX, List<Frau> frauen) {
    int gesamt = 0;
    for (Frau frau : frauen) {
      AYTO_Permutator.ZUSATZTYPE zusatztype = getData().getZusatztype();
      if (zusatztype == AYTO_Permutator.ZUSATZTYPE.JEDER && frauen.size() > 10 || frauen.indexOf(frau) <= 9) {
        gesamt += getCount(frau, mann);
      }
    }
    return 1d * getCount(frauX, mann) / gesamt;
  }

  public void addResult(boolean result, Set<Pair> pairs) {
    totalConstellations++;
    if (result) {
      addPossible(pairs);
      possible++;
    }
    else {
      notPossible++;
    }
  }

  /**
   * Fügt die Konstellation als gültig ein.
   *
   * Diese werden nur noch für die Aggregations-Statistiken eingefügt und nicht
   * mehr komplett in einer Liste gehalten, da es bei 199mio Einträgen zu viel
   * Speicher kostet.
   */
  private void addPossible(Set<Pair> pairs) {
    if(lightResults != null) {
      lightResults[AYTO_Result.getLights(pairs, matchingNightConstellation)]++;
    }

    // directly update pair-,frau-,mannCount
    for (Pair current : pairs) {
      Integer count = pairCount.get(current);
      if (count == null) {
        count = 0;
      }
      count++;
      pairCount.put(current, count);

      Set<Mann> frauSet = frauCount.get(current.frau);
      if (frauSet == null) {
        frauSet = new LinkedHashSet<>();
        frauCount.put(current.frau, frauSet);
      }
      frauSet.add(current.mann);

      Set<Frau> mannSet = mannCount.get(current.mann);
      if (mannSet == null) {
        mannSet = new LinkedHashSet<>();
        mannCount.put(current.mann, mannSet);
      }
      mannSet.add(current.frau);
    }
  }

  public static int getLights(Collection<Pair> assumptionModell, Collection<Pair> testConstellation) {
    int lights = 0;
    for (Pair pair : testConstellation) {
      if (assumptionModell.contains(pair)) {
        lights++;
      }
    }
    return lights;
  }

  public int[] getLightResultsForLastMatchingNight() {
    return lightResults;
  }
}
