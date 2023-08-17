package demawi.ayto.de;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_1
      extends StaffelData {

   private final Frau Luisa = frau("Luisa");
   private final Frau Katharina = frau("Katharina");
   private final Frau Nadine = frau("Nadine");
   private final Frau Laura = frau("Laura");
   private final Frau Maddie = frau("Maddie");
   private final Frau Michelle = frau("Michelle");
   private final Frau Aline = frau("Aline");
   private final Frau Ivana = frau("Ivana");
   private final Frau Melissa = frau("Melissa");
   private final Frau Sabrina = frau("Sabrina");

   private final Mann Axel = mann("Axel");
   private final Mann Rene = mann("René");
   private final Mann Elisha = mann("Elisha");
   private final Mann Ferhat = mann("Ferhat");
   private final Mann Laurin = mann("Laurin");
   private final Mann Mo = mann("Mo");
   private final Mann Kevin = mann("Kevin");
   private final Mann Juliano = mann("Juliano");
   private final Mann Dominic = mann("Dominic");
   private final Mann Aleks = mann("Aleks");
   private final Mann Edin = mann("Edin");

   public AYTO_1() {
      super("01", AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER);

      newTag().matchBox(pair(Ivana, Mo), false)
            .matchNight(1, pair(Luisa, Axel), pair(Katharina, Rene), pair(Nadine, Elisha), pair(Laura, Ferhat),
                  pair(Maddie, Laurin), pair(Michelle, Mo), pair(Aline, Kevin), pair(Ivana, Juliano),
                  pair(Melissa, Dominic), pair(Sabrina, Aleks));

      newTag().matchBox(pair(Melissa, Dominic), false)
            .matchNight(2, pair(Ivana, Elisha), pair(Katharina, Kevin), pair(Michelle, Mo), pair(Melissa, Laurin),
                  pair(Nadine, Aleks), pair(Aline, Ferhat), pair(Luisa, Rene), pair(Sabrina, Dominic),
                  pair(Laura, Juliano), pair(Maddie, Axel));

      newTag().matchBox(pair(Aline, Mo), true)
            .matchNight(3, pair(Aline, Mo), pair(Katharina, Kevin), pair(Sabrina, Dominic), pair(Nadine, Aleks),
                  pair(Melissa, Laurin), pair(Maddie, Axel), pair(Michelle, Ferhat), pair(Luisa, Rene),
                  pair(Ivana, Elisha), pair(Laura, Juliano));

      newTag().matchBox(pair(Melissa, Laurin), true)
            .matchNight( 2, pair(Aline, Mo), pair(Melissa, Laurin), pair(Laura, Kevin),
            pair(Michelle, Elisha), pair(Katharina, Juliano), pair(Nadine, Dominic), pair(Sabrina, Aleks),
            pair(Ivana, Axel), pair(Maddie, Ferhat), pair(Luisa, Rene));

      newTag().matchBox(pair(Ivana, Elisha), false)
            .matchNight( 5, pair(Aline, Mo), pair(Melissa, Laurin), pair(Luisa, Ferhat),
            pair(Sabrina, Elisha), pair(Nadine, Rene), pair(Katharina, Kevin), pair(Laura, Juliano),
            pair(Maddie, Aleks), pair(Michelle, Axel), pair(Ivana, Dominic));

      newTag().addNew(Edin).matchBox(pair(Michelle, Rene), true)
            .matchNight(5, pair(Aline, Mo), pair(Melissa, Laurin), pair(Michelle, Rene), pair(Nadine, Elisha),
                  pair(Luisa, Axel), pair(Laura, Edin), pair(Katharina, Kevin), pair(Ivana, Juliano),
                  pair(Maddie, Aleks), pair(Sabrina, Dominic)); // Ferhat

      // 7ter Tag
      newTag().matchBox(pair(Ivana, Edin), false)
            .matchNight( 5, pair(Aline, Mo), pair(Melissa, Laurin), pair(Michelle, Rene),
            pair(Nadine, Ferhat), pair(Katharina, Elisha), pair(Laura, Juliano), pair(Sabrina, Dominic),
            pair(Maddie, Aleks), pair(Luisa, Axel), pair(Ivana, Kevin)); // Edin

      // 8ter Tag
      newTag().matchBox(pair(Ivana, Kevin), false)
            .matchNight( 6, pair(Aline, Mo), pair(Melissa, Laurin), pair(Michelle, Rene),
            pair(Laura, Juliano), pair(Nadine, Ferhat), pair(Maddie, Edin), pair(Katharina, Aleks), pair(Luisa, Axel),
            pair(Ivana, Kevin), pair(Sabrina, Dominic)); // Elisha

      // 9ter Tag
      newTag().matchBox(pair(Maddie, Ferhat), false)
            .matchNight( 7, pair(Aline, Mo), pair(Melissa, Laurin), pair(Michelle, Rene),
            pair(Sabrina, Elisha), pair(Ivana, Dominic), pair(Maddie, Kevin), pair(Luisa, Axel), pair(Katharina, Aleks),
            pair(Nadine, Ferhat), pair(Laura, Juliano)); // Edin

      // 10ter Tag
      newTag().matchBox(pair(Katharina, Kevin), true)
            .matchNight( 10, pair(Katharina, Kevin), pair(Aline, Mo), pair(Melissa, Laurin),
            pair(Michelle, Rene), pair(Maddie, Juliano), pair(Ivana, Dominic), pair(Sabrina, Elisha),
            pair(Nadine, Ferhat), pair(Laura, Aleks), pair(Luisa, Axel)); // pair(Maddie, Edin)

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printDayResults(new AYTO_1());
   }

}
