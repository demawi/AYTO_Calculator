package demawi.ayto.us;

import demawi.ayto.modell.AYTO_Pair;
import demawi.ayto.modell.Person;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_08
      extends StaffelData {

   private final Person Aasha = person("Aasha");
   private final Person Amber = person("Amber");
   private final Person Basit = person("Basit");
   private final Person Brandon = person("Brandon");
   private final Person Danny = person("Danny");
   private final Person Jasmine = person("Jasmine");
   private final Person Jenna = person("Jenna");
   private final Person Jonathan = person("Jonathan");
   private final Person Justin = person("Justin");
   private final Person Kai = person("Kai");
   private final Person Kari = person("Kari");
   private final Person Kylie = person("Kylie");
   private final Person Max = person("Max ");
   private final Person Nour = person("Nour");
   private final Person Paige = person("Paige");
   private final Person Remy = person("Remy");

   public AYTO_08() {
      super(AYTO_Permutator.ZUSATZTYPE.BISEXUAL, 8);

      newTag().matchBox(AYTO_Pair.pair(Justin, Nour), false)
            .matchNight(2, AYTO_Pair.pair(Aasha, Paige), AYTO_Pair.pair(Amber, Nour), AYTO_Pair.pair(Basit, Jonathan), AYTO_Pair.pair(Brandon, Remy),
                  AYTO_Pair.pair(Danny, Kai), AYTO_Pair.pair(Jasmine, Jenna), AYTO_Pair.pair(Jenna, Jasmine), AYTO_Pair.pair(Jonathan, Basit),
                  AYTO_Pair.pair(Justin, Max), AYTO_Pair.pair(Kai, Danny), AYTO_Pair.pair(Kari, Kylie), AYTO_Pair.pair(Kylie, Kari), AYTO_Pair.pair(Max, Justin),
                  AYTO_Pair.pair(Nour, Amber), AYTO_Pair.pair(Paige, Aasha), AYTO_Pair.pair(Remy, Brandon));
   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printDayResults(new AYTO_08());
   }

}
