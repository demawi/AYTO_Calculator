package demawi.ayto;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.EventbasedMatchPrinter;

import static demawi.ayto.modell.Pair.pair;

public class AYTO_2 extends StaffelData {

  private final Frau Sabrina = frau("Sabrina");
  private final Frau Jill = frau("Jill");
  private final Frau Victoria = frau("Victoria");
  private final Frau Christin = frau("Christin");
  private final Frau Leonie = frau("Leonie");
  private final Frau Vanessa = frau("Vanessa");
  private final Frau Melissa = frau("Melissa");
  private final Frau Kathleen = frau("Kathleen");
  private final Frau Mirjam = frau("Mirjam");
  private final Frau Laura = frau("Laura");
  private final Frau VanessaM = frau("VanessaM");

  private final Mann Marko = mann("Marko");
  private final Mann Marvin = mann("Marvin");
  private final Mann Dario = mann("Dario");
  private final Mann Maximilian = mann("Maximilian");
  private final Mann Germain = mann("Germain");
  private final Mann Sascha = mann("Sascha");
  private final Mann Dominic = mann("Dominic");
  private final Mann Aaron = mann("Aaron");
  private final Mann Marc = mann("Marc");
  private final Mann Marcel = mann("Marcel");

  public AYTO_2() {
    super("02", AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER);

    newTag().matchBox(pair(Jill, Maximilian), false)
          .matchNight( 2, pair(Sabrina, Marko), pair(Jill, Marvin), pair(Victoria, Dario),
          pair(Christin, Maximilian), pair(Leonie, Germain), pair(Vanessa, Sascha), pair(Melissa, Dominic),
          pair(Kathleen, Aaron), pair(Mirjam, Marc), pair(Laura, Marcel));

    newTag().matchBox(pair(Jill, Maximilian), null)
          .matchNight( 2, pair(Leonie, Marcel), pair(Kathleen, Aaron), pair(Jill, Sascha),
          pair(Melissa, Dominic), pair(Vanessa, Marvin), pair(Victoria, Germain), pair(Christin, Maximilian),
          pair(Mirjam, Marc), pair(Sabrina, Marko), pair(Laura, Dario));

    newTag().matchBox(pair(Kathleen, Aaron), false)
          .matchNight( 3, pair(Laura, Marcel), pair(Melissa, Aaron), pair(Sabrina, Sascha),
          pair(Vanessa, Dominic), pair(Kathleen, Marvin), pair(Christin, Germain), pair(Jill, Maximilian),
          pair(Leonie, Marc), pair(Mirjam, Marko), pair(Victoria, Dario));

    newTag().addNew(VanessaM).matchBox(pair(Laura, Marcel), false)
          .matchNight(3, pair(Sabrina, Marko), pair(Jill, Maximilian), pair(Kathleen, Dario), pair(Vanessa, Sascha),
                pair(Victoria, Germain), pair(Mirjam, Marc), pair(Laura, Dominic), pair(Melissa, Aaron),
                pair(Leonie, Marcel), pair(Christin, Marvin));


    newTag().matchBox(pair(Leonie, Marcel), true)
          .matchNight( 3, pair(Leonie, Marcel), pair(Christin, Germain), pair(Laura, Maximilian),
          pair(Kathleen, Marvin), pair(Jill, Sascha), pair(Melissa, Marc), pair(Vanessa, Dominic),
          pair(Victoria, Dario), pair(Mirjam, Aaron), pair(VanessaM, Marko));

    newTag().matchBox(pair(Laura, Dario), null)
          .matchNight( 4, pair(Leonie, Marcel), pair(Melissa, Aaron), pair(Victoria, Germain),
          pair(Christin, Maximilian), pair(Vanessa, Dominic), pair(Mirjam, Marc), pair(Sabrina, Marko),
          pair(Laura, Marvin), pair(Jill, Sascha), pair(VanessaM, Dario));

    newTag().matchBox(pair(Laura, Dario), false)
          .matchNight( 4, pair(Leonie, Marcel), pair(Sabrina, Dario), pair(Kathleen, Marvin),
          pair(Jill, Sascha), pair(Mirjam, Marc), pair(Victoria, Marko), pair(Melissa, Aaron),
          pair(Christin, Maximilian), pair(Laura, Dominic), pair(VanessaM, Germain));

    newTag().matchBox(pair(Sabrina, Marko), false)
          .matchNight( 1, pair(Leonie, Marcel), pair(Kathleen, Marvin), pair(Mirjam, Maximilian),
          pair(Melissa, Dominic), pair(Vanessa, Aaron), pair(VanessaM, Marc), pair(Victoria, Dario),
          pair(Sabrina, Sascha), pair(Jill, Germain), pair(Laura, Marko));

    newTag().matchBox(pair(Melissa, Aaron), true)
          .matchNight( 3, pair(Leonie, Marcel), pair(Melissa, Aaron), pair(Mirjam, Marc),
          pair(Jill, Sascha), pair(Christin, Maximilian), pair(Victoria, Marko), pair(Sabrina, Dominic),
          pair(Vanessa, Marvin), pair(Laura, Germain), pair(Kathleen, Dario));

    newTag().matchBox(pair(Mirjam, Marc), true)
          .matchNight( 10, pair(Leonie, Marcel), pair(Melissa, Aaron), pair(Mirjam, Marc),
          pair(Sabrina, Dario), pair(Vanessa, Dominic), pair(Christin, Germain), pair(Kathleen, Marko),
          pair(Jill, Marvin), pair(Victoria, Maximilian), pair(Laura, Sascha));
  }

  public static void main(String[] args) {
    new EventbasedMatchPrinter().printDayResults(new AYTO_2());
  }

}
