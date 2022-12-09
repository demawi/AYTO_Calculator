package demawi.ayto.print;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import demawi.ayto.Formatter;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.Pair;
import demawi.ayto.modell.Tag;
import demawi.ayto.service.CalculationOptions;
import demawi.ayto.service.MatchFinder;

public class DefaultMatchPrinter
      extends MatchPrinter {

  /**
   * Berechnet die vorherigen Lichter Wahrscheinlichkeiten indem die letzte Matching Night herausgenommen wird.
   */
  public void printDayResults(AYTO_Data data, int tagNr) {
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
      // Falls neue Personen hinzugekommen sind: Berechnung des Ende des Vortages
      resultDesVortages = calculate(data, tagNr - 1, Integer.MAX_VALUE, true);
    }

    data.preProcess(out, tagNr);

    // Tages Anfang mit Prüfug zum Vortag falls neue Personen hinzugekommen sind
    AYTO_Result letztesResultat = calculate(data, tagNr, 0, false);
    if (resultDesVortages != null) {
      out.accept("Die Anzahl der Kombinationen hat sich verändert: " + resultDesVortages.getPossibleConstellationSize()
            + " => " + letztesResultat.getPossibleConstellationSize());
    }

    // Berechne Matchboxen...
    Tag tag = data.getTag(tagNr);
    for (int i = 0, l = tag.boxResults.size(); i < l; i++) {
      MatchBoxResult matchBoxResult = tag.boxResults.get(i);
      Pair boxPair = matchBoxResult.pair;
      Boolean boxResult = matchBoxResult.result;
      if (boxResult == null) {
        out.accept("");
      }
      out.accept("MatchBox: " + boxPair + (boxResult == null ? " verkauft." : "...")
            + " (Wahrscheinlichkeit für das Ausgang des Ergebnisses)");
      String yesMarker = "";
      String noMarker = "";
      int yesCombinations = letztesResultat.getCount(boxPair);
      int noCombinations = letztesResultat.getPossibleConstellationSize() - letztesResultat.getCount(boxPair);
      int newCombinationCount = 0;
      if (boxResult != null) {
        if (boxResult) {
          yesMarker = " <==";
          newCombinationCount = yesCombinations;
        }
        else {
          noMarker = " <==";
          newCombinationCount = noCombinations;
        }
      }
      out.accept("Ja   => " + Formatter.prozent(letztesResultat.getCount(boxPair),
            letztesResultat.getPossibleConstellationSize()) + "% [" + yesCombinations + "]" + yesMarker);
      out.accept("Nein => " + Formatter.prozent(
            letztesResultat.getPossibleConstellationSize() - letztesResultat.getCount(boxPair),
            letztesResultat.getPossibleConstellationSize()) + "% [" + noCombinations + "]" + noMarker);
      if (boxResult != null) {
        out.accept("Die Kombinationen reduzieren sich: " + letztesResultat.getPossibleConstellationSize() + " => "
              + newCombinationCount);
        letztesResultat = calculate(data, tagNr, i + 1, false);
      }

    }

    // Berechne Matching night...
    MatchingNight matchingNight = tag.matchingNight;
    if (matchingNight == null || matchingNight.lights == null) {
      out.accept("");
      out.accept("Es hat keine Matching Night stattgefunden!");
    }
    else {
      printLightChances(letztesResultat, matchingNight.constellation, matchingNight.lights);
      // Ende des Tages Resultat
      letztesResultat = calculate(data, tagNr, Integer.MAX_VALUE, true);
    }
    printResult(data, letztesResultat, frauen, maenner);
  }

  protected AYTO_Result calculate(AYTO_Data data, int tagNr, int mitAnzahlMatchBoxen, boolean mitMatchingNight) {
    return new MatchFinder(new CalculationOptions(data, tagNr, mitAnzahlMatchBoxen, mitMatchingNight)).calculate(out,
          true);
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
          possOut = Formatter.numberFormat(possibility * 100) + " %";
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
        out.accept(pair + " => " + Formatter.prozent(result.getCount(pair), result.getPossibleConstellationSize()) + "%");
      }
    }

    if (false) { // result.getAllPossibleConstellations() geht aktuell nicht, um Daten zu sparen
      List<Set<Pair>> sortedConstellations = new ArrayList<>(result.getAllPossibleConstellations());
      Collections.sort(sortedConstellations, (pairs1, pairs2) -> {
        Integer o1Count = pairs1.stream()
              .mapToInt(result.pairCount::get)
              .sum();
        Integer o2Count = pairs2.stream()
              .mapToInt(result.pairCount::get)
              .sum();
        return o2Count.compareTo(o1Count);
      });
      out.accept("");
      out.accept("==== Beste Konstellationen ====");
      for (int i = 0, l = Math.min(20, sortedConstellations.size()); i < l; i++) {
        Set<Pair> constellation = sortedConstellations.get(i);
        out.accept("> Platz " + (i + 1) + " mit " + constellation.stream()
              .mapToInt(result.pairCount::get)
              .sum()
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
    out.accept("Matching night: (Wahrscheinlichkeit, dass das jeweilige Paar ein Perfect Match ist.)");
    for (Pair pair : pairs) {
      out.accept(pair + ": " + Formatter.prozent(result.getCount(pair), result.getPossibleConstellationSize()) + "%");
    }
    out.accept("");
    out.accept("Wahrscheinlichkeit für entsprechende Spotanzahl:");
    for (int i = 0, l = 11; i < l; i++) {
      String marker = "";
      if (i == spotsReached) {
        marker = " <==";
      }
      out.accept(i + " => " + Formatter.prozent(lightResults[i], result.getPossibleConstellationSize()) + "% ["
            + lightResults[i] + "]" + marker);
    }
    out.accept("Die Kombinationen reduzieren sich: " + result.getPossibleConstellationSize() + " => "
          + lightResults[spotsReached]);
  }

}
