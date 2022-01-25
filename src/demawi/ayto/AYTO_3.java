package demawi.ayto;

import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;

import static demawi.ayto.modell.Pair.pair;

public class AYTO_3
      extends AYTO_Data {

  private static Frau Jessica = new Frau("Jessica");
  private static Frau Joelina = new Frau("Joelina");
  private static Frau Kerstin = new Frau("Kerstin");
  private static Frau Monami = new Frau("Monami");
  private static Frau Marie = new Frau("Marie");
  private static Frau Zaira = new Frau("Zaira");
  private static Frau Dana = new Frau("Dana");
  private static Frau Isabelle = new Frau("Isabelle");
  private static Frau Raphaela = new Frau("Raphaela");
  private static Frau Estelle = new Frau("Estelle");
  private static Frau Desiree = new Frau("Desiree");

  private static Mann Leon = new Mann("Leon");
  private static Mann Mike = new Mann("Mike");
  private static Mann Max = new Mann("Max");
  private static Mann Antonino = new Mann("Antonino");
  private static Mann Tim = new Mann("Tim");
  private static Mann Dustin = new Mann("Dustin");
  private static Mann William = new Mann("William");
  private static Mann Marius = new Mann("Marius");
  private static Mann Andre = new Mann("Andre");
  private static Mann Jordi = new Mann("Jordi");

  public AYTO_3() {
    super("03");
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
    add(false, pair(Jessica, Max), 4, pair(Dana, Antonino), pair(Estelle, Mike), pair(Isabelle, Dustin),
          pair(Jessica, Leon), pair(Joelina, Tim), pair(Kerstin, Max), pair(Marie, Andre), pair(Raphaela, William),
          pair(Zaira, Marius), pair(Desiree, Jordi));

    // 7ter Tag
    add(true, pair(Zaira, Marius), 6, pair(Zaira, Marius), pair(Raphaela, William), pair(Dana, Antonino),
          pair(Isabelle, Dustin), pair(Monami, Jordi), pair(Desiree, Andre), pair(Marie, Max), pair(Kerstin, Tim),
          pair(Joelina, Mike), pair(Jessica, Leon));

    // coming
    // add(false, pair(Kerstin, Max)); // 0
    // add(false, pair(Isabelle, Dustin)); // 0
    // add(false, pair(Raphaela, Jordi)); // 0

  }

  public static void main(String[] args) {
    new MatchFinder().printLightChancesAndResult(new AYTO_3());
  }

}
