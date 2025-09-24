package demawi.ayto.us;

import demawi.ayto.modell.Man;
import demawi.ayto.modell.PermutationConfiguration;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.modell.Woman;
import demawi.ayto.permutation.AYTO_Permutator.Mode;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_09
      extends SeasonData {

   private final Woman Anissa = frau("Anissa");
   private final Woman Brooke = frau("Brooke");
   private final Woman CC = frau("CC");
   private final Woman Courtney = frau("Courtney");
   private final Woman Danielle = frau("Danielle");
   private final Woman Dew = frau("Dew");
   private final Woman Jordanne = frau("Jordanne");
   private final Woman Julia_Ruth = frau("Julia-Ruth");
   private final Woman Mijntje = frau("Mijntje");
   private final Woman Roz = frau("Roz");
   private final Woman Taylor = frau("Taylor");

   private final Man Aqel = mann("Aqel");
   private final Man Brendan = mann("Brendan");
   private final Man Clay = mann("Clay");
   private final Man Eduardo = mann("Eduardo");
   private final Man Hamudi = mann("Hamudi");
   private final Man Leo = mann("Leo");
   private final Man Mikey = mann("Mikey");
   private final Man Nathan = mann("Nathan");
   private final Man Ollie = mann("Ollie");
   private final Man Samuel = mann("Samuel");
   private final Man Will = mann("Will");

   public AYTO_09() {
      super(new PermutationConfiguration(Mode.STANDARD), 11);

      newDay().matchBox(pair(Taylor, Nathan), false)
            .matchNight(0, pair(Anissa, Ollie), pair(Brooke, Leo), pair(CC, Brendan), pair(Courtney, Aqel),
                  pair(Danielle, Mikey), pair(Dew, Clay), pair(Jordanne, Will), pair(Julia_Ruth, Samuel),
                  pair(Mijntje, Nathan), pair(Roz, Eduardo), pair(Taylor, Hamudi));

      newDay().matchBox(pair(Anissa, Hamudi), false)
            .matchNight(2, pair(Anissa, Aqel), pair(Brooke, Ollie), pair(CC, Clay), pair(Courtney, Leo),
                  pair(Danielle, Eduardo), pair(Dew, Samuel), pair(Jordanne, Brendan), pair(Julia_Ruth, Nathan),
                  pair(Mijntje, Hamudi), pair(Roz, Mikey), pair(Taylor, Will));

      newDay().matchBox(pair(Taylor, Will), false)
            .matchNight(4, pair(Anissa, Aqel), pair(Brooke, Ollie), pair(CC, Clay), pair(Courtney, Will),
                  pair(Danielle, Hamudi), pair(Dew, Nathan), pair(Jordanne, Eduardo), pair(Julia_Ruth, Brendan),
                  pair(Mijntje, Mikey), pair(Roz, Samuel), pair(Taylor, Leo));

      newDay().matchBox(pair(Julia_Ruth, Brendan), true)
            .matchNight(2, pair(Anissa, Aqel), pair(Brooke, Clay), pair(CC, Mikey), pair(Courtney, Will),
                  pair(Danielle, Leo), pair(Dew, Ollie), pair(Jordanne, Eduardo), pair(Julia_Ruth, Brendan),
                  pair(Mijntje, Hamudi), pair(Roz, Nathan), pair(Taylor, Samuel));

      newDay().matchBox(pair(CC, Clay), false)
            .matchNight(3, pair(Anissa, Aqel), pair(Brooke, Ollie), pair(CC, Mikey), pair(Courtney, Will),
                  pair(Danielle, Leo), pair(Dew, Nathan), pair(Jordanne, Eduardo), pair(Julia_Ruth, Brendan),
                  pair(Mijntje, Hamudi), pair(Roz, Clay), pair(Taylor, Samuel));

      newDay().matchBox(pair(Brooke, Ollie), true)
            .matchNight(3, pair(Anissa, Samuel), pair(Brooke, Ollie), pair(CC, Mikey), pair(Courtney, Nathan),
                  pair(Danielle, Will), pair(Dew, Eduardo), pair(Jordanne, Leo), pair(Julia_Ruth, Brendan),
                  pair(Mijntje, Aqel), pair(Roz, Hamudi), pair(Taylor, Clay));

      newDay().matchBox(pair(Mijntje, Hamudi), false)
            .matchBox(pair(Mijntje, Samuel), true)
            .matchNight(6, pair(Anissa, Aqel), pair(Brooke, Ollie), pair(CC, Mikey), pair(Courtney, Hamudi),
                  pair(Danielle, Will), pair(Dew, Eduardo), pair(Jordanne, Nathan), pair(Julia_Ruth, Brendan),
                  pair(Mijntje, Samuel), pair(Roz, Leo), pair(Taylor, Clay));

      newDay().matchBox(pair(Taylor, Clay), true)
            .matchNight(6, pair(Anissa, Aqel), pair(Brooke, Ollie), pair(CC, Will), pair(Courtney, Hamudi),
                  pair(Danielle, Nathan), pair(Dew, Mikey), pair(Jordanne, Eduardo), pair(Julia_Ruth, Brendan),
                  pair(Mijntje, Samuel), pair(Roz, Leo), pair(Taylor, Clay));

      newDay().matchBox(pair(Roz, Mikey), false)
            .matchNight(11, pair(Anissa, Aqel), pair(Brooke, Ollie), pair(CC, Nathan), pair(Courtney, Eduardo),
                  pair(Danielle, Hamudi), pair(Dew, Will), pair(Jordanne, Mikey), pair(Julia_Ruth, Brendan),
                  pair(Mijntje, Samuel), pair(Roz, Leo), pair(Taylor, Clay));

   }

   public static void main(String[] args) {
      new DefaultMatchPrinter(new AYTO_09()).printLastDayResults();
   }

}
