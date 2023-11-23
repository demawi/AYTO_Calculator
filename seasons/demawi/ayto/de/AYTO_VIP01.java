package demawi.ayto.de;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;
import static demawi.ayto.modell.Markierung.CAN_BE_AN_EXTRA;

public class AYTO_VIP01
      extends StaffelData {

  private final Frau Steffi = frau("Steffi");
  private final Frau Jill = frau("Jill");
  private final Frau Walentina = frau("Walentina");
  private final Frau Kathleen = frau("Kathleen");
  private final Frau Finnja = frau("Finnja");
  private final Frau Aurelia = frau("Aurelia");
  private final Frau Jacky = frau("Jacky");
  private final Frau Jules = frau("Jules");
  private final Frau Melina = frau("Melina");
  private final Frau Sarah = frau("Sarah");
  private final Frau Vanessa = frau("Vanessa", CAN_BE_AN_EXTRA, true);

  private final Mann Danilo = mann("Danilo");
  private final Mann Jamie = mann("Jamie");
  private final Mann Eugen = mann("Eugen");
  private final Mann Manuel = mann("Manuel");
  private final Mann Francesco = mann("Francesco");
  private final Mann Diogo = mann("Diogo");
  private final Mann Salvatore = mann("Salvatore");
  private final Mann Alex = mann("Alex");
  private final Mann Tommy = mann("Tommy");
  private final Mann Josua = mann("Josua");

  public AYTO_VIP01() {
    super(AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER);

    newTag().matchBox(pair(Finnja, Danilo), false)
          .matchNight(3, pair(Steffi, Danilo), pair(Jill, Jamie), pair(Walentina, Eugen), pair(Kathleen, Manuel),
                pair(Finnja, Francesco), pair(Aurelia, Diogo), pair(Jacky, Salvatore), pair(Jules, Alex),
                pair(Melina, Tommy), pair(Sarah, Josua));

    // Keine Matching Night
    newTag().matchBox(pair(Jules, Francesco), true);

    newTag().matchBox(pair(Walentina, Tommy), false)
          .matchNight(3, pair(Jules, Francesco), pair(Steffi, Eugen), pair(Jill, Jamie), pair(Walentina, Josua),
                pair(Kathleen, Manuel), pair(Finnja, Alex), pair(Aurelia, Diogo), pair(Jacky, Salvatore),
                pair(Sarah, Danilo), pair(Melina, Tommy));

    newTag().addNew(Vanessa)
          .matchBox(pair(Aurelia, Diogo), null)
          .matchNight(4, pair(Jules, Francesco), pair(Aurelia, Diogo), pair(Finnja, Eugen), pair(Jill, Tommy),
                pair(Kathleen, Manuel), pair(Melina, Danilo), pair(Sarah, Josua), pair(Steffi, Alex),
                pair(Walentina, Salvatore), pair(Vanessa, Jamie));

    newTag().matchBox(pair(Melina, Tommy), false)
          .matchNight(3, pair(Jules, Francesco), pair(Aurelia, Danilo), pair(Finnja, Eugen), pair(Jill, Manuel),
                pair(Kathleen, Salvatore), pair(Melina, Tommy), pair(Sarah, Alex), pair(Steffi, Jamie),
                pair(Walentina, Josua), pair(Vanessa, Diogo));

    newTag().matchBox(pair(Finnja, Salvatore), false)
          .matchNight(3, pair(Jules, Francesco), pair(Finnja, Eugen), pair(Jacky, Salvatore), pair(Kathleen, Manuel),
                pair(Steffi, Alex), pair(Jill, Danilo), pair(Sarah, Jamie), pair(Walentina, Josua), pair(Melina, Tommy),
                pair(Aurelia, Diogo));

    newTag().matchBox(pair(Finnja, Eugen), false)
          .matchNight(2, pair(Jules, Francesco), pair(Aurelia, Danilo), pair(Finnja, Jamie), pair(Jacky, Alex),
                pair(Jill, Eugen), pair(Kathleen, Manuel), pair(Melina, Tommy), pair(Sarah, Josua),
                pair(Walentina, Salvatore), pair(Vanessa, Diogo));

    newTag().matchBox(pair(Steffi, Eugen), false)
          .matchNight(1, pair(Jules, Francesco), pair(Aurelia, Danilo), pair(Finnja, Manuel), pair(Jacky, Eugen),
                pair(Jill, Salvatore), pair(Melina, Alex), pair(Sarah, Jamie), pair(Walentina, Josua),
                pair(Vanessa, Diogo), pair(Steffi, Tommy));

    newTag().matchBox(pair(Melina, Tommy), false)
          .matchNight(4, pair(Jules, Francesco), pair(Aurelia, Diogo), pair(Finnja, Tommy), pair(Jill, Danilo),
                pair(Kathleen, Manuel), pair(Melina, Eugen), pair(Sarah, Alex), pair(Walentina, Salvatore),
                pair(Vanessa, Josua), pair(Steffi, Jamie));

    newTag().matchBox(pair(Aurelia, Josua), true)
          .matchNight(7, pair(Jules, Francesco), pair(Aurelia, Josua), pair(Finnja, Tommy), pair(Jill, Diogo),
                pair(Kathleen, Manuel), pair(Sarah, Danilo), pair(Walentina, Eugen), pair(Vanessa, Alex),
                pair(Steffi, Jamie), pair(Jacky, Salvatore));

  }

  public static void main(String[] args) {
    new DefaultMatchPrinter().printDayResults(new AYTO_VIP01());
  }

}
