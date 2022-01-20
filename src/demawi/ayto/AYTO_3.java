package demawi.ayto;

import static demawi.ayto.modell.Pair.*;

import java.util.Arrays;
import java.util.Collection;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.MatchingNight;
import demawi.ayto.modell.Pair;

public class AYTO_3 extends MatchFinder {

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

  private AYTO_3() {
    super("AYTO_3");
    add(false, pair(Jessica, Mike));
    mn(3, pair(Jessica, Leon), pair(Joelina, Mike), pair(Kerstin, Max), pair(Monami, Antonino), pair(Marie, Tim),
      pair(Zaira, Dustin), pair(Dana, William), pair(Isabelle, Marius), pair(Raphaela, Andre), pair(Estelle, Jordi));

    add(false, pair(Monami, Leon));
    mn(2, pair(Dana, Andre), pair(Estelle, Leon), pair(Isabelle, Dustin), pair(Jessica, Tim), pair(Joelina, Mike),
      pair(Kerstin, Max), pair(Marie, Marius), pair(Monami, Antonino), pair(Raphaela, William), pair(Zaira, Jordi));

    add(false, pair(Raphaela, Andre));
    mn(2, pair(Dana, William), pair(Estelle, Leon), pair(Isabelle, Marius), pair(Jessica, Jordi), pair(Joelina, Mike),
      pair(Kerstin, Antonino), pair(Marie, Tim), pair(Monami, Max), pair(Raphaela, Andre), pair(Zaira, Dustin));

    // add(true, pair(Dana, William));
    mn(3, pair(Dana, William), pair(Estelle, Leon), pair(Isabelle, Andre), pair(Jessica, Marius), pair(Joelina, Mike),
      pair(Kerstin, Tim), pair(Marie, Max), pair(Monami, Antonino), pair(Raphaela, Jordi), pair(Zaira, Dustin));

    // 5ter Tag
    add(false, pair(Monami, Antonino));
    mn(2, pair(Jessica, Leon), pair(Estelle, Mike), pair(Raphaela, Marius), pair(Desiree, Tim), pair(Joelina, Andre),
      pair(Isabelle, Dustin), pair(Monami, Max), pair(Kerstin, Antonino), pair(Zaira, Jordi), pair(Dana, William));

    // 6ter Tag
    add(false, pair(Jessica, Max));
    if (false) {
      mn(4, pair(Dana, Antonino), pair(Estelle, Mike), pair(Isabelle, Dustin), pair(Jessica, Leon), pair(Joelina, Tim),
        pair(Kerstin, Max), pair(Marie, Andre), pair(Raphaela, William), pair(Zaira, Marius), pair(Desiree, Jordi));

      // coming
      // add(false, pair(Kerstin, Max)); // 26.32
      // add(false, pair(Isabelle, Dustin)); // 0
      // add(false, pair(Zaira, Marius)); // 57.89
      // add(false, pair(Raphaela, Jordi)); // 15.79

    }

  }

  public Collection<Pair> testtest() {
    if (false) {
      return new MatchingNight(4, pair(Kerstin, Tim), pair(Dana, Dustin), pair(Monami, Max), pair(Estelle, Jordi),
        pair(Zaira, Marius), pair(Joelina, Mike), pair(Marie, Antonino), pair(Jessica, Leon), pair(Isabelle, Andre),
        pair(Raphaela, William)).constellation;
    }

    return Arrays.asList(pair(Kerstin, Tim), pair(Marie, Dustin), pair(Monami, Max), pair(Estelle, Jordi),
      pair(Zaira, Marius), pair(Dana, Antonino), pair(Joelina, Mike), pair(Jessica, Leon), pair(Desiree, Marius),
      pair(Isabelle, Andre), pair(Raphaela, William));
  }

  public static void main(String[] args) {
    AYTO_3 ayto = new AYTO_3();
    // System.out.println(ayto.test(ayto.testtest(), true));
    ayto.search(pair(Dana, Antonino), pair(Estelle, Mike), pair(Isabelle, Dustin), pair(Jessica, Leon),
      pair(Joelina, Tim), pair(Kerstin, Max), pair(Marie, Andre), pair(Raphaela, William), pair(Zaira, Marius),
      pair(Desiree, Jordi));

    // ayto.search();
  }

}
