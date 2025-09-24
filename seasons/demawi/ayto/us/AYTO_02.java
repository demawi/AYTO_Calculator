package demawi.ayto.us;

import demawi.ayto.modell.Man;
import demawi.ayto.modell.PermutationConfiguration;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.modell.Woman;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.permutation.Mark;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_02
      extends SeasonData {

   private final Woman Alexandria = frau("Alexandria");
   private final Woman Ashley = frau("Ashley");
   private final Woman Briana = frau("Briana");
   private final Woman Ellie = frau("Ellie");
   private final Woman Jasmine = frau("Jasmine");
   private final Woman Jenni = frau("Jenni");
   private final Woman Jessica = frau("Jessica");
   private final Woman Paris = frau("Paris");
   private final Woman Shelby = frau("Shelby");
   private final Woman Tyler = frau("Tyler");
   private final Woman Christina = frau("Christina", Mark.CAN_BE_AN_EXTRA_MATCH, true);

   private final Man Alex = mann("Alex");
   private final Man Anthony = mann("Anthony");
   private final Man Brandon = mann("Brandon");
   private final Man Curtis = mann("Curtis");
   private final Man Dario = mann("Dario");
   private final Man Garland = mann("Garland");
   private final Man John = mann("John");
   private final Man Layton = mann("Layton");
   private final Man Nathan = mann("Nathan");
   private final Man Pratt = mann("Pratt"); // Tyler Pratt

   public AYTO_02() {
      super(new PermutationConfiguration(Mode.MARKED));

      newDay().addNew(Christina)
            .matchBox(pair(Jessica, Brandon), false)
            .matchNight(2, pair(Alexandria, Garland), pair(Ashley, Dario), pair(Briana, Curtis),
                  pair(Christina, Brandon), pair(Ellie, Alex), pair(Jasmine, John), pair(Jenni, Layton),
                  pair(Jessica, Anthony), pair(Paris, Pratt), pair(Shelby, Nathan)); // Tyler

      newDay().matchBox(pair(Christina, Brandon), false)
            .matchNight(2, pair(Alexandria, Anthony), pair(Ashley, John), pair(Briana, Curtis), pair(Christina, Alex),
                  pair(Jasmine, Garland), pair(Jenni, Nathan), pair(Jessica, Layton), pair(Paris, Pratt),
                  pair(Shelby, Dario), pair(Tyler, Brandon)); // Ellie

      newDay().matchBox(pair(Alexandria, Brandon), false)
            .matchNight(3, pair(Alexandria, Garland), pair(Ashley, Dario), pair(Briana, Curtis), pair(Christina, John),
                  pair(Ellie, Layton), pair(Jasmine, Alex), pair(Jenni, Anthony), pair(Jessica, Nathan),
                  pair(Paris, Pratt), pair(Shelby, Brandon)); // Tyler

      newDay().matchBox(pair(Paris, Pratt), true)
            .matchNight(1, pair(Alexandria, Nathan), pair(Briana, Curtis), pair(Christina, John), pair(Ellie, Brandon),
                  pair(Jasmine, Garland), pair(Jenni, Anthony), pair(Jessica, Layton), pair(Paris, Pratt),
                  pair(Shelby, Dario), pair(Tyler, Alex)); // Ashley

      newDay().matchBox(pair(Shelby, Curtis), true)
            .matchNight(3, pair(Alexandria, Dario), pair(Ashley, Anthony), pair(Briana, Layton), pair(Christina, Alex),
                  pair(Jasmine, Brandon), pair(Jenni, John), pair(Jessica, Nathan), pair(Paris, Pratt),
                  pair(Shelby, Curtis), pair(Tyler, Garland)); // Ellie

      newDay().matchBox(pair(Jasmine, John), false)
            .matchNight(2, pair(Alexandria, John), pair(Ashley, Brandon), pair(Briana, Dario), pair(Ellie, Anthony),
                  pair(Jasmine, Nathan), pair(Jenni, Layton), pair(Jessica, Alex), pair(Paris, Pratt),
                  pair(Shelby, Curtis), pair(Tyler, Garland)); // Ellie

      newDay().matchBox(pair(Jenni, John), true)
            .matchNight(6, pair(Ashley, Dario), pair(Briana, Anthony), pair(Christina, Nathan), pair(Ellie, Layton),
                  pair(Jasmine, Alex), pair(Jenni, John), pair(Jessica, Garland), pair(Paris, Pratt),
                  pair(Shelby, Curtis), pair(Tyler, Brandon)); // Alexandria

      newDay().matchBox(pair(Christina, Nathan), false)
            .matchNight(5, pair(Alexandria, Garland), pair(Ashley, Dario), pair(Briana, Nathan), pair(Ellie, Layton),
                  pair(Jasmine, Alex), pair(Jenni, John), pair(Jessica, Anthony), pair(Paris, Pratt),
                  pair(Shelby, Curtis), pair(Tyler, Brandon)); // Christina

      newDay().matchBox(pair(Jasmine, Alex), true)
            .matchNight(8, pair(Alexandria, Anthony), pair(Ashley, Layton), pair(Briana, Brandon), pair(Ellie, Nathan),
                  pair(Jasmine, Alex), pair(Jenni, John), pair(Jessica, Garland), pair(Paris, Pratt),
                  pair(Shelby, Curtis), pair(Tyler, Dario)); // Christina

      newDay().matchBox(pair(Ashley, Dario), true)
            .matchNight(10, pair(Alexandria, Anthony), pair(Ashley, Dario), pair(Briana, Brandon),
                  pair(Christina, Layton), pair(Ellie, Nathan), pair(Jasmine, Alex), pair(Jenni, John),
                  pair(Jessica, Garland), pair(Paris, Pratt), pair(Shelby, Curtis)); // Tyler or Christina

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_02()).printLastDayResults();
   }

}
