package demawi.ayto.permutation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AYTO_Permutator<F, M, R> {

  /**
   * -1: Multi-threading deactivated
   * 0: branch on root (10 Threads)
   */
  private static final int BRANCH_LEVEL = 0;
  private final ExecutorService executorService;

  public enum ZUSATZTYPE {
    JEDER, // Jeder der Geschlechtsgruppe mit 11 Leuten kann der Doppelpartner zu jemand anderem sein
    NUR_LETZTER; // Nur der letzte der Geschlechtsgruppe mit 11 Leuten kann der Doppelpartner zu jemand anderem sein

    public <P, F extends P, M extends P> List<P> getAdditionals(List<F> frauen, List<M> maenner) {
      if (frauen.size() == maenner.size() || frauen.size() < 10 || maenner.size() < 10) {
        return Collections.emptyList();
      }
      if (this == JEDER) {
        if (frauen.size() < maenner.size())
          return (List<P>) maenner;
        else
          return (List<P>) frauen;
      }
      else { // NUR_LETZTER
        if (frauen.size() < maenner.size())
          return List.of(maenner.get(maenner.size() - 1));
        else
          return List.of(frauen.get(frauen.size() - 1));
      }
    }
  }

  protected final List<F> frauen;
  protected final List<M> maenner;
  protected final int minSize;
  protected final int maxSize;
  protected final int anzahlFrauen;
  protected final int anzahlMaenner;
  private final BiFunction<F, M, R> packingFunction;

  /**
   * -1: man weiß nicht wer der Zusatzmann/frau ist
   * >0:
   */
  protected AYTO_Permutator(List<F> frauen, List<M> maenner, BiFunction<F, M, R> packingFunction) {
    this.frauen = frauen;
    this.maenner = maenner;
    anzahlFrauen = frauen.size();
    anzahlMaenner = maenner.size();
    minSize = Math.min(anzahlFrauen, anzahlMaenner);
    maxSize = Math.max(anzahlFrauen, anzahlMaenner);
    this.packingFunction = packingFunction;
    executorService = Executors.newCachedThreadPool();
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

  public void permutate(Supplier<Consumer<Set<R>>> pairConsumer) {
    Object[] current = createInitialConstellation();
    permutateInternImpl(current, pairConsumer, null, BRANCH_LEVEL);
    // System.out.println("ActiveThreads: " + ((ThreadPoolExecutor) executorService).getActiveCount());
    executorService.shutdown();
    try {
      executorService.awaitTermination(10, TimeUnit.MINUTES);
    }
    catch (InterruptedException e) {
      executorService.shutdownNow();
      throw new RuntimeException(e);
    }
  }

  public abstract Object[] createInitialConstellation();

  /**
   * Wie viele currentConstellations gleichzeitig angelegt sind. Wir nutzen hier die Tiefensuche, um
   * Speicherplatz zu sparen.
   */
  private void permutateInternImpl(Object[] currentConstellation, Supplier<Consumer<Set<R>>> pairConsumerCreator,
        Consumer<Set<R>> pairConsumer, int branchLevel) {
    if (anzahlFrauen >= anzahlMaenner) { // jede Frau ist nur einmal vorhanden, somit iterieren wir über Frauen->Männer
      permutateInternImplFrau(0, currentConstellation, pairConsumerCreator, pairConsumer, branchLevel);
    }
    else { // jeder Mann ist nur einmal vorhanden, somit iterieren wir über Männer->Frauen
      permutateInternImplMann(0, currentConstellation, pairConsumerCreator, pairConsumer, branchLevel);
    }
  }

  private void permutateInternImplFrau(int frau, Object[] currentConstellation,
        Supplier<Consumer<Set<R>>> pairConsumerCreator, Consumer<Set<R>> pairConsumer, int branchLevel) {
    for (int mann = 0; mann < anzahlMaenner; mann++) {
      Object[] newSet = canAdd(frau, mann, currentConstellation);
      if (newSet != null) {
        Set<R> result = decodeResultPairs(newSet);
        if (result != null) {
          pairConsumer.accept(result);
        }
        else {
          if (branchLevel == 0) {
            final Consumer<Set<R>> pairConsumerF = (pairConsumer == null ? pairConsumerCreator.get() : pairConsumer);
            executorService.submit(
                  () -> permutateInternImplFrau(frau + 1, newSet, pairConsumerCreator, pairConsumerF, branchLevel - 1));
          }
          else {
            permutateInternImplFrau(frau + 1, newSet, pairConsumerCreator, pairConsumer, branchLevel - 1);
          }
        }
      }
    }
  }

  private void permutateInternImplMann(int mann, Object[] currentConstellation,
        Supplier<Consumer<Set<R>>> pairConsumerCreator, Consumer<Set<R>> pairConsumer, int branchLevel) {
    for (int frau = 0; frau < anzahlFrauen; frau++) {
      Object[] newSet = canAdd(frau, mann, currentConstellation);
      if (newSet != null) {
        Set<R> result = decodeResultPairs(newSet);
        if (result != null) {
          pairConsumer.accept(result);
        }
        else {
          if (branchLevel == 0) {
            final Consumer<Set<R>> pairConsumerF = (pairConsumer == null ? pairConsumerCreator.get() : pairConsumer);
            executorService.submit(
                  () -> permutateInternImplMann(mann + 1, newSet, pairConsumerCreator, pairConsumerF, branchLevel - 1));
          }
          else {
            permutateInternImplMann(mann + 1, newSet, pairConsumerCreator, pairConsumer, branchLevel - 1);
          }
        }
      }
    }
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

  protected abstract Set<R> decodeResultPairs(Object[] constellation);

}
