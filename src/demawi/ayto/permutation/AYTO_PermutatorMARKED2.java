package demawi.ayto.permutation;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class AYTO_PermutatorMARKED2<F, M, R>
      extends AYTO_Permutator<F, M, R> {

   private final int difference;

   public AYTO_PermutatorMARKED2(List<F> frauen, List<M> maenner, BiFunction<F, M, R> packingFunction) {
      super(frauen, maenner, packingFunction);
      difference = Math.abs(frauen.size() - maenner.size());
   }

   /**
    * Prüft, ob ein Paar wirklich zu der bisherigen Konstellation hinzugefügt werden kann.
    * <p>
    * Nur für ZUSATZTYPE.MARKED2
    * Special permutation: Mark.IS_AN_EXTRA_MATCH has to be an extra match and only one person from the other side has all of the extra matches.
    */
   protected Object[] canAdd(int frau, int mann, Object[] constellation) {
      int usedDoublePrevious = (Integer) constellation[0];
      int usedDouble = usedDoublePrevious;
      for (int i = 1, l = constellation.length; i < l; i++) {
         Integer current = (Integer) constellation[i];
         int decodedFrau = decodeFrau(current);
         if (decodedFrau == frau) { // die Frau ist bereits enthalten
            // Wenn noch kein Doppel genutzt wurde, könnte erlaubt sein, wenn der Mann ein Zusatzmann ist
            if (usedDoublePrevious >= difference) {
               return null;
            }
            else if (!hasMarkMan(mann, Mark.IS_A_THIRD_MATCH) && usedDoublePrevious >= difference - 1) {
               return null;
            }
            else if (hasMarkMan(mann, Mark.IS_A_THIRD_MATCH) && countFrau(frau, constellation) != difference) {
               return null;
            }
            else if (!hasMarkMan(mann, Mark.CAN_BE_AN_EXTRA_MATCH) && usedDoublePrevious >= difference - 2) {
               return null;
            }
            usedDouble = usedDoublePrevious + 1;
         }

         int decodedMann = decodeMann(current);
         if (decodedMann == mann) { // der Mann ist bereits enthalten
            // Wenn noch kein Doppel genutzt wurde, könnte erlaubt sein, wenn die Frau eine Zusatzfrau ist
            if (usedDoublePrevious >= difference) {
               return null;
            }
            else if (!hasMarkWoman(frau, Mark.IS_A_THIRD_MATCH) && usedDoublePrevious >= difference - 1) {
               return null;
            }
            else if (hasMarkWoman(frau, Mark.IS_A_THIRD_MATCH) && countMann(mann, constellation) != difference) {
               return null;
            }
            else if (!hasMarkWoman(frau, Mark.CAN_BE_AN_EXTRA_MATCH) && usedDoublePrevious >= difference - 2) {
               return null;
            }
            usedDouble = usedDoublePrevious + 1;
         }
      }
      int number = encodePair(frau, mann);
      Object[] result = increment(constellation, number);
      result[0] = usedDouble;
      return result;
   }

   public int countFrau(int frau, Object[] constellation) {
      int result = 0;
      for (int i = 1, l = constellation.length; i < l; i++) {
         Integer current = (Integer) constellation[i];
         int foundFrau = decodeFrau(current);
         if (frau == foundFrau) {
            result++;
         }
      }
      return result;
   }

   public int countMann(int mann, Object[] constellation) {
      int result = 0;
      for (int i = 1, l = constellation.length; i < l; i++) {
         Integer current = (Integer) constellation[i];
         int foundMann = decodeMann(current);
         if (mann == foundMann) {
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
      Set<R> result = new LinkedHashSet<>();
      for (int i = 1, l = constellation.length; i < l; i++) {
         result.add(decodePair((Integer) constellation[i]));
      }
      return result;
   }

}
