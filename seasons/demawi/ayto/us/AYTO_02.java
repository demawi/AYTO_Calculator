package demawi.ayto.us;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.Markierung;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_02
      extends StaffelData {

   private final Frau Alexandria = frau("Alexandria");
   private final Frau Ashley = frau("Ashley");
   private final Frau Briana = frau("Briana");
   private final Frau Ellie = frau("Ellie");
   private final Frau Jasmine = frau("Jasmine");
   private final Frau Jenni = frau("Jenni");
   private final Frau Jessica = frau("Jessica");
   private final Frau Paris = frau("Paris");
   private final Frau Shelby = frau("Shelby");
   private final Frau Tyler = frau("Tyler");
   private final Frau Christina = frau("Christina", Markierung.CAN_BE_AN_EXTRA, true);

   private final Mann Alex = mann("Alex");
   private final Mann Anthony = mann("Anthony");
   private final Mann Brandon = mann("Brandon");
   private final Mann Curtis = mann("Curtis");
   private final Mann Dario = mann("Dario");
   private final Mann Garland = mann("Garland");
   private final Mann John = mann("John");
   private final Mann Layton = mann("Layton");
   private final Mann Nathan = mann("Nathan");
   private final Mann Pratt = mann("Pratt"); // Tyler Pratt

   public AYTO_02() {
      super(AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER);

      newTag().addNew(Christina)
            .matchBox(pair(Jessica, Brandon), false)
            .matchNight(2, pair(Alexandria, Garland), pair(Ashley, Dario), pair(Briana, Curtis),
                  pair(Christina, Brandon), pair(Ellie, Alex), pair(Jasmine, John), pair(Jenni, Layton),
                  pair(Jessica, Anthony), pair(Paris, Pratt), pair(Shelby, Nathan)); // Tyler

      newTag().matchBox(pair(Christina, Brandon), false)
            .matchNight(2, pair(Alexandria, Anthony), pair(Ashley, John), pair(Briana, Curtis), pair(Christina, Alex),
                  pair(Jasmine, Garland), pair(Jenni, Nathan), pair(Jessica, Layton), pair(Paris, Pratt),
                  pair(Shelby, Dario), pair(Tyler, Brandon)); // Ellie

      newTag().matchBox(pair(Alexandria, Brandon), false)
            .matchNight(3, pair(Alexandria, Garland), pair(Ashley, Dario), pair(Briana, Curtis), pair(Christina, John),
                  pair(Ellie, Layton), pair(Jasmine, Alex), pair(Jenni, Anthony), pair(Jessica, Nathan),
                  pair(Paris, Pratt), pair(Shelby, Brandon)); // Tyler

      newTag().matchBox(pair(Paris, Pratt), true)
            .matchNight(1, pair(Alexandria, Nathan), pair(Briana, Curtis), pair(Christina, John), pair(Ellie, Brandon),
                  pair(Jasmine, Garland), pair(Jenni, Anthony), pair(Jessica, Layton), pair(Paris, Pratt),
                  pair(Shelby, Dario), pair(Tyler, Alex)); // Ashley

      newTag().matchBox(pair(Shelby, Curtis), true)
            .matchNight(3, pair(Alexandria, Dario), pair(Ashley, Anthony), pair(Briana, Layton), pair(Christina, Alex),
                  pair(Jasmine, Brandon), pair(Jenni, John), pair(Jessica, Nathan), pair(Paris, Pratt),
                  pair(Shelby, Curtis), pair(Tyler, Garland)); // Ellie

      newTag().matchBox(pair(Jasmine, John), false)
            .matchNight(2, pair(Alexandria, John), pair(Ashley, Brandon), pair(Briana, Dario), pair(Ellie, Anthony),
                  pair(Jasmine, Nathan), pair(Jenni, Layton), pair(Jessica, Alex), pair(Paris, Pratt),
                  pair(Shelby, Curtis), pair(Tyler, Garland)); // Ellie

      newTag().matchBox(pair(Jenni, John), true)
            .matchNight(6, pair(Ashley, Dario), pair(Briana, Anthony), pair(Christina, Nathan), pair(Ellie, Layton),
                  pair(Jasmine, Alex), pair(Jenni, John), pair(Jessica, Garland), pair(Paris, Pratt),
                  pair(Shelby, Curtis), pair(Tyler, Brandon)); // Alexandria

      newTag().matchBox(pair(Christina, Nathan), false)
            .matchNight(5, pair(Alexandria, Garland), pair(Ashley, Dario), pair(Briana, Nathan), pair(Ellie, Layton),
                  pair(Jasmine, Alex), pair(Jenni, John), pair(Jessica, Anthony), pair(Paris, Pratt),
                  pair(Shelby, Curtis), pair(Tyler, Brandon)); // Christina

      newTag().matchBox(pair(Jasmine, Alex), true)
            .matchNight(8, pair(Alexandria, Anthony), pair(Ashley, Layton), pair(Briana, Brandon), pair(Ellie, Nathan),
                  pair(Jasmine, Alex), pair(Jenni, John), pair(Jessica, Garland), pair(Paris, Pratt),
                  pair(Shelby, Curtis), pair(Tyler, Dario)); // Christina

      newTag().matchBox(pair(Ashley, Dario), true)
            .matchNight(10, pair(Alexandria, Anthony), pair(Ashley, Dario), pair(Briana, Brandon),
                  pair(Christina, Layton), pair(Ellie, Nathan), pair(Jasmine, Alex), pair(Jenni, John),
                  pair(Jessica, Garland), pair(Paris, Pratt), pair(Shelby, Curtis)); // Tyler or Christina

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printDayResults(new AYTO_02());
   }

}
