package demawi.ayto;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.EventbasedMatchPrinter;

import static demawi.ayto.modell.Pair.pair;

public class AYTO_4
      extends StaffelData {

  private final Frau Aurelia = frau("Aurelia");
  private final Frau Carina = frau("Carina");
  private final Frau Caroline = frau("Caroline");
  private final Frau Dorna = frau("Dorna");
  private final Frau Henna = frau("Henna");
  private final Frau Juliette = frau("Juliette");
  private final Frau Larissa = frau("Larissa");
  private final Frau Stefanie = frau("Stefanie");
  private final Frau Valeria = frau("Valeria");
  private final Frau Vanessa = frau("Vanessa");

  private final Mann Barkin = mann("Barkin");
  private final Mann Burim = mann("Burim");
  private final Mann Christopher = mann("Christopher");
  private final Mann Deniz = mann("Deniz");
  private final Mann Joel = mann("Joel");
  private final Mann Ken = mann("Ken");
  private final Mann Kenneth = mann("Kenneth");
  private final Mann Maximilian = mann("Maximilian");
  private final Mann Pascal = mann("Pascal");
  private final Mann Sasa = mann("Sasa");
  private final Mann Marwin = mann("Marwin");

  public AYTO_4() {
    super("04", AYTO_Permutator.ZUSATZTYPE.JEDER);

    newTag().addNew(Marwin)
          .matchBox(pair(Valeria, Marwin), false)
          .matchBox(pair(Dorna, Kenneth), false)
          .matchNight(null, pair(Aurelia, Barkin), pair(Carina, Burim), pair(Caroline, Christopher), pair(Dorna, Deniz),
                pair(Henna, Joel), pair(Juliette, Ken), pair(Larissa, Kenneth), pair(Stefanie, Maximilian),
                pair(Vanessa, Pascal), pair(Valeria, Sasa));

  }

  public static void main(String[] args) {
    new EventbasedMatchPrinter().printDayResults(new AYTO_4());
  }

}
