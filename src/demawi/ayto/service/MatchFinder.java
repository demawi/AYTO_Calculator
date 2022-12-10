package demawi.ayto.service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

import demawi.ayto.Formatter;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.Pair;
import demawi.ayto.modell.Tag;
import demawi.ayto.permutation.AYTO_Permutator;

public class MatchFinder {

   private final CalculationOptions calcOptions;

   public MatchFinder(CalculationOptions calcOptions) {
      this.calcOptions = calcOptions;
   }

   public AYTO_Result calculate(Consumer<String> out, boolean info) {
      long start = System.currentTimeMillis();
      AYTO_Result result = new AYTO_Result(calcOptions);
      if (calcOptions.getTagNr() == 0) {
         return result;
      }
      AYTO_Data data = calcOptions.getData();
      if (info) {
         out.accept("");
         breakLine(out);
         Tag tag = calcOptions.getTag();
         int anzahlMatchBoxen = calcOptions.getAnzahlMatchBoxen(tag.boxResults.size());
         out.accept("-- Berechne " + data.name + " - Nacht " + calcOptions.getTagNr() + (
               anzahlMatchBoxen > 0 ? " inkl. " + anzahlMatchBoxen + " Matchbox(en)" : "")
               + (calcOptions.isMitMatchingNight() ? " inkl. Matchingnight" : "") + "...");
      }

      AYTO_Permutator<Frau, Mann, Pair> permutator = AYTO_Permutator.create(calcOptions.getFrauen(),
            calcOptions.getMaenner(), calcOptions.getZusatztype(), Pair::pair);
      permutator.permutate(constellation -> result.addResult(test(constellation), constellation));
      if (info) {
         out.accept(
               "-- Combinations: " + result.totalConstellations + " Possible: " + result.possible + " Not possible: "
                     + result.notPossible + " (Intern constellation-add-pair checks: " + permutator.testCount + ")");
         System.out.println(" Calculation time: " + Formatter.minSecs(System.currentTimeMillis() - start));
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

   /**
    * Testet ob die übergebene Paar-Konstellation zu einem Widerspruch führt.
    */
   public boolean test(Collection<Pair> constellation) {
      for (int tagNr = 1; tagNr <= calcOptions.getTagNr(); tagNr++) {
         Tag tag = calcOptions.getData()
               .getTag(tagNr);
         boolean istVorherigerTag = tagNr < calcOptions.getTagNr(); // wird komplett abgearbeitet

         int matchBoxes = istVorherigerTag ? tag.boxResults.size() : calcOptions.getAnzahlMatchBoxen(
               tag.boxResults.size());
         if (matchBoxes > 0) {
            for (int matchBoxNr = 0; matchBoxNr < matchBoxes; matchBoxNr++) {
               MatchBoxResult matchBoxResult = tag.boxResults.get(matchBoxNr);
               Boolean result = matchBoxResult.result;
               if (result != null) {
                  if (result != constellation.contains(matchBoxResult.pair)) {
                     return false;
                  }
               }
            }
         }

         for (Map.Entry<Pair, Boolean> entry : tag.implicits.entrySet()) {
            if (entry.getValue() != constellation.contains(entry.getKey())) {
               return false;
            }
         }

         if (istVorherigerTag || calcOptions.isMitMatchingNight()) {
            MatchingNight night = tag.matchingNight;
            if (night != null) {
               int lights = night.getLights(constellation);
               if (lights != night.lights) {
                  return false;
               }
            }
         }
      }
      return true;
   }

}
