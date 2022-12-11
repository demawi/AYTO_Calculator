package demawi.ayto.print;

import java.util.List;

import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.Pair;
import demawi.ayto.modell.Tag;

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
    if (tagNr > 1 && (frauen.size() > frauenBefore.size() || maenner.size() > maennerBefore.size())) {
      // Falls neue Personen hinzugekommen sind: Berechnung des Ende des Vortages
      resultDesVortages = calculateSingle(data, tagNr - 1, Integer.MAX_VALUE, true);

      for (Frau frau : frauen) {
        if (!frauenBefore.contains(frau)) {
          out.accept(
                "Eine Frau ist hinzugekommen: " + frau.getName() + ". Die Anzahl der Kombinationen erhöht sich damit!");
        }
      }
      for (Mann mann : maenner) {
        if (!maennerBefore.contains(mann)) {
          out.accept(
                "Ein Mann ist hinzugekommen: " + mann.getName() + ". Die Anzahl der Kombinationen erhöht sich damit!");
        }
      }
    }

    data.preProcess(out, tagNr);

    // Tages Anfang mit Prüfug zum Vortag falls neue Personen hinzugekommen sind
    AYTO_Result letztesResultat = calculateSingle(data, tagNr, 0, false);
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
      out.accept("");
      out.accept("MatchBox: " + boxPair + (boxResult == null ? " verkauft." : "...")
            + " (Wahrscheinlichkeit für das Ausgang des Ergebnisses)");
      String yesMarker = "";
      String noMarker = "";
      int yesCombinations = letztesResultat.getPossibleCount(boxPair);
      int noCombinations = letztesResultat.getPossibleConstellationSize() - letztesResultat.getPossibleCount(boxPair);
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
      out.accept("Ja   => " + Formatter.prozent(letztesResultat.getPossibleCount(boxPair),
            letztesResultat.getPossibleConstellationSize()) + "% [" + yesCombinations + "]" + yesMarker);
      out.accept("Nein => " + Formatter.prozent(
            letztesResultat.getPossibleConstellationSize() - letztesResultat.getPossibleCount(boxPair),
            letztesResultat.getPossibleConstellationSize()) + "% [" + noCombinations + "]" + noMarker);
      if (boxResult != null) {
        out.accept("Die Kombinationen reduzieren sich: " + letztesResultat.getPossibleConstellationSize() + " => "
              + newCombinationCount);
        letztesResultat = calculateSingle(data, tagNr, i + 1, false);
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
      letztesResultat = calculateSingle(data, tagNr, Integer.MAX_VALUE, true);
    }
    printPossibilitiesAsTable(data, letztesResultat, frauen, maenner);
  }


}
