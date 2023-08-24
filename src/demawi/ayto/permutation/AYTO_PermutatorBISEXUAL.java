package demawi.ayto.permutation;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Nur der übschüssige Kandidat, kann sich zu jemand anderem doppeln.
 */
public class AYTO_PermutatorBISEXUAL<F, M, R>
      extends AYTO_Permutator<F, M, R> {

   public AYTO_PermutatorBISEXUAL(List<F> frauen, List<M> maenner, BiFunction<F, M, R> packingFunction) {
      super(frauen, maenner, packingFunction);
   }

   /**
    * Prüft, ob ein Paar wirklich zu der bisherigen Konstellation hinzugefügt werden kann.
    * <p>
    * Nur für {@link demawi.ayto.permutation.AYTO_Permutator.ZUSATZTYPE#BISEXUAL}
    */
   protected Object[] canAdd(int frau, int mann, Object[] constellation) {
      if(frau == mann) return null;
      for (Object current : constellation) {
         int curFirst = decodeFrau((Integer) current);
         int curSecond = decodeMann((Integer) current);
         if (curFirst == frau || curSecond == mann || curFirst == mann || curSecond == frau) {
            return null;
         }
      }
      int number = encodePair(frau, mann);
      return increment(constellation, number);
   }

   protected Set<R> decodeResultPairs(Object[] constellation) {
      if (constellation.length < maxSize/2) {
         return null;
      }
      Set<R> result = new LinkedHashSet<>();
      for (Object number : constellation) {
         result.add(decodePair((Integer) number));
      }
      return result;
   }

   @Override
   protected void permutateInternImplFrau(int frau, Object[] currentConstellation,
         Supplier<Consumer<Set<R>>> pairConsumerCreator, Consumer<Set<R>> pairConsumer, int branchLevel) {
      boolean addFound = false;
      for (int mann = 0; mann < anzahlMaenner; mann++) {
         Object[] newSet = canAdd(frau, mann, currentConstellation);
         if (newSet != null) {
            addFound = true;
            Set<R> result = decodeResultPairs(newSet);
            if (result != null) {
               final Consumer<Set<R>> pairConsumerF = (pairConsumer == null ? pairConsumerCreator.get() : pairConsumer);
               pairConsumerF.accept(result);
            }
            else {
               if (branchLevel == 0) {
                  final Consumer<Set<R>> pairConsumerF = (pairConsumer == null ? pairConsumerCreator.get() : pairConsumer);
                  executorService.submit(
                        () -> permutateInternImplFrau(frau + 1, newSet, pairConsumerCreator, pairConsumerF, branchLevel - 1));
               }
               else {
                  permutateInternImplFrau(frau + 1, newSet, pairConsumerCreator, pairConsumer, branchLevel - 1);
               }
            }
         }
      }
      if (!addFound) { // for bisexual-setting. Because every person is in group A and in Group B, not every Person in A can found a match.
         if (frau + 1 < frauen.size()) {
            permutateInternImplFrau(frau + 1, currentConstellation, pairConsumerCreator, pairConsumer, branchLevel - 1);
         }
      }
   }

}