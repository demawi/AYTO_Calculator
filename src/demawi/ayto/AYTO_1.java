package demawi.ayto;

import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.perm.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.Pair.pair;

public class AYTO_1
      extends AYTO_Data {

   private Frau Luisa = frau("Luisa");
   private Frau Katharina = frau("Katharina");
   private Frau Nadine = frau("Nadine");
   private Frau Laura = frau("Laura");
   private Frau Maddie = frau("Maddie");
   private Frau Michelle = frau("Michelle");
   private Frau Aline = frau("Aline");
   private Frau Ivana = frau("Ivana");
   private Frau Melissa = frau("Melissa");
   private Frau Sabrina = frau("Sabrina");

   private Mann Axel = mann("Axel");
   private Mann Rene = mann("René");
   private Mann Elisha = mann("Elisha");
   private Mann Ferhat = mann("Ferhat");
   private Mann Laurin = mann("Laurin");
   private Mann Mo = mann("Mo");
   private Mann Kevin = mann("Kevin");
   private Mann Juliano = mann("Juliano");
   private Mann Dominic = mann("Dominic");
   private Mann Aleks = mann("Aleks");
   private Mann Edin = mann("Edin");

   public AYTO_1() {
      super("01", AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER);

      add(false, pair(Ivana, Mo), 1, pair(Luisa, Axel), pair(Katharina, Rene), pair(Nadine, Elisha),
            pair(Laura, Ferhat), pair(Maddie, Laurin), pair(Michelle, Mo), pair(Aline, Kevin), pair(Ivana, Juliano),
            pair(Melissa, Dominic), pair(Sabrina, Aleks));

      add(false, pair(Melissa, Dominic), 2, pair(Ivana, Elisha), pair(Katharina, Kevin), pair(Michelle, Mo),
            pair(Melissa, Laurin), pair(Nadine, Aleks), pair(Aline, Ferhat), pair(Luisa, Rene), pair(Sabrina, Dominic),
            pair(Laura, Juliano), pair(Maddie, Axel));

      add(true, pair(Aline, Mo), 3, pair(Aline, Mo), pair(Katharina, Kevin), pair(Sabrina, Dominic),
            pair(Nadine, Aleks), pair(Melissa, Laurin), pair(Maddie, Axel), pair(Michelle, Ferhat), pair(Luisa, Rene),
            pair(Ivana, Elisha), pair(Laura, Juliano));

      add(true, pair(Melissa, Laurin), 2, pair(Aline, Mo), pair(Melissa, Laurin), pair(Laura, Kevin),
            pair(Michelle, Elisha), pair(Katharina, Juliano), pair(Nadine, Dominic), pair(Sabrina, Aleks),
            pair(Ivana, Axel), pair(Maddie, Ferhat), pair(Luisa, Rene));

      add(false, pair(Ivana, Elisha), 5, pair(Aline, Mo), pair(Melissa, Laurin), pair(Luisa, Ferhat),
            pair(Sabrina, Elisha), pair(Nadine, Rene), pair(Katharina, Kevin), pair(Laura, Juliano),
            pair(Maddie, Aleks), pair(Michelle, Axel), pair(Ivana, Dominic));

      addTag().matchBox(pair(Michelle, Rene), true)
            .matchNight(5, pair(Aline, Mo), pair(Melissa, Laurin), pair(Michelle, Rene), pair(Nadine, Elisha),
                  pair(Luisa, Axel), pair(Laura, Edin), pair(Katharina, Kevin), pair(Ivana, Juliano),
                  pair(Maddie, Aleks), pair(Sabrina, Dominic))
            .addNew(Edin); // Ferhat

      // 7ter Tag
      add(false, pair(Ivana, Edin), 5, pair(Aline, Mo), pair(Melissa, Laurin), pair(Michelle, Rene),
            pair(Nadine, Ferhat), pair(Katharina, Elisha), pair(Laura, Juliano), pair(Sabrina, Dominic),
            pair(Maddie, Aleks), pair(Luisa, Axel), pair(Ivana, Kevin)); // Edin

      // 8ter Tag
      add(false, pair(Ivana, Kevin), 6, pair(Aline, Mo), pair(Melissa, Laurin), pair(Michelle, Rene),
            pair(Laura, Juliano), pair(Nadine, Ferhat), pair(Maddie, Edin), pair(Katharina, Aleks), pair(Luisa, Axel),
            pair(Ivana, Kevin), pair(Sabrina, Dominic)); // Elisha

      // 9ter Tag
      add(false, pair(Maddie, Ferhat), 7, pair(Aline, Mo), pair(Melissa, Laurin), pair(Michelle, Rene),
            pair(Sabrina, Elisha), pair(Ivana, Dominic), pair(Maddie, Kevin), pair(Luisa, Axel), pair(Katharina, Aleks),
            pair(Nadine, Ferhat), pair(Laura, Juliano)); // Edin

      // 10ter Tag
      add(true, pair(Katharina, Kevin), 10, pair(Katharina, Kevin), pair(Aline, Mo), pair(Melissa, Laurin),
            pair(Michelle, Rene), pair(Maddie, Juliano), pair(Ivana, Dominic), pair(Sabrina, Elisha),
            pair(Nadine, Ferhat), pair(Laura, Aleks), pair(Luisa, Axel)); // pair(Maddie, Edin)

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printDayResults(new AYTO_1());
   }

}
