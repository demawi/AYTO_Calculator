package demawi.ayto.permutation;

import java.util.List;
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

   /**
    * Prüft, ob ein Paar wirklich zu der bisherigen Konstellation hinzugefügt werden kann.
    * <p>
    * Nur für ZUSATZTYPE.NUR_LETZTER
    */
   protected Object[] canAdd(int frau, int mann, Object[] constellation) {
      boolean[] addedFrauen = new boolean[anzahlFrauen];
      boolean[] addedMaenner = new boolean[anzahlMaenner];
      for (Object current : constellation) {
         int decodedFrau = decodeFrau((Integer) current);
         if (decodedFrau == frau) {
            if (mann < minSize) {
               return null;
            }
            // die Frau ist bereits enthalten
            // Könnte erlaubt sein, wenn der Mann ein Zusatzmann ist
            if (addedMaenner[mann]) { // aber auch nur einmal
               return null;
            }
            addedMaenner[mann] = true;
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
            if (addedFrauen[frau]) { // aber auch nur einmal
               return null;
            }
            addedFrauen[frau] = true;
         }
      }
      int number = encodePair(frau, mann);
      return increment(constellation, number);
   }

}
