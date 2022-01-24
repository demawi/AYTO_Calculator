package demawi.ayto;

import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;

import static demawi.ayto.modell.Pair.pair;

public class AYTO_2 extends AYTO_Data {

  private Frau Sabrina = new Frau("Sabrina");
  private Frau Jill = new Frau("Jill");
  private Frau Victoria = new Frau("Victoria");
  private Frau Christin = new Frau("Christin");
  private Frau Leonie = new Frau("Leonie");
  private Frau Vanessa = new Frau("Vanessa");
  private Frau Melissa = new Frau("Melissa");
  private Frau Kathleen = new Frau("Kathleen");
  private Frau Mirjam = new Frau("Mirjam");
  private Frau Laura = new Frau("Laura");
  private Frau VanessaM = new Frau("VanessaM");

  private Mann Marko = new Mann("Marko");
  private Mann Marvin = new Mann("Marvin");
  private Mann Dario = new Mann("Dario");
  private Mann Maximilian = new Mann("Maximilian");
  private Mann Germain = new Mann("Germain");
  private Mann Sascha = new Mann("Sascha");
  private Mann Dominic = new Mann("Dominic");
  private Mann Aaron = new Mann("Aaron");
  private Mann Marc = new Mann("Marc");
  private Mann Marcel = new Mann("Marcel");

  public AYTO_2() {
    super("02");

    add(false, pair(Jill, Maximilian), 2, pair(Sabrina, Marko), pair(Jill, Marvin), pair(Victoria, Dario),
          pair(Christin, Maximilian), pair(Leonie, Germain), pair(Vanessa, Sascha), pair(Melissa, Dominic),
          pair(Kathleen, Aaron), pair(Mirjam, Marc), pair(Laura, Marcel));

    add(null, pair(Jill, Maximilian), 2, pair(Leonie, Marcel), pair(Kathleen, Aaron), pair(Jill, Sascha),
          pair(Melissa, Dominic), pair(Vanessa, Marvin), pair(Victoria, Germain), pair(Christin, Maximilian),
          pair(Mirjam, Marc), pair(Sabrina, Marko), pair(Laura, Dario));

    add(false, pair(Kathleen, Aaron), 3, pair(Laura, Marcel), pair(Melissa, Aaron), pair(Sabrina, Sascha),
          pair(Vanessa, Dominic), pair(Kathleen, Marvin), pair(Christin, Germain), pair(Jill, Maximilian),
          pair(Leonie, Marc), pair(Mirjam, Marko), pair(Victoria, Dario));

    add(false, pair(Laura, Marcel), 3, pair(Sabrina, Marko), pair(Jill, Maximilian), pair(Kathleen, Dario),
          pair(Vanessa, Sascha), pair(Victoria, Germain), pair(Mirjam, Marc), pair(Laura, Dominic),
          pair(Melissa, Aaron), pair(Leonie, Marcel), pair(Christin, Marvin));

    add(true, pair(Leonie, Marcel), 3, pair(Leonie, Marcel), pair(Christin, Germain), pair(Laura, Maximilian),
          pair(Kathleen, Marvin), pair(Jill, Sascha), pair(Melissa, Marc), pair(Vanessa, Dominic),
          pair(Victoria, Dario), pair(Mirjam, Aaron), pair(VanessaM, Marko));

    add(null, pair(Laura, Dario), 4, pair(Leonie, Marcel), pair(Melissa, Aaron), pair(Victoria, Germain),
          pair(Christin, Maximilian), pair(Vanessa, Dominic), pair(Mirjam, Marc), pair(Sabrina, Marko),
          pair(Laura, Marvin), pair(Jill, Sascha), pair(VanessaM, Dario));

    add(false, pair(Laura, Dario), 4, pair(Leonie, Marcel), pair(Sabrina, Dario), pair(Kathleen, Marvin),
          pair(Jill, Sascha), pair(Mirjam, Marc), pair(Victoria, Marko), pair(Melissa, Aaron),
          pair(Christin, Maximilian), pair(Laura, Dominic), pair(VanessaM, Germain));

    add(false, pair(Sabrina, Marko), 1, pair(Leonie, Marcel), pair(Kathleen, Marvin), pair(Mirjam, Maximilian),
          pair(Melissa, Dominic), pair(Vanessa, Aaron), pair(VanessaM, Marc), pair(Victoria, Dario),
          pair(Sabrina, Sascha), pair(Jill, Germain), pair(Laura, Marko));

    add(true, pair(Melissa, Aaron), 3, pair(Leonie, Marcel), pair(Melissa, Aaron), pair(Mirjam, Marc),
          pair(Jill, Sascha), pair(Christin, Maximilian), pair(Victoria, Marko), pair(Sabrina, Dominic),
          pair(Vanessa, Marvin), pair(Laura, Germain), pair(Kathleen, Dario));
  }

  public static void main(String[] args) {
    new MatchFinder().printLightChancesAndResult(new AYTO_2());
  }

}
