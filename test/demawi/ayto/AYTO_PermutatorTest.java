package demawi.ayto;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AYTO_PermutatorTest {

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

   @Test
   public void testSmall() {
      List<String> frauen = Arrays.asList("A", "B", "C", "D");
      List<String> maenner = Arrays.asList("1", "2", "3");

      AYTO_Permutator<String, String, String> permutator = AYTO_Permutator.create(frauen, maenner,
            AYTO_Permutator.ZUSATZTYPE.JEDER, (a, b) -> "" + a + b);
      permutator.permutate(result -> {
         //check(result, frauen, maenner);
         System.out.println(result);
      });
      AYTO_Permutator<String, String, String> permutator2 = AYTO_Permutator.create(frauen, maenner,
            AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER, (a, b) -> "" + a + b);
      permutator2.permutate(result -> {
         //check(result, frauen, maenner);
         System.out.println(result);
      });
   }

   @Test
   public void testBigUNKNOWN() {
      List<String> frauen = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K");
      List<String> maenner = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0");
      AYTO_Permutator<String, String, String> permutator = AYTO_Permutator.create(frauen, maenner,
            AYTO_Permutator.ZUSATZTYPE.JEDER, (a, b) -> "" + a + b);
      permutator.permutate(result -> {
         //check(result, frauen, maenner);
         //System.out.println(result);
      });
   }

   @Test
   public void testBigLAST() {
      List<String> frauen = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K");
      List<String> maenner = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0");
      AYTO_Permutator<String, String, String> permutator = AYTO_Permutator.create(frauen, maenner,
            AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER, (a, b) -> "" + a + b);
      permutator.permutate(result -> {
         //check(result, frauen, maenner);
         //System.out.println(result);
      });
   }

   List<String> frauen = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K");
   List<String> maenner = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0");

   @Test
   public void testCanAdd() {
      AYTO_Permutator<String, String, String> permutator = AYTO_Permutator.create(frauen, maenner,
            AYTO_Permutator.ZUSATZTYPE.JEDER, (a, b) -> "" + a + b);
      // UrsprungDoppelt: 1 Es fehlt: 4
      System.out.println(
            permutator.canAdd(ind("A"), ind("1"), pairs("A1", "I9", "G8", "C7", "J2", "F0", "K5", "H6", "B1", "D3")));
      System.out.println(
            permutator.canAdd(ind("A"), ind("2"), pairs("A1", "I9", "G8", "C7", "J2", "F0", "K5", "H6", "B1", "D3")));
      System.out.println(
            permutator.canAdd(ind("A"), ind("4"), pairs("A1", "I9", "G8", "C7", "J2", "F0", "K5", "H6", "B1", "D3")));
      System.out.println(
            permutator.canAdd(ind("E"), ind("1"), pairs("A1", "I9", "G8", "C7", "J2", "F0", "K5", "H6", "B1", "D3")));
      System.out.println(
            permutator.canAdd(ind("E"), ind("4"), pairs("A1", "I9", "G8", "C7", "J2", "F0", "K5", "H6", "B1", "D3")));
      //permutator.canAdd(ind("A"), ind("9"), pairs("A1", "I9", "G8", "C7", "J2", "F0", "K5", "H6", "B1", "D3"));
   }

   @Test
   public void testCanAddSmall() {
      List<String> frauen = Arrays.asList("A", "B", "C", "D");
      List<String> maenner = Arrays.asList("1", "2", "3");
      AYTO_Permutator<String, String, String> permutator = AYTO_Permutator.create(frauen, maenner,
            AYTO_Permutator.ZUSATZTYPE.JEDER, (a, b) -> "" + a + b);
      System.out.println(permutator.canAdd(ind("B"), ind("2"), pairs(false, "A1")));
      System.out.println(permutator.canAdd(ind("C"), ind("1"), pairs(false, "A1", "B2")));
      System.out.println(permutator.canAdd(ind("D"), ind("3"), pairs(true, "A1", "B2", "C1")));
      //permutator.canAdd(ind("A"), ind("9"), pairs("A1", "I9", "G8", "C7", "J2", "F0", "K5", "H6", "B1", "D3"));
   }

   private Object[] pairs(Object... strs) {
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

   private int pair(String combinded) {
      return pair("" + combinded.charAt(0), "" + combinded.charAt(1));
   }

   private int pair(String f, String m) {
      return AYTO_Permutator.encodePair(ind(f), ind(m));
   }

   private int ind(String f) {
      for (int i = 0, l = frauen.size(); i < l; i++) {
         String frau = frauen.get(i);
         if (frau.equals(f)) {
            return i;
         }
      }
      for (int i = 0, l = maenner.size(); i < l; i++) {
         String frau = maenner.get(i);
         if (frau.equals(f)) {
            return i;
         }
      }
      throw new IllegalStateException("Can not found: '" + f + "'");
   }
}
