package demawi.ayto.permutation;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import demawi.ayto.modell.PermutationConfiguration;

public class AYTO_PermutatorMARKED<F, M, R>
      extends AYTO_Permutator<F, M, R> {

   private final int difference;
   private final int diff;
   private final Integer maxMatches;

   public AYTO_PermutatorMARKED(PermutationConfiguration permCfg, List<F> frauen, List<M> maenner,
         BiFunction<F, M, R> packingFunction) {
      super(frauen, maenner, packingFunction);
      diff = frauen.size() - maenner.size();
      difference = Math.abs(frauen.size() - maenner.size());
      maxMatches = permCfg.getMaxMatches();
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
         if (maxMatches != null && diff != 0) {
            if (diff < 0) {
               if (countFrau(decodedFrau, constellation) > maxMatches) {
                  return null;
               }
            }
            else {
               if (countMann(decodedMann, constellation) > maxMatches) {
                  return null;
               }
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
      for (Object cur : constellation) {
         if (decodeFrau((Integer) cur) == frau) {
            result++;
         }
      }
      return result;
   }

   private int countMann(int frau, Object[] constellation) {
      int result = 0;
      for (Object cur : constellation) {
         if (decodeMann((Integer) cur) == frau) {
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
