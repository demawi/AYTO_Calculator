package demawi.ayto.de;

import demawi.ayto.modell.Man;
import demawi.ayto.modell.PermutationConfiguration;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.modell.Woman;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.permutation.Mark;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

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
   private final Woman Dana = frau("Dana", Mark.CAN_BE_AN_EXTRA_MATCH, true);

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
      super(new PermutationConfiguration(Mode.MARKED));

      newDay().matchBox(pair(LauraL, Marc_Robin), false)
            .matchNight(2, pair(Linda, Tim), pair(Jennifer, Lukas), pair(Tara, Nikola), pair(Nadja, Lars),
                  pair(Asena, Kaan), pair(Emmy, Chris), pair(LauraL, Alex), pair(Anastasia, Marc_Robin),
                  pair(Gabriela, Ozan), pair(LauraM, Antonino));

      newDay().addNew(Dana)
            .matchBox(pair(Emmy, Chris), true)
            .matchNight(3, pair(Emmy, Chris), pair(Dana, Antonino), pair(Jennifer, Alex), pair(Anastasia, Lars), pair(Nadja, Kaan),
                  pair(Linda, Tim), pair(Asena, Marc_Robin), pair(LauraL, Ozan), pair(Tara, Nikola), pair(LauraM, Lukas)); // Gabriela

      newDay().matchBox(pair(Anastasia, Alex), null)
            .matchNight(3, pair(Emmy, Chris), pair(Jennifer, Kaan), pair(Anastasia, Alex), pair(Asena, Marc_Robin),
                  pair(Dana, Tim), pair(Nadja, Antonino), pair(Linda, Lukas), pair(LauraM, Ozan), pair(LauraL, Nikola),
                  pair(Gabriela, Lars)); // Tara

      newDay().matchBox(pair(Asena, Marc_Robin), false)
            .matchNight(1, pair(Emmy, Chris), pair(Tara, Lars), pair(LauraM, Ozan), pair(Nadja, Antonino),
                  pair(Anastasia, Nikola), pair(Asena, Kaan), pair(LauraL, Tim), pair(Gabriela, Lukas),
                  pair(Jennifer, Alex), pair(Linda, Marc_Robin)); // Dana

      newDay().matchBox(pair(LauraM, Nikola), null)
            .matchNight(5, pair(Emmy, Chris), pair(Dana, Ozan), pair(Tara, Kaan), pair(Asena, Antonino),
                  pair(LauraL, Nikola), pair(Gabriela, Alex), pair(Jennifer, Lukas), pair(Nadja, Lars),
                  pair(Anastasia, Marc_Robin), pair(Linda, Tim)); // LauraM

      newDay().matchBox(pair(Anastasia, Ozan), true)
            .matchNight(6, pair(Emmy, Chris), pair(Anastasia, Ozan), pair(Jennifer, Lukas), pair(LauraM, Marc_Robin),
                  pair(Dana, Lars), pair(Linda, Tim), pair(Gabriela, Alex), pair(Nadja, Nikola), pair(Tara, Kaan),
                  pair(Asena, Antonino)); // LauraL

      newDay().matchBox(pair(Jennifer, Lukas), false)
            .matchNight(6, pair(Emmy, Chris), pair(Anastasia, Ozan), pair(Linda, Tim), pair(Dana, Lukas),
                  pair(Gabriela, Alex), pair(LauraL, Lars), pair(Asena, Antonino), pair(Tara, Kaan),
                  pair(Nadja, Nikola), pair(LauraM, Marc_Robin)); // Jennifer

      newDay().matchBox(pair(Linda, Tim), true, pair(Dana, Tim))
            .matchNight(7, pair(Emmy, Chris), pair(Anastasia, Ozan), pair(Linda, Tim), pair(Tara, Lukas),
                  pair(LauraM, Marc_Robin), pair(Nadja, Nikola), pair(Jennifer, Kaan), pair(Gabriela, Alex),
                  pair(LauraL, Lars), pair(Asena, Antonino)); // Dana

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_VIP04()).printLastDayResults();
   }

}
