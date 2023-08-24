package demawi.ayto.modell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import demawi.ayto.events.MatchingNight;
import demawi.ayto.permutation.AYTO_Permutator;

public class AYTO_Result {

  private final CalculationOptions calcOptions;
  public final Map<AYTO_Pair, Integer> possiblePairCount = new HashMap<>();
  public int totalConstellations = 0;
  public int possible = 0;
  public int notPossible = 0;
  private int[] lightPossibilities; // wird nur gesetzt, wenn auch eine Matching Night stattgefunden hat.

  /**
   * Add all subresults
   */
  public void add(AYTO_Result cur) {
    totalConstellations += cur.totalConstellations;
    possible += cur.possible;
    notPossible += cur.notPossible;
    if (lightPossibilities != null) {
      for (int i = 0, l = calcOptions.getData()
            .getMatchingPairCount(); i <= l; i++) {
        lightPossibilities[i] += cur.lightPossibilities[i];
      }
    }
    for (Map.Entry<AYTO_Pair, Integer> pairEntry : cur.possiblePairCount.entrySet()) {
      addPairToCountMap(possiblePairCount, pairEntry.getKey(), pairEntry.getValue());
    }
  }

  public CalculationOptions getCalcOptions() {
    return calcOptions;
  }

  public void addAll(List<AYTO_Result> results) {
    for (AYTO_Result cur : results) {
      add(cur);
    }
  }

  public AYTO_Result(CalculationOptions calcOptions) {
    this.calcOptions = calcOptions;
    MatchingNight matchingNight = calcOptions.getMatchingNight();
    if (matchingNight != null) {
      int lightArrayLength = calcOptions.getTag()
            .getMatchingNightCount() + 1;
      lightPossibilities = new int[lightArrayLength];
      for (int i = 0; i < lightArrayLength; i++) {
        lightPossibilities[i] = 0;
      }
    }
  }

  public List<Tag> getTageBisher() {
    return calcOptions.getTageBisher();
  }

  public StaffelData getData() {
    return calcOptions.getData();
  }

  public int getPossibleConstellationSize() {
    return possible;
  }

  public List<Set<AYTO_Pair>> getAllPossibleConstellations() {
    throw new IllegalStateException("Wird nicht mehr geliefert!");
  }

  public Integer getPossibleCount(Person frau, Person mann) {
    return getPossibleCount(AYTO_Pair.pair(frau, mann));
  }

  public Integer getPossibleCount(AYTO_Pair pair) {
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

  public double getBasePossibility(AYTO_Pair pair) {
    if (getData().getZusatztype() == AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER) {
      int gesamt = 0;
      for (Person curFrau : getFrauen()) {
        if (isBasePerson(curFrau)) {
          gesamt += getPossibleCount(curFrau, pair.mann);
        }
      }
      return 1d * getPossibleCount(pair) / gesamt;
    }
    else {
      int gesamt = 0;
      List<Person> frauen = getFrauen();
      List<Person> maenner = getMaenner();
      if (maenner.size() >= frauen.size()) {
        for (Person curFrau : frauen) {
          gesamt += getPossibleCount(curFrau, pair.mann);
        }
      }
      else {
        for (Person curMann : maenner) {
          gesamt += getPossibleCount(pair.frau, curMann);
        }
      }
      return 1d * getPossibleCount(pair) / gesamt;
    }
  }

  public void addResult(Set<AYTO_Pair> constellation, boolean result) {
    Integer lights = result && lightPossibilities != null ? calcOptions.getMatchingNight()
          .getLights(constellation) : null;
    registerResults(constellation, result, lights);
  }

  /**
   * Eintragen der Ergebnisse.
   * <p>
   * Es werden keine kompletten Constellations mehr gespeichert, da es bei 199mio Einträgen zu viel
   * Speicher kostet.
   */
  private void registerResults(Set<AYTO_Pair> constellation, boolean result, Integer lights) {
    totalConstellations++;
    if (lights != null) {
      lightPossibilities[lights]++;
    }
    if (result) {
      possible++;
      for (AYTO_Pair current : constellation) {
        addPairToCountMap(possiblePairCount, current);
      }
    }
    else {
      notPossible++;
    }
  }

  private static void addPairToCountMap(Map<AYTO_Pair, Integer> pairCountMap, AYTO_Pair pair) {
    addPairToCountMap(pairCountMap, pair, 1);
  }

  private static void addPairToCountMap(Map<AYTO_Pair, Integer> pairCountMap, AYTO_Pair pair, int value) {
    Integer count = pairCountMap.get(pair);
    if (count == null) {
      count = 0;
    }
    count += value;
    pairCountMap.put(pair, count);
  }

  public int[] getLightResultsForLastMatchingNight() {
    return lightPossibilities;
  }

  public List<Person> getFrauen() {
    return calcOptions.getFrauen();
  }

  public List<Person> getMaenner() {
    return calcOptions.getMaenner();
  }

  public void fixTotalPossibilities(int totalConstellations) {
    this.totalConstellations = totalConstellations;
    notPossible = totalConstellations - possible;
  }

}
