package demawi.ayto;

import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.perm.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.Pair.pair;

public class AYTO_3
      extends AYTO_Data {

  private static Frau Jessica = frau("Jessica");
  private static Frau Joelina = frau("Joelina");
  private static Frau Kerstin = frau("Kerstin");
  private static Frau Monami = frau("Monami");
  private static Frau Marie = frau("Marie");
  private static Frau Zaira = frau("Zaira");
  private static Frau Dana = frau("Dana");
  private static Frau Isabelle = frau("Isabelle");
  private static Frau Raphaela = frau("Raphaela");
  private static Frau Estelle = frau("Estelle");
  private static Frau Desiree = frau("Desiree");

  private static Mann Leon = mann("Leon");
  private static Mann Mike = mann("Mike");
  private static Mann Max = mann("Max");
  private static Mann Antonino = mann("Antonino");
  private static Mann Tim = mann("Tim");
  private static Mann Dustin = mann("Dustin");
  private static Mann William = mann("William");
  private static Mann Marius = mann("Marius");
  private static Mann Andre = mann("Andre");
  private static Mann Jordi = mann("Jordi");

  public AYTO_3() {
    super("03", AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER);

    add(false, pair(Jessica, Mike), 3, pair(Jessica, Leon), pair(Joelina, Mike), pair(Kerstin, Max),
          pair(Monami, Antonino), pair(Marie, Tim), pair(Zaira, Dustin), pair(Dana, William), pair(Isabelle, Marius),
          pair(Raphaela, Andre), pair(Estelle, Jordi));

    add(false, pair(Monami, Leon), 2, pair(Dana, Andre), pair(Estelle, Leon), pair(Isabelle, Dustin),
          pair(Jessica, Tim), pair(Joelina, Mike), pair(Kerstin, Max), pair(Marie, Marius), pair(Monami, Antonino),
          pair(Raphaela, William), pair(Zaira, Jordi));

    add(false, pair(Raphaela, Andre), 2, pair(Dana, William), pair(Estelle, Leon), pair(Isabelle, Marius),
          pair(Jessica, Jordi), pair(Joelina, Mike), pair(Kerstin, Antonino), pair(Marie, Tim), pair(Monami, Max),
          pair(Raphaela, Andre), pair(Zaira, Dustin));

    add(null, pair(Dana, William), 3, pair(Dana, William), pair(Estelle, Leon), pair(Isabelle, Andre),
          pair(Jessica, Marius), pair(Joelina, Mike), pair(Kerstin, Tim), pair(Marie, Max), pair(Monami, Antonino),
          pair(Raphaela, Jordi), pair(Zaira, Dustin));

    // 5ter Tag
    add(false, pair(Monami, Antonino), 2, pair(Jessica, Leon), pair(Estelle, Mike), pair(Raphaela, Marius),
          pair(Desiree, Tim), pair(Joelina, Andre), pair(Isabelle, Dustin), pair(Monami, Max), pair(Kerstin, Antonino),
          pair(Zaira, Jordi), pair(Dana, William));

    // 6ter Tag
    addTag().matchBox(pair(Jessica, Max), false).matchNight(4, pair(Dana, Antonino), pair(Estelle, Mike), pair(Isabelle, Dustin),
          pair(Jessica, Leon), pair(Joelina, Tim), pair(Kerstin, Max), pair(Marie, Andre), pair(Raphaela, William),
          pair(Zaira, Marius), pair(Desiree, Jordi)).addNew(Desiree);

    // 7ter Tag
    add(true, pair(Zaira, Marius), 6, pair(Zaira, Marius), pair(Raphaela, William), pair(Dana, Antonino),
          pair(Isabelle, Dustin), pair(Monami, Jordi), pair(Desiree, Andre), pair(Marie, Max), pair(Kerstin, Tim),
          pair(Joelina, Mike), pair(Jessica, Leon));

    // 8ter Tag
    add(false, pair(Raphaela, Jordi), 4, pair(Zaira, Marius), pair(Dana, Antonino), pair(Monami, Jordi),
          pair(Joelina, Tim), pair(Raphaela, William), pair(Estelle, Mike), pair(Isabelle, Dustin), pair(Kerstin, Max),
          pair(Jessica, Leon), pair(Marie, Andre));

    // 9ter Tag
    add(false, pair(Kerstin, Max), 2, pair(Zaira, Marius), pair(Raphaela, Max), pair(Marie, William),
          pair(Desiree, Andre), pair(Dana, Dustin), pair(Isabelle, Antonino), pair(Monami, Jordi), pair(Estelle, Mike),
          pair(Joelina, Tim), pair(Jessica, Leon));

    // 10ter Tag
    add(false, pair(Isabelle, Dustin), 10, pair(Zaira, Marius), pair(Marie, Dustin), pair(Isabelle, Andre),
          pair(Kerstin, Tim), pair(Monami, Max), pair(Estelle, Jordi), pair(Jessica, Leon), pair(Joelina, Mike),
          pair(Raphaela, William), pair(Dana, Antonino)); // Desiree

    track(pair(Estelle, Jordi), pair(Zaira, Marius), pair(Monami, Max), pair(Isabelle, Andre), pair(Dana, Antonino),
          pair(Joelina, Mike), pair(Raphaela, William), pair(Marie, Dustin), pair(Kerstin, Tim), pair(Jessica, Leon));

    // coming

  }

  public static void main(String[] args) {
    new DefaultMatchPrinter().printDayResults(new AYTO_3());
  }

}
