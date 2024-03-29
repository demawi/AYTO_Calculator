package demawi.ayto.de;

import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.modell.Woman;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;
import static demawi.ayto.permutation.Mark.CAN_BE_AN_EXTRA_MATCH;

public class AYTO_01
      extends SeasonData {

   private final Woman Luisa = frau("Luisa");
   private final Woman Katharina = frau("Katharina");
   private final Woman Nadine = frau("Nadine");
   private final Woman Laura = frau("Laura");
   private final Woman Maddie = frau("Maddie");
   private final Woman Michelle = frau("Michelle");
   private final Woman Aline = frau("Aline");
   private final Woman Ivana = frau("Ivana");
   private final Woman Melissa = frau("Melissa");
   private final Woman Sabrina = frau("Sabrina");

   private final Man Axel = mann("Axel");
   private final Man Rene = mann("René");
   private final Man Elisha = mann("Elisha");
   private final Man Ferhat = mann("Ferhat");
   private final Man Laurin = mann("Laurin");
   private final Man Mo = mann("Mo");
   private final Man Kevin = mann("Kevin");
   private final Man Juliano = mann("Juliano");
   private final Man Dominic = mann("Dominic");
   private final Man Aleks = mann("Aleks");
   private final Man Edin = mann("Edin", CAN_BE_AN_EXTRA_MATCH, true);

   public AYTO_01() {
      super(Mode.MARKED);

      newDay().matchBox(pair(Ivana, Mo), false)
            .matchNight(1, pair(Luisa, Axel), pair(Katharina, Rene), pair(Nadine, Elisha), pair(Laura, Ferhat),
                  pair(Maddie, Laurin), pair(Michelle, Mo), pair(Aline, Kevin), pair(Ivana, Juliano),
                  pair(Melissa, Dominic), pair(Sabrina, Aleks));

      newDay().matchBox(pair(Melissa, Dominic), false)
            .matchNight(2, pair(Ivana, Elisha), pair(Katharina, Kevin), pair(Michelle, Mo), pair(Melissa, Laurin),
                  pair(Nadine, Aleks), pair(Aline, Ferhat), pair(Luisa, Rene), pair(Sabrina, Dominic),
                  pair(Laura, Juliano), pair(Maddie, Axel));

      newDay().matchBox(pair(Aline, Mo), true)
            .matchNight(3, pair(Aline, Mo), pair(Katharina, Kevin), pair(Sabrina, Dominic), pair(Nadine, Aleks),
                  pair(Melissa, Laurin), pair(Maddie, Axel), pair(Michelle, Ferhat), pair(Luisa, Rene),
                  pair(Ivana, Elisha), pair(Laura, Juliano));

      newDay().matchBox(pair(Melissa, Laurin), true)
            .matchNight( 2, pair(Aline, Mo), pair(Melissa, Laurin), pair(Laura, Kevin),
            pair(Michelle, Elisha), pair(Katharina, Juliano), pair(Nadine, Dominic), pair(Sabrina, Aleks),
            pair(Ivana, Axel), pair(Maddie, Ferhat), pair(Luisa, Rene));

      newDay().matchBox(pair(Ivana, Elisha), false)
            .matchNight( 5, pair(Aline, Mo), pair(Melissa, Laurin), pair(Luisa, Ferhat),
            pair(Sabrina, Elisha), pair(Nadine, Rene), pair(Katharina, Kevin), pair(Laura, Juliano),
            pair(Maddie, Aleks), pair(Michelle, Axel), pair(Ivana, Dominic));

      newDay().addNew(Edin).matchBox(pair(Michelle, Rene), true)
            .matchNight(5, pair(Aline, Mo), pair(Melissa, Laurin), pair(Michelle, Rene), pair(Nadine, Elisha),
                  pair(Luisa, Axel), pair(Laura, Edin), pair(Katharina, Kevin), pair(Ivana, Juliano),
                  pair(Maddie, Aleks), pair(Sabrina, Dominic)); // Ferhat

      // 7ter Tag
      newDay().matchBox(pair(Ivana, Edin), false)
            .matchNight( 5, pair(Aline, Mo), pair(Melissa, Laurin), pair(Michelle, Rene),
            pair(Nadine, Ferhat), pair(Katharina, Elisha), pair(Laura, Juliano), pair(Sabrina, Dominic),
            pair(Maddie, Aleks), pair(Luisa, Axel), pair(Ivana, Kevin)); // Edin

      // 8ter Tag
      newDay().matchBox(pair(Ivana, Kevin), false)
            .matchNight( 6, pair(Aline, Mo), pair(Melissa, Laurin), pair(Michelle, Rene),
            pair(Laura, Juliano), pair(Nadine, Ferhat), pair(Maddie, Edin), pair(Katharina, Aleks), pair(Luisa, Axel),
            pair(Ivana, Kevin), pair(Sabrina, Dominic)); // Elisha

      // 9ter Tag
      newDay().matchBox(pair(Maddie, Ferhat), false)
            .matchNight( 7, pair(Aline, Mo), pair(Melissa, Laurin), pair(Michelle, Rene),
            pair(Sabrina, Elisha), pair(Ivana, Dominic), pair(Maddie, Kevin), pair(Luisa, Axel), pair(Katharina, Aleks),
            pair(Nadine, Ferhat), pair(Laura, Juliano)); // Edin

      // 10ter Tag
      newDay().matchBox(pair(Katharina, Kevin), true)
            .matchNight( 10, pair(Katharina, Kevin), pair(Aline, Mo), pair(Melissa, Laurin),
            pair(Michelle, Rene), pair(Maddie, Juliano), pair(Ivana, Dominic), pair(Sabrina, Elisha),
            pair(Nadine, Ferhat), pair(Laura, Aleks), pair(Luisa, Axel)); // pair(Maddie, Edin)

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_01()).printLastDayResults();
   }

}
