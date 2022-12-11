package demawi.ayto;

import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.Pair.pair;

public class AYTO_Test
      extends AYTO_Data {

  private final Frau Aurelia = frau("Aurelia");
  private final Frau Karo = frau("Karo");
  private final Frau Marina = frau("Marina");

  private final Mann Barkin = mann("Barkin");
  private final Mann Burim = mann("Burim");
  private final Mann Christopher = mann("Christopher");

  public AYTO_Test() {
    super("04", AYTO_Permutator.ZUSATZTYPE.JEDER);

    if (false) {
      Frau newFrau = new Frau("Sarah");
      newTag().addNew(newFrau)
            .matchBox(pair(Aurelia, Barkin), true);
    }
    else {
      Mann newMann = new Mann("Deniz");
      newTag().addNew(newMann)
            .matchBox(pair(Aurelia, Barkin), true)
            .matchBox(pair(Aurelia, Burim), true);
    }
  }

  public static void main(String[] args) {
    new DefaultMatchPrinter().printDayResults(new AYTO_Test());
  }

}
