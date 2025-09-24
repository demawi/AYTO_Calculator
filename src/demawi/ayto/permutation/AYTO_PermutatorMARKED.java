package demawi.ayto.permutation;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import demawi.ayto.modell.PermutationConfiguration;
import demawi.ayto.modell.Person;

public class AYTO_PermutatorMARKED<F, M, R>
      extends AYTO_Permutator<F, M, R> {

   private final int difference;
   private final int diff;
   private final Integer maxMatches;
   private final boolean hasDefiniteExtraMatches;
   private final List<F> frauen;
   private final List<M> maenner;

   public AYTO_PermutatorMARKED(PermutationConfiguration permCfg, List<F> frauen, List<M> maenner,
         BiFunction<F, M, R> packingFunction) {
      super(frauen, maenner, packingFunction);
      this.frauen = frauen;
      this.maenner = maenner;
      diff = frauen.size() - maenner.size();
      difference = Math.abs(frauen.size() - maenner.size());
      maxMatches = permCfg.getMaxMatches();
      hasDefiniteExtraMatches = maenner.stream()
            .anyMatch(a -> ((Person) a).hasMark(Mark.IS_AN_EXTRA_MATCH)) || frauen.stream()
            .anyMatch(a -> ((Person) a).hasMark(Mark.IS_AN_EXTRA_MATCH));
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
            if (usedDoublePrevious >= difference || !hasMarkMan(mann, Mark.CAN_BE_AN_EXTRA_MATCH)) {
               return null;
            }
            usedDouble = usedDoublePrevious + 1;
         }

         int decodedMann = decodeMann(current);
         if (decodedMann == mann) { // der Mann ist bereits enthalten
            // Wenn noch kein Doppel genutzt wurde, könnte erlaubt sein, wenn die Frau eine Zusatzfrau ist
            if (usedDoublePrevious >= difference || !hasMarkWoman(frau, Mark.CAN_BE_AN_EXTRA_MATCH)) {
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
      if (hasDefiniteExtraMatches && diff != 0 && isLastOne(constellation)) {
         if (diff < 0) { // Frauen in Unterzahl
            if (IntStream.range(0, maenner.size())
                  .filter(idx -> ((Person) maenner.get(idx)).hasMark(Mark.IS_AN_EXTRA_MATCH))
                  .anyMatch(idx -> {
                     Integer seineFrau = -1;
                     if (idx == mann) {
                        seineFrau = frau;
                     }
                     else {
                        for (int i = 1, l = constellation.length; i < l; i++) {
                           Integer current = (Integer) constellation[i];
                           if (decodeMann(current) == idx) {
                              seineFrau = decodeFrau(current);
                              break;
                           }
                        }
                     }
                     return countFrau(seineFrau, constellation) + (seineFrau == frau ? 1 : 0) < 2;
                  })) {
               return null;
            }
         }
         else {
            if (IntStream.range(0, frauen.size())
                  .filter(idx -> ((Person) frauen.get(idx)).hasMark(Mark.IS_AN_EXTRA_MATCH))
                  .anyMatch(idx -> {
                     Integer ihrMann = -1;
                     if (idx == frau) {
                        ihrMann = mann;
                     }
                     else {
                        for (int i = 1, l = constellation.length; i < l; i++) {
                           Integer current = (Integer) constellation[i];
                           if (decodeFrau(current) == idx) {
                              ihrMann = decodeMann(current);
                              break;
                           }
                        }
                     }
                     return countMann(ihrMann, constellation) + (ihrMann == mann ? 1 : 0) < 2;
                  })) {
               return null;
            }
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
