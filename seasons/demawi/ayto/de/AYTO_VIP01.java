package demawi.ayto.de;

import demawi.ayto.modell.PermutationConfiguration;
import demawi.ayto.modell.Woman;
import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;
import static demawi.ayto.permutation.Mark.CAN_BE_AN_EXTRA_MATCH;

public class AYTO_VIP01
      extends SeasonData {

  private final Woman Steffi = frau("Steffi");
  private final Woman Jill = frau("Jill");
  private final Woman Walentina = frau("Walentina");
  private final Woman Kathleen = frau("Kathleen");
  private final Woman Finnja = frau("Finnja");
  private final Woman Aurelia = frau("Aurelia");
  private final Woman Jacky = frau("Jacky");
  private final Woman Jules = frau("Jules");
  private final Woman Melina = frau("Melina");
  private final Woman Sarah = frau("Sarah");
  private final Woman Vanessa = frau("Vanessa", CAN_BE_AN_EXTRA_MATCH, true);

  private final Man Danilo = mann("Danilo");
  private final Man Jamie = mann("Jamie");
  private final Man Eugen = mann("Eugen");
  private final Man Manuel = mann("Manuel");
  private final Man Francesco = mann("Francesco");
  private final Man Diogo = mann("Diogo");
  private final Man Salvatore = mann("Salvatore");
  private final Man Alex = mann("Alex");
  private final Man Tommy = mann("Tommy");
  private final Man Josua = mann("Josua");

  public AYTO_VIP01() {
    super(new PermutationConfiguration(Mode.MARKED));

    newDay().matchBox(pair(Finnja, Danilo), false)
          .matchNight(3, pair(Steffi, Danilo), pair(Jill, Jamie), pair(Walentina, Eugen), pair(Kathleen, Manuel),
                pair(Finnja, Francesco), pair(Aurelia, Diogo), pair(Jacky, Salvatore), pair(Jules, Alex),
                pair(Melina, Tommy), pair(Sarah, Josua));

    // Keine Matching Night
    newDay().matchBox(pair(Jules, Francesco), true);

    newDay().matchBox(pair(Walentina, Tommy), false)
          .matchNight(3, pair(Jules, Francesco), pair(Steffi, Eugen), pair(Jill, Jamie), pair(Walentina, Josua),
                pair(Kathleen, Manuel), pair(Finnja, Alex), pair(Aurelia, Diogo), pair(Jacky, Salvatore),
                pair(Sarah, Danilo), pair(Melina, Tommy));

    newDay().addNew(Vanessa)
          .matchBox(pair(Aurelia, Diogo), null)
          .matchNight(4, pair(Jules, Francesco), pair(Aurelia, Diogo), pair(Finnja, Eugen), pair(Jill, Tommy),
                pair(Kathleen, Manuel), pair(Melina, Danilo), pair(Sarah, Josua), pair(Steffi, Alex),
                pair(Walentina, Salvatore), pair(Vanessa, Jamie));

    newDay().matchBox(pair(Melina, Tommy), false)
          .matchNight(3, pair(Jules, Francesco), pair(Aurelia, Danilo), pair(Finnja, Eugen), pair(Jill, Manuel),
                pair(Kathleen, Salvatore), pair(Melina, Tommy), pair(Sarah, Alex), pair(Steffi, Jamie),
                pair(Walentina, Josua), pair(Vanessa, Diogo));

    newDay().matchBox(pair(Finnja, Salvatore), false)
          .matchNight(3, pair(Jules, Francesco), pair(Finnja, Eugen), pair(Jacky, Salvatore), pair(Kathleen, Manuel),
                pair(Steffi, Alex), pair(Jill, Danilo), pair(Sarah, Jamie), pair(Walentina, Josua), pair(Melina, Tommy),
                pair(Aurelia, Diogo));

    newDay().matchBox(pair(Finnja, Eugen), false)
          .matchNight(2, pair(Jules, Francesco), pair(Aurelia, Danilo), pair(Finnja, Jamie), pair(Jacky, Alex),
                pair(Jill, Eugen), pair(Kathleen, Manuel), pair(Melina, Tommy), pair(Sarah, Josua),
                pair(Walentina, Salvatore), pair(Vanessa, Diogo));

    newDay().matchBox(pair(Steffi, Eugen), false)
          .matchNight(1, pair(Jules, Francesco), pair(Aurelia, Danilo), pair(Finnja, Manuel), pair(Jacky, Eugen),
                pair(Jill, Salvatore), pair(Melina, Alex), pair(Sarah, Jamie), pair(Walentina, Josua),
                pair(Vanessa, Diogo), pair(Steffi, Tommy));

    newDay().matchBox(pair(Melina, Tommy), false)
          .matchNight(4, pair(Jules, Francesco), pair(Aurelia, Diogo), pair(Finnja, Tommy), pair(Jill, Danilo),
                pair(Kathleen, Manuel), pair(Melina, Eugen), pair(Sarah, Alex), pair(Walentina, Salvatore),
                pair(Vanessa, Josua), pair(Steffi, Jamie));

    newDay().matchBox(pair(Aurelia, Josua), true)
          .matchNight(7, pair(Jules, Francesco), pair(Aurelia, Josua), pair(Finnja, Tommy), pair(Jill, Diogo),
                pair(Kathleen, Manuel), pair(Sarah, Danilo), pair(Walentina, Eugen), pair(Vanessa, Alex),
                pair(Steffi, Jamie), pair(Jacky, Salvatore));

  }

  public static void main(String[] args) {
    new DefaultMatchPrinter(new AYTO_VIP01()).printLastDayResults();
  }

}
