package demawi.ayto;

import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;

import static demawi.ayto.modell.Pair.pair;

public class AYTO_VIP02
      extends AYTO_Data {

   private static Frau Ricarda = newFrau("Ricarda");
   private static Frau Luisa = newFrau("Luisa");
   private static Frau Cecilia = newFrau("Cecilia");
   private static Frau Karina = newFrau("Karina");
   private static Frau Isabelle = newFrau("Isabelle");
   private static Frau Gina = newFrau("Gina");
   private static Frau Celina = newFrau("Celina");
   private static Frau Zoe = newFrau("Zoe");
   private static Frau Franziska = newFrau("Franziska");
   private static Frau Anna = newFrau("Anna");

   private static Mann Calvin = newMann("Calvin");
   private static Mann Martin = newMann("Martin");
   private static Mann Lukas = newMann("Lukas");
   private static Mann Maurice = newMann("Maurice");
   private static Mann Amadu = newMann("Amadu");
   private static Mann Pharrell = newMann("Pharrell");
   private static Mann Fabio = newMann("Fabio");
   private static Mann Max = newMann("Max");
   private static Mann Michael = newMann("Michael");
   private static Mann Luca = newMann("Luca");
   private static Mann Felix = newMann("Felix");

   public AYTO_VIP02() {
      super("VIP_02");

      add(false, pair(Zoe, Martin), 3, pair(Franziska, Max), pair(Anna, Michael), pair(Celina, Martin),
            pair(Ricarda, Maurice), pair(Gina, Calvin), pair(Cecilia, Amadu), pair(Karina, Pharrell),
            pair(Isabelle, Lukas), pair(Luisa, Fabio), pair(Zoe, Luca));

      add(false, pair(Karina, Pharrell), 2, pair(Ricarda, Maurice), pair(Isabelle, Martin), pair(Anna, Michael),
            pair(Gina, Amadu), pair(Luisa, Calvin), pair(Franziska, Luca), pair(Celina, Max), pair(Zoe, Lukas),
            pair(Karina, Fabio), pair(Cecilia, Pharrell));

      add(false, pair(Celina, Lukas), 3, pair(Franziska, Lukas), pair(Karina, Calvin), pair(Celina, Fabio),
            pair(Zoe, Max), pair(Isabelle, Luca), pair(Luisa, Pharrell), pair(Cecilia, Amadu), pair(Ricarda, Maurice),
            pair(Anna, Martin), pair(Gina, Michael));

      add(true, pair(Luisa, Lukas), 2, null).addImplicit(false, pair(Luisa, Felix));

      add(false, pair(Ricarda, Felix), 3, pair(Luisa, Lukas), pair(Cecilia, Amadu), pair(Anna, Pharrell),
            pair(Franziska, Michael), pair(Ricarda, Max), pair(Zoe, Luca), pair(Celina, Fabio), pair(Karina, Martin),
            pair(Isabelle, Maurice), pair(Gina, Felix));

      add(false, pair(Isabelle, Pharrell), 3, pair(Luisa, Lukas), pair(Isabelle, Martin), pair(Ricarda, Pharrell),
            pair(Cecilia, Maurice), pair(Anna, Michael), pair(Franziska, Max), pair(Celina, Fabio), pair(Gina, Felix),
            pair(Karina, Calvin), pair(Zoe, Luca));

      add(true, pair(Cecilia, Amadu), 4, pair(Luisa, Lukas), pair(Cecilia, Amadu), pair(Gina, Felix),
            pair(Isabelle, Martin), pair(Anna, Michael), pair(Ricarda, Pharrell), pair(Celina, Fabio),
            pair(Karina, Calvin), pair(Franziska, Max), pair(Zoe, Luca));

      //add(true, pair(Celina, Michael), 2, null);
      //add(true, pair(Karina, Calvin), 2, null);
      //add(true, pair(Anna, Fabio), 2, null);

   }

   public static void main(String[] args) {
      new MatchFinder().printLightChancesAndResult(new AYTO_VIP02());
   }

}
