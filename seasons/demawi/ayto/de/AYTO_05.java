package demawi.ayto.de;

import demawi.ayto.modell.Man;
import demawi.ayto.modell.Mark;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.modell.Woman;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_05
      extends SeasonData {

   private final Woman Afra = frau("Afra", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Edda = frau("Edda", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Jana = frau("Jana", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Julia = frau("Julia", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Lisa_Marie = frau("Lisa-Marie", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Maja = frau("Maja", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Pia = frau("Pia", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Sina = frau("Sina", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Shelly = frau("Shelly", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Tais = frau("Tais", Mark.CAN_BE_AN_EXTRA_MATCH);
   private final Woman Lina = frau("Lina", Mark.CAN_BE_AN_EXTRA_MATCH, true);
   private final Woman Melanie = frau("Melanie", Mark.CAN_BE_AN_EXTRA_MATCH, true);

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
      super(Mode.MARKED);

      newDay().addNew(Lina)
            .matchBox(pair(Jana, Paolo), false)
            .matchNight(2, pair(Lina, Ryan), pair(Sina, Kevin), pair(Tais, Paolo), pair(Jana, Sidar),
                  pair(Julia, Martin), pair(Pia, Gerrit), pair(Lisa_Marie, Eti), pair(Shelly, Wilson),
                  pair(Maja, Paddy), pair(Edda, Sandro)); // Afra

      newDay().matchBox(pair(Lina, Ryan), false)
            .matchNight(2, pair(Martin, Lina), pair(Pia, Ryan), pair(Edda, Sandro), pair(Jana, Sidar),
                  pair(Shelly, Paddy), pair(Sina, Kevin), pair(Tais, Gerrit), pair(Lisa_Marie, Paolo), pair(Afra, Eti),
                  pair(Maja, Wilson)); // Julia

      // TODO: Noch nicht umgesetzte Aussage: Ein Mann hat drei Matches. Eine davon ist Melanie. (=> der Mann der mit Melanie liiert ist, hat auch 2 andere Matches)
      newDay().matchBox(pair(Maja, Kevin), false)
            .addNew(Melanie, true)
            .matchBox(pair(Melanie, Eti), false)
            .matchNight(4, pair(Melanie, Kevin), pair(Edda, Paolo), pair(Maja, Eti), pair(Jana, Ryan),
                  pair(Julia, Paddy), pair(Shelly, Wilson), pair(Tais, Gerrit), pair(Sina, Sandro), pair(Afra, Sidar),
                  pair(Lisa_Marie, Martin)); // Pia, Lina

      // newDay().matchBox(pair(Shelly, Wilson), false);

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_05()).printLastDayResults();
   }

}
