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

      newDay().matchBox(pair(Deisy, Dino), true)
            .matchNight(4, pair(Nadja, Danish), pair(Deisy, Dino), pair(Nasty, Sinan), pair(Joanna, Joshua),
                  pair(Ina, Josh), pair(Tori, Tano), pair(Sophia, Kaan), pair(Selina, Enes), pair(Chiara, Dion),
                  pair(Camelia, Levin)); // Anna

      newDay().matchBox(pair(Tori, Tano), null) // selbst verkauft
            .matchNight(5, pair(Nadja, Danish), pair(Deisy, Dino), pair(Anna, Dion), pair(Chiara, Levin),
                  pair(Nasty, Josh), pair(Camelia, Sinan), pair(Sophia, Kaan), pair(Tori, Tano), pair(Selina, Enes),
                  pair(Ina, Joshua)); // Joanna

      newDay().matchBox(pair(Sophia, Kaan), false)
            .matchNight(6, pair(Nadja, Danish), pair(Deisy, Dino), pair(Selina, Kaan), pair(Joanna, Joshua),
                  pair(Chiara, Levin), pair(Camelia, Josh), pair(Sophia, Enes), pair(Nasty, Sinan), pair(Tori, Dion),
                  pair(Anna, Tano)); // Ina

      newDay().matchBox(pair(Joanna, Joshua), false)
            .matchNight(5, pair(Nadja, Danish), pair(Deisy, Dino), pair(Anna, Joshua), pair(Camelia, Josh),
                  pair(Sophia, Enes), pair(Ina, Levin), pair(Selina, Kaan), pair(Chiara, Dion), pair(Nasty, Sinan),
                  pair(Joanna, Tano)); // Tori

      // Frauen-Wahl
      newDay().matchBox(pair(Nasty, Sinan), true)
            .matchNight(7, pair(Nadja, Danish), pair(Deisy, Dino), pair(Nasty, Sinan), pair(Sophia, Dion),
                  pair(Joanna, Tano), pair(Tori, Joshua), pair(Anna, Kaan), pair(Chiara, Levin), pair(Camelia, Josh),
                  pair(Ina, Enes)); // Selina

      newDay().matchBox(pair(Camelia, Josh), true)
            .matchNight(10, pair(Nadja, Danish), pair(Deisy, Dino), pair(Nasty, Sinan), pair(Camelia, Josh),
                  pair(Joanna, Tano), pair(Chiara, Levin), pair(Anna, Kaan), pair(Selina, Enes), pair(Tori, Dion),
                  pair(Ina, Joshua));

      // Beim Wiedersehen:
      newDay().matchBox(pair(Sophia, Tano), true);
   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_06()).printLastDayResults();
   }

}
