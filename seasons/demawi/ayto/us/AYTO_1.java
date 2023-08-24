package demawi.ayto.us;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_1
      extends StaffelData {

   private final Frau Amber = frau("Amber");
   private final Frau Ashleigh = frau("Ashleigh");
   private final Frau Brittany = frau("Brittany");
   private final Frau Coleysia = frau("Coleysia");
   private final Frau Jacy = frau("Jacy"); // Jacinda
   private final Frau Jessica = frau("Jessica");
   private final Frau Kayla = frau("Kayla");
   private final Frau Paige = frau("Paige");
   private final Frau Shanley = frau("Shanley");
   private final Frau Simone = frau("Simone");

   private final Mann Adam = mann("Adam");
   private final Mann Dre = mann("Dre"); // Andrean
   private final Mann ChrisS = mann("ChrisS");
   private final Mann ChrisT = mann("ChrisT");
   private final Mann Dillan = mann("Dillan");
   private final Mann Ethan = mann("Ethan");
   private final Mann Joey = mann("Joey");
   private final Mann John = mann("John");
   private final Mann Ryan = mann("Ryan");
   private final Mann Wes = mann("Wes"); // Wesley

   public AYTO_1() {
      super("01", AYTO_Permutator.ZUSATZTYPE.KEINER);

      newTag().matchBox(pair(Shanley, ChrisT), false)
            .matchNight(2, pair(Amber, Ryan), pair(Ashleigh, ChrisS), pair(Brittany, Adam), pair(Coleysia, Dillan),
                  pair(Jacy, Dre), pair(Jessica, ChrisT), pair(Kayla, Wes), pair(Paige, Joey), pair(Shanley, Ethan),
                  pair(Simone, John));

      newTag().matchBox(pair(Jessica, Ethan), false)
            .matchNight(4, pair(Amber, Ethan), pair(Ashleigh, Dre), pair(Brittany, Joey), pair(Coleysia, Wes),
                  pair(Jacy, John), pair(Jessica, Dillan), pair(Kayla, Ryan), pair(Paige, ChrisT), pair(Shanley, Adam),
                  pair(Simone, ChrisS));

      newTag().matchBox(pair(Simone, John), false)
            .matchNight(2, pair(Amber, Ethan), pair(Ashleigh, Dre), pair(Brittany, Adam), pair(Coleysia, Dillan),
                  pair(Jacy, Wes), pair(Jessica, John), pair(Kayla, Ryan), pair(Paige, ChrisS), pair(Shanley, Joey),
                  pair(Simone, ChrisT));

      newTag().matchBox(pair(Jessica, Dillan), false)
            .matchNight(2, pair(Amber, Adam), pair(Ashleigh, ChrisT), pair(Brittany, Ryan), pair(Coleysia, Dillan),
                  pair(Jacy, Joey), pair(Jessica, Wes), pair(Kayla, Ethan), pair(Paige, ChrisS), pair(Shanley, John),
                  pair(Simone, Dre));

      newTag().matchBox(pair(Ashleigh, Dre), false)
            .matchBox(pair(Coleysia, Dillan), true)
            .matchNight(5, pair(Amber, Ethan), pair(Ashleigh, Ryan), pair(Brittany, Dre), pair(Coleysia, Dillan),
                  pair(Jacy, John), pair(Jessica, Joey), pair(Kayla, Wes), pair(Paige, ChrisT), pair(Shanley, Adam),
                  pair(Simone, ChrisS));

      newTag().matchBox(pair(Paige, ChrisT), true)
            .matchNight(5, pair(Amber, Ethan), pair(Ashleigh, Adam), pair(Brittany, ChrisS), pair(Coleysia, Dillan),
                  pair(Jacy, John), pair(Jessica, Ryan), pair(Kayla, Wes), pair(Paige, ChrisT), pair(Shanley, Dre),
                  pair(Simone, Joey));

      newTag().matchBox(pair(Kayla, Ryan), false)
            .matchNight(7, pair(Amber, Ethan), pair(Ashleigh, Ryan), pair(Brittany, John), pair(Coleysia, Dillan),
                  pair(Jacy, ChrisS), pair(Jessica, Joey), pair(Kayla, Wes), pair(Paige, ChrisT), pair(Shanley, Adam),
                  pair(Simone, Dre));

      newTag().matchBox(pair(Kayla, Wes), true)
            .matchNight(8, pair(Amber, Ethan), pair(Ashleigh, ChrisS), pair(Brittany, Joey), pair(Coleysia, Dillan),
                  pair(Jacy, John), pair(Jessica, Ryan), pair(Kayla, Wes), pair(Paige, ChrisT), pair(Shanley, Adam),
                  pair(Simone, Dre));

      newTag().matchBox(pair(Jacy, John), false)
            .matchNight(10, pair(Amber, Ethan), pair(Ashleigh, John), pair(Brittany, Joey), pair(Coleysia, Dillan),
                  pair(Jacy, ChrisS), pair(Jessica, Ryan), pair(Kayla, Wes), pair(Paige, ChrisT), pair(Shanley, Adam),
                  pair(Simone, Dre));

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printDayResults(new AYTO_1());
   }

}
