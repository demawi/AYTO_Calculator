package demawi.ayto.service;

import java.util.Collection;
import java.util.List;
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
import demawi.ayto.perm.AYTO_Permutator;

public class MatchFinder {

   private CalculationOptions calcOptions;

   public MatchFinder(CalculationOptions calcOptions) {
      this.calcOptions = calcOptions;
   }

   public AYTO_Result calculate(Consumer<String> out, boolean info) {
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
      permutator.permutate(constellation -> result.addResult(test(constellation, debug), constellation));
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
   public boolean test(Collection<Pair> constellation, boolean debug) {
      for (int tagNr = 1; tagNr < calcOptions.tagNr + 1; tagNr++) {
         Tag tag = calcOptions.getData().getTag(tagNr);
         boolean istVorherigerTag = tagNr < calcOptions.tagNr; // wird komplett abgearbeitet

         int matchBoxes = istVorherigerTag ? tag.boxResults.size() : calcOptions.getAnzahlMatchBoxen(tag.boxResults.size());
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

         if (istVorherigerTag || calcOptions.mitMatchingNight) {
            MatchingNight night = tag.matchingNight;
            if (night != null) {
               for (int nightNr = 0, l = night.constellation.size(); nightNr < l; nightNr++) {
                  int lights = calcOptions.getData().getLights(constellation, night.constellation);
                  if (lights != night.lights) {
                     if (debug) {
                        System.out.println(
                              "Falsch aufgrund von Matching Night Nr. " + (nightNr + 1) + " Erwartete Lichter: "
                                    + lights + ". Es waren aber: " + night.lights);
                     }
                     return false;
                  }
               }
            }
         }
      }
      return true;
   }

}
