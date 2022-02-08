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
import demawi.ayto.modell.Tag;
import demawi.ayto.modell.TagDef;

public class MatchFinder {

  private Consumer<String> out = (str) -> {
    System.out.println(str);
  };

  private static String prozent(double anteil, double von) {
    return "" + Math.round(anteil / von * 10000.0) / 100.0;
  }

  private static String minSecs(long mills) {
    long min = Math.floorDiv(mills, 60 * 1000);
    long secs = Math.floorDiv(mills - min * 60 * 1000, 1000);
    return min + " min " + secs + " secs";
  }

  public Consumer<String> getOut() {
    return out;
  }

  public void setOut(Consumer<String> out) {
    this.out = out;
  }

  private AYTO_Result calculate(AYTO_Data data, TagDef tagDef, boolean info) {
    long start = System.currentTimeMillis();
    AYTO_Result result = new AYTO_Result();
    if (tagDef.tagNr == 0) {
      return result;
    }
    if (info) {
      out.accept("-- Berechne " + data.name + " - Nacht " + tagDef.tagNr + (tagDef.mitMatchbox ? " inkl. Matchbox" : "")
            + (tagDef.mitMatchingNight ? " inkl. Matchingnight" : "") + "...");
    }

    boolean debug = false;

    AYTO_Permutator<Frau, Mann, Pair> permutator = AYTO_Permutator.create(data.getFrauen(tagDef.tagNr),
          data.getMaenner(tagDef.tagNr), Pair::pair);
    permutator.permutate(constellation -> result.addResult(data.test(constellation, tagDef, debug), constellation));
    if (info) {
      out.accept("-- Combinations: " + result.totalConstellations + " Yes: " + result.yes + " No: " + result.no
            + " Calculation time: " + minSecs(System.currentTimeMillis() - start) + " (Tested intern iterations: "
            + permutator.testCount + ")");
      out.accept("");
    }
    return result;
  }

  public void printLightChancesAndResult(AYTO_Data data) {
    printLightChancesAndResult(data, data.tage.size());
  }

  /**
   * Berechnet die vorherigen Lichter Wahrscheinlichkeiten indem die letzte Matching Night herausgenommen wird.
   */
  public void printLightChancesAndResult(AYTO_Data data, int tagNr) {
    boolean info = true;
    List<Frau> frauenBefore = data.getFrauen(tagNr - 1);
    List<Mann> maennerBefore = data.getMaenner(tagNr - 1);

    List<Frau> frauen = data.getFrauen(tagNr);
    List<Mann> maenner = data.getMaenner(tagNr);
    AYTO_Result result0 = null;
    if (frauen.size() > frauenBefore.size() || maenner.size() > maennerBefore.size()) {
      for (Frau frau : frauen) {
        if (!frauenBefore.contains(frau)) {
          out.accept("Eine Frau ist hinzugekommen: " + frau + ". Die Anzahl der Kombinationen erhöht sich damit!");
        }
      }
      for (Mann mann : maenner) {
        if (!maennerBefore.contains(mann)) {
          out.accept("Ein Mann ist hinzugekommen: " + mann + ". Die Anzahl der Kombinationen erhöht sich damit!");
        }
      }
      result0 = calculate(data, new TagDef(tagNr - 1, true, true), info);
    }
    AYTO_Result result1 = calculate(data, new TagDef(tagNr, false, false), info);
    if (result0 != null) {
      out.accept("Die Anzahl der Kombinationen hat sich verändert: " + result0.possibleConstellations.size() + " => "
            + result1.possibleConstellations.size());
    }

    AYTO_Result result2 = calculate(data, new TagDef(tagNr, true, false), info);
    Tag tag = data.tage.get(tagNr - 1);
    out.accept("MatchBox: " + tag.matchingPair + (tag.perfectMatch == null ? " verkauft." : "...")
          + " (Wahrscheinlichkeit für das Ausgang des Ergebnisses)");
    String yesMarker = "";
    String noMarker = "";
    if (tag.perfectMatch != null) {
      if (tag.perfectMatch) {
        yesMarker = " <==";
      }
      else {
        noMarker = " <==";
      }
    }
    out.accept("Ja   => " + prozent(result1.getCount(tag.matchingPair), result1.possibleConstellations.size()) + "% ["
          + result1.getCount(tag.matchingPair) + "]" + yesMarker);
    out.accept("Nein => " + prozent(result1.possibleConstellations.size() - result1.getCount(tag.matchingPair),
          result1.possibleConstellations.size()) + "% [" + (result1.possibleConstellations.size() - result1.getCount(
          tag.matchingPair)) + "]" + noMarker);
    out.accept("Die Kombinationen reduzieren sich: " + result1.getYes() + " => " + result2.getYes());
    MatchingNight matchingNight = tag.matchingNight;
    if (matchingNight == null) {
      out.accept("");
      out.accept("Es hat keine Matching Night stattgefunden!");
    }
    else {
      printLightChances(data, result2, matchingNight.constellation, matchingNight.lights);
    }
    out.accept("");
    TagDef tagesEnde = new TagDef(tagNr, true, true);
    AYTO_Result result3 = calculate(data, tagesEnde, info);
    printResult(data, result3, frauen, maenner);
  }

  /**
   * Prints all pair probabilities
   */
  public void printResult(AYTO_Data data, AYTO_Result result, List<Frau> frauen, List<Mann> maenner) {
    Comparator<Pair> pairComparator = (pair1, pair2) -> result.pairCount.get(pair2)
          .compareTo(result.pairCount.get(pair1));

    List<Pair> sortedPairs = new ArrayList<>(result.pairCount.keySet());
    Collections.sort(sortedPairs, pairComparator);

    out.accept("==== Partner ==== (und entsprechende Wahrscheinlichkeiten)");
    for (Frau frau : frauen) {
      Set<Mann> set = result.frauCount.get(frau);
      out.accept(frau + ": " + (set == null ? "-" : set.size() + " " + getRanking(result, frau, maenner)));
    }
    out.accept("");
    for (Mann mann : maenner) {
      Set<Frau> set = result.mannCount.get(mann);
      out.accept(mann + ": " + (set == null ? "-" : set.size() + " " + getRanking(result, mann, frauen)));
    }

    List<Set<Pair>> sortedConstellations = new ArrayList<>(result.possibleConstellations);
    Collections.sort(sortedConstellations, (pairs1, pairs2) -> {
      Integer o1Count = pairs1.stream()
            .collect(Collectors.summingInt(a -> result.pairCount.get(a)));
      Integer o2Count = pairs2.stream()
            .collect(Collectors.summingInt(a -> result.pairCount.get(a)));
      return o2Count.compareTo(o1Count);
    });
    out.accept("");
    out.accept("==== Mögliche Paare: " + result.pairCount.size() + "/" + (frauen.size() * maenner.size()) + " ====");
    for (Pair pair : sortedPairs) {
      Integer pairCounting = result.pairCount.get(pair);
      Integer gesamtCounting = result.possibleConstellations.size();
      double percent = Math.round((pairCounting / (double) gesamtCounting) * 10000) / 100.0;

      int count = 0;
      for (Tag tag : data.tage) {
        MatchingNight night = tag.matchingNight;
        if (night != null && night.constellation.contains(pair)) {
          count++;
        }
      }
      out.accept(pair.frau + " & " + pair.mann + " => " + percent + "% [" + pairCounting + "/" + gesamtCounting
            + "] Gemeinsame MNs: " + count + "/" + data.tage.size());
    }

    if (data.pairsToTrack != null) {
      out.accept("");
      out.accept("==== Tracking der Perfect Matches im Verlauf ====");
      for (Pair pair : data.pairsToTrack) {
        out.accept(pair + " => " + prozent(result.getCount(pair), result.possibleConstellations.size())+"%");
      }
    }

    out.accept("");
    out.accept("==== Beste Konstellationen ====");
    for (int i = 0, l = Math.min(20, sortedConstellations.size()); i < l; i++) {
      Set<Pair> constellation = sortedConstellations.get(i);
      out.accept("> Platz " + (i + 1) + " mit " + constellation.stream()
            .collect(Collectors.summingInt(a -> result.pairCount.get(a)))
            + " Punkten (Summe der Vorkommen der Einzelpaare)");
      for (Pair pair : constellation) {
        out.accept(pair.toString());
      }
      out.accept("");
    }
  }

  /**
   * Prints the spot probabilities 0..10 for the given constellation.
   */
  public void printLightChances(AYTO_Data data, AYTO_Result result, Collection<Pair> pairs, Integer spotsReached) {
    MatchingNight.validatePairs(pairs);

    int[] lightResults = new int[11];
    for (int i = 0, l = 11; i < l; i++) {
      lightResults[i] = 0;
    }

    for (Set<Pair> constellation : result.possibleConstellations) {
      lightResults[data.getLights(constellation, pairs)]++;
    }

    out.accept("");
    out.accept("Matching night: (Wahrscheinlichkeit, dass das jeweilige Paar ein Perfect Match ist.)");
    for (Pair pair : pairs) {
      out.accept(pair + ": " + prozent(result.getCount(pair), result.possibleConstellations.size()) + "%");
    }
    out.accept("");
    out.accept("Wahrscheinlichkeit für entsprechende Spotanzahl:");
    for (int i = 0, l = 11; i < l; i++) {
      String marker = "";
      if (i == spotsReached) {
        marker = " <==";
      }
      out.accept(
            i + " => " + prozent(lightResults[i], result.possibleConstellations.size()) + "% [" + lightResults[i] + "]"
                  + marker);
    }
    out.accept("Die Kombinationen reduzieren sich: " + result.possibleConstellations.size() + " => "
          + lightResults[spotsReached]);

  }

  private String getRanking(AYTO_Result resulter, Frau frau, List<Mann> maenner) {
    int gesamt = 0;
    for (Mann mann : maenner) {
      if (maenner.indexOf(mann) <= 9) {
        gesamt += resulter.getCount(frau, mann);
      }
    }
    List<Mann> sortedPartner = new ArrayList<>(maenner);
    sortedPartner.sort((o1, o2) -> resulter.getCount(frau, o2)
          .compareTo(resulter.getCount(frau, o1)));
    sortedPartner = sortedPartner.stream()
          .filter(mann -> resulter.getCount(frau, mann) > 0)
          .collect(Collectors.toList());

    StringBuffer stringBuffer = new StringBuffer();
    for (Mann mann : sortedPartner) {
      if (stringBuffer.length() > 0) {
        stringBuffer.append(", ");
      }
      stringBuffer.append(mann + " (" + prozent(resulter.getCount(frau, mann), gesamt) + ")");
    }
    return "[" + stringBuffer.toString() + "]";
  }

  private String getRanking(AYTO_Result resulter, Mann mann, List<Frau> frauen) {
    int gesamt = 0;
    for (Frau frau : frauen) {
      if (frauen.indexOf(frau) <= 9) {
        gesamt += resulter.getCount(frau, mann);
      }
    }
    List<Frau> sortedPartner = new ArrayList<>(frauen);
    sortedPartner.sort((o1, o2) -> resulter.getCount(o2, mann)
          .compareTo(resulter.getCount(o1, mann)));
    sortedPartner = sortedPartner.stream()
          .filter(frau -> resulter.getCount(frau, mann) > 0)
          .collect(Collectors.toList());

    StringBuffer stringBuffer = new StringBuffer();
    for (Frau frau : sortedPartner) {
      if (stringBuffer.length() > 0) {
        stringBuffer.append(", ");
      }
      stringBuffer.append(frau + " (" + prozent(resulter.getCount(frau, mann), gesamt) + ")");
    }
    return "[" + stringBuffer.toString() + "]";
  }

}
