package demawi.ayto.de;

import demawi.ayto.modell.AYTO_Pair;
import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.modell.Woman;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.permutation.Mark;
import demawi.ayto.print.DefaultMatchPrinter;

public class AYTO_VIP04
      extends SeasonData {

   private final Woman Anastasia = frau("Anastasia");
   private final Woman Asena = frau("Asena");
   private final Woman Emmy = frau("Emmy");
   private final Woman Gabriela = frau("Gabriela");
   private final Woman Jennifer = frau("Jennifer");
   private final Woman LauraL = frau("LauraL");
   private final Woman LauraM = frau("LauraM");
   private final Woman Linda = frau("Linda");
   private final Woman Nadja = frau("Nadja");
   private final Woman Tara = frau("Tara");
   private final Woman Dana = frau("Max", Mark.CAN_BE_AN_EXTRA_MATCH, true);

   private final Man Alex = mann("Alex");
   private final Man Antonino = mann("Antonino");
   private final Man Chris = mann("Chris");
   private final Man Kaan = mann("Kaan");
   private final Man Lars = mann("Lars");
   private final Man Lukas = mann("Lukas");
   private final Man Marc_Robin = mann("Marc-Robin");
   private final Man Nikola = mann("Nikola");
   private final Man Ozan = mann("Ozan");
   private final Man Tim = mann("Tim");

   public AYTO_VIP04() {
      super(Mode.MARKED);

      newDay().matchBox(AYTO_Pair.pair(LauraL, Marc_Robin), false);

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_VIP04()).printLastDayResults();
   }

}
