package demawi.ayto.service;

import java.util.Map;
import java.util.function.Consumer;

import demawi.ayto.events.Event;
import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.Pair;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.Formatter;

public class MultiMatchFinder {

   int totalConstellations = 0;

   /**
    * Events dürfen keine NewPerson enthalten.
    */
   public void calculate(Map<Event, AYTO_Result> eventResults, PermutationOptions calcOptions,
         Consumer<String> out) {
      long start = System.currentTimeMillis();

      AYTO_Permutator<Frau, Mann, Pair> permutator = AYTO_Permutator.create(calcOptions.getFrauen(),
            calcOptions.getMaenner(), calcOptions.getZusatztype(), Pair::pair);

      permutator.permutate(() -> constellation -> {
         totalConstellations++;
         for (Map.Entry<Event, AYTO_Result> entry : eventResults.entrySet()) {
            if (entry.getKey()
                  .isValid(constellation)) {
               entry.getValue()
                     .addResult(constellation, true);
            }
            else {
               break;
            }
         }
      });

      eventResults.values()
            .forEach(result -> {
               result.fixTotalPossibilities(totalConstellations);
            });

      if (out != null) {
         System.out.println(" Calculation time: " + Formatter.minSecs(System.currentTimeMillis() - start));
      }
   }

}
