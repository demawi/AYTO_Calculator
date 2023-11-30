package demawi.ayto.de;

import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.modell.Woman;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;
import static demawi.ayto.modell.Mark.CAN_BE_AN_EXTRA_MATCH;

public class AYTO_02
      extends SeasonData {

  private final Woman Sabrina = frau("Sabrina");
  private final Woman Jill = frau("Jill");
  private final Woman Victoria = frau("Victoria");
  private final Woman Christin = frau("Christin");
  private final Woman Leonie = frau("Leonie");
  private final Woman Vanessa = frau("Vanessa");
  private final Woman Melissa = frau("Melissa");
  private final Woman Kathleen = frau("Kathleen");
  private final Woman Mirjam = frau("Mirjam");
  private final Woman Laura = frau("Laura");
  private final Woman VanessaM = frau("VanessaM", CAN_BE_AN_EXTRA_MATCH, true);

  private final Man Marko = mann("Marko");
  private final Man Marvin = mann("Marvin");
  private final Man Dario = mann("Dario");
  private final Man Maximilian = mann("Maximilian");
  private final Man Germain = mann("Germain");
  private final Man Sascha = mann("Sascha");
  private final Man Dominic = mann("Dominic");
  private final Man Aaron = mann("Aaron");
  private final Man Marc = mann("Marc");
  private final Man Marcel = mann("Marcel");

  public AYTO_02() {
    super(Mode.MARKED);

    newDay().matchBox(pair(Jill, Maximilian), false)
          .matchNight( 2, pair(Sabrina, Marko), pair(Jill, Marvin), pair(Victoria, Dario),
          pair(Christin, Maximilian), pair(Leonie, Germain), pair(Vanessa, Sascha), pair(Melissa, Dominic),
          pair(Kathleen, Aaron), pair(Mirjam, Marc), pair(Laura, Marcel));

    newDay().matchBox(pair(Jill, Maximilian), null)
          .matchNight( 2, pair(Leonie, Marcel), pair(Kathleen, Aaron), pair(Jill, Sascha),
          pair(Melissa, Dominic), pair(Vanessa, Marvin), pair(Victoria, Germain), pair(Christin, Maximilian),
          pair(Mirjam, Marc), pair(Sabrina, Marko), pair(Laura, Dario));

    newDay().matchBox(pair(Kathleen, Aaron), false)
          .matchNight( 3, pair(Laura, Marcel), pair(Melissa, Aaron), pair(Sabrina, Sascha),
          pair(Vanessa, Dominic), pair(Kathleen, Marvin), pair(Christin, Germain), pair(Jill, Maximilian),
          pair(Leonie, Marc), pair(Mirjam, Marko), pair(Victoria, Dario));

    newDay().addNew(VanessaM).matchBox(pair(Laura, Marcel), false)
          .matchNight(3, pair(Sabrina, Marko), pair(Jill, Maximilian), pair(Kathleen, Dario), pair(Vanessa, Sascha),
                pair(Victoria, Germain), pair(Mirjam, Marc), pair(Laura, Dominic), pair(Melissa, Aaron),
                pair(Leonie, Marcel), pair(Christin, Marvin));


    newDay().matchBox(pair(Leonie, Marcel), true)
          .matchNight( 3, pair(Leonie, Marcel), pair(Christin, Germain), pair(Laura, Maximilian),
          pair(Kathleen, Marvin), pair(Jill, Sascha), pair(Melissa, Marc), pair(Vanessa, Dominic),
          pair(Victoria, Dario), pair(Mirjam, Aaron), pair(VanessaM, Marko));

    newDay().matchBox(pair(Laura, Dario), null)
          .matchNight( 4, pair(Leonie, Marcel), pair(Melissa, Aaron), pair(Victoria, Germain),
          pair(Christin, Maximilian), pair(Vanessa, Dominic), pair(Mirjam, Marc), pair(Sabrina, Marko),
          pair(Laura, Marvin), pair(Jill, Sascha), pair(VanessaM, Dario));

    newDay().matchBox(pair(Laura, Dario), false)
          .matchNight( 4, pair(Leonie, Marcel), pair(Sabrina, Dario), pair(Kathleen, Marvin),
          pair(Jill, Sascha), pair(Mirjam, Marc), pair(Victoria, Marko), pair(Melissa, Aaron),
          pair(Christin, Maximilian), pair(Laura, Dominic), pair(VanessaM, Germain));

    newDay().matchBox(pair(Sabrina, Marko), false)
          .matchNight( 1, pair(Leonie, Marcel), pair(Kathleen, Marvin), pair(Mirjam, Maximilian),
          pair(Melissa, Dominic), pair(Vanessa, Aaron), pair(VanessaM, Marc), pair(Victoria, Dario),
          pair(Sabrina, Sascha), pair(Jill, Germain), pair(Laura, Marko));

    newDay().matchBox(pair(Melissa, Aaron), true)
          .matchNight( 3, pair(Leonie, Marcel), pair(Melissa, Aaron), pair(Mirjam, Marc),
          pair(Jill, Sascha), pair(Christin, Maximilian), pair(Victoria, Marko), pair(Sabrina, Dominic),
          pair(Vanessa, Marvin), pair(Laura, Germain), pair(Kathleen, Dario));

    newDay().matchBox(pair(Mirjam, Marc), true)
          .matchNight( 10, pair(Leonie, Marcel), pair(Melissa, Aaron), pair(Mirjam, Marc),
          pair(Sabrina, Dario), pair(Vanessa, Dominic), pair(Christin, Germain), pair(Kathleen, Marko),
          pair(Jill, Marvin), pair(Victoria, Maximilian), pair(Laura, Sascha));
  }

  public static void main(String[] args) {
    new DefaultMatchPrinter(new AYTO_02()).printLastDayResults();
  }

}
