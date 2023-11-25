package demawi.ayto.de;

import demawi.ayto.modell.Woman;
import demawi.ayto.modell.Man;
import demawi.ayto.modell.Mark;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.permutation.AYTO_Permutator.MODE;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_05
      extends SeasonData {

   private final Woman Afra = frau("Afra");
   private final Woman Edda = frau("Edda");
   private final Woman Jana = frau("Jana");
   private final Woman Julia = frau("Julia");
   private final Woman Lisa_Marie = frau("Lisa-Marie");
   private final Woman Maja = frau("Maja");
   private final Woman Pia = frau("Pia");
   private final Woman Sina = frau("Sina");
   private final Woman Shelly = frau("Shelly");
   private final Woman Tais = frau("Tais");
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
      super(MODE.MARKED);

      newDay().addNew(Lina)
            .matchBox(pair(Jana, Paolo), false)
            .matchNight(2, pair(Lina, Ryan), pair(Sina, Kevin), pair(Tais, Paolo), pair(Jana, Sidar),
                  pair(Julia, Martin), pair(Pia, Gerrit), pair(Lisa_Marie, Eti), pair(Shelly, Wilson),
                  pair(Maja, Paddy), pair(Edda, Sandro)); // Afra

      //newTag().matchBox(pair(Lina, Ryan), null);

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printLastDayResults(new AYTO_05());
   }

}
