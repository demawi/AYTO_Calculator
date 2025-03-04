package demawi.ayto.de;

import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.modell.Woman;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;
import static demawi.ayto.permutation.Mark.CAN_BE_AN_EXTRA_MATCH;

public class AYTO_06
      extends SeasonData {

   private final Woman Anna = frau("Anna").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Joanna = frau("Joanna").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Deisy = frau("Deisy").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Nasty = frau("Anastasia").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Camelia = frau("Camelia").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Ina = frau("Ina").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Chiara = frau("Chiara").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Nadja = frau("Nadja").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Selina = frau("Selina").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Sophia = frau("Sophia").mark(CAN_BE_AN_EXTRA_MATCH);
   private final Woman Tori = frau("Tori", true).mark(CAN_BE_AN_EXTRA_MATCH);

   private final Man Danish = mann("Danish");
   private final Man Dion = mann("Dion");
   private final Man Dino = mann("Dino");
   private final Man Enes = mann("Enes");
   private final Man Joshua = mann("Joshua");
   private final Man Josh = mann("Josh");
   private final Man Levin = mann("Levin");
   private final Man Kaan = mann("Kaan");
   private final Man Sinan = mann("Sinan");
   private final Man Tano = mann("Tano");

   public AYTO_06() {
      super(Mode.MARKED);

      newDay().matchBox(pair(Anna, Josh), false)
            .addNew(Tori)
            .matchNight(2, pair(Deisy, Dino), pair(Ina, Sinan), pair(Joanna, Kaan), pair(Tori, Dion), pair(Anna, Enes),
                  pair(Sophia, Danish), pair(Selina, Joshua), pair(Camelia, Levin), pair(Nadja, Josh),
                  pair(Chiara, Tano)); // Nasty

      newDay().matchBox(pair(Camelia, Levin), null)
            .matchNight(3, pair(Nadja, Josh), pair(Anna, Kaan), pair(Chiara, Levin), pair(Camelia, Danish),
                  pair(Enes, Nasty), pair(Tori, Tano), pair(Deisy, Dino), pair(Joanna, Joshua), pair(Ina, Sinan),
                  pair(Selina, Dion)); // Sophia

      newDay().matchBox(pair(Ina, Sinan), false)
            .matchNight(4, pair(Joanna, Joshua), pair(Chiara, Dion), pair(Deisy, Dino), pair(Anna, Kaan),
                  pair(Camelia, Levin), pair(Tori, Tano), pair(Nadja, Josh), pair(Selina, Enes), pair(Nasty, Sinan),
                  pair(Sophia, Danish)); // Ina

      newDay().matchBox(pair(Nadja, Danish), true)
            .matchNight(4, pair(Nadja, Danish), pair(Enes, Selina), pair(Camelia, Levin), pair(Tori, Tano),
                  pair(Deisy, Dino), pair(Ina, Josh), pair(Nasty, Dion), pair(Sophia, Sinan), pair(Anna, Kaan),
                  pair(Joanna, Joshua)); // Chiara

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_06()).printLastDayResults();
   }

}
