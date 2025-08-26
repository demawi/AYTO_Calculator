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
   private final Man Calvin_hOT = mann("Calvin hOT.", Mark.CAN_BE_AN_EXTRA_MATCH);
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
            .matchNight(2, pair(Leandro, Viki), pair(Calvin_hOT, Hati), pair(Jonny, Henna), pair(Sidar, Nelly),
                  pair(Kevin, Sandra), pair(Rob, Joanna), pair(Nico, Ariel), pair(Lennert, Antonia), pair(Xander, Elli),
                  pair(Calvin_Blond_S, Beverly)); // Olli
      //newDay().matchBox(pair(Xander, Elli), null);

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_VIP05()).printLastDayResults();
   }

}
