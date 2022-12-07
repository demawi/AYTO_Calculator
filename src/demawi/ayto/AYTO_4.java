package demawi.ayto;

import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;

import static demawi.ayto.modell.Pair.pair;

public class AYTO_4
      extends AYTO_Data {

  private static Frau Aurelia = frau("Aurelia");
  private static Frau Carina = frau("Carina");
  private static Frau Caroline = frau("Caroline");
  private static Frau Dorna = frau("Dorna");
  private static Frau Henna = frau("Henna");
  private static Frau Juliette = frau("Juliette");
  private static Frau Larissa = frau("Larissa");
  private static Frau Stefanie = frau("Stefanie");
  private static Frau Valeria = frau("Valeria");
  private static Frau Vanessa = frau("Vanessa");

  private static Mann Barkin = mann("Barkin");
  private static Mann Burim = mann("Burim");
  private static Mann Christopher = mann("Christopher");
  private static Mann Deniz = mann("Deniz");
  private static Mann Joel = mann("Joel");
  private static Mann Ken = mann("Ken");
  private static Mann Kenneth = mann("Kenneth");
  private static Mann Maximilian = mann("Maximilian");
  private static Mann Pascal = mann("Pascal");
  private static Mann Sasa = mann("Sasa");
  private static Mann Marwin = mann("Marwin");

  public AYTO_4() {
    super("04", AYTO_Permutator.ZUSATZTYPE.UNKNOWN);



    add(false, pair(Valeria, Marwin), 3, null);


  }

  public static void main(String[] args) {
    new MatchFinder().printLightChancesAndResult(new AYTO_4());
  }

}
