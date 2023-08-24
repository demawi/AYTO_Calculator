package demawi.ayto.us;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_3
      extends StaffelData {

   private final Frau Amanda = frau("Amanda");
   private final Frau Britni = frau("Britni");
   private final Frau Chelsey = frau("Chelsey");
   private final Frau Cheyenne = frau("Cheyenne");
   private final Frau Hannah = frau("Hannah");
   private final Frau Kayla = frau("Kayla");
   private final Frau Kiki = frau("Kiki");
   private final Frau Melanie = frau("Melanie");
   private final Frau Rashida = frau("Rashida");
   private final Frau Stacey = frau("Stacey");

   private final Mann Alec = mann("Alec");
   private final Mann Austin = mann("Austin");
   private final Mann Chuck = mann("Chuck");
   private final Mann Connor = mann("Connor");
   private final Mann Devin = mann("Devin");
   private final Mann Hunter = mann("Hunter");
   private final Mann Mike = mann("Mike");
   private final Mann Nelson = mann("Nelson");
   private final Mann Tyler = mann("Tyler");
   private final Mann Zak = mann("Zak");

   public AYTO_3() {
      super("03", AYTO_Permutator.ZUSATZTYPE.KEINER);

      newTag().matchBox(pair(Kiki, Hunter), false)
            .matchNight(2, pair(Amanda, Mike), pair(Britni, Hunter), pair(Chelsey, Connor), pair(Cheyenne, Nelson),
                  pair(Hannah, Chuck), pair(Kayla, Zak), pair(Kiki, Austin), pair(Melanie, Devin), pair(Rashida, Tyler),
                  pair(Stacey, Alec));

      newTag().matchBox(pair(Kiki, Devin), false)
            .matchNight(0, pair(Amanda, Mike), pair(Britni, Zak), pair(Chelsey, Alec), pair(Cheyenne, Nelson),
                  pair(Hannah, Chuck), pair(Kayla, Connor), pair(Kiki, Austin), pair(Melanie, Devin),
                  pair(Rashida, Tyler), pair(Stacey, Hunter));

      newTag().matchBox(pair(Kiki, Zak), false)
            .matchNight(3, pair(Amanda, Austin), pair(Britni, Hunter), pair(Chelsey, Connor), pair(Cheyenne, Tyler),
                  pair(Hannah, Zak), pair(Kayla, Mike), pair(Kiki, Chuck), pair(Melanie, Nelson), pair(Rashida, Devin),
                  pair(Stacey, Alec));

      newTag().matchBox(pair(Britni, Chuck), false)
            .matchNight(2, pair(Amanda, Alec), pair(Britni, Nelson), pair(Chelsey, Connor), pair(Cheyenne, Zak),
                  pair(Hannah, Devin), pair(Kayla, Mike), pair(Kiki, Chuck), pair(Melanie, Tyler),
                  pair(Rashida, Hunter), pair(Stacey, Austin));

      newTag().matchBox(pair(Chelsey, Connor), true)
            .matchNight(2, pair(Amanda, Tyler), pair(Britni, Mike), pair(Chelsey, Connor), pair(Cheyenne, Devin),
                  pair(Hannah, Austin), pair(Kayla, Zak), pair(Kiki, Chuck), pair(Melanie, Hunter),
                  pair(Rashida, Nelson), pair(Stacey, Alec));

      newTag().matchBox(pair(Kiki, Chuck), false)
            .matchNight(3, pair(Amanda, Chuck), pair(Britni, Hunter), pair(Chelsey, Connor), pair(Cheyenne, Austin),
                  pair(Hannah, Tyler), pair(Kayla, Zak), pair(Kiki, Nelson), pair(Melanie, Mike), pair(Rashida, Devin),
                  pair(Stacey, Alec));

      newTag().matchBox(pair(Melanie, Alec), false)
            .matchNight(3, pair(Amanda, Hunter), pair(Britni, Devin), pair(Chelsey, Connor), pair(Cheyenne, Tyler),
                  pair(Hannah, Zak), pair(Kayla, Austin), pair(Kiki, Nelson), pair(Melanie, Chuck), pair(Rashida, Alec),
                  pair(Stacey, Mike));

      newTag().matchBox(pair(Kiki, Nelson), false)
            .matchNight(3, pair(Amanda, Chuck), pair(Britni, Hunter), pair(Chelsey, Connor), pair(Cheyenne, Tyler),
                  pair(Hannah, Zak), pair(Kayla, Nelson), pair(Kiki, Austin), pair(Melanie, Mike), pair(Rashida, Devin),
                  pair(Stacey, Alec));

      newTag().matchBox(pair(Britni, Hunter), false)
            .matchNight(2, pair(Amanda, Nelson), pair(Britni, Devin), pair(Chelsey, Connor), pair(Cheyenne, Austin),
                  pair(Hannah, Zak), pair(Kayla, Hunter), pair(Kiki, Mike), pair(Melanie, Tyler), pair(Rashida, Chuck),
                  pair(Stacey, Alec));

      newTag().matchBox(pair(Kayla, Zak), true)
            .matchNight(10, pair(Amanda, Alec), pair(Britni, Austin), pair(Chelsey, Connor), pair(Cheyenne, Tyler),
                  pair(Hannah, Hunter), pair(Kayla, Zak), pair(Kiki, Mike), pair(Melanie, Chuck), pair(Rashida, Devin),
                  pair(Stacey, Nelson));

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printDayResults(new AYTO_3());
   }

}
