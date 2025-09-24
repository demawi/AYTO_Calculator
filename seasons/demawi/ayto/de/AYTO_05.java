package demawi.ayto.de;

import demawi.ayto.modell.Man;
import demawi.ayto.modell.PermutationConfiguration;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.modell.Woman;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;
import static demawi.ayto.permutation.Mark.CAN_BE_AN_EXTRA_MATCH;
import static demawi.ayto.permutation.Mark.IS_A_THIRD_MATCH;

public class AYTO_05
      extends SeasonData {

   private final Woman Afra = frau("Afra").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Edda = frau("Edda").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Jana = frau("Jana").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Julia = frau("Julia").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Lisa_Marie = frau("Lisa-Marie").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Maja = frau("Maja").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Pia = frau("Pia").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Sina = frau("Sina").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Shelly = frau("Shelly").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Tais = frau("Tais").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Lina = frau("Lina", true).mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Melanie = frau("Melanie", true).mark(CAN_BE_AN_EXTRA_MATCH).mark(IS_A_THIRD_MATCH);

   private final Man Eti = mann("Eti");
   private final Man Gerrit = mann("Gerrit");
   private final Man Kevin = mann("Kevin");
   private final Man Martin = mann("Martin");
   private final Man Paddy = mann("Paddy");
   private final Man Paolo = mann("Paolo");
   private final Man Ryan = mann("Ryan");
   private final Man Sandro = mann("Sandro");
   private final Man Sidar = mann("Sidar");
   private final Man Wilson = mann("Wilson");

   public AYTO_05() {
      super(new PermutationConfiguration(Mode.MARKED));

      newDay().addNew(Lina)
            .matchBox(pair(Jana, Paolo), false)
            .matchNight(2, pair(Lina, Ryan), pair(Sina, Kevin), pair(Tais, Paolo), pair(Jana, Sidar),
                  pair(Julia, Martin), pair(Pia, Gerrit), pair(Lisa_Marie, Eti), pair(Shelly, Wilson),
                  pair(Maja, Paddy), pair(Edda, Sandro)); // Afra

      newDay().matchBox(pair(Lina, Ryan), false)
            .matchNight(2, pair(Martin, Lina), pair(Pia, Ryan), pair(Edda, Sandro), pair(Jana, Sidar),
                  pair(Shelly, Paddy), pair(Sina, Kevin), pair(Tais, Gerrit), pair(Lisa_Marie, Paolo), pair(Afra, Eti),
                  pair(Maja, Wilson)); // Julia

      newDay().matchBox(pair(Maja, Kevin), false)
            .addNew(Melanie, true)
            .matchBox(pair(Melanie, Eti), false)
            .matchNight(4, pair(Melanie, Kevin), pair(Edda, Paolo), pair(Maja, Eti), pair(Jana, Ryan),
                  pair(Julia, Paddy), pair(Shelly, Wilson), pair(Tais, Gerrit), pair(Sina, Sandro), pair(Afra, Sidar),
                  pair(Lisa_Marie, Martin)); // Pia, Lina

      newDay().matchBox(pair(Shelly, Wilson), true)
            .matchNight(3, pair(Shelly, Wilson), pair(Tais, Gerrit), pair(Lina, Paddy), pair(Afra, Eti),
                  pair(Lisa_Marie, Paolo), pair(Edda, Ryan), pair(Sina, Kevin), pair(Julia, Martin), pair(Pia, Sandro),
                  pair(Jana, Sidar)); // Maja, Melanie

      newDay().matchBox(pair(Tais, Gerrit), true, pair(Melanie, Gerrit), pair(Pia, Gerrit))
            .matchNight(4, pair(Shelly, Wilson), pair(Tais, Gerrit), pair(Lisa_Marie, Martin), pair(Sina, Sandro),
                  pair(Jana, Ryan), pair(Julia, Kevin), pair(Maja, Eti), pair(Afra, Sidar), pair(Lina, Paddy),
                  pair(Edda, Paolo)); // Melanie, Pia

      newDay().matchBox(pair(Sina, Sandro), false)
            .matchNight(5, pair(Shelly, Wilson), pair(Tais, Gerrit), pair(Lisa_Marie, Martin), pair(Sina, Paolo),
                  pair(Lina, Kevin), pair(Maja, Eti), pair(Afra, Sandro), pair(Julia, Sidar), pair(Jana, Ryan),
                  pair(Edda, Paddy)); // Melanie, Pia

      newDay().matchBox(pair(Julia, Paolo), false)
            .matchNight(5, pair(Shelly, Wilson), pair(Tais, Gerrit), pair(Maja, Eti), pair(Lina, Kevin),
                  pair(Afra, Martin), pair(Julia, Sandro), pair(Jana, Paddy), pair(Lisa_Marie, Sidar),
                  pair(Sina, Paolo), pair(Edda, Ryan)); // Melanie, Pia

      newDay().matchBox(pair(Maja, Eti), null)
            .matchNight(4, pair(Shelly, Wilson), pair(Tais, Gerrit), pair(Afra, Eti), pair(Lina, Kevin),
                  pair(Edda, Ryan), pair(Jana, Paddy), pair(Sina, Paolo), pair(Lisa_Marie, Martin), pair(Julia, Sandro),
                  pair(Maja, Sidar));

      newDay().matchBox(pair(Afra, Sandro), false)
            .matchNight(6, pair(Shelly, Wilson), pair(Tais, Gerrit), pair(Sina, Paddy), pair(Julia, Sandro),
                  pair(Edda, Paolo), pair(Jana, Ryan), pair(Maja, Eti), pair(Lina, Kevin), pair(Lisa_Marie, Martin),
                  pair(Afra, Sidar));

      newDay().matchBox(pair(Lina, Kevin), true)
            .matchNight(7, pair(Shelly, Wilson), pair(Tais, Gerrit), pair(Lina, Kevin), pair(Maja, Eti),
                  pair(Julia, Sandro), pair(Paddy, Edda), pair(Lisa_Marie, Paolo), pair(Afra, Martin),
                  pair(Sina, Sidar), pair(Jana, Ryan));

      // Bekanntgabe während der 10ten MatchingNight:
      newDay().matchBox(pair(Jana, Ryan), true);

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_05()).printLastDayResults();
   }

}
