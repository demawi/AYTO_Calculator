package demawi.ayto.de;

import demawi.ayto.modell.Woman;
import demawi.ayto.modell.Man;
import demawi.ayto.modell.Mark;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_VIP03_ohneRauswurf
      extends SeasonData {

   private final Woman Shakira = frau("Shakira");
   private final Woman Sandra = frau("Sandra");
   private final Woman Alicia = frau("Alicia");
   private final Woman Sabrina = frau("Sabrina");
   private final Woman Stefanie = frau("Stefanie");
   private final Woman Jennifer = frau("Jennifer");
   private final Woman Paulina = frau("Paulina");
   private final Woman Kim = frau("Kim");
   private final Woman Marie = frau("Marie");
   private final Woman Darya = frau("Darya");

   private final Man Paco = mann("Paco");
   private final Man Mike = mann("Mike");
   private final Man Peter = mann("Peter");
   private final Man Danilo = mann("Danilo");
   private final Man Martini_Teezy = mann("Teezy");
   private final Man Marvin = mann("Marvin");
   private final Man Elia = mann("Elia");
   private final Man Emanuell = mann("Emanuell");
   private final Man Steffen = mann("Steffen");
   private final Man Fabio = mann("Fabio");
   private final Man Max = mann("Max", Mark.CAN_BE_AN_EXTRA_MATCH, true);

   public AYTO_VIP03_ohneRauswurf() {
      super(Mode.MARKED);

      newDay().matchBox(pair(Jennifer, Danilo), false)
            .matchNight(3, pair(Darya, Danilo), pair(Sandra, Paco), pair(Paulina, Steffen), pair(Shakira, Marvin),
                  pair(Kim, Mike), pair(Sabrina, Emanuell), pair(Jennifer, Elia), pair(Marie, Fabio),
                  pair(Stefanie, Martini_Teezy), pair(Alicia, Peter));

      newDay().matchBox(pair(Jennifer, Elia), false)
            .matchNight(2, pair(Sabrina, Mike), pair(Paulina, Danilo), pair(Kim, Paco), pair(Jennifer, Marvin),
                  pair(Stefanie, Martini_Teezy), pair(Darya, Emanuell), pair(Marie, Fabio), pair(Shakira, Peter),
                  pair(Sandra, Elia), pair(Alicia, Steffen));

      newDay().matchBox(pair(Darya, Elia), false)
            .matchNight(2, pair(Alicia, Steffen), pair(Sabrina, Peter), pair(Sandra, Paco), pair(Shakira, Fabio),
                  pair(Marie, Elia), pair(Darya, Danilo), pair(Stefanie, Marvin), pair(Paulina, Mike),
                  pair(Kim, Martini_Teezy), pair(Jennifer, Emanuell));

      newDay().addNew(Max)
            .matchBox(pair(Kim, Mike), false)
            .matchNight(4, pair(Sandra, Steffen), pair(Paulina, Max), pair(Marie, Elia), pair(Alicia, Martini_Teezy),
                  pair(Darya, Danilo), pair(Shakira, Fabio), pair(Sabrina, Paco), pair(Kim, Peter),
                  pair(Stefanie, Emanuell), pair(Jennifer, Marvin)); // Mike

      newDay().matchBox(pair(Darya, Danilo), true)
            .matchNight(3, pair(Darya, Danilo), pair(Paulina, Mike), pair(Kim, Martini_Teezy), pair(Shakira, Paco),
                  pair(Stefanie, Elia), pair(Jennifer, Marvin), pair(Sandra, Max), pair(Alicia, Steffen),
                  pair(Sabrina, Emanuell), pair(Marie, Fabio)); // Peter

      newDay().matchBox(pair(Alicia, Martini_Teezy), false)
            .matchNight(4, pair(Darya, Danilo), pair(Alicia, Paco), pair(Sandra, Steffen), pair(Paulina, Mike),
                  pair(Jennifer, Marvin), pair(Shakira, Fabio), pair(Marie, Elia), pair(Sabrina, Peter),
                  pair(Stefanie, Emanuell), pair(Kim, Martini_Teezy)); // Max

      newDay().matchNight(3, pair(Darya, Danilo), pair(Stefanie, Elia), pair(Marie, Steffen), pair(Kim, Martini_Teezy),
            pair(Sabrina, Emanuell), pair(Max, Shakira), pair(Paulina, Marvin), pair(Jennifer, Paco),
            pair(Alicia, Fabio), pair(Sandra, Mike)); // Peter

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printLastDayResults(new AYTO_VIP03_ohneRauswurf());
   }

}
