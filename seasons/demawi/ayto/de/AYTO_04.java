package demawi.ayto.de;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;
import static demawi.ayto.modell.Markierung.CAN_BE_A_DOUBLE;

public class AYTO_04
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

  private final Mann Barkin = mann("Barkin", CAN_BE_A_DOUBLE);
  private final Mann Burim = mann("Burim", CAN_BE_A_DOUBLE);
  private final Mann Christopher = mann("Christopher", CAN_BE_A_DOUBLE);
  private final Mann Deniz = mann("Deniz", CAN_BE_A_DOUBLE);
  private final Mann Joel = mann("Joel", CAN_BE_A_DOUBLE);
  private final Mann Ken = mann("Ken", CAN_BE_A_DOUBLE);
  private final Mann Kenneth = mann("Kenneth", CAN_BE_A_DOUBLE);
  private final Mann Maximilian = mann("Maximilian", CAN_BE_A_DOUBLE);
  private final Mann Pascal = mann("Pascal", CAN_BE_A_DOUBLE);
  private final Mann Sasa = mann("Sasa", CAN_BE_A_DOUBLE);
  private final Mann Marwin = mann("Marwin", CAN_BE_A_DOUBLE);

  public AYTO_04() {
    super(AYTO_Permutator.ZUSATZTYPE.JEDER);

    newTag().addNew(Marwin)
          .matchBox(pair(Valeria, Marwin), false)
          .matchBox(pair(Dorna, Kenneth), false)
          .matchNight(3, pair(Vanessa, Sasa), pair(Carina, Ken), pair(Henna, Kenneth), pair(Juliette, Burim),
                pair(Larissa, Joel), pair(Aurelia, Deniz), pair(Dorna, Marwin), pair(Valeria, Maximilian),
                pair(Stefanie, Christopher), pair(Caroline, Barkin)); // Pascal

    newTag().matchBox(pair(Carina, Burim), false)
          .matchNight(3, pair(Larissa, Joel), pair(Juliette, Barkin), pair(Henna, Kenneth), pair(Carina, Ken),
                pair(Vanessa, Sasa), pair(Stefanie, Christopher), pair(Dorna, Marwin), pair(Valeria, Pascal),
                pair(Caroline, Maximilian), pair(Aurelia, Burim)); // Deniz

    newTag().matchBox(pair(Stefanie, Christopher), true)
          .matchNight(2, pair(Stefanie, Christopher), pair(Aurelia, Kenneth), pair(Valeria, Maximilian),
                pair(Caroline, Pascal), pair(Carina, Sasa), pair(Vanessa, Marwin), pair(Dorna, Deniz),
                pair(Larissa, Joel), pair(Juliette, Barkin), pair(Henna, Ken)); // Burim

    newTag().matchBox(pair(Valeria, Maximilian), false)
          .matchNight(3, pair(Stefanie, Christopher), pair(Henna, Ken), pair(Carina, Pascal), pair(Vanessa, Sasa),
                pair(Larissa, Deniz), pair(Dorna, Burim), pair(Juliette, Kenneth), pair(Aurelia, Marwin),
                pair(Valeria, Joel), pair(Caroline, Maximilian)); // Barkin

    newTag().matchBox(pair(Caroline, Ken), true, pair(Caroline, Maximilian))
          .matchNight(4, pair(Stefanie, Christopher), pair(Caroline, Ken), pair(Vanessa, Sasa), pair(Juliette, Barkin),
                pair(Carina, Pascal), pair(Henna, Kenneth), pair(Aurelia, Marwin), pair(Dorna, Burim),
                pair(Valeria, Joel), pair(Larissa, Deniz)); // Maximilian

    newTag().matchBox(pair(Dorna, Burim), false)
          .matchNight(3, pair(Stefanie, Christopher), pair(Caroline, Ken), pair(Vanessa, Joel), pair(Carina, Sasa),
                pair(Juliette, Barkin), pair(Henna, Kenneth), pair(Dorna, Deniz), pair(Larissa, Marwin),
                pair(Aurelia, Burim), pair(Valeria, Pascal)); // Maximilian

    newTag().matchBox(pair(Juliette, Joel), false)
          .matchNight(2, pair(Stefanie, Christopher), pair(Caroline, Ken), pair(Juliette, Kenneth),
                pair(Vanessa, Deniz), pair(Dorna, Barkin), pair(Henna, Burim), pair(Carina, Marwin),
                pair(Aurelia, Joel), pair(Valeria, Pascal), pair(Larissa, Sasa)); // Maximilian

    newTag().matchBox(pair(Dorna, Marwin), false)
          .matchNight(2, pair(Stefanie, Christopher), pair(Caroline, Ken), pair(Valeria, Burim), pair(Carina, Kenneth),
                pair(Juliette, Barkin), pair(Larissa, Pascal), pair(Henna, Deniz), pair(Dorna, Joel),
                pair(Vanessa, Sasa), pair(Aurelia, Marwin)); // Maximilian

    newTag().matchBox(pair(Carina, Pascal), false)
          .matchNight(10, pair(Stefanie, Christopher), pair(Caroline, Ken), pair(Dorna, Sasa), pair(Vanessa, Marwin),
                pair(Aurelia, Pascal), pair(Henna, Kenneth), pair(Juliette, Burim), pair(Larissa, Barkin),
                pair(Valeria, Joel), pair(Carina, Deniz));
  }

  public static void main(String[] args) {
    new DefaultMatchPrinter().printDayResults(new AYTO_04());
  }

}
