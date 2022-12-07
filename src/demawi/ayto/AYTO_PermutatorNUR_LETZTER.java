package demawi.ayto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Nur der übschüssige Kandidat, kann sich zu jemand anderem doppeln.
 */
public class AYTO_PermutatorNUR_LETZTER<F, M, R>
      extends AYTO_Permutator<F, M, R> {

   public AYTO_PermutatorNUR_LETZTER(List<F> frauen, List<M> maenner, ZUSATZTYPE zusatzType,
         BiFunction<F, M, R> packingFunction) {
      super(frauen, maenner, zusatzType, packingFunction);
   }

   // these will be reused
   private Set<Integer> addedFrauen = new HashSet<>();
   private Set<Integer> addedMaenner = new HashSet<>();

   /**
    * Prüft, ob ein Paar wirklich zu der bisherigen Konstellation hinzugefügt werden kann.
    * <p>
    * Nur für ZUSATZTYPE.LAST
    */
   protected Object[] canAdd(int frau, int mann, Object[] constellation) {
      addedFrauen.clear();
      addedMaenner.clear();
      for (Object current : constellation) {
         Integer decodedFrau = decodeFrau((Integer) current);
         if (decodedFrau == frau) {
            if (mann < minSize) {
               return null;
            }
            // die Frau ist bereits enthalten
            // Könnte erlaubt sein, wenn der Mann ein Zusatzmann ist
            if (addedMaenner.contains(mann)) { // aber auch nur einmal
               return null;
            }
            addedMaenner.add(mann);
         }
         if (decodeMann((Integer) current) == mann) {
            if (decodedFrau == frau) {
               return null;
            }
            if (frau < minSize) {
               return null;
            }
            // der Mann ist bereits enthalten
            // Könnte erlaubt sein, wenn die Frau eine Zusatzfrau ist
            if (addedFrauen.contains(frau)) { // aber auch nur einmal
               return null;
            }
            addedFrauen.add(frau);
         }
      }
      int number = encodePair(frau, mann);
      return increment(constellation, number);
   }

   @Override
   public Object[] createInitialConstellation() {
      return new Object[0];
   }

   @Override
   protected Set<R> decodePairs(Object[] constellation) {
      if (constellation.length < maxSize) {
         return null;
      }
      Set<R> result = new HashSet<>();
      for (Object number : constellation) {
         result.add(decodePair((Integer) number));
      }
      return result;
   }
}
