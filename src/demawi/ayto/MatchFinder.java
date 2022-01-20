package demawi.ayto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
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

  protected AYTO_Data data = new AYTO_Data();
  protected AYTO_Result result;

  private String name;
  private Set<Set<Integer>> setsOhneZusatz = new HashSet<>();

  public MatchFinder(String name) {
    this.name = name;
  }

  private void calulate(boolean info) {
    if (result == null) {
      long start = System.currentTimeMillis();
      if (info) System.out.println("Berechne " + name + " - Nacht " + data.matchingNights.size() + "...");
      result = new AYTO_Result(data);

      boolean debug = false;

      AYTO_Permutator3<Frau, Mann, Pair> permutator = AYTO_Permutator3.create(data.frauen, data.maenner, Pair::pair);
      permutator.permutate(new Consumer<Set<Pair>>() {
        // AYTO_Permutator2.create(data.frauen, data.maenner).permutate(new Consumer<Set<Pair>>() {
        @Override
        public void accept(Set<Pair> constellation) {
          result.addResult(test(constellation, debug), constellation);
        }
      });
      if (info) System.out.println("Size: " + result.totalConstellations + " Yes: " + result.yes + " / "
        + setsOhneZusatz.size() + " No: " + result.no + " Calculation time: "
        + minSecs(System.currentTimeMillis() - start) + " Tested iterations: " + permutator.testCount);
    }
  }

  private static String minSecs(long mills) {
    long min = Math.floorDiv(mills, 60 * 1000);
    long secs = Math.floorDiv(mills - min * 60 * 1000, 1000);
    return min + " min " + secs + " secs";
  }

  public static void main(String[] args) {
    System.out.println(minSecs(60000));
  }

  /**
   * Prints all pair probabilities
   */
  public void search() {
    calulate(true);

    Comparator<Pair> pairComparator = new Comparator<Pair>() {
      @Override
      public int compare(Pair o1, Pair o2) {
        return result.pairCount.get(o2).compareTo(result.pairCount.get(o1));
      }
    };
    List<Pair> sortedPairs = new ArrayList<Pair>(result.pairCount.keySet());
    Collections.sort(sortedPairs, pairComparator);

    System.out.println("Potential pairs: " + result.pairCount.size());

    for (Pair pair : sortedPairs) {
      double percent = Math.round((result.pairCount.get(pair) / ((double) result.constellations.size())) * 10000)
        / 100.0;

      int count = 0;
      for (MatchingNight night : data.matchingNights) {
        if (night.constellation.contains(pair)) {
          count++;
        }
      }
      System.out
        .println(pair.frau + " & " + pair.mann + " => " + percent + "% " + count + "/" + data.matchingNights.size());
    }

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

    List<Set<Pair>> sortedConstellations = new ArrayList<Set<Pair>>(result.constellations);
    Collections.sort(sortedConstellations, new Comparator<Set<Pair>>() {
      @Override
      public int compare(Set<Pair> o1, Set<Pair> o2) {
        Integer o1Count = o1.stream().collect(Collectors.summingInt(a -> result.pairCount.get(a)));
        Integer o2Count = o2.stream().collect(Collectors.summingInt(a -> result.pairCount.get(a)));
        return o2Count.compareTo(o1Count);
      }
    });

    System.out.println();
    System.out.println("==== Beste Konstellationen ====");
    for (int i = 0; i < 100; i++) {
      Set<Pair> constellation = sortedConstellations.get(i);
      System.out.println("> Platz " + (i + 1) + " mit "
        + constellation.stream().collect(Collectors.summingInt(a -> result.pairCount.get(a))) + " Punkten");
      for (Pair pair : constellation) {
        System.out.println(pair);
      }
      System.out.println();
    }
  }

  /**
   * Prints the spot probabilities 0..10 for the given constellation.
   */
  public void search(Pair... pairs) {
    calulate(true);

    for (Pair pair : pairs) {
      result.getChance(pair);
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

  /**
   * Testet ob die übergebene Paar-Konstellation zu einem Widerspruch führt.
   */
  public boolean test(Collection<Pair> constellation, boolean debug) {
    for (Pair pair : data.noMatches) {
      if (constellation.contains(pair)) {
        return false;
      }
    }
    for (Pair pair : data.matches) {
      if (!constellation.contains(pair)) {
        return false;
      }
    }
    for (int i = 0, l = data.matchingNights.size(); i < l; i++) {
      MatchingNight night = data.matchingNights.get(i);
      int lights = 0;
      for (Pair pair : night.constellation) {
        if (constellation.contains(pair)) {
          lights++;
        }
      }
      if (lights != night.lights) {
        if (debug) {
          System.out.println("Falsch aufgrund von Matching Night Nr. " + (i + 1) + " Erwartete Lichter: " + lights
            + ". Es waren aber: " + night.lights);
        }
        return false;
      }
    }
    return true;
  }

  protected void mn(int spots, Pair... constellation) {
    data.add(new MatchingNight(spots, constellation));
  }

  protected void add(boolean b, Pair pair) {
    data.add(b, pair);
  }

}
