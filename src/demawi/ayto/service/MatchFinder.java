package demawi.ayto.service;

import java.util.function.Consumer;

import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.Pair;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.Formatter;

public class MatchFinder {

   private final CalculationOptions calcOptions;

   public MatchFinder(CalculationOptions calcOptions) {
      this.calcOptions = calcOptions;
   }

   public AYTO_Result calculate(Consumer<String> out) {
      long start = System.currentTimeMillis();
      AYTO_Result result = new AYTO_Result(calcOptions);
      if (calcOptions.getTagNr() == 0) {
         return result;
      }
      StaffelData data = calcOptions.getData();
      if (out != null) {
         out.accept("");
         breakLine(out);
         int anzahlMatchBoxen = calcOptions.getAnzahlMatchBoxen();
         out.accept("-- Berechne " + data.name + " - Nacht " + calcOptions.getTagNr() + (
               calcOptions.getAnzahlNeuePersonen() > 0 ?
                     " inkl. " + calcOptions.getAnzahlNeuePersonen() + " neuer Person(en)" : "") + (
               anzahlMatchBoxen > 0 ? " inkl. " + anzahlMatchBoxen + " Matchbox(en)" : "")
               + (calcOptions.isMitMatchingNight() ? " inkl. Matchingnight" : "") + "..." + " [Ereignis#"
               + calcOptions.getEventCount() + "]");
      }

      AYTO_Permutator<Frau, Mann, Pair> permutator = AYTO_Permutator.create(calcOptions.getFrauen(),
            calcOptions.getMaenner(), calcOptions.getZusatztype(), Pair::pair);
      permutator.permutate(constellation -> result.addResult(constellation, calcOptions.isValid(constellation)));
      if (out != null) {
         out.accept(
               "-- Combinations: " + result.totalConstellations + " Possible: " + result.possible + " Not possible: "
                     + result.notPossible + " (Intern constellation-add-pair checks: " + permutator.testCount + ")");
         System.out.println(" Calculation time: " + Formatter.minSecs(System.currentTimeMillis() - start) + " ["
               + calcOptions.getTagNr() + "." + calcOptions.getEventCount() + "]");
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

}
