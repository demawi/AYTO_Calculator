package demawi.ayto.us;

import demawi.ayto.modell.Woman;
import demawi.ayto.modell.Man;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_03
      extends SeasonData {

   private final Woman Amanda = frau("Amanda");
   private final Woman Britni = frau("Britni");
   private final Woman Chelsey = frau("Chelsey");
   private final Woman Cheyenne = frau("Cheyenne");
   private final Woman Hannah = frau("Hannah");
   private final Woman Kayla = frau("Kayla");
   private final Woman Kiki = frau("Kiki");
   private final Woman Melanie = frau("Melanie");
   private final Woman Rashida = frau("Rashida");
   private final Woman Stacey = frau("Stacey");

   private final Man Alec = mann("Alec");
   private final Man Austin = mann("Austin");
   private final Man Chuck = mann("Chuck");
   private final Man Connor = mann("Connor");
   private final Man Devin = mann("Devin");
   private final Man Hunter = mann("Hunter");
   private final Man Mike = mann("Mike");
   private final Man Nelson = mann("Nelson");
   private final Man Tyler = mann("Tyler");
   private final Man Zak = mann("Zak");

   public AYTO_03() {
      super(Mode.STANDARD);

      newDay().matchBox(pair(Kiki, Hunter), false)
            .matchNight(2, pair(Amanda, Mike), pair(Britni, Hunter), pair(Chelsey, Connor), pair(Cheyenne, Nelson),
                  pair(Hannah, Chuck), pair(Kayla, Zak), pair(Kiki, Austin), pair(Melanie, Devin), pair(Rashida, Tyler),
                  pair(Stacey, Alec));

      newDay().matchBox(pair(Kiki, Devin), false)
            .matchNight(0, pair(Amanda, Mike), pair(Britni, Zak), pair(Chelsey, Alec), pair(Cheyenne, Nelson),
                  pair(Hannah, Chuck), pair(Kayla, Connor), pair(Kiki, Austin), pair(Melanie, Devin),
                  pair(Rashida, Tyler), pair(Stacey, Hunter));

      newDay().matchBox(pair(Kiki, Zak), false)
            .matchNight(3, pair(Amanda, Austin), pair(Britni, Hunter), pair(Chelsey, Connor), pair(Cheyenne, Tyler),
                  pair(Hannah, Zak), pair(Kayla, Mike), pair(Kiki, Chuck), pair(Melanie, Nelson), pair(Rashida, Devin),
                  pair(Stacey, Alec));

      newDay().matchBox(pair(Britni, Chuck), false)
            .matchNight(2, pair(Amanda, Alec), pair(Britni, Nelson), pair(Chelsey, Connor), pair(Cheyenne, Zak),
                  pair(Hannah, Devin), pair(Kayla, Mike), pair(Kiki, Chuck), pair(Melanie, Tyler),
                  pair(Rashida, Hunter), pair(Stacey, Austin));

      newDay().matchBox(pair(Chelsey, Connor), true)
            .matchNight(2, pair(Amanda, Tyler), pair(Britni, Mike), pair(Chelsey, Connor), pair(Cheyenne, Devin),
                  pair(Hannah, Austin), pair(Kayla, Zak), pair(Kiki, Chuck), pair(Melanie, Hunter),
                  pair(Rashida, Nelson), pair(Stacey, Alec));

      newDay().matchBox(pair(Kiki, Chuck), false)
            .matchNight(3, pair(Amanda, Chuck), pair(Britni, Hunter), pair(Chelsey, Connor), pair(Cheyenne, Austin),
                  pair(Hannah, Tyler), pair(Kayla, Zak), pair(Kiki, Nelson), pair(Melanie, Mike), pair(Rashida, Devin),
                  pair(Stacey, Alec));

      newDay().matchBox(pair(Melanie, Alec), false)
            .matchNight(3, pair(Amanda, Hunter), pair(Britni, Devin), pair(Chelsey, Connor), pair(Cheyenne, Tyler),
                  pair(Hannah, Zak), pair(Kayla, Austin), pair(Kiki, Nelson), pair(Melanie, Chuck), pair(Rashida, Alec),
                  pair(Stacey, Mike));

      newDay().matchBox(pair(Kiki, Nelson), false)
            .matchNight(3, pair(Amanda, Chuck), pair(Britni, Hunter), pair(Chelsey, Connor), pair(Cheyenne, Tyler),
                  pair(Hannah, Zak), pair(Kayla, Nelson), pair(Kiki, Austin), pair(Melanie, Mike), pair(Rashida, Devin),
                  pair(Stacey, Alec));

      newDay().matchBox(pair(Britni, Hunter), false)
            .matchNight(2, pair(Amanda, Nelson), pair(Britni, Devin), pair(Chelsey, Connor), pair(Cheyenne, Austin),
                  pair(Hannah, Zak), pair(Kayla, Hunter), pair(Kiki, Mike), pair(Melanie, Tyler), pair(Rashida, Chuck),
                  pair(Stacey, Alec));

      newDay().matchBox(pair(Kayla, Zak), true)
            .matchNight(10, pair(Amanda, Alec), pair(Britni, Austin), pair(Chelsey, Connor), pair(Cheyenne, Tyler),
                  pair(Hannah, Hunter), pair(Kayla, Zak), pair(Kiki, Mike), pair(Melanie, Chuck), pair(Rashida, Devin),
                  pair(Stacey, Nelson));

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printLastDayResults(new AYTO_03());
   }

}
