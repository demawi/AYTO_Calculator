package demawi.ayto.permutation;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Markierung;
import demawi.ayto.modell.Person;
import demawi.ayto.print.Formatter;
import demawi.ayto.util.Pair;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AYTO_PermutatorTest {

   private AtomicInteger atomicCount = new AtomicInteger(0);

   /**
    * Kleiner 4:3 Test mit kompletter Ausgabe
    */
   @Test
   public void testSmall() {
      int frauenAnzahl = 12;
      int maennerAnzahl = 12;
      boolean withFullOutput = frauenAnzahl + maennerAnzahl < 10;
      boolean withFullCheck = true;
      long start = System.currentTimeMillis();
      AYTO_Permutator<Person, Person, Pair> permutator = AYTO_Permutator.create(
            markAll(frauen(frauenAnzahl), frauenAnzahl > maennerAnzahl),
            markAll(maenner(maennerAnzahl), maennerAnzahl > frauenAnzahl), AYTO_Permutator.ZUSATZTYPE.MARKED,
            Pair::pair);
      atomicCount.set(0);
      permutator.permutate(() -> result -> {
         atomicCount.incrementAndGet();
         if (withFullCheck) {
            check(result, frauen(frauenAnzahl), maenner(maennerAnzahl));
         }
         if (withFullOutput) {
            System.out.println(result);
         }
      });
      System.out.println(
            "Found " + atomicCount + " constellations in " + Formatter.minSecs(System.currentTimeMillis() - start));
      start = System.currentTimeMillis();
      AYTO_Permutator<Person, Person, Pair> permutator2 = AYTO_Permutator.create(
            markLast(frauen(frauenAnzahl), frauenAnzahl > maennerAnzahl),
            markLast(maenner(maennerAnzahl), maennerAnzahl > frauenAnzahl), AYTO_Permutator.ZUSATZTYPE.MARKED,
            Pair::pair);
      atomicCount.set(0);
      permutator2.permutate(() -> result -> {
         atomicCount.incrementAndGet();
         if (withFullCheck) {
            check(result, frauen(frauenAnzahl), maenner(maennerAnzahl));
         }
         if (withFullOutput) {
            System.out.println(result);
         }
      });
      System.out.println("Found " + atomicCount + " constellations in " + Formatter.minSecs(System.currentTimeMillis() - start));
   }

   @Test
   public void testBigJEDER() {
      AYTO_Permutator<Person, Person, String> permutator = AYTO_Permutator.create(markAll(frauen(11)), maenner(10),
            AYTO_Permutator.ZUSATZTYPE.MARKED, (a, b) -> "" + a + b);
      long start = System.currentTimeMillis();
      atomicCount.set(0);
      permutator.permutate(() -> result -> {
         atomicCount.incrementAndGet();
      });
      System.out.println(
            "JEDER 11:10 PERMUTATION Taken time: " + Formatter.minSecs(System.currentTimeMillis() - start));
      System.out.println("Anzahl erzeugter Permutationen: " + atomicCount);
   }

   @Test
   public void testBigNUR_LETZTER() {
      AYTO_Permutator<Person, Person, String> permutator = AYTO_Permutator.create(markLast(frauen(11)), maenner(10),
            AYTO_Permutator.ZUSATZTYPE.MARKED, (a, b) -> "" + a + b);
      long start = System.currentTimeMillis();
      atomicCount.set(0);
      permutator.permutate(() -> result -> {
         atomicCount.incrementAndGet();
      });
      System.out.println(
            "NUR_LETZTER 11:10 PERMUTATION Taken time: " + Formatter.minSecs(System.currentTimeMillis() - start));
      System.out.println("Anzahl erzeugter Permutationen: " + atomicCount);
   }

   @Test
   public void testCanAddBigJEDER() {
      AYTO_Permutator<Person, Person, String> permutator = AYTO_Permutator.create(markAll(frauen(11)), maenner(10),
            AYTO_Permutator.ZUSATZTYPE.MARKED, (a, b) -> "" + a + b);
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
   public void testCanAddSmallALLE() {
      Frau frau1 = new Frau("A");
      Frau frau2 = new Frau("B");
      Frau frau3 = new Frau("C");
      Frau frau4 = new Frau("D");
      Frau mann1 = new Frau("1");
      Frau mann2 = new Frau("2");
      Frau mann3 = new Frau("3");
      List<Person> frauen = markAll(Arrays.asList(frau1, frau2, frau3, frau4));
      List<Person> maenner = Arrays.asList(mann1, mann2, mann3);
      AYTO_Permutator<Person, Person, Pair> permutator = AYTO_Permutator.create(markAll(frauen), maenner,
            AYTO_Permutator.ZUSATZTYPE.MARKED, Pair::pair);
      assertNotNull(permutator.canAdd(ind("B"), ind("2"), pairs(false, "A1")));
      assertNotNull(permutator.canAdd(ind("C"), ind("1"), pairs(false, "A1", "B2")));
      assertNotNull(permutator.canAdd(ind("D"), ind("3"), pairs(true, "A1", "B2", "C1")));
      assertNull(permutator.canAdd(ind("D"), ind("2"), pairs(true, "A1", "B2", "C1")));
   }

   public List<Person> markAll(List<Person> persons) {
      persons.forEach(p -> p.mark(Markierung.CAN_BE_AN_EXTRA_MATCH));
      return persons;
   }

   public List<Person> markAll(List<Person> persons, boolean value) {
      if (value) {
         persons.forEach(p -> p.mark(Markierung.CAN_BE_AN_EXTRA_MATCH));
      }
      return persons;
   }

   public List<Person> markLast(List<Person> persons) {
      persons.get(persons.size() - 1)
            .mark(Markierung.CAN_BE_AN_EXTRA_MATCH);
      return persons;
   }

   public List<Person> markLast(List<Person> persons, boolean value) {
      if (value) {
         persons.get(persons.size() - 1)
               .mark(Markierung.CAN_BE_AN_EXTRA_MATCH);
      }
      return persons;
   }

   @Test
   public void testCanAddSmallMARKED() {
      Frau frau1 = new Frau("A");
      Frau frau2 = new Frau("B");
      Frau frau3 = new Frau("C");
      Frau frau4 = new Frau("D");
      Frau mann1 = new Frau("1");
      Frau mann2 = new Frau("2");
      Frau mann3 = new Frau("3");
      frau4.mark(Markierung.CAN_BE_AN_EXTRA_MATCH);

      List<Person> frauen = Arrays.asList(frau1, frau2, frau3, frau4);
      List<Person> maenner = Arrays.asList(mann1, mann2, mann3);
      AYTO_Permutator<Person, Person, String> permutator = AYTO_Permutator.create(frauen, maenner,
            AYTO_Permutator.ZUSATZTYPE.MARKED, (a, b) -> "" + a + b);
      assertNotNull(permutator.canAdd(ind("B"), ind("3"), pairs(false, "A1")));
      assertNull(permutator.canAdd(ind("C"), ind("3"), pairs(false, "A1", "B3")));
      assertNotNull(permutator.canAdd(ind("D"), ind("1"), pairs(false, "A1", "B2")));

      frau3.mark(Markierung.CAN_BE_AN_EXTRA_MATCH);
      permutator = AYTO_Permutator.create(frauen, maenner, AYTO_Permutator.ZUSATZTYPE.MARKED, (a, b) -> "" + a + b);
      assertNotNull(permutator.canAdd(ind("B"), ind("3"), pairs(false, "A1")));
      assertNotNull(permutator.canAdd(ind("C"), ind("3"), pairs(false, "A1", "B3")));
      assertNotNull(permutator.canAdd(ind("D"), ind("1"), pairs(false, "A1", "B2")));
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

   private static final List<Person> allFrauen = Stream.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
               "M", "N")
         .map(Person::new)
         .collect(Collectors.toList());

   private static List<Person> frauen(int anzahl) {
      return allFrauen.subList(0, anzahl)
            .stream()
            .map(a -> new Person(a.getName()))
            .collect(Collectors.toList());
   }

   private static final List<Person> allMaenner = Stream.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "X", "Y",
               "Z")
         .map(Person::new)
         .collect(Collectors.toList());

   private static List<Person> maenner(int anzahl) {
      return allMaenner.subList(0, anzahl)
            .stream()
            .map(a -> new Person(a.getName()))
            .collect(Collectors.toList());
   }

   private static int pair(String combinded) {
      return pair("" + combinded.charAt(0), "" + combinded.charAt(1));
   }

   private static int pair(String f, String m) {
      return AYTO_Permutator.encodePair(ind(f), ind(m));
   }

   private static int ind(String f) {
      for (int i = 0, l = allFrauen.size(); i < l; i++) {
         Person frau = allFrauen.get(i);
         if (frau.getName()
               .equals(f)) {
            return i;
         }
      }
      for (int i = 0, l = allMaenner.size(); i < l; i++) {
         Person frau = allMaenner.get(i);
         if (frau.getName()
               .equals(f)) {
            return i;
         }
      }
      throw new IllegalStateException("Can not found: '" + f + "'");
   }

   private void check(Set<Pair> result, List<Person> frauen, List<Person> maenner) {
      for (Person frau : frauen) {
         if (result.stream()
               .filter(r -> r.getFirst()
                     .equals(frau))
               .count() == 0) {
            System.out.println(
                  "Frau '" + frau + "' ist nicht vorhanden im Resultat: " + Arrays.deepToString(result.toArray()));
            throw new RuntimeException(
                  "Frau '" + frau + "' ist nicht vorhanden im Resultat: " + Arrays.deepToString(result.toArray()));
         }
      }
      for (Person mann : maenner) {
         if (result.stream()
               .filter(r -> r.getSecond()
                     .equals(mann))
               .count() == 0) {
            System.out.println(
                  "Mann '" + mann + "' ist nicht vorhanden im Resultat: " + Arrays.deepToString(result.toArray()));
            throw new RuntimeException(
                  "Mann '" + mann + "' ist nicht vorhanden im Resultat: " + Arrays.deepToString(result.toArray()));
         }
      }
   }
}
