package demawi.ayto.de;

import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.modell.Woman;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.permutation.Mark;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_VIP05
      extends SeasonData {

   private final Woman Sandra = frau("Sandra");
   private final Woman Antonia = frau("Antonia");
   private final Woman Ariel = frau("Ariel");
   private final Woman Beverly = frau("Beverly");
   private final Woman Elli = frau("Elli");
   private final Woman Henna = frau("Henna");
   private final Woman Joanna = frau("Joanna");
   private final Woman Hati = frau("Hati");
   private final Woman Nelly = frau("Nelly");
   private final Woman Viki = frau("Viki");

   private final Man Kevin = mann("Kevin", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Sidar = mann("Sidar", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Xander = mann("Xander", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Calvin_hOt = mann("Calvin hOt.", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Calvin_Blond_S = mann("Calvin Blond S.", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Jonny = mann("Jonny", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Lennert = mann("Lennert", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Nico = mann("Nico", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Olli = mann("Olli", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Rob = mann("Rob", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Leandro = mann("Leandro", Mark.CAN_BE_AN_EXTRA_MATCH, true);

   public AYTO_VIP05() {
      super(Mode.MARKED);

      newDay().matchBox(pair(Sandra, Kevin), null)
            .addNew(Leandro)
            .matchNight(2, pair(Viki, Leandro), pair(Hati, Calvin_hOt), pair(Henna, Jonny), pair(Nelly, Sidar),
                  pair(Sandra, Kevin), pair(Joanna, Rob), pair(Ariel, Nico), pair(Antonia, Lennert), pair(Elli, Xander),
                  pair(Beverly, Calvin_Blond_S)); // Olli
      newDay().matchBox(pair(Elli, Xander), true)
            .matchNight(2, pair(Elli, Xander), pair(Joanna, Calvin_Blond_S), pair(Nelly, Rob), pair(Sandra, Kevin),
                  pair(Ariel, Nico), pair(Beverly, Sidar), pair(Henna, Olli), pair(Hati, Calvin_hOt),
                  pair(Viki, Leandro), pair(Antonia, Jonny)); // Lennert
      //newDay().matchBox(pair(Nelly, Calvin_Blond_S), false).matchNight(2, pair(Elli, Xander), pair(Joanna, Calvin_Blond_S), pair(Antonia, Olli), pair(Beverly, Nico), pair(Viki, Jonny), pair(Hati, Rob), pair(Sandra, Kevin), pair(Henna, Leandro), pair(Nelly, Lennert), pair(Ariel, Calvin_hOt)); // Sidar

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_VIP05()).printLastDayResults();
   }

}
