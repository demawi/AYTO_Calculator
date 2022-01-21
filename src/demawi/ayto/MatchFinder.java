package demawi.ayto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.MatchingNight;
import demawi.ayto.modell.Pair;

public class MatchFinder {

  private String name;
  protected AYTO_Data data = new AYTO_Data();
  protected AYTO_Result result;

  public MatchFinder(String name) {
    this.name = name;
  }

  protected void setData(AYTO_Data data) {
    this.data = data;
  }

  private void calulate(boolean info) {
    if (result == null) {
      long start = System.currentTimeMillis();
      if (info) System.out.println("Berechne " + name + " - Nacht " + data.matchingNights.size() + "...");
      result = new AYTO_Result(data);

      boolean debug = false;

      AYTO_Permutator<Frau, Mann, Pair> permutator = AYTO_Permutator.create(data.frauen, data.maenner, Pair::pair);
      permutator.permutate(new Consumer<Set<Pair>>() {
        @Override
        public void accept(Set<Pair> constellation) {
          result.addResult(data.test(constellation, debug), constellation);
        }
      });
      if (info) System.out.println("Combinations: " + result.totalConstellations + " Yes: " + result.yes + " No: "
        + result.no + " Calculation time: " + minSecs(System.currentTimeMillis() - start)
        + " (Tested intern iterations: " + permutator.testCount + ")");
    }
  }

  private static String minSecs(long mills) {
    long min = Math.floorDiv(mills, 60 * 1000);
    long secs = Math.floorDiv(mills - min * 60 * 1000, 1000);
    return min + " min " + secs + " secs";
  }

  /**
   * Berechnet die vorherigen Lichter Wahrscheinlichkeiten indem die letzte Matching Night herausgenommen wird.
   */
  public void printLightChancesAndResult() {
    AYTO_Data previous = data;
    data = new AYTO_Data();
    for (Pair pair : previous.matches) {
      data.add(true, pair);
    }
    for (Pair pair : previous.noMatches) {
      data.add(false, pair);
    }
    int size = previous.matchingNights.size();
    for (int i = 0; i < size - 1; i++) {
      data.add(previous.matchingNights.get(i));
    }
    for (Frau frau : previous.frauen) {
      if (!data.frauen.contains(frau)) {
        data.frauen.add(frau);
        System.out
          .println("Eine Frau ist hinzugekommen: " + frau + ". Die Anzahl der Kombinationen erhöht sich damit!");
      }
    }
    for (Mann mann : previous.maenner) {
      if (!data.maenner.contains(mann)) {
        data.maenner.add(mann);
        System.out.println("Ein Mann ist hinzugekommen: " + mann + ". Die Anzahl der Kombinationen erhöht sich damit!");
      }
    }
    result = null;
    MatchingNight matchingNight = previous.matchingNights.get(size - 1);
    printLightChances(matchingNight.constellation, matchingNight.lights);
    data = previous;
    result = null;
    System.out.println();
    printResult();
  }

  /**
   * Prints all pair probabilities
   */
  public void printResult() {
    calulate(true);

    Comparator<Pair> pairComparator = new Comparator<Pair>() {
      @Override
      public int compare(Pair o1, Pair o2) {
        return result.pairCount.get(o2).compareTo(result.pairCount.get(o1));
      }
    };
    List<Pair> sortedPairs = new ArrayList<Pair>(result.pairCount.keySet());
    Collections.sort(sortedPairs, pairComparator);

    System.out.println();
    System.out.println("==== Partner ====");
    for (Frau frau : data.frauen) {
      Set<Mann> set = result.frauCount.get(frau);
      System.out.println(frau + ": " + (set == null ? "-" : set.size() + " " + getRanking(result, frau)));
    }
    System.out.println();
    for (Mann mann : data.maenner) {
      Set<Frau> set = result.mannCount.get(mann);
      System.out.println(mann + ": " + (set == null ? "-" : set.size() + " " + getRanking(result, mann)));
    }

    List<Set<Pair>> sortedConstellations = new ArrayList<Set<Pair>>(result.possibleConstellations);
    Collections.sort(sortedConstellations, new Comparator<Set<Pair>>() {
      @Override
      public int compare(Set<Pair> o1, Set<Pair> o2) {
        Integer o1Count = o1.stream().collect(Collectors.summingInt(a -> result.pairCount.get(a)));
        Integer o2Count = o2.stream().collect(Collectors.summingInt(a -> result.pairCount.get(a)));
        return o2Count.compareTo(o1Count);
      }
    });

    System.out.println();
    System.out.println(
      "==== Mögliche Paare: " + result.pairCount.size() + "/" + (data.frauen.size() * data.maenner.size()) + " ====");
    for (Pair pair : sortedPairs) {
      Integer pairCounting = result.pairCount.get(pair);
      Integer gesamtCounting = result.possibleConstellations.size();
      double percent = Math.round((pairCounting / (double) gesamtCounting) * 10000) / 100.0;

      int count = 0;
      for (MatchingNight night : data.matchingNights) {
        if (night.constellation.contains(pair)) {
          count++;
        }
      }
      System.out.println(pair.frau + " & " + pair.mann + " => " + percent + "% [" + pairCounting + "/" + gesamtCounting
        + "] Gemeinsame MNs: " + count + "/" + data.matchingNights.size());
    }

    System.out.println();
    System.out.println("==== Beste Konstellationen ====");
    for (int i = 0, l = Math.min(20, sortedConstellations.size()); i < l; i++) {
      Set<Pair> constellation = sortedConstellations.get(i);
      System.out.println("> Platz " + (i + 1) + " mit "
        + constellation.stream().collect(Collectors.summingInt(a -> result.pairCount.get(a)))
        + " Punkten (Summe der Vorkommen der Einzelpaare)");
      for (Pair pair : constellation) {
        System.out.println(pair);
      }
      System.out.println();
    }
  }

  /**
   * Prints the spot probabilities 0..10 for the given constellation.
   */
  public void printLightChances(Collection<Pair> pairs, Integer spotsReached) {
    calulate(true);
    MatchingNight.validatePairs(pairs);

    int[] lightResults = new int[11];
    for (int i = 0, l = 11; i < l; i++) {
      lightResults[i] = 0;
    }

    for (Set<Pair> constellation : result.possibleConstellations) {
      lightResults[data.getLights(constellation, pairs)]++;
    }

    for (int i = 0, l = 11; i < l; i++) {
      String marker = "";
      if (i == spotsReached) {
        marker = " <==";
      }
      System.out.println("Spot probabilities " + i + " => "
        + Math.round(((double) lightResults[i] / result.possibleConstellations.size()) * 10000.0) / 100.0 + " ["
        + lightResults[i] + "]" + marker);
    }

  }

  private static String getRankingStat(int acc, int gesamt) {
    return "" + Math.round(((double) acc) / gesamt * 10000) / 100.0;
  }

  private String getRanking(AYTO_Result resulter, Frau frau) {
    int gesamt = 0;
    for (Mann mann : data.maenner) {
      if (data.maenner.indexOf(mann) <= 9) {
        gesamt += resulter.getCount(frau, mann);
      }
    }
    List<Mann> sortedPartner = new ArrayList<>(data.maenner);
    sortedPartner.sort(new Comparator<Mann>() {
      @Override
      public int compare(Mann o1, Mann o2) {
        return resulter.getCount(frau, o2).compareTo(resulter.getCount(frau, o1));
      }
    });
    StringBuffer stringBuffer = new StringBuffer();
    for (Mann mann : sortedPartner) {
      if (stringBuffer.length() > 0) {
        stringBuffer.append(", ");
      }
      stringBuffer.append(mann + " (" + getRankingStat(resulter.getCount(frau, mann), gesamt) + ")");
    }
    return "[" + stringBuffer.toString() + "]";
  }

  private String getRanking(AYTO_Result resulter, Mann mann) {
    int gesamt = 0;
    for (Frau frau : data.frauen) {
      if (data.frauen.indexOf(frau) <= 9) {
        gesamt += resulter.getCount(frau, mann);
      }
    }
    List<Frau> sortedPartner = new ArrayList<>(data.frauen);
    sortedPartner.sort(new Comparator<Frau>() {
      @Override
      public int compare(Frau o1, Frau o2) {
        return resulter.getCount(o2, mann).compareTo(resulter.getCount(o1, mann));
      }
    });
    StringBuffer stringBuffer = new StringBuffer();
    for (Frau frau : sortedPartner) {
      if (stringBuffer.length() > 0) {
        stringBuffer.append(", ");
      }
      stringBuffer.append(frau + " (" + getRankingStat(resulter.getCount(frau, mann), gesamt) + ")");
    }
    return "[" + stringBuffer.toString() + "]";
  }

}
