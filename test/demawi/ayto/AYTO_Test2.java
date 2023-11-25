package demawi.ayto;

import demawi.ayto.modell.Woman;
import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

public class AYTO_Test2
      extends SeasonData {

   private final Woman Aurelia = frau("Aurelia");
   private final Woman Karo = frau("Karo");
   private final Woman Marina = frau("Marina");

   private final Man Barkin = mann("Barkin");
   private final Man Burim = mann("Burim");
   private final Man Christopher = mann("Christopher");

   public AYTO_Test2() {
      super(AYTO_Permutator.Mode.MARKED);

      Woman newFrau = frau("Sarah");
      newDay().addNew(newFrau);

      // newTag().addNew(newFrau);
      // newTag().matchBox(pair(Marina, Barkin), false);
      //newTag().sameMatch(newFrau, Marina);

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printLastDayResults(new AYTO_Test2());
   }

}
