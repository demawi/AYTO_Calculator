package demawi.ayto.perm;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import demawi.ayto.service.StandardMatchFinder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AYTO_PermutatorTest {

   /**
    * Kleiner 4:3 Test mit kompletter Ausgabe
    */
   @Test
   public void testSmall() {
      AYTO_Permutator<String, String, String> permutator = AYTO_Permutator.create(frauen(4), maenner(3),
            AYTO_Permutator.ZUSATZTYPE.JEDER, (a, b) -> "" + a + b);
      permutator.permutate(result -> {
         //check(result, frauen, maenner);
         System.out.println(result);
      });
      AYTO_Permutator<String, String, String> permutator2 = AYTO_Permutator.create(frauen(4), maenner(3),
            AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER, (a, b) -> "" + a + b);
      permutator2.permutate(result -> {
         //check(result, frauen, maenner);
         System.out.println(result);
      });
   }

   private int count;
   @Test
   public void testBigJEDER() {
      AYTO_Permutator<String, String, String> permutator = AYTO_Permutator.create(frauen(11), maenner(10),
            AYTO_Permutator.ZUSATZTYPE.JEDER, (a, b) -> "" + a + b);
      long start = System.currentTimeMillis();
      count = 0;
      permutator.permutate(result -> {
         count++;
      });
      System.out.println(
            "JEDER 11:10 PERMUTATION Taken time: " + StandardMatchFinder.minSecs(System.currentTimeMillis() - start));
      System.out.println("Anzahl erzeugter Permutationen: " + count);
   }

   @Test
   public void testBigNUR_LETZTER() {
      AYTO_Permutator<String, String, String> permutator = AYTO_Permutator.create(frauen(11), maenner(10),
            AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER, (a, b) -> "" + a + b);
      long start = System.currentTimeMillis();
      count = 0;
      permutator.permutate(result -> {
         count++;
      });
      System.out.println("NUR_LETZTER 11:10 PERMUTATION Taken time: " + StandardMatchFinder.minSecs(
            System.currentTimeMillis() - start));
      System.out.println("Anzahl erzeugter Permutationen: " + count);
   }

   @Test
   public void testCanAddBigJEDER() {
      AYTO_Permutator<String, String, String> permutator = AYTO_Permutator.create(frauen(11), maenner(10),
            AYTO_Permutator.ZUSATZTYPE.JEDER, (a, b) -> "" + a + b);
      // UrsprungDoppelt: 1 Es fehlt: E4
      assertNull(permutator.canAdd(ind("A"), ind("1"),
            pairs(true, "A1", "I9", "G8", "C7", "J2", "F0", "K5", "H6", "B1", "D3")));
      assertNull(permutator.canAdd(ind("A"), ind("2"),
            pairs(true, "A1", "I9", "G8", "C7", "J2", "F0", "K5", "H6", "B1", "D3")));
      assertNull(permutator.canAdd(ind("A"), ind("4"),
            pairs(true, "A1", "I9", "G8", "C7", "J2", "F0", "K5", "H6", "B1", "D3")));
      assertNull(permutator.canAdd(ind("E"), ind("1"),
            pairs(true, "A1", "I9", "G8", "C7", "J2", "F0", "K5", "H6", "B1", "D3")));
      assertNotNull(permutator.canAdd(ind("E"), ind("4"),
            pairs(true, "A1", "I9", "G8", "C7", "J2", "F0", "K5", "H6", "B1", "D3")));
   }

   @Test
   public void testCanAddSmallJEDER() {
      List<String> frauen = Arrays.asList("A", "B", "C", "D");
      List<String> maenner = Arrays.asList("1", "2", "3");
      AYTO_Permutator<String, String, String> permutator = AYTO_Permutator.create(frauen, maenner,
            AYTO_Permutator.ZUSATZTYPE.JEDER, (a, b) -> "" + a + b);
      assertNotNull(permutator.canAdd(ind("B"), ind("2"), pairs(false, "A1")));
      assertNotNull(permutator.canAdd(ind("C"), ind("1"), pairs(false, "A1", "B2")));
      assertNotNull(permutator.canAdd(ind("D"), ind("3"), pairs(true, "A1", "B2", "C1")));
      assertNull(permutator.canAdd(ind("D"), ind("2"), pairs(true, "A1", "B2", "C1")));
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

   private static final List<String> allFrauen = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K");

   private static List<String> frauen(int anzahl) {
      return allFrauen.subList(0, anzahl);
   }

   private static final List<String> allMaenner = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0");

   private static List<String> maenner(int anzahl) {
      return allMaenner.subList(0, anzahl);
   }

   private static int pair(String combinded) {
      return pair("" + combinded.charAt(0), "" + combinded.charAt(1));
   }

   private static int pair(String f, String m) {
      return AYTO_Permutator.encodePair(ind(f), ind(m));
   }

   private static int ind(String f) {
      for (int i = 0, l = allFrauen.size(); i < l; i++) {
         String frau = allFrauen.get(i);
         if (frau.equals(f)) {
            return i;
         }
      }
      for (int i = 0, l = allMaenner.size(); i < l; i++) {
         String frau = allMaenner.get(i);
         if (frau.equals(f)) {
            return i;
         }
      }
      throw new IllegalStateException("Can not found: '" + f + "'");
   }

   private void check(Set<String> result, List<String> frauen, List<String> maenner) {
      for (String frau : frauen) {
         assertTrue(result.stream()
                     .filter(r -> r.contains(frau))
                     .count() > 0,
               "Frau '" + frau + "' ist nicht vorhanden im Resultat: " + Arrays.deepToString(result.toArray()));
      }
      for (String mann : maenner) {
         assertTrue(result.stream()
                     .filter(r -> r.contains(mann))
                     .count() > 0,
               "Mann '" + mann + "' ist nicht vorhanden im Resultat: " + Arrays.deepToString(result.toArray()));
      }
   }

}
