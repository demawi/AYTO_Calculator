package demawi.ayto.print;

import java.util.*;
import java.util.function.Consumer;

import demawi.ayto.modell.*;
import demawi.ayto.modell.events.MatchingNight;
import demawi.ayto.service.MatchCalculator;
import demawi.ayto.util.Language;

public abstract class MatchPrinter {

   private Consumer<String> out = System.out::println;
   protected Language lang;
   protected final SeasonData data;

   public MatchPrinter(SeasonData data) {
      this.data = data;
   }

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
      if (lang == Language.DE) {
         out.accept(outDE);
      }
      else {
         out.accept(outEN);
      }
   }

   protected void print(String outDE, String outEN, String... parameters) {
      if (lang == Language.DE) {
         out.accept(String.format(outDE, (Object[]) parameters));
      }
      else {
         out.accept(String.format(outEN, (Object[]) parameters));
      }
   }

   protected void printCombinationChange(long before, long after) {
      if (after < before) {
         print("Die Anzahl der Kombinationen hat sich reduziert: %s", "The number of combinations has decreased: %s",
               before + " => " + after);
      }
      else if (after > before) {
         print("Die Anzahl der Kombinationen hat sich erhöht: %s", "The number of combinations has increased: %s",
               before + " => " + after);
      }
      else {
         print("Die Anzahl der Kombinationen bleibt unverändert: %s", "The number of combinations stays unchanged: %s",
               "" + before);
      }
   }

   /**
    * Druckt den letzten Tag der Staffel.
    */
   public void printLastDayResults() {
      printLastDayResults(data.getAnzahlTage());
   }

   public abstract void printLastDayResults(int tagNr);

   protected List<AYTO_Result> calculate(int fromTag, int fromEventCount, int toTag,
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
      print("Matching night: (Wahrscheinlichkeit, dass das jeweilige Paar ein Perfect Match ist.)",
            "Matchup ceremony: (Probability that the respective pair is a perfect match.)");
      for (AYTO_Pair pair : pairs) {
         print(pair + ": " + Formatter.prozent(result.getPossibleCount(pair), result.getPossibleConstellationSize())
               + "%");
      }
      List<Person> missingPersons = new ArrayList<>();
      result.getMaenner()
            .stream()
            .filter(person -> pairs.stream()
                  .noneMatch(pair -> person.equals(pair.frau) || person.equals(pair.mann)))
            .forEach(missingPersons::add);
      result.getFrauen()
            .stream()
            .filter(person -> pairs.stream()
                  .noneMatch(pair -> person.equals(pair.frau) || person.equals(pair.mann)))
            .forEach(missingPersons::add);
      for (Person missingPerson : missingPersons) {
         print("Kein Partner: %s", "No partner: %s", "" + missingPerson.getName());
      }

      print("");
      print("Wahrscheinlichkeit für entsprechende Spotanzahl:", "Probability of corresponding number of spots:");
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
         printCombinationChange(result.getPossibleConstellationSize(), lightResults[spotsReached]);
      }
      else {
         print("Das Ergebnis steht noch nicht fest.", "The result is still pending.");
      }
   }

   /**
    * Prints all pair probabilities
    */
   public void printPossibilitiesAsTable(AYTO_Result result) {
      print("======= Paar-Wahrscheinlichkeiten (In Klammern: Anzahl gemeinsame Matching Nights bisher) =======",
            "======= Pair probabilities (In brackets: number of matching nights together so far) =======");

      List<List<String>> table = new ArrayList<>();
      table.add(new ArrayList<>());
      table.get(0)
            .add("");
      int markedPerson = 0;
      List<Person> sortedFrauen = new ArrayList<>(result.getFrauen());
      List<Person> sortedMaenner = new ArrayList<>(result.getMaenner());
      sortedFrauen.sort(Comparator.comparing(a -> a.getNamePlusMark()));
      sortedMaenner.sort(Comparator.comparing(a -> a.getNamePlusMark()));
      for (Person frau : sortedFrauen) {
         table.get(0)
               .add(frau.getNamePlusMark());
         if (frau.isMarked()) {
            markedPerson++;
         }
      }
      for (Person mann : sortedMaenner) {
         if (mann.isMarked()) {
            markedPerson++;
         }
         List<String> line = new ArrayList<>();
         table.add(line);
         line.add(mann.getNamePlusMark());
         for (Person frau : sortedFrauen) {
            AYTO_Pair pair = AYTO_Pair.pair(frau, mann);
            int count = 0;
            for (Day tag : result.getTageBisher()) {
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
      print("Mögliche Paare: %s", "Possible pairs: %s",
            result.possiblePairCount.size() + "/" + (sortedFrauen.size() * sortedMaenner.size()));
      if (markedPerson == 1) {
         print("*: Diese Person teilt sich ein Match.", "*: this person shares a match.");
      }
      else if (markedPerson > 1) {
         print("*: Diese Personen müssen sich ggf. ein Match teilen.", "*: These persons may share a match.");
      }

      if (result.getData().pairsToTrack != null) {
         print("");
         print("==== Tracking der Perfect Matches im Verlauf ====", "==== Tracking of perfect matches over time ====");
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
         print("==== Beste Konstellationen ====", "==== Best constellations ====");
         for (int i = 0, l = Math.min(20, sortedConstellations.size()); i < l; i++) {
            Set<AYTO_Pair> constellation = sortedConstellations.get(i);
            if (lang == Language.DE) {
               print("> Platz " + (i + 1) + " mit " + constellation.stream()
                     .mapToInt(result.possiblePairCount::get)
                     .sum() + " Punkten (Summe der Vorkommen der Einzelpaare)");
            }
            else {
               print("> Place " + (i + 1) + " with " + constellation.stream()
                     .mapToInt(result.possiblePairCount::get)
                     .sum() + " points (Sum of the occurrences of the individual pairs)");
            }
            for (AYTO_Pair pair : constellation) {
               print(pair.toString());
            }
            print("");
         }
      }
   }
}
