package demawi.ayto;

import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;

import static demawi.ayto.modell.Pair.pair;

public class AYTO_VIP01
      extends AYTO_Data {
  
  private static Frau Steffi = frau("Steffi");
  private static Frau Jill = frau("Jill");
  private static Frau Walentina = frau("Walentina");
  private static Frau Kathleen = frau("Kathleen");
  private static Frau Finnja = frau("Finnja");
  private static Frau Aurelia = frau("Aurelia");
  private static Frau Jacky = frau("Jacky");
  private static Frau Jules = frau("Jules");
  private static Frau Melina = frau("Melina");
  private static Frau Sarah = frau("Sarah");
  private static Frau Vanessa = frau("Vanessa");

  private static Mann Danilo = mann("Danilo");
  private static Mann Jamie = mann("Jamie");
  private static Mann Eugen = mann("Eugen");
  private static Mann Manuel = mann("Manuel");
  private static Mann Francesco = mann("Francesco");
  private static Mann Diogo = mann("Diogo");
  private static Mann Salvatore = mann("Salvatore");
  private static Mann Alex = mann("Alex");
  private static Mann Tommy = mann("Tommy");
  private static Mann Josua = mann("Josua");

  public AYTO_VIP01() {
    super("VIP_01", AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER);

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
          pair(Walentina, Salvatore), pair(Vanessa, Jamie)).implicit(false, pair(Vanessa, Francesco));

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
    new MatchFinder().printDayResults(new AYTO_VIP01());
  }

}
