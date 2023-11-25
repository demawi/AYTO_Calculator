package demawi.ayto.de;

import demawi.ayto.modell.Woman;
import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.permutation.AYTO_Permutator.MODE;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;
import static demawi.ayto.modell.Mark.CAN_BE_AN_EXTRA_MATCH;

public class AYTO_04
      extends SeasonData {

  private final Woman Aurelia = frau("Aurelia");
  private final Woman Carina = frau("Carina");
  private final Woman Caroline = frau("Caroline");
  private final Woman Dorna = frau("Dorna");
  private final Woman Henna = frau("Henna");
  private final Woman Juliette = frau("Juliette");
  private final Woman Larissa = frau("Larissa");
  private final Woman Stefanie = frau("Stefanie");
  private final Woman Valeria = frau("Valeria");
  private final Woman Vanessa = frau("Vanessa");

  private final Man Barkin = mann("Barkin", CAN_BE_AN_EXTRA_MATCH);
  private final Man Burim = mann("Burim", CAN_BE_AN_EXTRA_MATCH);
  private final Man Christopher = mann("Christopher", CAN_BE_AN_EXTRA_MATCH);
  private final Man Deniz = mann("Deniz", CAN_BE_AN_EXTRA_MATCH);
  private final Man Joel = mann("Joel", CAN_BE_AN_EXTRA_MATCH);
  private final Man Ken = mann("Ken", CAN_BE_AN_EXTRA_MATCH);
  private final Man Kenneth = mann("Kenneth", CAN_BE_AN_EXTRA_MATCH);
  private final Man Maximilian = mann("Maximilian", CAN_BE_AN_EXTRA_MATCH);
  private final Man Pascal = mann("Pascal", CAN_BE_AN_EXTRA_MATCH);
  private final Man Sasa = mann("Sasa", CAN_BE_AN_EXTRA_MATCH);
  private final Man Marwin = mann("Marwin", CAN_BE_AN_EXTRA_MATCH);

  public AYTO_04() {
    super(MODE.MARKED);

    newDay().addNew(Marwin)
          .matchBox(pair(Valeria, Marwin), false)
          .matchBox(pair(Dorna, Kenneth), false)
          .matchNight(3, pair(Vanessa, Sasa), pair(Carina, Ken), pair(Henna, Kenneth), pair(Juliette, Burim),
                pair(Larissa, Joel), pair(Aurelia, Deniz), pair(Dorna, Marwin), pair(Valeria, Maximilian),
                pair(Stefanie, Christopher), pair(Caroline, Barkin)); // Pascal

    newDay().matchBox(pair(Carina, Burim), false)
          .matchNight(3, pair(Larissa, Joel), pair(Juliette, Barkin), pair(Henna, Kenneth), pair(Carina, Ken),
                pair(Vanessa, Sasa), pair(Stefanie, Christopher), pair(Dorna, Marwin), pair(Valeria, Pascal),
                pair(Caroline, Maximilian), pair(Aurelia, Burim)); // Deniz

    newDay().matchBox(pair(Stefanie, Christopher), true)
          .matchNight(2, pair(Stefanie, Christopher), pair(Aurelia, Kenneth), pair(Valeria, Maximilian),
                pair(Caroline, Pascal), pair(Carina, Sasa), pair(Vanessa, Marwin), pair(Dorna, Deniz),
                pair(Larissa, Joel), pair(Juliette, Barkin), pair(Henna, Ken)); // Burim

    newDay().matchBox(pair(Valeria, Maximilian), false)
          .matchNight(3, pair(Stefanie, Christopher), pair(Henna, Ken), pair(Carina, Pascal), pair(Vanessa, Sasa),
                pair(Larissa, Deniz), pair(Dorna, Burim), pair(Juliette, Kenneth), pair(Aurelia, Marwin),
                pair(Valeria, Joel), pair(Caroline, Maximilian)); // Barkin

    newDay().matchBox(pair(Caroline, Ken), true, pair(Caroline, Maximilian))
          .matchNight(4, pair(Stefanie, Christopher), pair(Caroline, Ken), pair(Vanessa, Sasa), pair(Juliette, Barkin),
                pair(Carina, Pascal), pair(Henna, Kenneth), pair(Aurelia, Marwin), pair(Dorna, Burim),
                pair(Valeria, Joel), pair(Larissa, Deniz)); // Maximilian

    newDay().matchBox(pair(Dorna, Burim), false)
          .matchNight(3, pair(Stefanie, Christopher), pair(Caroline, Ken), pair(Vanessa, Joel), pair(Carina, Sasa),
                pair(Juliette, Barkin), pair(Henna, Kenneth), pair(Dorna, Deniz), pair(Larissa, Marwin),
                pair(Aurelia, Burim), pair(Valeria, Pascal)); // Maximilian

    newDay().matchBox(pair(Juliette, Joel), false)
          .matchNight(2, pair(Stefanie, Christopher), pair(Caroline, Ken), pair(Juliette, Kenneth),
                pair(Vanessa, Deniz), pair(Dorna, Barkin), pair(Henna, Burim), pair(Carina, Marwin),
                pair(Aurelia, Joel), pair(Valeria, Pascal), pair(Larissa, Sasa)); // Maximilian

    newDay().matchBox(pair(Dorna, Marwin), false)
          .matchNight(2, pair(Stefanie, Christopher), pair(Caroline, Ken), pair(Valeria, Burim), pair(Carina, Kenneth),
                pair(Juliette, Barkin), pair(Larissa, Pascal), pair(Henna, Deniz), pair(Dorna, Joel),
                pair(Vanessa, Sasa), pair(Aurelia, Marwin)); // Maximilian

    newDay().matchBox(pair(Carina, Pascal), false)
          .matchNight(10, pair(Stefanie, Christopher), pair(Caroline, Ken), pair(Dorna, Sasa), pair(Vanessa, Marwin),
                pair(Aurelia, Pascal), pair(Henna, Kenneth), pair(Juliette, Burim), pair(Larissa, Barkin),
                pair(Valeria, Joel), pair(Carina, Deniz));
  }

  public static void main(String[] args) {
    new DefaultMatchPrinter().printLastDayResults(new AYTO_04());
  }

}
