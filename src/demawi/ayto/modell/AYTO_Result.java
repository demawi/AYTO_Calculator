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
  public final Map<Pair, Integer> possiblePairCount = new HashMap<>();
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

  public StaffelData getData() {
    return calcOptions.getData();
  }

  public int getPossibleConstellationSize() {
    return possible;
  }

  public List<Set<Pair>> getAllPossibleConstellations() {
    throw new IllegalStateException("Wird nicht mehr geliefert!");
  }

  public Integer getPossibleCount(Frau frau, Mann mann) {
    return getPossibleCount(Pair.pair(frau, mann));
  }

  public Integer getPossibleCount(Pair pair) {
    Integer result = possiblePairCount.get(pair);
    return result == null ? 0 : result;
  }

  /**
   * Gilt unabhängig vom Zusatztype
   */
  public boolean isBasePerson(Person person) {
    if (person instanceof Frau) {
      return getData().initialFrauen.contains(person);
    }
    else {
      return getData().initialMaenner.contains(person);
    }
  }

  public double getBasePossibility(Pair pair) {
    if (getData().getZusatztype() == AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER) {
      int gesamt = 0;
      for (Frau curFrau : getFrauen()) {
        if (isBasePerson(curFrau)) {
          gesamt += getPossibleCount(curFrau, pair.mann);
        }
      }
      return 1d * getPossibleCount(pair) / gesamt;
    }
    else {
      int gesamt = 0;
      List<Frau> frauen = getFrauen();
      List<Mann> maenner = getMaenner();
      if (maenner.size() >= frauen.size()) {
        for (Frau curFrau : frauen) {
          gesamt += getPossibleCount(curFrau, pair.mann);
        }
      }
      else {
        for (Mann curMann : maenner) {
          gesamt += getPossibleCount(pair.frau, curMann);
        }
      }
      return 1d * getPossibleCount(pair) / gesamt;
    }
  }

  public void addResult(Set<Pair> constellation, boolean result) {
    totalConstellations++;

    if (result) {
      addPossible(constellation);
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
      incrementCountMap(possiblePairCount, current);
      frauCount.computeIfAbsent(current.frau, k -> new LinkedHashSet<>())
            .add(current.mann);
      mannCount.computeIfAbsent(current.mann, k -> new LinkedHashSet<>())
            .add(current.frau);
    }
  }

  private static void incrementCountMap(Map<Pair, Integer> pairCountMap, Pair pair) {
    Integer count = pairCountMap.get(pair);
    if (count == null) {
      count = 0;
    }
    count++;
    pairCountMap.put(pair, count);
  }

  public int[] getLightResultsForLastMatchingNight() {
    return lightPossibilities;
  }

  public List<Frau> getFrauen() {
    return calcOptions.getFrauen();
  }

  public List<Mann> getMaenner() {
    return calcOptions.getMaenner();
  }

  public void fixTotalPossibilities(int totalConstellations) {
    this.totalConstellations = totalConstellations;
    notPossible = totalConstellations - possible;
  }
}
