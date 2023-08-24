package demawi.ayto.modell;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AYTO_PairTest {

   @Test
   public void test() {
      Person a = new Person("a");
      Person a2 = new Person("a");
      Person b = new Person("b");
      Frau frauC = new Frau("c");
      Mann mannC = new Mann("c");
      assertTrue(a.equals(a2));
      assertFalse(a.equals(b));
      assertFalse(frauC.equals(mannC));
      assertTrue(AYTO_Pair.pair(a, b)
            .equals(AYTO_Pair.pair(b, a)));
      assertFalse(AYTO_Pair.pair(frauC, mannC)
            .equals(AYTO_Pair.pair(mannC, frauC)));
   }
}
