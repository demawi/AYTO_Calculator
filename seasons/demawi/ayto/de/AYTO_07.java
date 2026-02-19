package demawi.ayto.de;

import demawi.ayto.modell.Man;
import demawi.ayto.modell.PermutationConfiguration;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.modell.Woman;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;
import static demawi.ayto.permutation.Mark.CAN_BE_AN_EXTRA_MATCH;

public class AYTO_07
      extends SeasonData {

   private final Woman Adrianna = frau("Adrianna").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Alicia = frau("Alicia").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Aurora = frau("Aurora", true).mark(
         CAN_BE_AN_EXTRA_MATCH); // Nachzüglerin, aber nicht zwangsweise im Doppelmatch vertreten
   private final Woman Elena = frau("Elena").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Ella = frau("Ella").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Laura = frau("Laura").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Linda = frau("Linda").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Marla = frau("Marla").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Michelle = frau("Michelle").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Tiziana = frau("Tiziana").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Tonia = frau("Tonia").mark(CAN_BE_AN_EXTRA_MATCH);

   private final Man Chris = mann("Chris");
   private final Man Ema = mann("Ema");
   private final Man Evi = mann("Evi");
   private final Man Jeronymo = mann("Jeronymo");
   private final Man Jerry = mann("Jerry");
   private final Man Julian_M = mann("Julian_M");
   private final Man Julian_S = mann("Julian_S"); // "Julien"
   private final Man Luke = mann("Luke");
   private final Man Meji = mann("Meji");
   private final Man Noel = mann("Noel");

   public AYTO_07() {
      super(new PermutationConfiguration(Mode.MARKED));

      newDay().addNew(Aurora)
            .matchBox(pair(Tiziana, Evi), false)
            .matchNight(3, pair(Julian_S, Aurora), pair(Adrianna, Julian_M), pair(Jerry, Elena),
                  pair(Tiziana, Jeronymo), pair(Marla, Luke), pair(Tonia, Noel), pair(Michelle, Ema), pair(Laura, Evi),
                  pair(Ella, Meji), pair(Linda, Chris)); // Alicia

      newDay().matchBox(pair(Laura, Evi), null)
            .matchNight(2, pair(Tonia, Chris), pair(Tiziana, Jeronymo), pair(Adrianna, Julian_S), pair(Ella, Ema),
                  pair(Alicia, Julian_M), pair(Linda, Noel), pair(Marla, Luke), pair(Michelle, Jerry), pair(Laura, Evi),
                  pair(Elena, Meji)); // Aurora

      newDay().matchBox(pair(Linda, Julian_S), false)
            .matchNight(2, pair(Marla, Luke), pair(Alicia, Julian_S), pair(Adrianna, Julian_M), pair(Tonia, Noel),
                  pair(Aurora, Jerry), pair(Elena, Meji), pair(Linda, Chris), pair(Laura, Evi), pair(Tiziana, Jeronymo),
                  pair(Michelle, Ema)); // Ella

      //newDay().matchBox(pair(Marla, Julian_M), true); // Matching Night wurde verkauft

      //newDay().matchBox(pair(Aurora, Chris), false);
   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_07()).printLastDayResults();
   }

}
