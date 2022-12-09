package demawi.ayto;

import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.perm.AYTO_Permutator;
import demawi.ayto.service.StandardMatchFinder;

import static demawi.ayto.modell.Pair.pair;

public class AYTO_VIP02
      extends AYTO_Data {

   private static Frau Ricarda = frau("Ricarda");
   private static Frau Luisa = frau("Luisa");
   private static Frau Cecilia = frau("Cecilia");
   private static Frau Karina = frau("Karina");
   private static Frau Isabelle = frau("Isabelle");
   private static Frau Gina = frau("Gina");
   private static Frau Celina = frau("Celina");
   private static Frau Zoe = frau("Zoe");
   private static Frau Franziska = frau("Franziska");
   private static Frau Anna = frau("Anna");

   private static Mann Calvin = mann("Calvin");
   private static Mann Martin = mann("Martin");
   private static Mann Lukas = mann("Lukas");
   private static Mann Maurice = mann("Maurice");
   private static Mann Amadu = mann("Amadu");
   private static Mann Pharrell = mann("Pharrell");
   private static Mann Fabio = mann("Fabio");
   private static Mann Max = mann("Max");
   private static Mann Michael = mann("Michael");
   private static Mann Luca = mann("Luca");
   private static Mann Felix = mann("Felix");

   public AYTO_VIP02() {
      super("VIP_02", AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER);

      add(false, pair(Zoe, Martin), 3, pair(Franziska, Max), pair(Anna, Michael), pair(Celina, Martin),
            pair(Ricarda, Maurice), pair(Gina, Calvin), pair(Cecilia, Amadu), pair(Karina, Pharrell),
            pair(Isabelle, Lukas), pair(Luisa, Fabio), pair(Zoe, Luca));

      add(false, pair(Karina, Pharrell), 2, pair(Ricarda, Maurice), pair(Isabelle, Martin), pair(Anna, Michael),
            pair(Gina, Amadu), pair(Luisa, Calvin), pair(Franziska, Luca), pair(Celina, Max), pair(Zoe, Lukas),
            pair(Karina, Fabio), pair(Cecilia, Pharrell));

      add(false, pair(Celina, Lukas), 3, pair(Franziska, Lukas), pair(Karina, Calvin), pair(Celina, Fabio),
            pair(Zoe, Max), pair(Isabelle, Luca), pair(Luisa, Pharrell), pair(Cecilia, Amadu), pair(Ricarda, Maurice),
            pair(Anna, Martin), pair(Gina, Michael));

      addTag().matchBox(pair(Luisa, Lukas), true)
            .noMatchingNight()
            .addNew(Felix);

      add(false, pair(Ricarda, Felix), 3, pair(Luisa, Lukas), pair(Cecilia, Amadu), pair(Anna, Pharrell),
            pair(Franziska, Michael), pair(Ricarda, Max), pair(Zoe, Luca), pair(Celina, Fabio), pair(Karina, Martin),
            pair(Isabelle, Maurice), pair(Gina, Felix));  // Calvin

      add(false, pair(Isabelle, Pharrell), 3, pair(Luisa, Lukas), pair(Isabelle, Martin), pair(Ricarda, Pharrell),
            pair(Cecilia, Maurice), pair(Anna, Michael), pair(Franziska, Max), pair(Celina, Fabio), pair(Gina, Felix),
            pair(Karina, Calvin), pair(Zoe, Luca)); // Amadu

      add(true, pair(Cecilia, Amadu), 4, pair(Luisa, Lukas), pair(Cecilia, Amadu), pair(Gina, Felix),
            pair(Isabelle, Martin), pair(Anna, Michael), pair(Ricarda, Pharrell), pair(Celina, Fabio),
            pair(Karina, Calvin), pair(Franziska, Max), pair(Zoe, Luca)); // Maurice

      add(true, pair(Anna, Michael), 5, pair(Luisa, Lukas), pair(Anna, Michael), pair(Cecilia, Amadu),
            pair(Franziska, Calvin), pair(Celina, Luca), pair(Karina, Martin), pair(Zoe, Maurice),
            pair(Ricarda, Pharrell), pair(Gina, Fabio), pair(Isabelle, Felix)); // Max

      add(false, pair(Ricarda, Pharrell), 7, pair(Luisa, Lukas), pair(Anna, Michael), pair(Cecilia, Amadu),
            pair(Gina, Calvin), pair(Franziska, Max), pair(Ricarda, Maurice), pair(Celina, Fabio), pair(Zoe, Pharrell),
            pair(Karina, Martin), pair(Isabelle, Felix)); // Luca

      add(false, pair(Franziska, Max), 10, pair(Luisa, Lukas), pair(Anna, Michael), pair(Cecilia, Amadu),
            pair(Karina, Luca), pair(Celina, Fabio), pair(Franziska, Calvin), pair(Zoe, Pharrell),
            pair(Ricarda, Maurice), pair(Gina, Martin), pair(Isabelle, Felix)); // Max

   }

   public static void main(String[] args) {
      new StandardMatchFinder().printDayResults(new AYTO_VIP02());
   }

}
