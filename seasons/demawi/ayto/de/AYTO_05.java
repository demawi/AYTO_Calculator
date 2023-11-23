package demawi.ayto.de;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.Markierung;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_05
      extends StaffelData {

   private final Frau Afra = frau("Afra");
   private final Frau Edda = frau("Edda");
   private final Frau Jana = frau("Jana");
   private final Frau Julia = frau("Julia");
   private final Frau Lisa_Marie = frau("Lisa-Marie");
   private final Frau Maja = frau("Maja");
   private final Frau Pia = frau("Pia");
   private final Frau Sina = frau("Sina");
   private final Frau Shelly = frau("Shelly");
   private final Frau Tais = frau("Tais");
   private final Frau Lina = frau("Lina", Markierung.CAN_BE_AN_EXTRA);

   private final Frau Melanie = frau("Melanie", Markierung.CAN_BE_AN_EXTRA, true);

   private final Mann Eti = mann("Eti");
   private final Mann Gerrit = mann("Gerrit");
   private final Mann Kevin = mann("Kevin");
   private final Mann Martin = mann("Martin");
   private final Mann Paddy = mann("Paddy");
   private final Mann Paul = mann("Paul");
   private final Mann Ryan = mann("Ryan");
   private final Mann Sandro = mann("Sandro");
   private final Mann Sidar = mann("Sidar");
   private final Mann Wilson = mann("Wilson");

   public AYTO_05() {
      super(AYTO_Permutator.ZUSATZTYPE.MARKED);

      newTag().matchBox(pair(Jana, Paul), null);

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printDayResults(new AYTO_05());
   }

}
