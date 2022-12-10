package demawi.ayto.modell;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import demawi.ayto.events.MatchingNight;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.service.CalculationOptions;

public class AYTO_Result {

  private final CalculationOptions calcOptions;
  public final Map<Pair, Integer> pairCount = new HashMap<>();
  public final Map<Frau, Set<Mann>> frauCount = new HashMap<>();
  public final Map<Mann, Set<Frau>> mannCount = new HashMap<>();
  public int totalConstellations = 0;
  public int possible = 0;
  public int notPossible = 0;
  private int[] lightPossibilities; // wird nur gesetzt, wenn auch eine Matching Night stattgefunden hat.

  public AYTO_Result(CalculationOptions calcOptions) {
    this.calcOptions = calcOptions;
    MatchingNight matchingNight = calcOptions.getMatchingNight();
    if (matchingNight != null) {
      lightPossibilities = new int[11];
      for (int i = 0, l = 11; i < l; i++) {
        lightPossibilities[i] = 0;
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
    if (lightPossibilities != null) {
      lightPossibilities[calcOptions.getMatchingNight()
            .getLights(pairs)]++;
    }

    // directly update pair-,frau-,mannCount
    for (Pair current : pairs) {
      Integer count = pairCount.get(current);
      if (count == null) {
        count = 0;
      }
      count++;
      pairCount.put(current, count);

      frauCount.computeIfAbsent(current.frau, k -> new LinkedHashSet<>())
            .add(current.mann);
      mannCount.computeIfAbsent(current.mann, k -> new LinkedHashSet<>())
            .add(current.frau);
    }
  }

  public int[] getLightResultsForLastMatchingNight() {
    return lightPossibilities;
  }
}
