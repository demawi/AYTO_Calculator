package demawi.ayto;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_3
      extends StaffelData {

  private final Frau Jessica = frau("Jessica");
  private final Frau Joelina = frau("Joelina");
  private final Frau Kerstin = frau("Kerstin");
  private final Frau Monami = frau("Monami");
  private final Frau Marie = frau("Marie");
  private final Frau Zaira = frau("Zaira");
  private final Frau Dana = frau("Dana");
  private final Frau Isabelle = frau("Isabelle");
  private final Frau Raphaela = frau("Raphaela");
  private final Frau Estelle = frau("Estelle");
  private final Frau Desiree = frau("Desiree");

  private final Mann Leon = mann("Leon");
  private final Mann Mike = mann("Mike");
  private final Mann Max = mann("Max");
  private final Mann Antonino = mann("Antonino");
  private final Mann Tim = mann("Tim");
  private final Mann Dustin = mann("Dustin");
  private final Mann William = mann("William");
  private final Mann Marius = mann("Marius");
  private final Mann Andre = mann("Andre");
  private final Mann Jordi = mann("Jordi");

  public AYTO_3() {
    super("03", AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER);

    newTag().matchBox(pair(Jessica, Mike), false)
          .matchNight(3, pair(Jessica, Leon), pair(Joelina, Mike), pair(Kerstin, Max), pair(Monami, Antonino),
                pair(Marie, Tim), pair(Zaira, Dustin), pair(Dana, William), pair(Isabelle, Marius),
                pair(Raphaela, Andre), pair(Estelle, Jordi));

    newTag().matchBox(pair(Monami, Leon), false)
          .matchNight(2, pair(Dana, Andre), pair(Estelle, Leon), pair(Isabelle, Dustin), pair(Jessica, Tim),
                pair(Joelina, Mike), pair(Kerstin, Max), pair(Marie, Marius), pair(Monami, Antonino),
                pair(Raphaela, William), pair(Zaira, Jordi));

    newTag().matchBox(pair(Raphaela, Andre), false)
          .matchNight(2, pair(Dana, William), pair(Estelle, Leon), pair(Isabelle, Marius), pair(Jessica, Jordi),
                pair(Joelina, Mike), pair(Kerstin, Antonino), pair(Marie, Tim), pair(Monami, Max),
                pair(Raphaela, Andre), pair(Zaira, Dustin));

    newTag().matchBox(pair(Dana, William), null)
          .matchNight(3, pair(Dana, William), pair(Estelle, Leon), pair(Isabelle, Andre), pair(Jessica, Marius),
                pair(Joelina, Mike), pair(Kerstin, Tim), pair(Marie, Max), pair(Monami, Antonino),
                pair(Raphaela, Jordi), pair(Zaira, Dustin));

    // 5ter Tag
    newTag().addNew(Desiree)
          .matchBox(pair(Monami, Antonino), false)
          .matchNight(2, pair(Jessica, Leon), pair(Estelle, Mike), pair(Raphaela, Marius), pair(Desiree, Tim),
                pair(Joelina, Andre), pair(Isabelle, Dustin), pair(Monami, Max), pair(Kerstin, Antonino),
                pair(Zaira, Jordi), pair(Dana, William));

    // 6ter Tag
    newTag().matchBox(pair(Jessica, Max), false)
          .matchNight(4, pair(Dana, Antonino), pair(Estelle, Mike), pair(Isabelle, Dustin), pair(Jessica, Leon),
                pair(Joelina, Tim), pair(Kerstin, Max), pair(Marie, Andre), pair(Raphaela, William),
                pair(Zaira, Marius), pair(Desiree, Jordi));

    // 7ter Tag
    newTag().matchBox(pair(Zaira, Marius), true)
          .matchNight(6, pair(Zaira, Marius), pair(Raphaela, William), pair(Dana, Antonino), pair(Isabelle, Dustin),
                pair(Monami, Jordi), pair(Desiree, Andre), pair(Marie, Max), pair(Kerstin, Tim), pair(Joelina, Mike),
                pair(Jessica, Leon));

    // 8ter Tag
    newTag().matchBox(pair(Raphaela, Jordi), false)
          .matchNight(4, pair(Zaira, Marius), pair(Dana, Antonino), pair(Monami, Jordi), pair(Joelina, Tim),
                pair(Raphaela, William), pair(Estelle, Mike), pair(Isabelle, Dustin), pair(Kerstin, Max),
                pair(Jessica, Leon), pair(Marie, Andre));

    // 9ter Tag
    newTag().matchBox(pair(Kerstin, Max), false)
          .matchNight(2, pair(Zaira, Marius), pair(Raphaela, Max), pair(Marie, William), pair(Desiree, Andre),
                pair(Dana, Dustin), pair(Isabelle, Antonino), pair(Monami, Jordi), pair(Estelle, Mike),
                pair(Joelina, Tim), pair(Jessica, Leon));

    // 10ter Tag
    newTag().matchBox(pair(Isabelle, Dustin), false)
          .matchNight(10, pair(Zaira, Marius), pair(Marie, Dustin), pair(Isabelle, Andre), pair(Kerstin, Tim),
                pair(Monami, Max), pair(Estelle, Jordi), pair(Jessica, Leon), pair(Joelina, Mike),
                pair(Raphaela, William), pair(Dana, Antonino)); // Desiree

  }

  public static void main(String[] args) {
    new DefaultMatchPrinter().printDayResults(new AYTO_3());
  }

}
