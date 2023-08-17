package demawi.ayto.us;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_5
      extends StaffelData {

   private final Frau Alicia = frau("Alicia");
   private final Frau Carolina = frau("Carolina");
   private final Frau Casandra = frau("Casandra");
   private final Frau Gianna = frau("Gianna");
   private final Frau Hannah = frau("Hannah");
   private final Frau Kam = frau("Kam");
   private final Frau Kari = frau("Kari");
   private final Frau Kathryn = frau("Kathryn");
   private final Frau Shannon = frau("Shannon");
   private final Frau Taylor = frau("Taylor");
   private final Frau Tyranny = frau("Tyranny");

   private final Mann Andre = mann("Andre");
   private final Mann Derrick = mann("Derrick ");
   private final Mann Edward = mann("Edward");
   private final Mann Hayden = mann("Hayden");
   private final Mann Jaylan = mann("Jaylan");
   private final Mann Joey = mann("Joey ");
   private final Mann Michael = mann("Michael");
   private final Mann Mike = mann("Mike");
   private final Mann Osvaldo = mann("Osvaldo");
   private final Mann Ozzy = mann("Ozzy");
   private final Mann Tyler = mann("Tyler");

   public AYTO_5() {
      super("05", AYTO_Permutator.ZUSATZTYPE.KEINER, 11);

      newTag().matchBox(pair(Gianna, Hayden), false)
            .matchNight(2, pair(Alicia, Andre), pair(Carolina, Joey), pair(Casandra, Jaylan), pair(Gianna, Ozzy),
                  pair(Hannah, Michael), pair(Kam, Edward), pair(Kari, Mike), pair(Kathryn, Derrick),
                  pair(Shannon, Hayden), pair(Taylor, Tyler), pair(Tyranny, Osvaldo));

      newTag().matchBox(pair(Alicia, Andre), false)
            .matchNight(0, pair(Alicia, Derrick), pair(Carolina, Joey), pair(Casandra, Mike), pair(Gianna, Michael),
                  pair(Hannah, Andre), pair(Kam, Jaylan), pair(Kari, Osvaldo), pair(Kathryn, Ozzy),
                  pair(Shannon, Edward), pair(Taylor, Hayden), pair(Tyranny, Tyler));

      newTag().matchBox(pair(Carolina, Ozzy), false)
            .matchNight(4, pair(Alicia, Mike), pair(Carolina, Hayden), pair(Casandra, Jaylan), pair(Gianna, Ozzy),
                  pair(Hannah, Derrick), pair(Kam, Edward), pair(Kari, Andre), pair(Kathryn, Joey),
                  pair(Shannon, Tyler), pair(Taylor, Michael), pair(Tyranny, Osvaldo));

      newTag().matchBox(pair(Tyranny, Osvaldo), false)
            .matchNight(4, pair(Alicia, Edward), pair(Carolina, Hayden), pair(Casandra, Andre), pair(Gianna, Derrick),
                  pair(Hannah, Ozzy), pair(Kam, Mike), pair(Kari, Michael), pair(Kathryn, Joey), pair(Shannon, Tyler),
                  pair(Taylor, Osvaldo), pair(Tyranny, Jaylan));

      newTag().matchBox(pair(Kam, Edward), true)
            .matchNight(4, pair(Alicia, Mike), pair(Carolina, Hayden), pair(Casandra, Jaylan), pair(Gianna, Osvaldo),
                  pair(Hannah, Ozzy), pair(Kam, Edward), pair(Kari, Michael), pair(Kathryn, Joey), pair(Shannon, Tyler),
                  pair(Taylor, Andre), pair(Tyranny, Derrick));

      newTag().matchBox(pair(Hannah, Ozzy), false)
            .matchNight(4, pair(Alicia, Mike), pair(Carolina, Hayden), pair(Casandra, Ozzy), pair(Gianna, Osvaldo),
                  pair(Hannah, Jaylan), pair(Kam, Edward), pair(Kari, Michael), pair(Kathryn, Joey),
                  pair(Shannon, Tyler), pair(Taylor, Andre), pair(Tyranny, Derrick));

      newTag().matchBox(pair(Taylor, Andre), false)
            .matchNight(4, pair(Alicia, Ozzy), pair(Carolina, Hayden), pair(Casandra, Andre), pair(Gianna, Mike),
                  pair(Hannah, Michael), pair(Kam, Edward), pair(Kari, Derrick), pair(Kathryn, Joey),
                  pair(Shannon, Tyler), pair(Taylor, Osvaldo), pair(Tyranny, Jaylan));

      newTag().matchBox(pair(Carolina, Hayden), true)
            .matchNight(5, pair(Alicia, Ozzy), pair(Carolina, Hayden), pair(Casandra, Joey), pair(Gianna, Andre),
                  pair(Hannah, Tyler), pair(Kam, Edward), pair(Kari, Mike), pair(Kathryn, Michael),
                  pair(Shannon, Derrick), pair(Taylor, Osvaldo), pair(Tyranny, Jaylan));

      newTag().matchBox(pair(Tyranny, Derrick), false)
            .matchNight(9, pair(Alicia, Mike), pair(Carolina, Hayden), pair(Casandra, Derrick), pair(Gianna, Ozzy),
                  pair(Hannah, Joey), pair(Kam, Edward), pair(Kari, Michael), pair(Kathryn, Andre),
                  pair(Shannon, Tyler), pair(Taylor, Osvaldo), pair(Tyranny, Jaylan));

      newTag().matchBox(pair(Casandra, Joey), false)
            .matchNight(8, pair(Alicia, Mike), pair(Carolina, Hayden), pair(Casandra, Tyler), pair(Gianna, Ozzy),
                  pair(Hannah, Joey), pair(Kam, Edward), pair(Kari, Michael), pair(Kathryn, Andre),
                  pair(Shannon, Derrick), pair(Taylor, Osvaldo), pair(Tyranny, Jaylan));

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printDayResults(new AYTO_5());
   }

}
