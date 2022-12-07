package demawi.ayto.modell;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import demawi.ayto.AYTO_Permutator;

public class AYTO_Result {

  private AYTO_Data data;
  public Map<Pair, Integer> pairCount = new HashMap<>();
  public Map<Frau, Set<Mann>> frauCount = new HashMap<>();
  public Map<Mann, Set<Frau>> mannCount = new HashMap<>();
  public int totalConstellations = 0;
  public int possible = 0;
  public int notPossible = 0;
  private Set<Pair> matchingNightConstellation;
  private int[] lightResults;

  public AYTO_Result(AYTO_Data data, CalculationOptions calcOptions) {
    this.data = data;

    if (calcOptions.tagNr > 0) {
      matchingNightConstellation = data.tage.get(calcOptions.tagNr - 1).matchingNight.constellation;

      lightResults = new int[11];
      for (int i = 0, l = 11; i < l; i++) {
        lightResults[i] = 0;
      }
    }

  }

  public AYTO_Data getData() {
    return data;
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
   * Fügt die Konsteallation als gültig ein.
   */
  public void addPossible(Set<Pair> pairs) {
    if(lightResults != null) {
      lightResults[data.getLights(pairs, matchingNightConstellation)]++;
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
        frauSet = new HashSet<>();
        frauCount.put(current.frau, frauSet);
      }
      frauSet.add(current.mann);

      Set<Frau> mannSet = mannCount.get(current.mann);
      if (mannSet == null) {
        mannSet = new HashSet<>();
        mannCount.put(current.mann, mannSet);
      }
      mannSet.add(current.frau);
    }
  }

  public int[] getLightResultsForLastMatchingNight() {
    return lightResults;
  }
}
