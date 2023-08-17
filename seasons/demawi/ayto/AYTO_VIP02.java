package demawi.ayto;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_VIP02
      extends StaffelData {

   private final Frau Ricarda = frau("Ricarda");
   private final Frau Luisa = frau("Luisa");
   private final Frau Cecilia = frau("Cecilia");
   private final Frau Karina = frau("Karina");
   private final Frau Isabelle = frau("Isabelle");
   private final Frau Gina = frau("Gina");
   private final Frau Celina = frau("Celina");
   private final Frau Zoe = frau("Zoe");
   private final Frau Franziska = frau("Franziska");
   private final Frau Anna = frau("Anna");

   private final Mann Calvin = mann("Calvin");
   private final Mann Martin = mann("Martin");
   private final Mann Lukas = mann("Lukas");
   private final Mann Maurice = mann("Maurice");
   private final Mann Amadu = mann("Amadu");
   private final Mann Pharrell = mann("Pharrell");
   private final Mann Fabio = mann("Fabio");
   private final Mann Max = mann("Max");
   private final Mann Michael = mann("Michael");
   private final Mann Luca = mann("Luca");
   private final Mann Felix = mann("Felix");

   public AYTO_VIP02() {
      super("VIP_02", AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER);

      newTag().matchBox(pair(Zoe, Martin), false)
            .matchNight(3, pair(Franziska, Max), pair(Anna, Michael), pair(Celina, Martin), pair(Ricarda, Maurice),
                  pair(Gina, Calvin), pair(Cecilia, Amadu), pair(Karina, Pharrell), pair(Isabelle, Lukas),
                  pair(Luisa, Fabio), pair(Zoe, Luca));

      newTag().matchBox(pair(Karina, Pharrell), false)
            .matchNight(2, pair(Ricarda, Maurice), pair(Isabelle, Martin), pair(Anna, Michael), pair(Gina, Amadu),
                  pair(Luisa, Calvin), pair(Franziska, Luca), pair(Celina, Max), pair(Zoe, Lukas), pair(Karina, Fabio),
                  pair(Cecilia, Pharrell));

      newTag().matchBox(pair(Celina, Lukas), false)
            .matchNight(3, pair(Franziska, Lukas), pair(Karina, Calvin), pair(Celina, Fabio), pair(Zoe, Max),
                  pair(Isabelle, Luca), pair(Luisa, Pharrell), pair(Cecilia, Amadu), pair(Ricarda, Maurice),
                  pair(Anna, Martin), pair(Gina, Michael));

      newTag().addNew(Felix)
            .matchBox(pair(Luisa, Lukas), true);

      newTag().matchBox(pair(Ricarda, Felix), false)
            .matchNight(3, pair(Luisa, Lukas), pair(Cecilia, Amadu), pair(Anna, Pharrell), pair(Franziska, Michael),
                  pair(Ricarda, Max), pair(Zoe, Luca), pair(Celina, Fabio), pair(Karina, Martin),
                  pair(Isabelle, Maurice), pair(Gina, Felix));  // Calvin

      newTag().matchBox(pair(Isabelle, Pharrell), false)
            .matchNight(3, pair(Luisa, Lukas), pair(Isabelle, Martin), pair(Ricarda, Pharrell), pair(Cecilia, Maurice),
                  pair(Anna, Michael), pair(Franziska, Max), pair(Celina, Fabio), pair(Gina, Felix),
                  pair(Karina, Calvin), pair(Zoe, Luca)); // Amadu

      newTag().matchBox(pair(Cecilia, Amadu), true)
            .matchNight(4, pair(Luisa, Lukas), pair(Cecilia, Amadu), pair(Gina, Felix), pair(Isabelle, Martin),
                  pair(Anna, Michael), pair(Ricarda, Pharrell), pair(Celina, Fabio), pair(Karina, Calvin),
                  pair(Franziska, Max), pair(Zoe, Luca)); // Maurice

      newTag().matchBox(pair(Anna, Michael), true)
            .matchNight(5, pair(Luisa, Lukas), pair(Anna, Michael), pair(Cecilia, Amadu), pair(Franziska, Calvin),
                  pair(Celina, Luca), pair(Karina, Martin), pair(Zoe, Maurice), pair(Ricarda, Pharrell),
                  pair(Gina, Fabio), pair(Isabelle, Felix)); // Max

      newTag().matchBox(pair(Ricarda, Pharrell), false)
            .matchNight(7, pair(Luisa, Lukas), pair(Anna, Michael), pair(Cecilia, Amadu), pair(Gina, Calvin),
                  pair(Franziska, Max), pair(Ricarda, Maurice), pair(Celina, Fabio), pair(Zoe, Pharrell),
                  pair(Karina, Martin), pair(Isabelle, Felix)); // Luca

      newTag().matchBox(pair(Franziska, Max), false)
            .matchNight(10, pair(Luisa, Lukas), pair(Anna, Michael), pair(Cecilia, Amadu), pair(Karina, Luca),
                  pair(Celina, Fabio), pair(Franziska, Calvin), pair(Zoe, Pharrell), pair(Ricarda, Maurice),
                  pair(Gina, Martin), pair(Isabelle, Felix)); // Max

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printDayResults(new AYTO_VIP02());
   }

}
