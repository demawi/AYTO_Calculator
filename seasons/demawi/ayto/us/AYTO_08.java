package demawi.ayto.us;

import demawi.ayto.modell.Person;
import demawi.ayto.modell.StaffelData;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.print.DefaultMatchPrinter;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class AYTO_08
      extends StaffelData {

   private final Person Aasha = person("Aasha");
   private final Person Amber = person("Amber");
   private final Person Basit = person("Basit");
   private final Person Brandon = person("Brandon");
   private final Person Danny = person("Danny");
   private final Person Jasmine = person("Jasmine");
   private final Person Jenna = person("Jenna");
   private final Person Jonathan = person("Jonathan");
   private final Person Justin = person("Justin");
   private final Person Kai = person("Kai");
   private final Person Kari = person("Kari");
   private final Person Kylie = person("Kylie");
   private final Person Max = person("Max ");
   private final Person Nour = person("Nour");
   private final Person Paige = person("Paige");
   private final Person Remy = person("Remy");

   public AYTO_08() {
      super(AYTO_Permutator.ZUSATZTYPE.BISEXUAL, 8);

      newTag().matchBox(pair(Justin, Nour), false)
            .matchNight(2, pair(Aasha, Paige), pair(Amber, Nour), pair(Basit, Jonathan), pair(Brandon, Remy),
                  pair(Danny, Kai), pair(Jasmine, Jenna), pair(Jenna, Jasmine), pair(Jonathan, Basit),
                  pair(Justin, Max), pair(Kai, Danny), pair(Kari, Kylie), pair(Kylie, Kari), pair(Max, Justin),
                  pair(Nour, Amber), pair(Paige, Aasha), pair(Remy, Brandon));

      newTag().matchBox(pair(Remy, Brandon), false)
            .matchNight(2, pair(Aasha, Brandon), pair(Amber, Nour), pair(Basit, Jonathan), pair(Brandon, Aasha),
                  pair(Danny, Remy), pair(Jasmine, Justin), pair(Jenna, Kai), pair(Jonathan, Basit),
                  pair(Justin, Jasmine), pair(Kai, Jenna), pair(Kari, Kylie), pair(Kylie, Kari), pair(Max, Paige),
                  pair(Nour, Amber), pair(Paige, Max), pair(Remy, Danny));

      newTag().matchBox(pair(Jenna, Kai), false)
            .matchNight(2, pair(Aasha, Max), pair(Amber, Paige), pair(Basit, Remy), pair(Brandon, Jonathan),
                  pair(Danny, Kai), pair(Jasmine, Nour), pair(Jenna, Justin), pair(Jonathan, Brandon),
                  pair(Justin, Jenna), pair(Kai, Danny), pair(Kari, Kylie), pair(Kylie, Kari), pair(Max, Aasha),
                  pair(Nour, Jasmine), pair(Paige, Amber), pair(Remy, Basit));

      newTag().matchBox(pair(Danny, Jenna), false)
            .matchNight(1, pair(Aasha, Remy), pair(Amber, Nour), pair(Basit, Danny), pair(Brandon, Jasmine),
                  pair(Danny, Basit), pair(Jasmine, Brandon), pair(Jenna, Paige), pair(Jonathan, Kylie),
                  pair(Justin, Max), pair(Kai, Kari), pair(Kari, Kai), pair(Kylie, Jonathan), pair(Max, Justin),
                  pair(Nour, Amber), pair(Paige, Jenna), pair(Remy, Aasha));

      newTag().matchBox(pair(Kari, Kylie), false)
            .matchNight(0, pair(Aasha, Kai), pair(Amber, Nour), pair(Basit, Remy), pair(Brandon, Max),
                  pair(Danny, Kari), pair(Jasmine, Paige), pair(Jenna, Kylie), pair(Jonathan, Justin),
                  pair(Justin, Jonathan), pair(Kai, Aasha), pair(Kari, Danny), pair(Kylie, Jenna), pair(Max, Brandon),
                  pair(Nour, Amber), pair(Paige, Jasmine), pair(Remy, Basit));

      newTag().matchBox(pair(Aasha, Brandon), true)
            .matchNight(3, pair(Aasha, Brandon), pair(Amber, Jenna), pair(Basit, Jonathan), pair(Brandon, Aasha),
                  pair(Danny, Kai), pair(Jasmine, Kylie), pair(Jenna, Amber), pair(Jonathan, Basit), pair(Justin, Max),
                  pair(Kai, Danny), pair(Kari, Paige), pair(Kylie, Jasmine), pair(Max, Justin), pair(Nour, Remy),
                  pair(Paige, Kari), pair(Remy, Nour));

      newTag().matchBox(pair(Jasmine, Jenna), false)
            .matchNight(3, pair(Aasha, Brandon), pair(Amber, Danny), pair(Basit, Jonathan), pair(Brandon, Aasha),
                  pair(Danny, Amber), pair(Jasmine, Kai), pair(Jenna, Paige), pair(Jonathan, Basit), pair(Justin, Max),
                  pair(Kai, Jasmine), pair(Kari, Remy), pair(Kylie, Nour), pair(Max, Justin), pair(Nour, Kylie),
                  pair(Paige, Jenna), pair(Remy, Kari));

      newTag().matchBox(pair(Paige, Remy), false)
            .matchNight(3, pair(Aasha, Brandon), pair(Amber, Paige), pair(Basit, Jonathan), pair(Brandon, Aasha),
                  pair(Danny, Kylie), pair(Jasmine, Nour), pair(Jenna, Kari), pair(Jonathan, Basit), pair(Justin, Max),
                  pair(Kai, Remy), pair(Kari, Jenna), pair(Kylie, Danny), pair(Max, Justin), pair(Nour, Jasmine),
                  pair(Paige, Amber), pair(Remy, Kai));

      newTag().matchBox(pair(Amber, Max), false)
            .matchNight(6, pair(Aasha, Brandon), pair(Amber, Kylie), pair(Basit, Jonathan), pair(Brandon, Aasha),
                  pair(Danny, Kai), pair(Jasmine, Nour), pair(Jenna, Paige), pair(Jonathan, Basit), pair(Justin, Remy),
                  pair(Kai, Danny), pair(Kari, Max), pair(Kylie, Amber), pair(Max, Kari), pair(Nour, Jasmine),
                  pair(Paige, Jenna), pair(Remy, Justin));

      newTag().matchBox(pair(Amber, Kylie), false)
            .matchNight(8, pair(Aasha, Brandon), pair(Amber, Remy), pair(Basit, Jonathan), pair(Brandon, Aasha),
                  pair(Danny, Kai), pair(Jasmine, Nour), pair(Jenna, Paige), pair(Jonathan, Basit), pair(Justin, Kylie),
                  pair(Kai, Danny), pair(Kari, Max), pair(Kylie, Justin), pair(Max, Kari), pair(Nour, Jasmine),
                  pair(Paige, Jenna), pair(Remy, Amber));
   }

   public static void main(String[] args) {
      new DefaultMatchPrinter().printDayResults(new AYTO_08());
   }

}
