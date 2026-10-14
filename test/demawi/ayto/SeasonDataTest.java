package demawi.ayto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import demawi.ayto.de.AYTO_VIP06;
import demawi.ayto.modell.Day;
import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.CalculationOptions;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.modell.Man;
import demawi.ayto.modell.AYTO_Pair;
import demawi.ayto.modell.Timepoint;
import demawi.ayto.modell.Woman;
import demawi.ayto.modell.events.Event;
import demawi.ayto.modell.events.MatchBoxResult;
import demawi.ayto.modell.events.NewPerson;
import demawi.ayto.modell.events.PairInterpreter;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static demawi.ayto.modell.AYTO_Pair.pair;

public class SeasonDataTest {

   @Test
   void propagatesAllConfirmedPerfectMatchesToNewPersonImplicits() {
      SeasonData data = new AYTO_VIP06();
      data.ensureDataIsClosed();

      NewPerson laurenzEvent = null;
      for (Day day : data.getTage()) {
         for (Event event : day.getEvents()) {
            if (event instanceof NewPerson && ((NewPerson) event).person.getName().equals("Laurenz")) {
               laurenzEvent = (NewPerson) event;
            }
         }
      }

      assertTrue(laurenzEvent != null);
      assertEquals(2, laurenzEvent.implicits.size());
      assertTrue(laurenzEvent.implicits.stream()
            .anyMatch(result -> result.pair.toString().equals("Marta & Laurenz") && !result.result));
      assertTrue(laurenzEvent.implicits.stream()
            .anyMatch(result -> result.pair.toString().equals("Janice & Laurenz") && !result.result));
   }

   @Test
   void validatesAdditionalConfirmedPerfectMatch() {
      Woman marta = new Woman("Marta");
      Woman janice = new Woman("Janice");
      Man johannes = new Man("Johannes");
      MatchBoxResult result = new MatchBoxResult(pair(marta, johannes), true, new AYTO_Pair[] {
            pair(janice, johannes)
      });
      PairInterpreter lookup = PairInterpreter.create();

      assertTrue(result.isValid(Set.of(pair(marta, johannes), pair(janice, johannes)), lookup));
      assertTrue(!result.isValid(Set.of(pair(marta, johannes)), lookup));
   }

   @Test
   void reportsBothConfirmedPerfectMatchesAsCertainAfterNewPerson() {
      SeasonData data = new AYTO_VIP06();
      data.ensureDataIsClosed();
      AYTO_Result result = new AYTO_Result(
            new CalculationOptions(data, new Timepoint(5, 1)));

      Woman janice = (Woman) data.initialFrauen.stream()
            .filter(person -> person.getName().equals("Janice"))
            .findFirst().get();
      Woman marta = (Woman) data.initialFrauen.stream()
            .filter(person -> person.getName().equals("Marta"))
            .findFirst().get();
      Man johannes = (Man) data.initialMaenner.stream()
            .filter(person -> person.getName().equals("Johannes"))
            .findFirst().get();

      assertEquals(1d, result.getBasePossibility(pair(janice, johannes)));
      assertEquals(1d, result.getBasePossibility(pair(marta, johannes)));
   }
}
