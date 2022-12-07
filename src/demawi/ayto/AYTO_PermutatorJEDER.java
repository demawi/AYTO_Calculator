package demawi.ayto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Jeder Kandidat, kann sich zu jemand anderem doppeln.
 */
public class AYTO_PermutatorJEDER<F, M, R>
      extends AYTO_Permutator<F, M, R> {

   public AYTO_PermutatorJEDER(List<F> frauen, List<M> maenner, ZUSATZTYPE zusatzType,
         BiFunction<F, M, R> packingFunction) {
      super(frauen, maenner, zusatzType, packingFunction);
   }

   /**
    * Prüft, ob ein Paar wirklich zu der bisherigen Konstellation hinzugefügt werden kann.
    * <p>
    * Nur für ZUSATZTYPE.UNKNOWN
    */
   protected Object[] canAdd(int frau, int mann, Object[] constellation) {
      boolean foundDouble = (Boolean) constellation[0];
      for (int i = 1, l = constellation.length; i < l; i++) {
         Integer current = (Integer) constellation[i];
         int decodedFrau = decodeFrau(current);
         if (decodedFrau == frau) {
            if (foundDouble || frauen.size() > minSize) {
               return null;
            }
            foundDouble = true;
         }

         int decodedMann = decodeMann(current);
         if (decodedMann == mann) {
            if (foundDouble || maenner.size() > minSize) {
               return null;
            }
            foundDouble = true;
         }
      }
      int number = encodePair(frau, mann);
      Object[] result = increment(constellation, number);
      result[0] = foundDouble;
      return result;
   }

   @Override
   public Object[] createInitialConstellation() {
      return new Object[] { false };
   }

   @Override
   protected Set<R> decodePairs(Object[] constellation) {
      if (constellation.length < maxSize + 1) {
         return null;
      }
      Set<R> result = new HashSet<>();
      for (int i = 1, l = constellation.length; i < l; i++) {
         result.add(decodePair((Integer) constellation[i]));
      }
      return result;
   }

}
