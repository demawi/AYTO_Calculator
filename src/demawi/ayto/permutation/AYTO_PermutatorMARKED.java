package demawi.ayto.permutation;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import demawi.ayto.modell.PermutationConfiguration;
import demawi.ayto.modell.Person;

public class AYTO_PermutatorMARKED<F, M, R>
      extends AYTO_Permutator<F, M, R> {

   private final int difference;
   private final int duplicateCapacity;
   private final int diff;
   private final Integer maxMatches;
   private final boolean hasDefiniteExtraMatches;
   private final List<F> frauen;
   private final List<M> maenner;
   private final int[] definiteExtraMatchMen;
   private final int[] definiteExtraMatchWomen;
   private final boolean useDualDuplicateSearch;

   public AYTO_PermutatorMARKED(PermutationConfiguration permCfg, List<F> frauen, List<M> maenner,
         BiFunction<F, M, R> packingFunction) {
      super(frauen, maenner, packingFunction, permCfg.getParallelism());
      this.frauen = frauen;
      this.maenner = maenner;
      diff = frauen.size() - maenner.size();
      difference = Math.abs(frauen.size() - maenner.size());
      maxMatches = permCfg.getMaxMatches();
      hasDefiniteExtraMatches = maenner.stream()
            .anyMatch(a -> ((Person) a).hasMark(Mark.IS_AN_EXTRA_MATCH)) || frauen.stream()
            .anyMatch(a -> ((Person) a).hasMark(Mark.IS_AN_EXTRA_MATCH));
      definiteExtraMatchMen = indicesOfDefiniteExtraMatches(maenner);
      definiteExtraMatchWomen = indicesOfDefiniteExtraMatches(frauen);
      duplicateCapacity = difference == 0 && hasDefiniteExtraMatches ? 1 : difference;
      useDualDuplicateSearch = difference == 0 && hasDefiniteExtraMatches;
   }

   /**
    * Bei gleich großen Gruppen kann die Staffel während der Laufzeit auf beiden
    * Seiten eine Person doppelt enthalten. Die normale Rekursion iteriert immer
    * über eine Seite eindeutig und kann deshalb nur eine Duplikatrichtung
    * darstellen. Die duale Suche bildet stattdessen vollständige Paarmengen in
    * kanonischer Reihenfolge ab; dadurch wird jede Konstellation genau einmal
    * erzeugt.
    */
   @Override
   public void permutate(Supplier<Consumer<Set<R>>> pairConsumerCreator) {
      if (!useDualDuplicateSearch) {
         super.permutate(pairConsumerCreator);
         return;
      }

      Object[] current = createInitialConstellation();
      permutateDual(current, -1, pairConsumerCreator, null, BRANCH_LEVEL);
      executorService.shutdown();
      try {
         while (!executorService.awaitTermination(10, java.util.concurrent.TimeUnit.MINUTES)) {
            // Keep waiting for submitted branches.
         }
      }
      catch (InterruptedException e) {
         executorService.shutdownNow();
         Thread.currentThread().interrupt();
         throw new RuntimeException(e);
      }
   }

   private static final int BRANCH_LEVEL = 0;

   private void permutateDual(Object[] constellation, int lastPair,
         Supplier<Consumer<Set<R>>> pairConsumerCreator, Consumer<Set<R>> pairConsumer, int branchLevel) {
      int requiredLength = requiredDualConstellationLength(constellation);
      if (constellation.length == requiredLength) {
         if (!containsEveryParticipant(constellation) || !hasRequiredDefiniteExtraMatches(constellation)) {
            return;
         }
         Set<R> result = decodeResultPairs(constellation);
         if (result != null) {
            (pairConsumer == null ? pairConsumerCreator.get() : pairConsumer).accept(result);
         }
         return;
      }
      if (!canStillComplete(constellation, requiredLength)) {
         return;
      }

      Consumer<Set<R>> currentConsumer = pairConsumer;
      for (int pairIndex = lastPair + 1; pairIndex < frauen.size() * maenner.size(); pairIndex++) {
         int frau = pairIndex / maenner.size();
         int mann = pairIndex % maenner.size();
         Object[] next = canAddDual(frau, mann, constellation);
         if (next == null) {
            continue;
         }

         if (branchLevel == 0) {
            Consumer<Set<R>> branchConsumer = currentConsumer == null
                  ? pairConsumerCreator.get()
                  : currentConsumer;
            final int nextPairIndex = pairIndex;
            executorService.submit(() -> permutateDual(next, nextPairIndex, pairConsumerCreator,
                  branchConsumer, branchLevel - 1));
         }
         else {
            if (currentConsumer == null) {
               currentConsumer = pairConsumerCreator.get();
            }
            permutateDual(next, pairIndex, pairConsumerCreator, currentConsumer, branchLevel - 1);
         }
      }
   }

   private boolean canStillComplete(Object[] constellation, int requiredLength) {
         int remainingPairs = requiredLength - constellation.length;
         boolean[] womenPresent = new boolean[frauen.size()];
         boolean[] menPresent = new boolean[maenner.size()];
         for (int i = 1; i < constellation.length; i++) {
            int pair = (Integer) constellation[i];
            womenPresent[decodeFrau(pair)] = true;
            menPresent[decodeMann(pair)] = true;
         }

         int missingWomen = 0;
         for (boolean present : womenPresent) {
            if (!present) {
               missingWomen++;
            }
         }
         int missingMen = 0;
         for (boolean present : menPresent) {
            if (!present) {
               missingMen++;
            }
         }
         if (missingWomen > remainingPairs || missingMen > remainingPairs) {
            return false;
         }

         int missingDefiniteMen = 0;
         for (int idx : definiteExtraMatchMen) {
            if (!menPresent[idx]) {
               missingDefiniteMen++;
            }
         }
         int missingDefiniteWomen = 0;
         for (int idx : definiteExtraMatchWomen) {
            if (!womenPresent[idx]) {
               missingDefiniteWomen++;
            }
         }
         return missingDefiniteMen <= remainingPairs && missingDefiniteWomen <= remainingPairs;
   }
 
   private int requiredDualConstellationLength(Object[] constellation) {
      int state = (Integer) constellation[0];
      int usedDoubleWomen = state & 0xffff;
      int usedDoubleMen = state >>> 16;
      return maxSize + Math.max(usedDoubleWomen, usedDoubleMen) + 1;
   }

   private boolean containsEveryParticipant(Object[] constellation) {
      boolean[] womenPresent = new boolean[frauen.size()];
      boolean[] menPresent = new boolean[maenner.size()];
      for (int i = 1; i < constellation.length; i++) {
         int pair = (Integer) constellation[i];
         womenPresent[decodeFrau(pair)] = true;
         menPresent[decodeMann(pair)] = true;
      }
      for (boolean present : womenPresent) {
         if (!present) {
            return false;
         }
      }
      for (boolean present : menPresent) {
         if (!present) {
            return false;
         }
      }
      return true;
   }

   private Object[] canAddDual(int frau, int mann, Object[] constellation) {
      int state = (Integer) constellation[0];
      int usedDoubleWomen = state & 0xffff;
      int usedDoubleMen = state >>> 16;

      for (int i = 1; i < constellation.length; i++) {
         int current = (Integer) constellation[i];
         if (decodeFrau(current) == frau) {
            if (usedDoubleWomen >= 1 || !canBeExtraMatchMan(mann)) {
               return null;
            }
            usedDoubleWomen++;
         }
         if (decodeMann(current) == mann) {
            if (usedDoubleMen >= 1 || !canBeExtraMatchWoman(frau)) {
               return null;
            }
            usedDoubleMen++;
         }
      }

      if (maxMatches != null
            && (countFrau(frau, constellation) >= maxMatches
                  || countMann(mann, constellation) >= maxMatches)) {
         return null;
      }

      Object[] result = increment(constellation, encodePair(frau, mann));
      result[0] = usedDoubleWomen | (usedDoubleMen << 16);
      return result;
   }

   private boolean hasRequiredDefiniteExtraMatches(Object[] constellation) {
      for (int idx : definiteExtraMatchMen) {
         if (countMann(idx, constellation) != 1) {
            return false;
         }
         for (int partnerWoman : partnersOfMan(idx, constellation)) {
            if (countFrau(partnerWoman, constellation) < 2) {
               return false;
            }
         }
      }
      for (int idx : definiteExtraMatchWomen) {
         if (countFrau(idx, constellation) != 1) {
            return false;
         }
         for (int partnerMan : partnersOfWoman(idx, constellation)) {
            if (countMann(partnerMan, constellation) < 2) {
               return false;
            }
         }
      }
      return true;
   }

   private Set<Integer> partnersOfMan(int mann, Object[] constellation) {
      Set<Integer> partners = new LinkedHashSet<>();
      for (int i = 1; i < constellation.length; i++) {
         int pair = (Integer) constellation[i];
         if (decodeMann(pair) == mann) {
            partners.add(decodeFrau(pair));
         }
      }
      return partners;
   }

   private Set<Integer> partnersOfWoman(int frau, Object[] constellation) {
      Set<Integer> partners = new LinkedHashSet<>();
      for (int i = 1; i < constellation.length; i++) {
         int pair = (Integer) constellation[i];
         if (decodeFrau(pair) == frau) {
            partners.add(decodeMann(pair));
         }
      }
      return partners;
   }

   @Override
   public long estimateConstellations() {
      if (!useDualDuplicateSearch || frauen.size() != maenner.size()) {
         return -1;
      }
      long n = frauen.size();
      if (n < 2) {
         return 0;
      }

      if (definiteExtraMatchMen.length == 1 && definiteExtraMatchWomen.length == 0) {
         return estimateWithOneDefiniteExtraMatch(n);
      }
      if (definiteExtraMatchMen.length == 0 && definiteExtraMatchWomen.length == 1) {
         return estimateWithOneDefiniteExtraMatch(n);
      }

      long factorial = factorial(n - 2);
      long choicesWithSharedPair = saturatedMultiply(n - 1, n - 1);
      long choicesWithoutSharedPair = binomial(n - 1, 2);
      choicesWithoutSharedPair = saturatedMultiply(choicesWithoutSharedPair, choicesWithoutSharedPair);
      long pairings = saturatedMultiply(factorial,
            saturatedAdd(choicesWithSharedPair, choicesWithoutSharedPair));
      // Choose the duplicated woman and man independently, then count all
      // simple bipartite graphs with exactly one duplicate on each side.
      return saturatedMultiply(saturatedMultiply(n, n), pairings);
   }

   @Override
   public boolean hasExactConstellationEstimate() {
      if (!useDualDuplicateSearch || frauen.size() != maenner.size()
            || (definiteExtraMatchMen.length == 1) == (definiteExtraMatchWomen.length == 1)) {
         return false;
      }
      for (int i = 0; i < maenner.size(); i++) {
         if ((definiteExtraMatchMen.length == 0 || i != definiteExtraMatchMen[0])
               && !canBeExtraMatchMan(i)) {
            return false;
         }
      }
      for (int i = 0; i < frauen.size(); i++) {
         if ((definiteExtraMatchWomen.length == 0 || i != definiteExtraMatchWomen[0])
               && !canBeExtraMatchWoman(i)) {
            return false;
         }
      }
      return true;
   }

   /**
    * Counts the terminal constellations when exactly one person is a definite
    * extra match. That person must occur once, while its partner is the one
    * duplicated person on the other side.
    *
    * For every duplicated person there are {@code n - 1} choices for the
    * duplicated person on the other side. After fixing the definite pair,
    * the remaining assignments contain one column with degree two and all
    * other columns with degree one.
    */
   private long estimateWithOneDefiniteExtraMatch(long n) {
      long result = saturatedMultiply(n, n);
      result = saturatedMultiply(result, n - 1);
      result = saturatedMultiply(result, n - 1);
      result = saturatedMultiply(result, factorial(n - 2));
      return result / 2;
   }

   private static long factorial(long n) {
      long result = 1;
      for (long i = 2; i <= n; i++) {
         result = saturatedMultiply(result, i);
      }
      return result;
   }

   private static long binomial(long n, long k) {
      if (k < 0 || k > n) {
         return 0;
      }
      k = Math.min(k, n - k);
      long result = 1;
      for (long i = 1; i <= k; i++) {
         result = saturatedMultiply(result, n - k + i);
         result /= i;
      }
      return result;
   }

   private static long saturatedAdd(long left, long right) {
      if (left > Long.MAX_VALUE - right) {
         return Long.MAX_VALUE;
      }
      return left + right;
   }

   private static long saturatedMultiply(long left, long right) {
      if (left == 0 || right == 0 || left > Long.MAX_VALUE / right) {
         return Long.MAX_VALUE;
      }
      return left * right;
   }

   private static <T> int[] indicesOfDefiniteExtraMatches(List<T> people) {
      int count = 0;
      for (T person : people) {
         if (((Person) person).hasMark(Mark.IS_AN_EXTRA_MATCH)) {
            count++;
         }
      }
      int[] result = new int[count];
      int index = 0;
      for (int i = 0; i < people.size(); i++) {
         if (((Person) people.get(i)).hasMark(Mark.IS_AN_EXTRA_MATCH)) {
            result[index++] = i;
         }
      }
      return result;
   }

   /**
    * Prüft, ob ein Paar wirklich zu der bisherigen Konstellation hinzugefügt werden kann.
    * <p>
    * Nur für ZUSATZTYPE.MARKED
    */
   protected Object[] canAdd(int frau, int mann, Object[] constellation) {
      int usedDoublePrevious = (Integer) constellation[0];
      int usedDouble = usedDoublePrevious;
      for (int i = 1, l = constellation.length; i < l; i++) {
         Integer current = (Integer) constellation[i];
         int decodedFrau = decodeFrau(current);

         if (decodedFrau == frau) { // die Frau ist bereits enthalten
            // Wenn noch kein Doppel genutzt wurde, könnte erlaubt sein, wenn der Mann ein Zusatzmann ist
            if (usedDoublePrevious >= duplicateCapacity || !canBeExtraMatchMan(mann)) {
               return null;
            }
            usedDouble = usedDoublePrevious + 1;
         }

         int decodedMann = decodeMann(current);
         if (decodedMann == mann) { // der Mann ist bereits enthalten
            // Wenn noch kein Doppel genutzt wurde, könnte erlaubt sein, wenn die Frau eine Zusatzfrau ist
            if (usedDoublePrevious >= duplicateCapacity || !canBeExtraMatchWoman(frau)) {
               return null;
            }
            usedDouble = usedDoublePrevious + 1;
         }
         // MaxMatch-Filter
         if (maxMatches != null && diff != 0) {
            if (diff < 0) {
               if (countFrau(decodedFrau, constellation) + (decodedFrau == frau ? 1 : 0) > maxMatches) {
                  return null;
               }
            }
            else {
               if (countMann(decodedMann, constellation) + (decodedMann == mann ? 1 : 0) > maxMatches) {
                  return null;
               }
            }
         }
      }
      // IS_AN_EXTRA_MATCH überprüfen
      if (hasDefiniteExtraMatches && isLastOne(constellation)) {
         if (diff < 0) { // Frauen in Unterzahl
            if (hasDefiniteExtraMatchOnMen(frau, mann, constellation)) {
               return null;
            }
         }
         else if (diff > 0) {
            if (hasDefiniteExtraMatchOnWomen(frau, mann, constellation)) {
               return null;
            }
         }
         else if (hasDefiniteExtraMatchOnMen(frau, mann, constellation)
               || hasDefiniteExtraMatchOnWomen(frau, mann, constellation)) {
            return null;
         }
      }
      int number = encodePair(frau, mann);
      Object[] result = increment(constellation, number);
      result[0] = usedDouble;
      return result;
   }

   private int countFrau(int frau, Object[] constellation) {
      int result = 0;
      for (int i = 1, l = constellation.length; i < l; i++) {
         Integer current = (Integer) constellation[i];
         if (decodeFrau(current) == frau) {
            result++;
         }
      }
      return result;
   }

   private int countMann(int frau, Object[] constellation) {
      int result = 0;
      for (int i = 1, l = constellation.length; i < l; i++) {
         Integer current = (Integer) constellation[i];
         if (decodeMann(current) == frau) {
            result++;
         }
      }
      return result;
   }

   private boolean canBeExtraMatchMan(int mann) {
      return hasMarkMan(mann, Mark.CAN_BE_AN_EXTRA_MATCH)
            || hasMarkMan(mann, Mark.IS_AN_EXTRA_MATCH);
   }

   private boolean canBeExtraMatchWoman(int frau) {
      return hasMarkWoman(frau, Mark.CAN_BE_AN_EXTRA_MATCH)
            || hasMarkWoman(frau, Mark.IS_AN_EXTRA_MATCH);
   }

   private boolean hasDefiniteExtraMatchOnMen(int frau, int mann, Object[] constellation) {
      for (int idx : definiteExtraMatchMen) {
         if (countFrau(getPartnerWoman(idx, frau, mann, constellation), constellation)
               + (idx == mann ? 1 : 0) < 2) {
            return true;
         }
      }
      return false;
   }

   private boolean hasDefiniteExtraMatchOnWomen(int frau, int mann, Object[] constellation) {
      for (int idx : definiteExtraMatchWomen) {
         if (countMann(getPartnerMan(idx, frau, mann, constellation), constellation)
               + (idx == frau ? 1 : 0) < 2) {
            return true;
         }
      }
      return false;
   }

   private int getPartnerWoman(int mann, int currentFrau, int currentMann, Object[] constellation) {
      if (mann == currentMann) {
         return currentFrau;
      }
      for (int i = 1, l = constellation.length; i < l; i++) {
         Integer current = (Integer) constellation[i];
         if (decodeMann(current) == mann) {
            return decodeFrau(current);
         }
      }
      return -1;
   }

   private int getPartnerMan(int frau, int currentFrau, int currentMann, Object[] constellation) {
      if (frau == currentFrau) {
         return currentMann;
      }
      for (int i = 1, l = constellation.length; i < l; i++) {
         Integer current = (Integer) constellation[i];
         if (decodeFrau(current) == frau) {
            return decodeMann(current);
         }
      }
      return -1;
   }

   /**
    * Die erste Stelle wird zur Algorithmus-Optimierung mit einem Integer belegt, wie viel doppelte Personen bereits eingetragen sind.
    */
   @Override
   public Object[] createInitialConstellation() {
      return new Object[] { 0 };
   }

   protected boolean isLastOne(Object[] constellation) {
      return constellation.length == maxSize;
   }

   @Override
   protected Set<R> decodeResultPairs(Object[] constellation) {
      if (constellation.length < maxSize + 1) {
         return null;
      }
      return decodeResultPairsIntern(constellation);
   }

   protected Set<R> decodeResultPairsIntern(Object[] constellation) {
      Set<R> result = new LinkedHashSet<>();
      for (int i = 1, l = constellation.length; i < l; i++) {
         result.add(decodePair((Integer) constellation[i]));
      }
      return result;
   }

}
