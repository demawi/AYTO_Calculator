package demawi.ayto.permutation;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Nur der übschüssige Kandidat, kann sich zu jemand anderem doppeln.
 */
public class AYTO_PermutatorKEINER<F, M, R>
      extends AYTO_Permutator<F, M, R> {

   public AYTO_PermutatorKEINER(List<F> frauen, List<M> maenner, BiFunction<F, M, R> packingFunction) {
      super(frauen, maenner, packingFunction);
   }

   /**
    * Prüft, ob ein Paar wirklich zu der bisherigen Konstellation hinzugefügt werden kann.
    * <p>
    * Nur für ZUSATZTYPE.KEINER
    */
   protected Object[] canAdd(int frau, int mann, Object[] constellation) {
      for (Object current : constellation) {
         if (decodeFrau((Integer) current) == frau || decodeMann((Integer) current) == mann) {
            return null;
         }
      }
      int number = encodePair(frau, mann);
      return increment(constellation, number);
   }

}
