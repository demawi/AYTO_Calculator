package demawi.ayto.de;

import demawi.ayto.modell.Woman;
import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;
import static demawi.ayto.modell.Mark.CAN_BE_AN_EXTRA_MATCH;

public class AYTO_03
      extends SeasonData {

  private final Woman Jessica = frau("Jessica");
  private final Woman Joelina = frau("Joelina");
  private final Woman Kerstin = frau("Kerstin");
  private final Woman Monami = frau("Monami");
  private final Woman Marie = frau("Marie");
  private final Woman Zaira = frau("Zaira");
  private final Woman Dana = frau("Dana");
  private final Woman Isabelle = frau("Isabelle");
  private final Woman Raphaela = frau("Raphaela");
  private final Woman Estelle = frau("Estelle");
  private final Woman Desiree = frau("Desiree", CAN_BE_AN_EXTRA_MATCH, true);

  private final Man Leon = mann("Leon");
  private final Man Mike = mann("Mike");
  private final Man Max = mann("Max");
  private final Man Antonino = mann("Antonino");
  private final Man Tim = mann("Tim");
  private final Man Dustin = mann("Dustin");
  private final Man William = mann("William");
  private final Man Marius = mann("Marius");
  private final Man Andre = mann("Andre");
  private final Man Jordi = mann("Jordi");

  public AYTO_03() {
    super(Mode.MARKED);

    newDay().matchBox(pair(Jessica, Mike), false)
          .matchNight(3, pair(Jessica, Leon), pair(Joelina, Mike), pair(Kerstin, Max), pair(Monami, Antonino),
                pair(Marie, Tim), pair(Zaira, Dustin), pair(Dana, William), pair(Isabelle, Marius),
                pair(Raphaela, Andre), pair(Estelle, Jordi));

    newDay().matchBox(pair(Monami, Leon), false)
          .matchNight(2, pair(Dana, Andre), pair(Estelle, Leon), pair(Isabelle, Dustin), pair(Jessica, Tim),
                pair(Joelina, Mike), pair(Kerstin, Max), pair(Marie, Marius), pair(Monami, Antonino),
                pair(Raphaela, William), pair(Zaira, Jordi));

    newDay().matchBox(pair(Raphaela, Andre), false)
          .matchNight(2, pair(Dana, William), pair(Estelle, Leon), pair(Isabelle, Marius), pair(Jessica, Jordi),
                pair(Joelina, Mike), pair(Kerstin, Antonino), pair(Marie, Tim), pair(Monami, Max),
                pair(Raphaela, Andre), pair(Zaira, Dustin));

    newDay().matchBox(pair(Dana, William), null)
          .matchNight(3, pair(Dana, William), pair(Estelle, Leon), pair(Isabelle, Andre), pair(Jessica, Marius),
                pair(Joelina, Mike), pair(Kerstin, Tim), pair(Marie, Max), pair(Monami, Antonino),
                pair(Raphaela, Jordi), pair(Zaira, Dustin));

    // 5ter Tag
    newDay().addNew(Desiree)
          .matchBox(pair(Monami, Antonino), false)
          .matchNight(2, pair(Jessica, Leon), pair(Estelle, Mike), pair(Raphaela, Marius), pair(Desiree, Tim),
                pair(Joelina, Andre), pair(Isabelle, Dustin), pair(Monami, Max), pair(Kerstin, Antonino),
                pair(Zaira, Jordi), pair(Dana, William));

    // 6ter Tag
    newDay().matchBox(pair(Jessica, Max), false)
          .matchNight(4, pair(Dana, Antonino), pair(Estelle, Mike), pair(Isabelle, Dustin), pair(Jessica, Leon),
                pair(Joelina, Tim), pair(Kerstin, Max), pair(Marie, Andre), pair(Raphaela, William),
                pair(Zaira, Marius), pair(Desiree, Jordi));

    // 7ter Tag
    newDay().matchBox(pair(Zaira, Marius), true)
          .matchNight(6, pair(Zaira, Marius), pair(Raphaela, William), pair(Dana, Antonino), pair(Isabelle, Dustin),
                pair(Monami, Jordi), pair(Desiree, Andre), pair(Marie, Max), pair(Kerstin, Tim), pair(Joelina, Mike),
                pair(Jessica, Leon));

    // 8ter Tag
    newDay().matchBox(pair(Raphaela, Jordi), false)
          .matchNight(4, pair(Zaira, Marius), pair(Dana, Antonino), pair(Monami, Jordi), pair(Joelina, Tim),
                pair(Raphaela, William), pair(Estelle, Mike), pair(Isabelle, Dustin), pair(Kerstin, Max),
                pair(Jessica, Leon), pair(Marie, Andre));

    // 9ter Tag
    newDay().matchBox(pair(Kerstin, Max), false)
          .matchNight(2, pair(Zaira, Marius), pair(Raphaela, Max), pair(Marie, William), pair(Desiree, Andre),
                pair(Dana, Dustin), pair(Isabelle, Antonino), pair(Monami, Jordi), pair(Estelle, Mike),
                pair(Joelina, Tim), pair(Jessica, Leon));

    // 10ter Tag
    newDay().matchBox(pair(Isabelle, Dustin), false)
          .matchNight(10, pair(Zaira, Marius), pair(Marie, Dustin), pair(Isabelle, Andre), pair(Kerstin, Tim),
                pair(Monami, Max), pair(Estelle, Jordi), pair(Jessica, Leon), pair(Joelina, Mike),
                pair(Raphaela, William), pair(Dana, Antonino)); // Desiree

  }

  public static void main(String[] args) {
    new DefaultMatchPrinter().printLastDayResults(new AYTO_03());
  }

}
