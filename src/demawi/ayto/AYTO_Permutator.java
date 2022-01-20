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
    int maxA = frauen.size();
    int maxB = maenner.size();
    int size = Math.max(maxA, maxB);
    Set<Integer> current = new HashSet<>();
    permutateIntern(maxA, maxB, size, -1, current, pairConsumer);
  }

  private void permutateIntern(int maxA, int maxB, int size, int lastAdd, Set<Integer> current,
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
          permutateIntern(maxA, maxB, size, number, newSet, pairConsumer);
        }
      }
    }
  }

  // these will be reused
  private Set<Integer> frauDouble = new HashSet<>();
  private Set<Integer> mannDouble = new HashSet<>();

  private boolean canAdd(int frau, int mann, Set<Integer> set) {
    testCount++;
    frauDouble.clear();
    mannDouble.clear();
    for (Integer current : set) {
      if (decodeFrau(current) == frau) {
        // die Frau ist bereits enthalten
        if (mann > 9) { // Könnte erlaubt sein, wenn der Mann ein Zusatzmann ist
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
        if (frau > 9) { // Könnte erlaubt sein, wenn die Frau eine Zusatzfrau ist
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
