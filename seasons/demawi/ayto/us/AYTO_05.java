package demawi.ayto.us;

import demawi.ayto.modell.PermutationConfiguration;
import demawi.ayto.modell.Woman;
import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_05
      extends SeasonData {

   private final Woman Alicia = frau("Alicia");
   private final Woman Carolina = frau("Carolina");
   private final Woman Casandra = frau("Casandra");
   private final Woman Gianna = frau("Gianna");
   private final Woman Hannah = frau("Hannah");
   private final Woman Kam = frau("Kam");
   private final Woman Kari = frau("Kari");
   private final Woman Kathryn = frau("Kathryn");
   private final Woman Shannon = frau("Shannon");
   private final Woman Taylor = frau("Taylor");
   private final Woman Tyranny = frau("Tyranny");

   private final Man Andre = mann("Andre");
   private final Man Derrick = mann("Derrick ");
   private final Man Edward = mann("Edward");
   private final Man Hayden = mann("Hayden");
   private final Man Jaylan = mann("Jaylan");
   private final Man Joey = mann("Joey ");
   private final Man Michael = mann("Michael");
   private final Man Mike = mann("Mike");
   private final Man Osvaldo = mann("Osvaldo");
   private final Man Ozzy = mann("Ozzy");
   private final Man Tyler = mann("Tyler");

   public AYTO_05() {
      super(new PermutationConfiguration(Mode.STANDARD), 11);

      newDay().matchBox(pair(Gianna, Hayden), false)
            .matchNight(2, pair(Alicia, Andre), pair(Carolina, Joey), pair(Casandra, Jaylan), pair(Gianna, Ozzy),
                  pair(Hannah, Michael), pair(Kam, Edward), pair(Kari, Mike), pair(Kathryn, Derrick),
                  pair(Shannon, Hayden), pair(Taylor, Tyler), pair(Tyranny, Osvaldo));

      newDay().matchBox(pair(Alicia, Andre), false)
            .matchNight(0, pair(Alicia, Derrick), pair(Carolina, Joey), pair(Casandra, Mike), pair(Gianna, Michael),
                  pair(Hannah, Andre), pair(Kam, Jaylan), pair(Kari, Osvaldo), pair(Kathryn, Ozzy),
                  pair(Shannon, Edward), pair(Taylor, Hayden), pair(Tyranny, Tyler));

      newDay().matchBox(pair(Carolina, Ozzy), false)
            .matchNight(4, pair(Alicia, Mike), pair(Carolina, Hayden), pair(Casandra, Jaylan), pair(Gianna, Ozzy),
                  pair(Hannah, Derrick), pair(Kam, Edward), pair(Kari, Andre), pair(Kathryn, Joey),
                  pair(Shannon, Tyler), pair(Taylor, Michael), pair(Tyranny, Osvaldo));

      newDay().matchBox(pair(Tyranny, Osvaldo), false)
            .matchNight(4, pair(Alicia, Edward), pair(Carolina, Hayden), pair(Casandra, Andre), pair(Gianna, Derrick),
                  pair(Hannah, Ozzy), pair(Kam, Mike), pair(Kari, Michael), pair(Kathryn, Joey), pair(Shannon, Tyler),
                  pair(Taylor, Osvaldo), pair(Tyranny, Jaylan));

      newDay().matchBox(pair(Kam, Edward), true)
            .matchNight(4, pair(Alicia, Mike), pair(Carolina, Hayden), pair(Casandra, Jaylan), pair(Gianna, Osvaldo),
                  pair(Hannah, Ozzy), pair(Kam, Edward), pair(Kari, Michael), pair(Kathryn, Joey), pair(Shannon, Tyler),
                  pair(Taylor, Andre), pair(Tyranny, Derrick));

      newDay().matchBox(pair(Hannah, Ozzy), false)
            .matchNight(4, pair(Alicia, Mike), pair(Carolina, Hayden), pair(Casandra, Ozzy), pair(Gianna, Osvaldo),
                  pair(Hannah, Jaylan), pair(Kam, Edward), pair(Kari, Michael), pair(Kathryn, Joey),
                  pair(Shannon, Tyler), pair(Taylor, Andre), pair(Tyranny, Derrick));

      newDay().matchBox(pair(Taylor, Andre), false)
            .matchNight(4, pair(Alicia, Ozzy), pair(Carolina, Hayden), pair(Casandra, Andre), pair(Gianna, Mike),
                  pair(Hannah, Michael), pair(Kam, Edward), pair(Kari, Derrick), pair(Kathryn, Joey),
                  pair(Shannon, Tyler), pair(Taylor, Osvaldo), pair(Tyranny, Jaylan));

      newDay().matchBox(pair(Carolina, Hayden), true)
            .matchNight(5, pair(Alicia, Ozzy), pair(Carolina, Hayden), pair(Casandra, Joey), pair(Gianna, Andre),
                  pair(Hannah, Tyler), pair(Kam, Edward), pair(Kari, Mike), pair(Kathryn, Michael),
                  pair(Shannon, Derrick), pair(Taylor, Osvaldo), pair(Tyranny, Jaylan));

      newDay().matchBox(pair(Tyranny, Derrick), false)
            .matchNight(9, pair(Alicia, Mike), pair(Carolina, Hayden), pair(Casandra, Derrick), pair(Gianna, Ozzy),
                  pair(Hannah, Joey), pair(Kam, Edward), pair(Kari, Michael), pair(Kathryn, Andre),
                  pair(Shannon, Tyler), pair(Taylor, Osvaldo), pair(Tyranny, Jaylan));

      newDay().matchBox(pair(Casandra, Joey), false)
            .matchNight(8, pair(Alicia, Mike), pair(Carolina, Hayden), pair(Casandra, Tyler), pair(Gianna, Ozzy),
                  pair(Hannah, Joey), pair(Kam, Edward), pair(Kari, Michael), pair(Kathryn, Andre),
                  pair(Shannon, Derrick), pair(Taylor, Osvaldo), pair(Tyranny, Jaylan));

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_05()).printLastDayResults();
   }

}
