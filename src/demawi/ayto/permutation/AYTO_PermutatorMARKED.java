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
   private final int duplicateCapacity;
   private final int diff;
   private final Integer maxMatches;
   private final boolean hasDefiniteExtraMatches;
   private final boolean hasDefiniteExtraMatchOnMen;
   private final boolean hasDefiniteExtraMatchOnWomen;
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
      hasDefiniteExtraMatchOnMen = maenner.stream()
            .anyMatch(a -> ((Person) a).hasMark(Mark.IS_AN_EXTRA_MATCH));
      hasDefiniteExtraMatchOnWomen = frauen.stream()
            .anyMatch(a -> ((Person) a).hasMark(Mark.IS_AN_EXTRA_MATCH));
      duplicateCapacity = difference == 0 && hasDefiniteExtraMatches ? 1 : difference;
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
      return IntStream.range(0, maenner.size())
            .filter(idx -> ((Person) maenner.get(idx)).hasMark(Mark.IS_AN_EXTRA_MATCH))
            .anyMatch(idx -> countFrau(getPartnerWoman(idx, frau, mann, constellation), constellation)
                  + (idx == mann ? 1 : 0) < 2);
   }

   private boolean hasDefiniteExtraMatchOnWomen(int frau, int mann, Object[] constellation) {
      return IntStream.range(0, frauen.size())
            .filter(idx -> ((Person) frauen.get(idx)).hasMark(Mark.IS_AN_EXTRA_MATCH))
            .anyMatch(idx -> countMann(getPartnerMan(idx, frau, mann, constellation), constellation)
                  + (idx == frau ? 1 : 0) < 2);
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
