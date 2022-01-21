package demawi.ayto;

import static demawi.ayto.modell.Pair.*;

import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;

public class AYTO_VIP01 extends MatchFinder {

  private static Frau Steffi = new Frau("Steffi");
  private static Frau Jill = new Frau("Jill");
  private static Frau Walentina = new Frau("Walentina");
  private static Frau Kathleen = new Frau("Kathleen");
  private static Frau Finnja = new Frau("Finnja");
  private static Frau Aurelia = new Frau("Aurelia");
  private static Frau Jacky = new Frau("Jacky");
  private static Frau Jules = new Frau("Jules");
  private static Frau Melina = new Frau("Melina");
  private static Frau Sarah = new Frau("Sarah");
  private static Frau Vanessa = new Frau("Vanessa");

  private static Mann Danilo = new Mann("Danilo");
  private static Mann Jamie = new Mann("Jamie");
  private static Mann Eugen = new Mann("Eugen");
  private static Mann Manuel = new Mann("Manuel");
  private static Mann Francesco = new Mann("Francesco");
  private static Mann Diogo = new Mann("Diogo");
  private static Mann Salvatore = new Mann("Salvatore");
  private static Mann Alex = new Mann("Alex");
  private static Mann Tommy = new Mann("Tommy");
  private static Mann Josua = new Mann("Josua");

  private AYTO_VIP01() {
    super("AYTO_VIP");

    AYTO_Data data = new AYTO_Data();
    data.add(false, pair(Finnja, Danilo));
    data.add(3, pair(Steffi, Danilo), pair(Jill, Jamie), pair(Walentina, Eugen), pair(Kathleen, Manuel),
      pair(Finnja, Francesco), pair(Aurelia, Diogo), pair(Jacky, Salvatore), pair(Jules, Alex), pair(Melina, Tommy),
      pair(Sarah, Josua));

    data.add(true, pair(Jules, Francesco));
    // Keine Matching Night, deswegen Kopie von vorher
    data.add(3, pair(Steffi, Danilo), pair(Jill, Jamie), pair(Walentina, Eugen), pair(Kathleen, Manuel),
      pair(Finnja, Francesco), pair(Aurelia, Diogo), pair(Jacky, Salvatore), pair(Jules, Alex), pair(Melina, Tommy),
      pair(Sarah, Josua));

    data.add(false, pair(Walentina, Tommy));
    data.add(3, pair(Jules, Francesco), pair(Steffi, Eugen), pair(Jill, Jamie), pair(Walentina, Josua),
      pair(Kathleen, Manuel), pair(Finnja, Alex), pair(Aurelia, Diogo), pair(Jacky, Salvatore), pair(Sarah, Danilo),
      pair(Melina, Tommy));

    // data.add(false, pair(Aurelia, Diogo)); // verkauft
    data.add(4, pair(Jules, Francesco), pair(Aurelia, Diogo), pair(Finnja, Eugen), pair(Jill, Tommy),
      pair(Kathleen, Manuel), pair(Melina, Danilo), pair(Sarah, Josua), pair(Steffi, Alex), pair(Walentina, Salvatore),
      pair(Vanessa, Jamie));

    // data.add(false, pair(Melina, Tommy));
    data.add(3, pair(Jules, Francesco), pair(Aurelia, Danilo), pair(Finnja, Eugen), pair(Jill, Manuel),
      pair(Kathleen, Salvatore), pair(Melina, Tommy), pair(Sarah, Alex), pair(Steffi, Jamie), pair(Walentina, Josua),
      pair(Vanessa, Diogo));

    data.add(false, pair(Finnja, Salvatore));
    data.add(3, pair(Jules, Francesco), pair(Finnja, Eugen), pair(Jacky, Salvatore), pair(Kathleen, Manuel),
      pair(Steffi, Alex), pair(Jill, Danilo), pair(Sarah, Jamie), pair(Walentina, Josua), pair(Melina, Tommy),
      pair(Aurelia, Diogo));

    data.add(false, pair(Finnja, Eugen));
    data.add(2, pair(Jules, Francesco), pair(Aurelia, Danilo), pair(Finnja, Jamie), pair(Jacky, Alex),
      pair(Jill, Eugen), pair(Kathleen, Manuel), pair(Melina, Tommy), pair(Sarah, Josua), pair(Walentina, Salvatore),
      pair(Vanessa, Diogo));

    data.add(false, pair(Steffi, Eugen));
    data.add(1, pair(Jules, Francesco), pair(Aurelia, Danilo), pair(Finnja, Manuel), pair(Jacky, Eugen),
      pair(Jill, Salvatore), pair(Melina, Alex), pair(Sarah, Jamie), pair(Walentina, Josua), pair(Vanessa, Diogo),
      pair(Steffi, Tommy));

    data.add(false, pair(Melina, Tommy));
    data.add(4, pair(Jules, Francesco), pair(Aurelia, Diogo), pair(Finnja, Tommy), pair(Jill, Danilo),
      pair(Kathleen, Manuel), pair(Melina, Eugen), pair(Sarah, Alex), pair(Walentina, Salvatore), pair(Vanessa, Josua),
      pair(Steffi, Jamie));

    data.add(true, pair(Aurelia, Josua));
    data.add(7, pair(Jules, Francesco), pair(Aurelia, Josua), pair(Finnja, Tommy), pair(Jill, Diogo),
      pair(Kathleen, Manuel), pair(Sarah, Danilo), pair(Walentina, Eugen), pair(Vanessa, Alex), pair(Steffi, Jamie),
      pair(Jacky, Salvatore));

    setData(data);

    // coming
    // add(false, pair(Kerstin, Max)); // 26.32
    // add(false, pair(Isabelle, Dustin)); // 0
    // add(false, pair(Zaira, Marius)); // 57.89
    // add(false, pair(Raphaela, Jordi)); // 15.79

  }

  public static void main(String[] args) {
    new AYTO_VIP01().printLightChancesAndResult();
  }

}
