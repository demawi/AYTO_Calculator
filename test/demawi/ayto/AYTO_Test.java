package demawi.ayto;

import demawi.ayto.modell.Woman;
import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_Test
      extends SeasonData {

  private final Woman Aurelia = frau("Aurelia");
  private final Woman Karo = frau("Karo");
  private final Woman Marina = frau("Marina");

  private final Man Barkin = mann("Barkin");
  private final Man Burim = mann("Burim");
  private final Man Christopher = mann("Christopher");

  public AYTO_Test() {
    super(AYTO_Permutator.MODE.MARKED);

    if (true) {
      Woman newFrau = new Woman("Sarah");
      newDay().addNew(newFrau)
            .matchBox(pair(Aurelia, Barkin), true);
    }
    else {
      Man newMann = new Man("Deniz");
      newDay().addNew(newMann)
            .matchBox(pair(Aurelia, Barkin), true, pair(Aurelia, Burim));
    }
  }

  public static void main(String[] args) {
    new DefaultMatchPrinter().printLastDayResults(new AYTO_Test());
  }

}
