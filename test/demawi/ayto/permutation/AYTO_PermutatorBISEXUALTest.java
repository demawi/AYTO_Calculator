package demawi.ayto.permutation;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import demawi.ayto.print.Formatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AYTO_PermutatorBISEXUALTest {

   private AtomicInteger atomicCount = new AtomicInteger(0);
   private static final List<String> allPersonenKlein = Arrays.asList("A", "B", "C", "D", "E");
   private static final List<String> allPersonen = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
         "L", "M", "N", "O", "P", "Q", "R", "S", "T");

   @Test
   public void testCanAddSmallBISEXUAL() {
      List<String> curPersonen = Arrays.asList("A", "B", "C", "D");
      AYTO_Permutator<String, String, String> permutator = AYTO_Permutator.create(curPersonen, curPersonen,
            AYTO_Permutator.ZUSATZTYPE.BISEXUAL, (a, b) -> "" + a + b);
      assertNull(permutator.canAdd(ind("B"), ind("A"), pairs("AB")));
      assertNull(permutator.canAdd(ind("A"), ind("B"), pairs("AB")));
      assertNull(permutator.canAdd(ind("A"), ind("C"), pairs("AB")));
      assertNull(permutator.canAdd(ind("D"), ind("B"), pairs("AB")));
      assertNull(permutator.canAdd(ind("C"), ind("D"), pairs("AB")));
      assertNull(permutator.canAdd(ind("D"), ind("C"), pairs("AB")));
   }

   @Test
   public void printSmall() {
      // Darf aufgrund der aktuellen Pair Codierung nicht größer als 16 sein!!!
      int anzahlPersonen = 16;
      boolean withFullOutput = anzahlPersonen < 10;
      boolean withFullCheck = true;
      long start = System.currentTimeMillis();
      AYTO_Permutator<String, String, String> permutator = AYTO_Permutator.create(personen(anzahlPersonen),
            personen(anzahlPersonen), AYTO_Permutator.ZUSATZTYPE.BISEXUAL, (a, b) -> "" + a + b);
      atomicCount.set(0);
      permutator.permutate(() -> result -> {
         atomicCount.incrementAndGet();
         if (withFullCheck) {
            check(result, personen(anzahlPersonen), personen(anzahlPersonen));
         }
         if (withFullOutput) {
            System.out.println(result);
         }
      });
      System.out.println("Found " + atomicCount + " constellations in " + Formatter.minSecs(System.currentTimeMillis() - start));
   }

   private static int ind(String f) {
      for (int i = 0, l = allPersonen.size(); i < l; i++) {
         String frau = allPersonen.get(i);
         if (frau.equals(f)) {
            return i;
         }
      }
      throw new IllegalStateException("Can not found: '" + f + "'");
   }

   private static Object[] pairs(Object... strs) {
      Object[] result = new Object[strs.length];
      for (int i = 0, l = strs.length; i < l; i++) {
         Object cur = strs[i];
         if (cur instanceof String) {
            result[i] = pair((String) cur);
         }
         else {
            result[i] = cur;
         }
      }
      return result;
   }

   private static int pair(String combinded) {
      return pair("" + combinded.charAt(0), "" + combinded.charAt(1));
   }

   private static int pair(String f, String m) {
      return AYTO_Permutator.encodePair(ind(f), ind(m));
   }

   private static List<String> personen(int anzahl) {
      return allPersonen.subList(0, anzahl);
   }

   private void check(Set<String> result, List<String> frauen, List<String> maenner) {
      for (String frau : frauen) {
         if (result.stream()
               .filter(r -> r.contains(frau))
               .count() == 0) {
            System.out.println(
                  "Frau '" + frau + "' ist nicht vorhanden im Resultat: " + Arrays.deepToString(result.toArray()));
            throw new RuntimeException(
                  "Frau '" + frau + "' ist nicht vorhanden im Resultat: " + Arrays.deepToString(result.toArray()));
         }
      }
      for (String mann : maenner) {
         if (result.stream()
               .filter(r -> r.contains(mann))
               .count() == 0) {
            System.out.println(
                  "Mann '" + mann + "' ist nicht vorhanden im Resultat: " + Arrays.deepToString(result.toArray()));
            throw new RuntimeException(
                  "Mann '" + mann + "' ist nicht vorhanden im Resultat: " + Arrays.deepToString(result.toArray()));
         }
      }
   }
}
