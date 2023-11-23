package demawi.ayto.permutation;

import java.util.List;
import java.util.function.BiFunction;

public class AYTO_PermutatorSTANDARD<F, M, R>
      extends AYTO_Permutator<F, M, R> {

   public AYTO_PermutatorSTANDARD(List<F> frauen, List<M> maenner, BiFunction<F, M, R> packingFunction) {
      super(frauen, maenner, packingFunction);
   }

   /**
    * Prüft, ob ein Paar wirklich zu der bisherigen Konstellation hinzugefügt werden kann.
    * <p>
    * Nur für {@link ZUSATZTYPE:NONE}
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
