package demawi.ayto;

import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;

import static demawi.ayto.modell.Pair.pair;

public class AYTO_VIP01
      extends AYTO_Data {

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

  public AYTO_VIP01() {
    super("VIP_01");

    add(false, pair(Finnja, Danilo), 3, pair(Steffi, Danilo), pair(Jill, Jamie), pair(Walentina, Eugen),
          pair(Kathleen, Manuel), pair(Finnja, Francesco), pair(Aurelia, Diogo), pair(Jacky, Salvatore),
          pair(Jules, Alex), pair(Melina, Tommy), pair(Sarah, Josua));

    // Keine Matching Night
    add(true, pair(Jules, Francesco), 0, null);

    add(false, pair(Walentina, Tommy), 3, pair(Jules, Francesco), pair(Steffi, Eugen), pair(Jill, Jamie),
          pair(Walentina, Josua), pair(Kathleen, Manuel), pair(Finnja, Alex), pair(Aurelia, Diogo),
          pair(Jacky, Salvatore), pair(Sarah, Danilo), pair(Melina, Tommy));

    add(null, pair(Aurelia, Diogo), 4, pair(Jules, Francesco), pair(Aurelia, Diogo), pair(Finnja, Eugen),
          pair(Jill, Tommy), pair(Kathleen, Manuel), pair(Melina, Danilo), pair(Sarah, Josua), pair(Steffi, Alex),
          pair(Walentina, Salvatore), pair(Vanessa, Jamie)).addImplicit(false, pair(Vanessa, Francesco));

    add(false, pair(Melina, Tommy), 3, pair(Jules, Francesco), pair(Aurelia, Danilo), pair(Finnja, Eugen),
          pair(Jill, Manuel), pair(Kathleen, Salvatore), pair(Melina, Tommy), pair(Sarah, Alex), pair(Steffi, Jamie),
          pair(Walentina, Josua), pair(Vanessa, Diogo));

    add(false, pair(Finnja, Salvatore), 3, pair(Jules, Francesco), pair(Finnja, Eugen), pair(Jacky, Salvatore),
          pair(Kathleen, Manuel), pair(Steffi, Alex), pair(Jill, Danilo), pair(Sarah, Jamie), pair(Walentina, Josua),
          pair(Melina, Tommy), pair(Aurelia, Diogo));

    add(false, pair(Finnja, Eugen), 2, pair(Jules, Francesco), pair(Aurelia, Danilo), pair(Finnja, Jamie),
          pair(Jacky, Alex), pair(Jill, Eugen), pair(Kathleen, Manuel), pair(Melina, Tommy), pair(Sarah, Josua),
          pair(Walentina, Salvatore), pair(Vanessa, Diogo));

    add(false, pair(Steffi, Eugen), 1, pair(Jules, Francesco), pair(Aurelia, Danilo), pair(Finnja, Manuel),
          pair(Jacky, Eugen), pair(Jill, Salvatore), pair(Melina, Alex), pair(Sarah, Jamie), pair(Walentina, Josua),
          pair(Vanessa, Diogo), pair(Steffi, Tommy));

    add(false, pair(Melina, Tommy), 4, pair(Jules, Francesco), pair(Aurelia, Diogo), pair(Finnja, Tommy),
          pair(Jill, Danilo), pair(Kathleen, Manuel), pair(Melina, Eugen), pair(Sarah, Alex),
          pair(Walentina, Salvatore), pair(Vanessa, Josua), pair(Steffi, Jamie));

    add(true, pair(Aurelia, Josua), 7, pair(Jules, Francesco), pair(Aurelia, Josua), pair(Finnja, Tommy),
          pair(Jill, Diogo), pair(Kathleen, Manuel), pair(Sarah, Danilo), pair(Walentina, Eugen), pair(Vanessa, Alex),
          pair(Steffi, Jamie), pair(Jacky, Salvatore));
    // falsch: Finnja&Tommy, Jill&Diogo, Sarah&Danilo... Richtig: Finnja&Diogo, Jill&Tommy, Sara&Alex, Melina&Danilo

    // coming
    // add(false, pair(Kerstin, Max)); // 26.32
    // add(false, pair(Isabelle, Dustin)); // 0
    // add(false, pair(Zaira, Marius)); // 57.89
    // add(false, pair(Raphaela, Jordi)); // 15.79

  }

  public static void main(String[] args) {
    new MatchFinder().printLightChancesAndResult(new AYTO_VIP01());
  }

}
