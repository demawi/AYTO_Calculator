package demawi.ayto.perm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public abstract class AYTO_Permutator<F, M, R> {

  public static enum ZUSATZTYPE {
    JEDER, NUR_LETZTER
  }

  protected final List<F> frauen;
  protected final List<M> maenner;
  protected final int minSize;
  protected final int maxSize;
  private final BiFunction<F, M, R> packingFunction;
  public long testCount = 0; // wie oft die canAdd-Methode aufgerufen wurde

  /**
   * -1: man weiß nicht wer der Zusatzmann/frau ist
   * >0:
   */
  protected AYTO_Permutator(List<F> frauen, List<M> maenner, BiFunction<F, M, R> packingFunction) {
    this.frauen = frauen;
    this.maenner = maenner;
    minSize = Math.min(frauen.size(), maenner.size());
    maxSize = Math.max(frauen.size(), maenner.size());
    this.packingFunction = packingFunction;
  }

  public static <F, M, R> AYTO_Permutator<F, M, R> create(List<F> setA, List<M> setB, ZUSATZTYPE zusatzType,
    BiFunction<F, M, R> packingFunction) {
    if (zusatzType == ZUSATZTYPE.JEDER) {
      return new AYTO_PermutatorJEDER<>(setA, setB, packingFunction);
    }
    else {
      return new AYTO_PermutatorNUR_LETZTER<>(setA, setB, packingFunction);
    }
  }

  int count = 0;

  public void permutate(Consumer<Set<R>> pairConsumer) {
    int anzahlFrauen = frauen.size();
    int anzahlMaenner = maenner.size();
    Object[] current = createInitialConstellation();
    count = 0;
    permutateInternImpl(anzahlFrauen, anzahlMaenner, 0, 0, current, (a -> {
      count++;
      if ((count % 1000000) == 0) {
        //System.out.println("Count: " + count / 1000000 + "mio");
      }
      pairConsumer.accept(a);
    }));
    //System.out.println("Permutations: " + count);
  }

  public abstract Object[] createInitialConstellation();

  /**
   * Wie viele currentConstellations gleichzeitig angelegt sind. Wir nutzen hier die Tiefensuche, um
   * Speicherplatz zu sparen.
   */
  private int openInts = 0;

  private void permutateInternImpl(int anzahlFrauen, int anzahlMaenner, int frauAktuell, int mannAktuell,
        Object[] currentConstellation, Consumer<Set<R>> pairConsumer) {
    openInts++;
    // System.out.println("openInts: "+openInts);

    if (anzahlFrauen >= anzahlMaenner) { // jede Frau ist nur einmal vorhanden, somit frauAktuell immer + 1
      for (int mann = 0; mann < anzahlMaenner; mann++) {
        testCount++;
        Object[] newSet = canAdd(frauAktuell, mann, currentConstellation);
        if (newSet != null) {
          Set<R> result = decodePairs(newSet);
          if (result != null) {
            pairConsumer.accept(result);
          }
          else {
            permutateInternImpl(anzahlFrauen, anzahlMaenner, frauAktuell + 1, 0, newSet, pairConsumer);
          }
        }
      }
    }
    else { // jeder Mann ist nur einmal vorhanden, somit mannAktuell immer +1.
      for (int frau = 0; frau < anzahlFrauen; frau++) {
        testCount++;
        Object[] newSet = canAdd(frau, mannAktuell, currentConstellation);
        if (newSet != null) {
          Set<R> result = decodePairs(newSet);
          if (result != null) {
            pairConsumer.accept(result);
          }
          else {
            permutateInternImpl(anzahlFrauen, anzahlMaenner, 0, mannAktuell + 1, newSet, pairConsumer);
          }
        }
      }
    }
    openInts--;
  }

  protected Object[] increment(Object[] array, Integer value) {
    Object[] result = Arrays.copyOf(array, array.length + 1);
    result[array.length] = value;
    return result;
  }

  protected abstract Object[] canAdd(int frau, int mann, Object[] constellation);

  protected static int encodePair(int frau, int mann) {
    return (frau << 4) + mann;
  }

  protected static int decodeFrau(int number) {
    return number >> 4;
  }

  protected static int decodeMann(int number) {
    return number & 15;
  }

  protected R decodePair(Integer number) {
    return packingFunction.apply(frauen.get(decodeFrau(number)), maenner.get(decodeMann(number)));
  }

  protected abstract Set<R> decodePairs(Object[] constellation);

  private Set<R> decodePairs(Integer[] setting) {
    Set<R> result = new HashSet<>();
    for (Integer number : setting) {
      result.add(decodePair(number));
    }
    return result;
  }

}
