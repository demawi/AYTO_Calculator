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
  private final Frau Carina = frau("Carina");
  private final Frau Caroline = frau("Caroline");

  private final Mann Barkin = mann("Barkin");
  private final Mann Burim = mann("Burim");
  private final Mann Christopher = mann("Christopher");
  private final Mann Deniz = mann("Deniz");

  public AYTO_Test() {
    super("04", AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER);

    newTag().addNew(Deniz);
  }

  public static void main(String[] args) {
    new DefaultMatchPrinter().printDayResults(new AYTO_Test());
  }

}
