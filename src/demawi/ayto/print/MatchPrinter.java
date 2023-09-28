package demawi.ayto.print;

import java.util.*;
import java.util.function.Consumer;

import demawi.ayto.events.MatchingNight;
import demawi.ayto.modell.*;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.service.MatchCalculator;
import demawi.ayto.util.Language;

public abstract class MatchPrinter {

   private Consumer<String> out = System.out::println;
   private Language lang;

   public void setOut(Consumer<String> out) {
      this.out = out;
   }

   public void setLanguage(Language lang) {
      this.lang = lang;
   }

   protected void print(String overallOut) {
      out.accept(overallOut);
   }

   protected void print(String outDE, String outEN) {
      if (lang == Language.EN) {
         out.accept(outEN);
      }
      else {
         out.accept(outDE);
      }
   }

   /**
    * Druckt den letzten Tag der Staffel.
    */
   public void printDayResults(StaffelData data) {
      printDayResults(data, data.getAnzahlTage());
   }

   public abstract void printDayResults(StaffelData data, int tagNr);

   protected List<AYTO_Result> calculate(StaffelData data, int fromTag, int fromEventCount, int toTag,
         int toEventCount) {
      return new MatchCalculator(data, fromTag, fromEventCount, toTag, toEventCount).calculate(out);
   }

   /**
    * Prints the spot probabilities 0..10 for the given constellation.
    *
    * @spotsReached kann null sein, wenn die Entscheidung noch nicht stattgefunden hat.
    */
   public void printLightChances(AYTO_Result result, Collection<AYTO_Pair> pairs, Integer spotsReached) {
      int[] lightResults = result.getLightResultsForLastMatchingNight();

      print("");
      print("Matching night: (Wahrscheinlichkeit, dass das jeweilige Paar ein Perfect Match ist.)");
      for (AYTO_Pair pair : pairs) {
         print(
               pair + ": " + Formatter.prozent(result.getPossibleCount(pair), result.getPossibleConstellationSize())
                     + "%");
      }
      print("");
      print("Wahrscheinlichkeit für entsprechende Spotanzahl:");
      for (int i = 0, l = result.getData()
            .getMatchingPairCount(); i <= l; i++) {
         String marker = "";
         if (spotsReached != null && i == spotsReached) {
            marker = " <==";
         }
         print(i + " => " + Formatter.prozent(lightResults[i], result.getPossibleConstellationSize()) + "% ["
               + lightResults[i] + "]" + marker);
      }
      if (spotsReached != null) {
         print("Die Kombinationen reduzieren sich: " + result.getPossibleConstellationSize() + " => "
               + lightResults[spotsReached]);
      }
      else {
         print("Das Ergebnis steht noch nicht fest.");
      }
   }

   /**
    * Prints all pair probabilities
    */
   public void printPossibilitiesAsTable(AYTO_Result result) {
      print("======= Paar-Wahrscheinlichkeiten (In Klammern: Anzahl gemeinsame Matching Nights bisher) =======");

      List<List<String>> table = new ArrayList<>();
      table.add(new ArrayList<>());
      table.get(0)
            .add("");
      boolean markedPerson = false;
      List<Person> sortedFrauen = new ArrayList<>(result.getFrauen());
      List<Person> sortedMaenner = new ArrayList<>(result.getMaenner());
      sortedFrauen.sort(Comparator.comparing(a -> a.getNamePlusMark()));
      sortedMaenner.sort(Comparator.comparing(a -> a.getNamePlusMark()));
      for (Person frau : sortedFrauen) {
         table.get(0)
               .add(frau.getNamePlusMark());
         if (frau.isMarked()) {
            markedPerson = true;
         }
      }
      for (Person mann : sortedMaenner) {
         if (mann.isMarked()) {
            markedPerson = true;
         }
         List<String> line = new ArrayList<>();
         table.add(line);
         line.add(mann.getNamePlusMark());
         for (Person frau : sortedFrauen) {
            AYTO_Pair pair = AYTO_Pair.pair(frau, mann);
            int count = 0;
            for (Tag tag : result.getTageBisher()) {
               MatchingNight night = tag.getMatchingNight();
               if (night != null && night.constellation.contains(pair)) {
                  count++;
               }
            }
            double possibility = result.getBasePossibility(pair);
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
            line.add(possOut + " (" + count + ")");
         }
      }
      print(TableFormatter.formatAsTable(table));
      print("Mögliche Paare: " + result.possiblePairCount.size() + "/" + (sortedFrauen.size() * sortedMaenner.size()));
      if (markedPerson) {
         if (result.getData().getZusatztype() == AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER) {
            print("*: Diese Person teilt sich ein Match.");
         }
         else {
            print("*: Diese Personen müssen sich ggf. ein Match teilen.");
         }
      }

      if (result.getData().pairsToTrack != null) {
         print("");
         print("==== Tracking der Perfect Matches im Verlauf ====");
         for (AYTO_Pair pair : result.getData().pairsToTrack) {
            print(pair + " => " + Formatter.prozent(result.getPossibleCount(pair),
                  result.getPossibleConstellationSize()) + "%");
         }
      }

      if (result.getAllPossibleConstellations() != null) {
         List<Set<AYTO_Pair>> sortedConstellations = new ArrayList<>(result.getAllPossibleConstellations());
         sortedConstellations.sort((pairs1, pairs2) -> {
            Integer o1Count = pairs1.stream()
                  .mapToInt(result.possiblePairCount::get)
                  .sum();
            Integer o2Count = pairs2.stream()
                  .mapToInt(result.possiblePairCount::get)
                  .sum();
            return o2Count.compareTo(o1Count);
         });
         print("");
         print("==== Beste Konstellationen ====");
         for (int i = 0, l = Math.min(20, sortedConstellations.size()); i < l; i++) {
            Set<AYTO_Pair> constellation = sortedConstellations.get(i);
            print("> Platz " + (i + 1) + " mit " + constellation.stream()
                  .mapToInt(result.possiblePairCount::get)
                  .sum() + " Punkten (Summe der Vorkommen der Einzelpaare)");
            for (AYTO_Pair pair : constellation) {
               print(pair.toString());
            }
            print("");
         }
      }
   }
}
