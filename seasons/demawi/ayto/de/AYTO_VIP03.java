package demawi.ayto.de;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_VIP03
      extends StaffelData {

   private final Frau Shakira = frau("Shakira");
   private final Frau Sandra = frau("Sandra");
   private final Frau Alicia = frau("Alicia");
   private final Frau Sabrina = frau("Sabrina");
   private final Frau Stefanie = frau("Stefanie");
   private final Frau Jennifer = frau("Jennifer");
   private final Frau Paulina = frau("Paulina");
   private final Frau Kim = frau("Kim");
   private final Frau Marie = frau("Marie");
   private final Frau Darya = frau("Darya");

   private final Mann Paco = mann("Paco");
   private final Mann Mike = mann("Mike");
   private final Mann Peter = mann("Peter");
   private final Mann Danilo = mann("Danilo");
   private final Mann Martini_Teezy = mann("Teezy");
   private final Mann Marvin = mann("Marvin");
   private final Mann Elia = mann("Elia");
   private final Mann Emanuell = mann("Emanuell");
   private final Mann Steffen = mann("Steffen");
   private final Mann Fabio = mann("Fabio");
   private final Mann Max = mann("Max");

   public AYTO_VIP03() {
      super(AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER);

      newTag().matchBox(pair(Jennifer, Danilo), false)
            .matchNight(3, pair(Darya, Danilo), pair(Sandra, Paco), pair(Paulina, Steffen), pair(Shakira, Marvin),
                  pair(Kim, Mike), pair(Sabrina, Emanuell), pair(Jennifer, Elia), pair(Marie, Fabio),
                  pair(Stefanie, Martini_Teezy), pair(Alicia, Peter));

      newTag().matchBox(pair(Jennifer, Elia), false)
            .matchNight(2, pair(Sabrina, Mike), pair(Paulina, Danilo), pair(Kim, Paco), pair(Jennifer, Marvin),
                  pair(Stefanie, Martini_Teezy), pair(Darya, Emanuell), pair(Marie, Fabio), pair(Shakira, Peter),
                  pair(Sandra, Elia), pair(Alicia, Steffen));

      newTag().matchBox(pair(Darya, Elia), false)
            .matchNight(2, pair(Alicia, Steffen), pair(Sabrina, Peter), pair(Sandra, Paco), pair(Shakira, Fabio),
                  pair(Marie, Elia), pair(Darya, Danilo), pair(Stefanie, Marvin), pair(Paulina, Mike),
                  pair(Kim, Martini_Teezy), pair(Jennifer, Emanuell));

      newTag().addNew(Max)
            .matchBox(pair(Kim, Mike), false)
            .matchNight(4, pair(Sandra, Steffen), pair(Paulina, Max), pair(Marie, Elia), pair(Alicia, Martini_Teezy),
                  pair(Darya, Danilo), pair(Shakira, Fabio), pair(Sabrina, Paco), pair(Kim, Peter),
                  pair(Stefanie, Emanuell), pair(Jennifer, Marvin)); // Max

      newTag().matchBox(pair(Darya, Danilo), true);

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printDayResults(new AYTO_VIP03());
   }

}
