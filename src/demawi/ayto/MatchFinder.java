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
import demawi.ayto.modell.CalculationOptions;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.MatchBoxResult;
import demawi.ayto.modell.MatchingNight;
import demawi.ayto.modell.Pair;
import demawi.ayto.modell.TablePrinter;
import demawi.ayto.modell.Tag;

public class MatchFinder {

  private Consumer<String> out = (str) -> {
    System.out.println(str);
  };

  private static String prozent(double anteil, double von) {
    return String.format("%.2f", 100 * anteil / von);
  }

  private static String numberFormat(double number) {
    return String.format("%5s", String.format("%.2f", number));
  }

  private static String minSecs(long mills) {
    long min = Math.floorDiv(mills, 60 * 1000);
    long secs = Math.floorDiv(mills - min * 60 * 1000, 1000);
    return min + " min " + secs + " secs";
  }

  public void setOut(Consumer<String> out) {
    this.out = out;
  }

  private AYTO_Result calculate(AYTO_Data data, CalculationOptions calcOptions, boolean info) {
    long start = System.currentTimeMillis();
    AYTO_Result result = new AYTO_Result(data, calcOptions);
    if (calcOptions.tagNr == 0) {
      return result;
    }
    if (info) {
      out.accept("");
      breakLine(out);
      Tag tag = data.getTag(calcOptions);
      int anzahlMatchBoxen = calcOptions.getAnzahlMatchBoxen(tag.boxPairs.size());
      out.accept("-- Berechne " + data.name + " - Nacht " + calcOptions.tagNr + (
            anzahlMatchBoxen > 0 ? " inkl. " + anzahlMatchBoxen + " Matchbox(en)" : "")
            + (calcOptions.mitMatchingNight ? " inkl. Matchingnight" : "") + "...");
    }

    boolean debug = false;

    AYTO_Permutator<Frau, Mann, Pair> permutator = AYTO_Permutator.create(data.getFrauen(calcOptions.tagNr),
          data.getMaenner(calcOptions.tagNr), data.getZusatztype(), Pair::pair);
    permutator.permutate(
          constellation -> result.addResult(data.test(constellation, calcOptions, debug), constellation));
    if (info) {
      out.accept("-- Combinations: " + result.totalConstellations + " Possible: " + result.possible + " Not possible: "
            + result.notPossible + " Calculation time: " + minSecs(System.currentTimeMillis() - start)
            + " (Intern constellation-add-pair checks: " + permutator.testCount + ")");
      breakLine2(out);
    }
    return result;
  }

  public void breakLine(Consumer<String> out) {
    out.accept(
          "==================================================================================================================================================");
  }

  public void breakLine2(Consumer<String> out) {
    out.accept(
          "--------------------------------------------------------------------------------------------------------------------------------------------------");
  }

  public void printDayResults(AYTO_Data data) {
    printDayResults(data, data.getAnzahlTage());
  }

  /**
   * Berechnet die vorherigen Lichter Wahrscheinlichkeiten indem die letzte Matching Night herausgenommen wird.
   */
  public void printDayResults(AYTO_Data data, int tagNr) {
    boolean info = true;
    List<Frau> frauenBefore = data.getFrauen(tagNr - 1);
    List<Mann> maennerBefore = data.getMaenner(tagNr - 1);

    List<Frau> frauen = data.getFrauen(tagNr);
    List<Mann> maenner = data.getMaenner(tagNr);

    // Kombinationsänderungen, wenn Leute hinzugekommen sind
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
      result0 = calculate(data, new CalculationOptions(tagNr - 1, Integer.MAX_VALUE, true), info);
    }

    // Vorher Result
    AYTO_Result resultVorMatchBox_X = calculate(data, new CalculationOptions(tagNr, 0, false), info);
    if (result0 != null) {
      out.accept("Die Anzahl der Kombinationen hat sich verändert: " + result0.getPossibleConstellationSize() + " => "
            + resultVorMatchBox_X.getPossibleConstellationSize());
    }

    // Zwischen Result
    Tag letzterTag = data.getTag(tagNr);
    AYTO_Result resultVorMatchingNight = resultVorMatchBox_X;
    for (int i = 0, l = letzterTag.boxPairs.size(); i < l; i++) {
      resultVorMatchBox_X = resultVorMatchingNight;
      resultVorMatchingNight = calculate(data, new CalculationOptions(tagNr, i + 1, false), info);
      MatchBoxResult matchBoxResult = letzterTag.boxPairs.get(i);
      Pair boxPair = matchBoxResult.pair;
      Boolean boxResult = matchBoxResult.result;
      out.accept("MatchBox: " + boxPair + (boxResult == null ? " verkauft." : "...")
            + " (Wahrscheinlichkeit für das Ausgang des Ergebnisses)");
      String yesMarker = "";
      String noMarker = "";
      if (boxResult != null) {
        if (boxResult) {
          yesMarker = " <==";
        }
        else {
          noMarker = " <==";
        }
      }
      out.accept("Ja   => " + prozent(resultVorMatchBox_X.getCount(boxPair),
            resultVorMatchBox_X.getPossibleConstellationSize()) + "% [" + resultVorMatchBox_X.getCount(boxPair) + "]"
            + yesMarker);
      out.accept("Nein => " + prozent(
            resultVorMatchBox_X.getPossibleConstellationSize() - resultVorMatchBox_X.getCount(boxPair),
            resultVorMatchBox_X.getPossibleConstellationSize()) + "% [" + (
            resultVorMatchBox_X.getPossibleConstellationSize() - resultVorMatchBox_X.getCount(boxPair)) + "]"
            + noMarker);
      out.accept("Die Kombinationen reduzieren sich: " + resultVorMatchBox_X.getPossibleConstellationSize() + " => "
            + resultVorMatchingNight.getPossibleConstellationSize());
    }

    MatchingNight matchingNight = letzterTag.matchingNight;
    if (matchingNight == null) {
      out.accept("");
      out.accept("Es hat keine Matching Night stattgefunden!");
    }
    else {
      printLightChances(resultVorMatchingNight, matchingNight.constellation, matchingNight.lights);
    }

    // Ende des Tages Result
    AYTO_Result resultNachMatchingNight = calculate(data, new CalculationOptions(tagNr, Integer.MAX_VALUE, true), info);
    printResult(data, resultNachMatchingNight, frauen, maenner);
  }

  /**
   * Prints all pair probabilities
   */
  public void printResult(AYTO_Data data, AYTO_Result result, List<Frau> frauen, List<Mann> maenner) {
    Comparator<Pair> pairComparator = (pair1, pair2) -> result.pairCount.get(pair2)
          .compareTo(result.pairCount.get(pair1));

    List<Pair> sortedPairs = new ArrayList<>(result.pairCount.keySet());
    Collections.sort(sortedPairs, pairComparator);

    out.accept("======= Paar-Wahrscheinlichkeiten (In Klammern: Anzahl gemeinsame Matching Nights) =======");
    for (Frau frau : frauen) {
      Set<Mann> set = result.frauCount.get(frau);
      //out.accept(frau + ": " + (set == null ? "-" : set.size() + " " + getRanking(result, frau, maenner)));
    }
    out.accept("");
    for (Mann mann : maenner) {
      Set<Frau> set = result.mannCount.get(mann);
      //out.accept(mann + ": " + (set == null ? "-" : set.size() + " " + getRanking(result, mann, frauen)));
    }

    List<List<String>> table = new ArrayList<>();
    table.add(new ArrayList<>());
    table.get(0)
          .add("");
    List<Frau> sortedFrauen = new ArrayList<>(frauen);
    List<Mann> sortedMaenner = new ArrayList<>(maenner);
    sortedFrauen.sort(Comparator.comparing(a -> a.name));
    sortedMaenner.sort(Comparator.comparing(a -> a.name));
    for (Frau frau : sortedFrauen) {
      table.get(0)
            .add(frau.name);
    }
    for (Mann mann : sortedMaenner) {
      List<String> line = new ArrayList<>();
      table.add(line);
      line.add(mann.name);
      for (Frau frau : sortedFrauen) {
        Pair pair = Pair.pair(frau, mann);
        int count = 0;
        for (Tag tag : data.getTage()) {
          MatchingNight night = tag.matchingNight;
          if (night != null && night.constellation.contains(pair)) {
            count++;
          }
        }
        double possibility = result.getPossibility(mann, frau, frauen);
        String possOut;
        if (possibility == 0d) {
          possOut = "  -    ";
        }
        else if (possibility == 1d) {
          possOut = "  ✓    ";
        }
        else {
          possOut = numberFormat(possibility * 100)+" %";
        }
        line.add(possOut + " (" + count + ")"); //  + "/" + data.tage.size()+
      }
    }
    out.accept(TablePrinter.formatAsTable(table));
    out.accept("Mögliche Paare: " + result.pairCount.size() + "/" + (frauen.size() * maenner.size()));

    if (data.pairsToTrack != null) {
      out.accept("");
      out.accept("==== Tracking der Perfect Matches im Verlauf ====");
      for (Pair pair : data.pairsToTrack) {
        out.accept(pair + " => " + prozent(result.getCount(pair), result.getPossibleConstellationSize()) + "%");
      }
    }

    if (false) { // result.getAllPossibleConstellations() geht aktuell nicht, um Daten zu sparen
      List<Set<Pair>> sortedConstellations = new ArrayList<>(result.getAllPossibleConstellations());
      Collections.sort(sortedConstellations, (pairs1, pairs2) -> {
        Integer o1Count = pairs1.stream()
              .collect(Collectors.summingInt(a -> result.pairCount.get(a)));
        Integer o2Count = pairs2.stream()
              .collect(Collectors.summingInt(a -> result.pairCount.get(a)));
        return o2Count.compareTo(o1Count);
      });
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
  }

  /**
   * Prints the spot probabilities 0..10 for the given constellation.
   */
  public void printLightChances(AYTO_Result result, Collection<Pair> pairs, Integer spotsReached) {
    MatchingNight.validatePairs(pairs);

    int[] lightResults = result.getLightResultsForLastMatchingNight();

    out.accept("");
    out.accept("Anstehende Matching night: (Wahrscheinlichkeit, dass das jeweilige Paar ein Perfect Match ist.)");
    for (Pair pair : pairs) {
      out.accept(pair + ": " + prozent(result.getCount(pair), result.getPossibleConstellationSize()) + "%");
    }
    out.accept("");
    out.accept("Wahrscheinlichkeit für entsprechende Spotanzahl:");
    for (int i = 0, l = 11; i < l; i++) {
      String marker = "";
      if (i == spotsReached) {
        marker = " <==";
      }
      out.accept(
            i + " => " + prozent(lightResults[i], result.getPossibleConstellationSize()) + "% [" + lightResults[i] + "]"
                  + marker);
    }
    out.accept("Die Kombinationen reduzieren sich: " + result.getPossibleConstellationSize() + " => "
          + lightResults[spotsReached]);
  }

  private String getRanking(AYTO_Result result, Frau frau, List<Mann> maenner) {
    int gesamt = 0;
    AYTO_Permutator.ZUSATZTYPE zusatztype = result.getData()
          .getZusatztype();
    for (Mann mann : maenner) {
      if (zusatztype == AYTO_Permutator.ZUSATZTYPE.JEDER || maenner.indexOf(mann) <= 9) {
        gesamt += result.getCount(frau, mann);
      }
    }
    List<Mann> sortedPartner = new ArrayList<>(maenner);
    sortedPartner.sort((o1, o2) -> result.getCount(frau, o2)
          .compareTo(result.getCount(frau, o1)));
    sortedPartner = sortedPartner.stream()
          .filter(mann -> result.getCount(frau, mann) > 0)
          .collect(Collectors.toList());

    StringBuffer stringBuffer = new StringBuffer();
    for (Mann mann : sortedPartner) {
      if (stringBuffer.length() > 0) {
        stringBuffer.append(", ");
      }
      stringBuffer.append(mann + " (" + prozent(result.getCount(frau, mann), gesamt) + ")");
    }
    return "[" + stringBuffer.toString() + "]";
  }

  private String getRanking(AYTO_Result result, Mann mann, List<Frau> frauen) {
    int gesamt = 0;
    AYTO_Permutator.ZUSATZTYPE zusatztype = result.getData()
          .getZusatztype();
    for (Frau frau : frauen) {
      if (zusatztype == AYTO_Permutator.ZUSATZTYPE.JEDER || frauen.indexOf(frau) <= 9) {
        gesamt += result.getCount(frau, mann);
      }
    }
    List<Frau> sortedPartner = new ArrayList<>(frauen);
    sortedPartner.sort((o1, o2) -> result.getCount(o2, mann)
          .compareTo(result.getCount(o1, mann)));
    sortedPartner = sortedPartner.stream()
          .filter(frau -> result.getCount(frau, mann) > 0)
          .collect(Collectors.toList());

    StringBuffer stringBuffer = new StringBuffer();
    for (Frau frau : sortedPartner) {
      if (stringBuffer.length() > 0) {
        stringBuffer.append(", ");
      }
      stringBuffer.append(frau + " (" + prozent(result.getCount(frau, mann), gesamt) + ")");
    }
    return "[" + stringBuffer.toString() + "]";
  }

}
