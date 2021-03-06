package demawi.ayto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class AYTO_Permutator<F, M, R> {

  private List<F> frauen;
  private List<M> maenner;
  private BiFunction<F, M, R> packingFunction;
  public long testCount = 0;

  private AYTO_Permutator(List<F> frauen, List<M> maenner, BiFunction<F, M, R> packingFunction) {
    this.frauen = frauen;
    this.maenner = maenner;
    this.packingFunction = packingFunction;
  }

  public static <F, M, R> AYTO_Permutator<F, M, R> create(List<F> setA, List<M> setB,
    BiFunction<F, M, R> packingFunction) {
    return new AYTO_Permutator<F, M, R>(setA, setB, packingFunction);
  }

  public void permutate(Consumer<Set<R>> pairConsumer) {
    int anzahlFrauen = frauen.size();
    int anzahlMaenner = maenner.size();
    int size = Math.max(anzahlFrauen, anzahlMaenner);
    Set<Integer> current = new HashSet<>();
    permutateInternImpl2(anzahlFrauen, anzahlMaenner, size, 0, 0, current, pairConsumer);
  }

  /**
   * Just iterate over both lists.
   */
  private void permutateInternImpl1(int maxA, int maxB, int size, int lastAdd, Set<Integer> current,
    Consumer<Set<R>> pairConsumer) {

    if (size == current.size()) {
      pairConsumer.accept(decodePairs(current));
      return;
    }

    for (int i = 0; i < maxA; i++) {
      for (int k = 0; k < maxB; k++) {
        int number = encodePair(i, k);

        if (canAdd(i, k, current)) {
          if (number <= lastAdd) return;
          Set<Integer> newSet = new HashSet<>(current);
          newSet.add(number);
          permutateInternImpl1(maxA, maxB, size, number, newSet, pairConsumer);
        }
      }
    }
  }

  /**
   * Optimized iteration only over one list.
   */
  private void permutateInternImpl2(int anzahlFrauen, int anzahlMaenner, int size, int frauAktuell, int mannAktuell,
    Set<Integer> currentConstellation, Consumer<Set<R>> pairConsumer) {

    if (size == currentConstellation.size()) {
      pairConsumer.accept(decodePairs(currentConstellation));
      return;
    }

    if (anzahlFrauen >= anzahlMaenner) { // jede Frau ist nur einmal vorhanden, somit frauAktuell immer + 1
      for (int mann = 0; mann < anzahlMaenner; mann++) {
        if (canAdd(frauAktuell, mann, currentConstellation)) {
          int number = encodePair(frauAktuell, mann);
          Set<Integer> newSet = new HashSet<>(currentConstellation);
          newSet.add(number);
          permutateInternImpl2(anzahlFrauen, anzahlMaenner, size, frauAktuell + 1, 0, newSet, pairConsumer);
        }
      }
    }
    else { // jeder Mann ist nur einmal vorhanden, somit mannAktuell immer +1.
      for (int frau = 0; frau < anzahlFrauen; frau++) {
        if (canAdd(frau, mannAktuell, currentConstellation)) {
          int number = encodePair(frau, mannAktuell);
          Set<Integer> newSet = new HashSet<>(currentConstellation);
          newSet.add(number);
          permutateInternImpl2(anzahlFrauen, anzahlMaenner, size, 0, mannAktuell + 1, newSet, pairConsumer);
        }
      }
    }
  }

  // these will be reused
  private Set<Integer> frauDouble = new HashSet<>();
  private Set<Integer> mannDouble = new HashSet<>();

  /**
   * Pr??ft, ob ein Paar wirklich zu der bisherigen Konstellation hinzugef??gt werden kann.
   */
  private boolean canAdd(int frau, int mann, Set<Integer> constellation) {
    testCount++;
    frauDouble.clear();
    mannDouble.clear();
    for (Integer current : constellation) {
      if (decodeFrau(current) == frau) {
        // die Frau ist bereits enthalten
        if (mann > 9) { // K??nnte erlaubt sein, wenn der Mann ein Zusatzmann ist
          if (mannDouble.contains(mann)) { // aber auch nur einmal
            return false;
          }
          mannDouble.add(mann);
          continue;
        }
        return false;
      }
      if (decodeMann(current) == mann) {
        // der Mann ist bereits enthalten
        if (frau > 9) { // K??nnte erlaubt sein, wenn die Frau eine Zusatzfrau ist
          if (frauDouble.contains(frau)) { // aber auch nur einmal
            return false;
          }
          frauDouble.add(frau);
          continue;
        }
        return false;
      }
    }
    return true;
  }

  private static int encodePair(int frau, int mann) {
    return (frau << 4) + mann;
  }

  private static int decodeFrau(int number) {
    return number >> 4;
  }

  private static int decodeMann(int number) {
    return number & 15;
  }

  private R decodePair(Integer number) {
    return packingFunction.apply(frauen.get(decodeFrau(number)), maenner.get(decodeMann(number)));
  }

  private Set<R> decodePairs(Set<Integer> setting) {
    Set<R> result = new HashSet<>();
    for (Integer number : setting) {
      result.add(decodePair(number));
    }
    return result;
  }

}
