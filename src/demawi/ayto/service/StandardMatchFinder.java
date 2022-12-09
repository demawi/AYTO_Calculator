package demawi.ayto.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import demawi.ayto.perm.AYTO_Permutator;
import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.CalculationOptions;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.modell.Pair;
import demawi.ayto.print.TablePrinter;
import demawi.ayto.modell.Tag;

public class StandardMatchFinder
      implements IMatchFinder {

  private Consumer<String> out = (str) -> {
    System.out.println(str);
  };

  private static String prozent(double anteil, double von) {
    return String.format("%.2f", 100 * anteil / von);
  }

  private static String numberFormat(double number) {
    return String.format("%5s", String.format("%.2f", number));
  }

  public static String minSecs(long mills) {
    long min = Math.floorDiv(mills, 60 * 1000);
    long secs = Math.floorDiv(mills - min * 60 * 1000, 1000);
    return min + " min " + secs + " secs";
  }

  public void setOut(Consumer<String> out) {
    this.out = out;
  }

  private AYTO_Result calculate(CalculationOptions calcOptions, boolean info) {
    long start = System.currentTimeMillis();
    AYTO_Result result = new AYTO_Result(calcOptions);
    if (calcOptions.tagNr == 0) {
      return result;
    }
    AYTO_Data data = calcOptions.getData();
    if (info) {
      out.accept("");
      breakLine(out);
      Tag tag = calcOptions.getTag();
      int anzahlMatchBoxen = calcOptions.getAnzahlMatchBoxen(tag.boxResults.size());
      out.accept("-- Berechne " + data.name + " - Nacht " + calcOptions.tagNr + (
            anzahlMatchBoxen > 0 ? " inkl. " + anzahlMatchBoxen + " Matchbox(en)" : "")
            + (calcOptions.mitMatchingNight ? " inkl. Matchingnight" : "") + "...");
    }

    boolean debug = false;

    AYTO_Permutator<Frau, Mann, Pair> permutator = AYTO_Permutator.create(calcOptions.getFrauen(),
          calcOptions.getMaenner(), calcOptions.getZusatztype(), Pair::pair);
    permutator.permutate(
          constellation -> result.addResult(calcOptions.test(constellation, debug), constellation));
    if (info) {
      out.accept("-- Combinations: " + result.totalConstellations + " Possible: " + result.possible + " Not possible: "
            + result.notPossible + " (Intern constellation-add-pair checks: " + permutator.testCount + ")");
      System.out.println(" Calculation time: " + minSecs(System.currentTimeMillis() - start));
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
    AYTO_Result resultDesVortages = null;
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
      resultDesVortages = calculate(new CalculationOptions(data, tagNr - 1, Integer.MAX_VALUE, true), info);
    }

    data.preProcess(out, tagNr);

    // Tages Anfang mit Prüfug zum Vortag falls neue Personen hinzugekommen sind
    AYTO_Result letztesResultat = calculate(new CalculationOptions(data, tagNr, 0, false), info);
    if (resultDesVortages != null) {
      out.accept("Die Anzahl der Kombinationen hat sich verändert: " + resultDesVortages.getPossibleConstellationSize()
            + " => " + letztesResultat.getPossibleConstellationSize());
    }

    // Matchboxen
    Tag tag = data.getTag(tagNr);
    for (int i = 0, l = tag.boxResults.size(); i < l; i++) {
      MatchBoxResult matchBoxResult = tag.boxResults.get(i);
      Pair boxPair = matchBoxResult.pair;
      Boolean boxResult = matchBoxResult.result;
      AYTO_Result aktuellesResultat = null;
      if (boxResult != null) {
        aktuellesResultat = calculate(new CalculationOptions(data, tagNr, i + 1, false), info);
      }
      if(boxResult == null) {
        out.accept("");
      }
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
      out.accept("Ja   => " + prozent(letztesResultat.getCount(boxPair), letztesResultat.getPossibleConstellationSize())
            + "% [" + letztesResultat.getCount(boxPair) + "]" + yesMarker);
      out.accept(
            "Nein => " + prozent(letztesResultat.getPossibleConstellationSize() - letztesResultat.getCount(boxPair),
                  letztesResultat.getPossibleConstellationSize()) + "% [" + (
                  letztesResultat.getPossibleConstellationSize() - letztesResultat.getCount(boxPair)) + "]" + noMarker);
      if (aktuellesResultat != null) {
        out.accept("Die Kombinationen reduzieren sich: " + letztesResultat.getPossibleConstellationSize() + " => "
              + aktuellesResultat.getPossibleConstellationSize());
        letztesResultat = aktuellesResultat;
      }

    }

    // Matching night
    MatchingNight matchingNight = tag.matchingNight;
    if (matchingNight == null || matchingNight.lights == null) {
      out.accept("");
      out.accept("Es hat keine Matching Night stattgefunden!");
    }
    else {
      printLightChances(letztesResultat, matchingNight.constellation, matchingNight.lights);
      // Ende des Tages Resultat
      letztesResultat = calculate(new CalculationOptions(data, tagNr, Integer.MAX_VALUE, true), info);
    }
    printResult(data, letztesResultat, frauen, maenner);
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

}
