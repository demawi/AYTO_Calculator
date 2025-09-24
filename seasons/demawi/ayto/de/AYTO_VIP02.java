package demawi.ayto.de;

import demawi.ayto.modell.PermutationConfiguration;
import demawi.ayto.modell.Woman;
import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;
import static demawi.ayto.permutation.Mark.CAN_BE_AN_EXTRA_MATCH;

public class AYTO_VIP02
      extends SeasonData {

   private final Woman Ricarda = frau("Ricarda");
   private final Woman Luisa = frau("Luisa");
   private final Woman Cecilia = frau("Cecilia");
   private final Woman Karina = frau("Karina");
   private final Woman Isabelle = frau("Isabelle");
   private final Woman Gina = frau("Gina");
   private final Woman Celina = frau("Celina");
   private final Woman Zoe = frau("Zoe");
   private final Woman Franziska = frau("Franziska");
   private final Woman Anna = frau("Anna");

   private final Man Calvin = mann("Calvin");
   private final Man Martin = mann("Martin");
   private final Man Lukas = mann("Lukas");
   private final Man Maurice = mann("Maurice");
   private final Man Amadu = mann("Amadu");
   private final Man Pharrell = mann("Pharrell");
   private final Man Fabio = mann("Fabio");
   private final Man Max = mann("Max");
   private final Man Michael = mann("Michael");
   private final Man Luca = mann("Luca");
   private final Man Felix = mann("Felix", CAN_BE_AN_EXTRA_MATCH, true);

   public AYTO_VIP02() {
      super(new PermutationConfiguration(Mode.MARKED));

      newDay().matchBox(pair(Zoe, Martin), false)
            .matchNight(3, pair(Franziska, Max), pair(Anna, Michael), pair(Celina, Martin), pair(Ricarda, Maurice),
                  pair(Gina, Calvin), pair(Cecilia, Amadu), pair(Karina, Pharrell), pair(Isabelle, Lukas),
                  pair(Luisa, Fabio), pair(Zoe, Luca));

      newDay().matchBox(pair(Karina, Pharrell), false)
            .matchNight(2, pair(Ricarda, Maurice), pair(Isabelle, Martin), pair(Anna, Michael), pair(Gina, Amadu),
                  pair(Luisa, Calvin), pair(Franziska, Luca), pair(Celina, Max), pair(Zoe, Lukas), pair(Karina, Fabio),
                  pair(Cecilia, Pharrell));

      newDay().matchBox(pair(Celina, Lukas), false)
            .matchNight(3, pair(Franziska, Lukas), pair(Karina, Calvin), pair(Celina, Fabio), pair(Zoe, Max),
                  pair(Isabelle, Luca), pair(Luisa, Pharrell), pair(Cecilia, Amadu), pair(Ricarda, Maurice),
                  pair(Anna, Martin), pair(Gina, Michael));

      newDay().addNew(Felix)
            .matchBox(pair(Luisa, Lukas), true);

      newDay().matchBox(pair(Ricarda, Felix), false)
            .matchNight(3, pair(Luisa, Lukas), pair(Cecilia, Amadu), pair(Anna, Pharrell), pair(Franziska, Michael),
                  pair(Ricarda, Max), pair(Zoe, Luca), pair(Celina, Fabio), pair(Karina, Martin),
                  pair(Isabelle, Maurice), pair(Gina, Felix));  // Calvin

      newDay().matchBox(pair(Isabelle, Pharrell), false)
            .matchNight(3, pair(Luisa, Lukas), pair(Isabelle, Martin), pair(Ricarda, Pharrell), pair(Cecilia, Maurice),
                  pair(Anna, Michael), pair(Franziska, Max), pair(Celina, Fabio), pair(Gina, Felix),
                  pair(Karina, Calvin), pair(Zoe, Luca)); // Amadu

      newDay().matchBox(pair(Cecilia, Amadu), true)
            .matchNight(4, pair(Luisa, Lukas), pair(Cecilia, Amadu), pair(Gina, Felix), pair(Isabelle, Martin),
                  pair(Anna, Michael), pair(Ricarda, Pharrell), pair(Celina, Fabio), pair(Karina, Calvin),
                  pair(Franziska, Max), pair(Zoe, Luca)); // Maurice

      newDay().matchBox(pair(Anna, Michael), true)
            .matchNight(5, pair(Luisa, Lukas), pair(Anna, Michael), pair(Cecilia, Amadu), pair(Franziska, Calvin),
                  pair(Celina, Luca), pair(Karina, Martin), pair(Zoe, Maurice), pair(Ricarda, Pharrell),
                  pair(Gina, Fabio), pair(Isabelle, Felix)); // Max

      newDay().matchBox(pair(Ricarda, Pharrell), false)
            .matchNight(7, pair(Luisa, Lukas), pair(Anna, Michael), pair(Cecilia, Amadu), pair(Gina, Calvin),
                  pair(Franziska, Max), pair(Ricarda, Maurice), pair(Celina, Fabio), pair(Zoe, Pharrell),
                  pair(Karina, Martin), pair(Isabelle, Felix)); // Luca

      newDay().matchBox(pair(Franziska, Max), false)
            .matchNight(10, pair(Luisa, Lukas), pair(Anna, Michael), pair(Cecilia, Amadu), pair(Karina, Luca),
                  pair(Celina, Fabio), pair(Franziska, Calvin), pair(Zoe, Pharrell), pair(Ricarda, Maurice),
                  pair(Gina, Martin), pair(Isabelle, Felix)); // Max

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_VIP02()).printLastDayResults();
   }

}
