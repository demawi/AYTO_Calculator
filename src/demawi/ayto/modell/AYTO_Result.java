package demawi.ayto.modell;

import java.util.*;

import demawi.ayto.modell.events.MatchingNight;
import demawi.ayto.modell.events.PairInterpreter;

public class AYTO_Result {

  private final CalculationOptions calcOptions;
  public final Map<AYTO_Pair, Long> possiblePairCount = new LinkedHashMap<>();
  public long totalConstellations = 0;
  public long possible = 0;
  public long notPossible = 0;
  private long[] lightPossibilities; // wird nur gesetzt, wenn auch eine Matching Night stattgefunden hat.

  private List<Set<AYTO_Pair>> allPossibleTrueConstellations = null; // deaktiviert wenn = null

  /**
   * Add all subresults
   */
  public void add(AYTO_Result cur) {
    totalConstellations = Math.addExact(totalConstellations, cur.totalConstellations);
    possible = Math.addExact(possible, cur.possible);
    notPossible = Math.addExact(notPossible, cur.notPossible);
    if (lightPossibilities != null) {
      for (int i = 0, l = calcOptions.getSeasonData()
            .getMatchingPairCount(); i <= l; i++) {
        lightPossibilities[i] = Math.addExact(lightPossibilities[i], cur.lightPossibilities[i]);
      }
    }
    if (allPossibleTrueConstellations != null && cur.allPossibleTrueConstellations != null) {
      allPossibleTrueConstellations.addAll(cur.allPossibleTrueConstellations);
    }
    for (Map.Entry<AYTO_Pair, Long> pairEntry : cur.possiblePairCount.entrySet()) {
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
      int lightArrayLength = matchingNight.constellation.size() + 1;
      lightPossibilities = new long[lightArrayLength];
      for (int i = 0; i < lightArrayLength; i++) {
        lightPossibilities[i] = 0;
      }
    }
  }

  public List<Day> getTageBisher() {
    return calcOptions.getTageBisher();
  }

  public SeasonData getData() {
    return calcOptions.getSeasonData();
  }

  public long getPossibleConstellationSize() {
    return possible;
  }

  public List<Set<AYTO_Pair>> getAllPossibleConstellations() {
    return allPossibleTrueConstellations;
  }

  public Long getPossibleCount(Person frau, Person mann) {
    return getPossibleCount(AYTO_Pair.pair(frau, mann));
  }

  public Long getPossibleCount(AYTO_Pair pair) {
    Long result = possiblePairCount.get(pair);
    return result == null ? 0 : result;
  }

  /**
   * Gilt unabhängig vom Zusatztype
   */
  public boolean isBasePerson(Person person) {
    if (person instanceof Woman) {
      return getData().initialFrauen.contains(person);
    }
    else {
      return getData().initialMaenner.contains(person);
    }
  }

  public double getBasePossibility(AYTO_Pair pair) {
    long gesamt = 0;
    List<Person> frauen = getFrauen();
    List<Person> maenner = getMaenner();
    if (maenner.size() >= frauen.size()) {
      for (Person curFrau : frauen) {
        gesamt = Math.addExact(gesamt, getPossibleCount(curFrau, pair.mann));
      }
    }
    else {
      for (Person curMann : maenner) {
        gesamt = Math.addExact(gesamt, getPossibleCount(pair.frau, curMann));
      }
    }
    return 1d * getPossibleCount(pair) / gesamt;
  }

  public void addResult(Set<AYTO_Pair> constellation, boolean result, PairInterpreter lookup) {
    Integer lights = result && lightPossibilities != null ? calcOptions.getMatchingNight()
          .getLights(constellation, lookup) : null;
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
      addPossibleTrueConstellation(constellation);
    }
    else {
      notPossible++;
    }
  }

  private void addPossibleTrueConstellation(Set<AYTO_Pair> constellation) {
    if (allPossibleTrueConstellations != null) {
      allPossibleTrueConstellations.add(constellation);
    }
    for (AYTO_Pair current : constellation) {
      addPairToCountMap(possiblePairCount, current);
    }
  }

  private static void addPairToCountMap(Map<AYTO_Pair, Long> pairCountMap, AYTO_Pair pair) {
    addPairToCountMap(pairCountMap, pair, 1);
  }

  private static void addPairToCountMap(Map<AYTO_Pair, Long> pairCountMap, AYTO_Pair pair, long value) {
    Long count = pairCountMap.get(pair);
    if (count == null) {
      count = 0L;
    }
    count = Math.addExact(count, value);
    pairCountMap.put(pair, count);
  }

  public long[] getLightResultsForLastMatchingNight() {
    return lightPossibilities;
  }

  public List<Person> getFrauen() {
    return calcOptions.getFrauen();
  }

  public List<Person> getMaenner() {
    return calcOptions.getMaenner();
  }

  public void fixTotalPossibilities(long totalConstellations) {
    this.totalConstellations = totalConstellations;
    notPossible = Math.addExact(totalConstellations, -possible);
  }

  public static double p(int current, int gesamt) {
    return current / (double) gesamt;
  }

  public static double p2(int current, int gesamt) {
    return Math.sqrt(current) / Math.sqrt(gesamt);
  }

  public static double a(int current, int gesamt) {
    return Math.log10(current) / Math.log10(gesamt);
  }

  public static double b(int current, int gesamt) {
    return Math.log(current) / Math.log(gesamt);
  }

  public static void main(String[] args) {
    int gesamt = 36288000;
    int current = 26990;
    System.out.println(p(current, gesamt));
    System.out.println(p2(current, gesamt));
    System.out.println(a(current, gesamt));
    System.out.println(b(current, gesamt));

    System.out.println(Math.log10(1));
    System.out.println(Math.log(1));
  }
}
