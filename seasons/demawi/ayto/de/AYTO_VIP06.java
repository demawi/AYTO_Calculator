package demawi.ayto.de;

import demawi.ayto.modell.Man;
import demawi.ayto.modell.PermutationConfiguration;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.modell.Woman;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.permutation.Mark;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_VIP06
      extends SeasonData {

   private final Woman Alexandra = frau("Alexandra", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Christin = frau("Christin", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Emma = frau("Emma", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Francesca = frau("Francesca", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Janice = frau("Janice", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Jenny = frau("Jenny", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Julia = frau("Julia", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Marta = frau("Marta", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Michelle = frau("Michelle", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Zoe = frau("Zoe", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Joena = frau("Joena", Mark.CAN_BE_AN_EXTRA_MATCH,
         true); // ein Mann hat zwei PerfectMatches, kann muss aber nicht Joena sein

   private final Man Bennett = mann("Bennett", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Brian = mann("Brian", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Cansin = mann("Cansin", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Daymian = mann("Daymian", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Fabi = mann("Fabi", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Germain = mann("Germain", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Marwin = mann("Marwin", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Johannes = mann("Johannes", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Raul = mann("Raul", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Robin = mann("Robin", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Man Laurenz = mann("Laurenz", Mark.IS_AN_EXTRA_MATCH, true);

   public AYTO_VIP06() {
      super(new PermutationConfiguration(Mode.MARKED, 2));

      newDay().matchBox(pair(Christin, Marwin), false)
            .matchBox(pair(Marta, Robin), false)
            .addNew(Joena)
            //.addNew(Laurenz)
            .matchNight(3, pair(Jenny, Robin), pair(Marta, Johannes), pair(Christin, Brian), pair(Michelle, Marwin),
                  pair(Emma, Germain), pair(Alexandra, Raul), pair(Janice, Fabi), pair(Julia, Daymian),
                  pair(Zoe, Cansin), pair(Francesca, Bennett)); // Joena

      newDay().matchBox(pair(Julia, Daymian), false)
            .matchNight(3, pair(Emma, Germain), pair(Alexandra, Brian), pair(Julia, Fabi), pair(Joena, Bennett),
                  pair(Christin, Robin), pair(Jenny, Daymian), pair(Michelle, Marwin), pair(Marta, Raul),
                  pair(Janice, Johannes), pair(Zoe, Cansin)); // Francesa

      newDay().matchBox(pair(Marta, Johannes), true, pair(Janice, Johannes))
            .matchNight(3, pair(Marta, Johannes), pair(Michelle, Raul), pair(Christin, Robin), pair(Alexandra, Germain),
                  pair(Emma, Fabi), pair(Jenny, Cansin), pair(Julia, Bennett), pair(Zoe, Daymian), pair(Joena, Marwin),
                  pair(Francesca, Brian));

      newDay().matchBox(pair(Julia, Bennett), false)
            .matchNight(4, pair(Marta, Johannes), pair(Zoe, Fabi), pair(Julia, Cansin), pair(Christin, Bennett),
                  pair(Francesca, Raul), pair(Joena, Robin), pair(Jenny, Daymian), pair(Emma, Marwin),
                  pair(Michelle, Germain), pair(Alexandra, Brian));

      //newDay().addNew(Laurenz);
      //.matchBox(pair(Emma, Laurenz), null)
      //.matchNight(3, pair(Marta, Johannes), pair(Zoe, Laurenz), pair(Alexandra, Brian), pair(Emma, Germain), pair(Francesca, Marwin), pair(Jenny, Daymian), pair(Joena, Robin), pair(Christin, Bennett), pair(Julia, Cansin), pair(Michelle, Raul)); // Fabi

      // newDay().matchBox(pair(Michelle, Raul), false).matchNight(3, pair(Marta, Johannes), pair(Alexandra, Brian), pair(Joena, Robin), pair(Jenny, Daymian), pair(Francesca, Marwin), pair(Zoe, Cansin), pair(Michelle, Fabi), pair(Emma, Laurenz), pair(Christin, Bennett), pair(Julia, Germain)); // Raul
   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_VIP06()).printLastDayResults();
   }

}
