package demawi.ayto;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_Test
      extends StaffelData {

  private final Frau Aurelia = frau("Aurelia");
  private final Frau Karo = frau("Karo");
  private final Frau Marina = frau("Marina");

  private final Mann Barkin = mann("Barkin");
  private final Mann Burim = mann("Burim");
  private final Mann Christopher = mann("Christopher");

  public AYTO_Test() {
    super(AYTO_Permutator.ZUSATZTYPE.ALLE);

    if (true) {
      Frau newFrau = new Frau("Sarah");
      newTag().addNew(newFrau)
            .matchBox(pair(Aurelia, Barkin), true);
    }
    else {
      Mann newMann = new Mann("Deniz");
      newTag().addNew(newMann)
            .matchBox(pair(Aurelia, Barkin), true, pair(Aurelia, Burim));
    }
  }

  public static void main(String[] args) {
    new DefaultMatchPrinter().printDayResults(new AYTO_Test());
  }

}
