package demawi.ayto.permutation;

import java.util.*;
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
  protected final ExecutorService executorService;

  public enum ZUSATZTYPE {
    BISEXUAL, // Jeder kann mit jedem
    KEINER, // Jeder hat genau einen Partner zugewiesen es gibt keine Doppelpartner.
    ALLE, // Alle der Geschlechtsgruppe mit 11 Leuten kann der Doppelpartner zu jemand anderem sein
    MARKED, // Nur die markieren Personen können der Doppelpartner zu jemand anderem sein
    NUR_LETZTER; // Nur der letzte der Geschlechtsgruppe mit 11 Leuten kann der Doppelpartner zu jemand anderem sein

    public <Type extends DoubleEntry, F extends Type, M extends Type> List<Type> getZusatzpersonen(List<F> frauen,
          List<M> maenner) {
      List<Type> result = new ArrayList<>();
      Consumer<Type> checkZusatz = p -> {
        if (p.canBeDouble())
          result.add(p);
      };
      frauen.forEach(checkZusatz);
      maenner.forEach(checkZusatz);
      return result;
    }

  }

  protected final List<F> frauen;
  protected final List<M> maenner;
  protected final int minSize;
  protected final int maxSize;
  protected final int anzahlFrauen;
  protected final int anzahlMaenner;
  private final ZUSATZTYPE zusatzType;
  private final BiFunction<F, M, R> packingFunction;
  private final boolean[] doubleMapFrauen;
  private final boolean[] doubleMapMaenner;

  protected AYTO_Permutator(List<F> frauen, List<M> maenner, ZUSATZTYPE zusatzType,
        BiFunction<F, M, R> packingFunction) {
    this.frauen = frauen;
    this.maenner = maenner;
    this.zusatzType = zusatzType;
    this.packingFunction = packingFunction;
    anzahlFrauen = frauen.size();
    anzahlMaenner = maenner.size();
    minSize = Math.min(anzahlFrauen, anzahlMaenner);
    maxSize = Math.max(anzahlFrauen, anzahlMaenner);
    executorService = Executors.newCachedThreadPool();

    // Pre-calculate doubleMaps
    doubleMapFrauen = new boolean[frauen.size()];
    for (int i = 0, l = frauen.size(); i < l; i++) {
      F f = frauen.get(i);
      if (canBeDoubleFrau(f)) {
        doubleMapFrauen[i] = true;
      }
    }
    doubleMapMaenner = new boolean[maenner.size()];
    for (int i = 0, l = maenner.size(); i < l; i++) {
      M m = maenner.get(i);
      if (canBeDoubleMann(m)) {
        doubleMapMaenner[i] = true;
      }
    }
  }

  protected boolean canBeDoubleFrau(int frau) {
    return doubleMapFrauen[frau];
  }

  protected boolean canBeDoubleMann(int mann) {
    return doubleMapMaenner[mann];
  }

  protected boolean canBeDoubleFrau(F frau) {
    if (frau instanceof DoubleEntry) {
      return ((DoubleEntry) frau).canBeDouble();
    }
    return false;
  }

  protected boolean canBeDoubleMann(M mann) {
    if (mann instanceof DoubleEntry) {
      return ((DoubleEntry) mann).canBeDouble();
    }
    return false;
  }

  public static <F, M, R> AYTO_Permutator<F, M, R> create(List<F> setA, List<M> setB, ZUSATZTYPE zusatzType,
        BiFunction<F, M, R> packingFunction) {
    if (zusatzType == ZUSATZTYPE.KEINER) {
      return new AYTO_PermutatorKEINER<>(setA, setB, zusatzType, packingFunction);
    }
    if (zusatzType == ZUSATZTYPE.ALLE) {
      return new AYTO_PermutatorALLE<>(setA, setB, zusatzType, packingFunction);
    }
    if (zusatzType == ZUSATZTYPE.BISEXUAL) {
      return new AYTO_PermutatorBISEXUAL<>(setA, setB, zusatzType, packingFunction);
    }
    else if (zusatzType == ZUSATZTYPE.NUR_LETZTER) {
      return new AYTO_PermutatorNUR_LETZTER<>(setA, setB, zusatzType, packingFunction);
    }
    else if (zusatzType == ZUSATZTYPE.MARKED) {
      return new AYTO_PermutatorMARKED<>(setA, setB, zusatzType, packingFunction);
    }
    else {
      throw new RuntimeException("Kein Permutationsalgorithmus für '" + zusatzType + "' gefunden!");
    }
  }

  public void permutate(Supplier<Consumer<Set<R>>> pairConsumer) {
    Object[] current = createInitialConstellation();
    permutateInternImpl(current, pairConsumer, BRANCH_LEVEL);
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

  /**
   * Wie viele currentConstellations gleichzeitig angelegt sind. Wir nutzen hier die Tiefensuche, um
   * Speicherplatz zu sparen.
   */
  private void permutateInternImpl(Object[] currentConstellation, Supplier<Consumer<Set<R>>> pairConsumerCreator,
        int branchLevel) {
    Consumer<Set<R>> pairConsumer = branchLevel < 0 ? pairConsumerCreator.get() : null;
    if (anzahlFrauen >= anzahlMaenner) { // jede Frau ist nur einmal vorhanden, somit iterieren wir über Frauen->Männer
      iterateFirstGroup(0, currentConstellation, pairConsumerCreator, pairConsumer, branchLevel);
    }
    else { // jeder Mann ist nur einmal vorhanden, somit iterieren wir über Männer->Frauen
      iterateSecondGroup(0, currentConstellation, pairConsumerCreator, pairConsumer, branchLevel);
    }
  }

  protected boolean iterateFirstGroup(int frau, Object[] currentConstellation,
        Supplier<Consumer<Set<R>>> pairConsumerCreator, Consumer<Set<R>> pairConsumer, int branchLevel) {
    boolean addFound = false;
    for (int mann = 0; mann < anzahlMaenner; mann++) {
      Object[] newSet = canAdd(frau, mann, currentConstellation);
      if (newSet != null) {
        addFound = true;
        Set<R> result = decodeResultPairs(newSet);
        if (result != null) {
          pairConsumer.accept(result);
        }
        else {
          if (branchLevel == 0) {
            final Consumer<Set<R>> pairConsumerF = (pairConsumer == null ? pairConsumerCreator.get() : pairConsumer);
            executorService.submit(
                  () -> iterateFirstGroup(frau + 1, newSet, pairConsumerCreator, pairConsumerF, branchLevel - 1));
          }
          else {
            iterateFirstGroup(frau + 1, newSet, pairConsumerCreator, pairConsumer, branchLevel - 1);
          }
        }
      }
    }
    return addFound;
  }

  private boolean iterateSecondGroup(int mann, Object[] currentConstellation,
        Supplier<Consumer<Set<R>>> pairConsumerCreator, Consumer<Set<R>> pairConsumer, int branchLevel) {
    boolean addFound = false;
    for (int frau = 0; frau < anzahlFrauen; frau++) {
      Object[] newSet = canAdd(frau, mann, currentConstellation);
      if (newSet != null) {
        addFound = true;
        Set<R> result = decodeResultPairs(newSet);
        if (result != null) {
          pairConsumer.accept(result);
        }
        else {
          if (branchLevel == 0) {
            final Consumer<Set<R>> pairConsumerF = (pairConsumer == null ? pairConsumerCreator.get() : pairConsumer);
            executorService.submit(
                  () -> iterateSecondGroup(mann + 1, newSet, pairConsumerCreator, pairConsumerF, branchLevel - 1));
          }
          else {
            iterateSecondGroup(mann + 1, newSet, pairConsumerCreator, pairConsumer, branchLevel - 1);
          }
        }
      }
    }
    return addFound;
  }

  protected Object[] increment(Object[] array, Integer value) {
    Object[] result = Arrays.copyOf(array, array.length + 1);
    result[array.length] = value;
    return result;
  }

  protected abstract Object[] canAdd(int frau, int mann, Object[] constellation);

  /**
   * Currently 16 is the maximum for each of the groups.
   */
  protected static int encodePair(int frau, int mann) {
    return (frau << 4) + mann;
  }

  /**
   * Currently 16 is the maximum for each of the groups.
   */
  protected static int decodeFrau(int number) {
    return number >> 4;
  }

  /**
   * Currently 16 is the maximum for each of the groups.
   */
  protected static int decodeMann(int number) {
    return number & 15;
  }

  protected R decodePair(Integer number) {
    return packingFunction.apply(frauen.get(decodeFrau(number)), maenner.get(decodeMann(number)));
  }

  public Object[] createInitialConstellation() {
    return new Object[0];
  }

  protected Set<R> decodeResultPairs(Object[] constellation) {
    if (constellation.length < maxSize) {
      return null;
    }
    Set<R> result = new LinkedHashSet<>();
    for (Object number : constellation) {
      result.add(decodePair((Integer) number));
    }
    return result;
  }

}
