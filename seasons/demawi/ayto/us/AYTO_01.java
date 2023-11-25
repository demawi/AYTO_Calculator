package demawi.ayto.us;

import demawi.ayto.modell.Woman;
import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_01
      extends SeasonData {

   private final Woman Amber = frau("Amber");
   private final Woman Ashleigh = frau("Ashleigh");
   private final Woman Brittany = frau("Brittany");
   private final Woman Coleysia = frau("Coleysia");
   private final Woman Jacy = frau("Jacy"); // Jacinda
   private final Woman Jessica = frau("Jessica");
   private final Woman Kayla = frau("Kayla");
   private final Woman Paige = frau("Paige");
   private final Woman Shanley = frau("Shanley");
   private final Woman Simone = frau("Simone");

   private final Man Adam = mann("Adam");
   private final Man Dre = mann("Dre"); // Andrean
   private final Man ChrisS = mann("ChrisS");
   private final Man ChrisT = mann("ChrisT");
   private final Man Dillan = mann("Dillan");
   private final Man Ethan = mann("Ethan");
   private final Man Joey = mann("Joey");
   private final Man John = mann("John");
   private final Man Ryan = mann("Ryan");
   private final Man Wes = mann("Wes"); // Wesley

   public AYTO_01() {
      super(Mode.STANDARD);

      newDay().matchBox(pair(Shanley, ChrisT), false)
            .matchNight(2, pair(Amber, Ryan), pair(Ashleigh, ChrisS), pair(Brittany, Adam), pair(Coleysia, Dillan),
                  pair(Jacy, Dre), pair(Jessica, ChrisT), pair(Kayla, Wes), pair(Paige, Joey), pair(Shanley, Ethan),
                  pair(Simone, John));

      newDay().matchBox(pair(Jessica, Ethan), false)
            .matchNight(4, pair(Amber, Ethan), pair(Ashleigh, Dre), pair(Brittany, Joey), pair(Coleysia, Wes),
                  pair(Jacy, John), pair(Jessica, Dillan), pair(Kayla, Ryan), pair(Paige, ChrisT), pair(Shanley, Adam),
                  pair(Simone, ChrisS));

      newDay().matchBox(pair(Simone, John), false)
            .matchNight(2, pair(Amber, Ethan), pair(Ashleigh, Dre), pair(Brittany, Adam), pair(Coleysia, Dillan),
                  pair(Jacy, Wes), pair(Jessica, John), pair(Kayla, Ryan), pair(Paige, ChrisS), pair(Shanley, Joey),
                  pair(Simone, ChrisT));

      newDay().matchBox(pair(Jessica, Dillan), false)
            .matchNight(2, pair(Amber, Adam), pair(Ashleigh, ChrisT), pair(Brittany, Ryan), pair(Coleysia, Dillan),
                  pair(Jacy, Joey), pair(Jessica, Wes), pair(Kayla, Ethan), pair(Paige, ChrisS), pair(Shanley, John),
                  pair(Simone, Dre));

      newDay().matchBox(pair(Ashleigh, Dre), false)
            .matchBox(pair(Coleysia, Dillan), true)
            .matchNight(5, pair(Amber, Ethan), pair(Ashleigh, Ryan), pair(Brittany, Dre), pair(Coleysia, Dillan),
                  pair(Jacy, John), pair(Jessica, Joey), pair(Kayla, Wes), pair(Paige, ChrisT), pair(Shanley, Adam),
                  pair(Simone, ChrisS));

      newDay().matchBox(pair(Paige, ChrisT), true)
            .matchNight(5, pair(Amber, Ethan), pair(Ashleigh, Adam), pair(Brittany, ChrisS), pair(Coleysia, Dillan),
                  pair(Jacy, John), pair(Jessica, Ryan), pair(Kayla, Wes), pair(Paige, ChrisT), pair(Shanley, Dre),
                  pair(Simone, Joey));

      newDay().matchBox(pair(Kayla, Ryan), false)
            .matchNight(7, pair(Amber, Ethan), pair(Ashleigh, Ryan), pair(Brittany, John), pair(Coleysia, Dillan),
                  pair(Jacy, ChrisS), pair(Jessica, Joey), pair(Kayla, Wes), pair(Paige, ChrisT), pair(Shanley, Adam),
                  pair(Simone, Dre));

      newDay().matchBox(pair(Kayla, Wes), true)
            .matchNight(8, pair(Amber, Ethan), pair(Ashleigh, ChrisS), pair(Brittany, Joey), pair(Coleysia, Dillan),
                  pair(Jacy, John), pair(Jessica, Ryan), pair(Kayla, Wes), pair(Paige, ChrisT), pair(Shanley, Adam),
                  pair(Simone, Dre));

      newDay().matchBox(pair(Jacy, John), false)
            .matchNight(10, pair(Amber, Ethan), pair(Ashleigh, John), pair(Brittany, Joey), pair(Coleysia, Dillan),
                  pair(Jacy, ChrisS), pair(Jessica, Ryan), pair(Kayla, Wes), pair(Paige, ChrisT), pair(Shanley, Adam),
                  pair(Simone, Dre));

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printLastDayResults(new AYTO_01());
   }

}
