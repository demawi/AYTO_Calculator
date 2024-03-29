package demawi.ayto.permutation;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
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

  public enum Mode {
    STANDARD, // Jeder hat genau einen Partner zugewiesen es gibt keine Doppelpartner.
    MARKED, // Die markieren Personen können der Doppelpartner zu jemand anderem sein
    BISEXUAL, // Every person can match with every other person. There aren't two groups.
    ;

    public <Type extends Markable, F extends Type, M extends Type> List<Type> getExtraMatchPersons(List<F> frauen,
          List<M> maenner) {
      List<Type> result = new ArrayList<>();
      Consumer<Type> checkExtra = p -> {
        if (p.hasMark(Mark.CAN_BE_AN_EXTRA_MATCH))
          result.add(p);
      };
      frauen.forEach(checkExtra);
      maenner.forEach(checkExtra);
      return result;
    }

  }

  protected final List<F> women;
  protected final List<M> men;
  protected final int maxSize;
  protected final int womenCount;
  protected final int menCount;
  private final BiFunction<F, M, R> packingFunction;
  private final Map<Mark, boolean[]> marksWomen = new LinkedHashMap<>();
  private final Map<Mark, boolean[]> marksMen = new LinkedHashMap<>();

  protected AYTO_Permutator(List<F> women, List<M> men, BiFunction<F, M, R> packingFunction) {
    this.women = women;
    this.men = men;
    this.packingFunction = packingFunction;
    womenCount = women.size();
    menCount = men.size();
    maxSize = Math.max(womenCount, menCount);
    executorService = Executors.newCachedThreadPool();

    // Pre-calculate MarkMaps
    for (Mark mark : Arrays.asList(Mark.CAN_BE_AN_EXTRA_MATCH, Mark.IS_AN_EXTRA_MATCH)) {
      marksWomen.put(mark, createMarkMap(women, mark));
      marksMen.put(mark, createMarkMap(men, mark));
    }
  }

  private static boolean[] createMarkMap(List<?> group, Mark mark) {
    boolean[] result = new boolean[group.size()];
    for (int i = 0, l = group.size(); i < l; i++) {
      result[i] = hasMark(group.get(i), mark);
    }
    return result;
  }

  protected boolean hasMarkWoman(int frau, Mark mark) {
    return marksWomen.get(mark)[frau];
  }

  protected boolean hasMarkMan(int mann, Mark mark) {
    return marksMen.get(mark)[mann];
  }

  protected static boolean hasMark(Object check, Mark mark) {
    if (check instanceof Markable) {
      return ((Markable) check).hasMark(mark);
    }
    return false;
  }

  public static <F, M, R> AYTO_Permutator<F, M, R> create(List<F> setA, List<M> setB, Mode zusatzType,
        BiFunction<F, M, R> packingFunction) {
    if (zusatzType == Mode.BISEXUAL) {
      return new AYTO_PermutatorBISEXUAL<>(setA, setB, packingFunction);
    }
    else {
      if (setA.size() == setB.size()) {
        return new AYTO_PermutatorSTANDARD<>(setA, setB, packingFunction);
      }
      else {
        if (Markable.hasMark(setA, Mark.IS_AN_EXTRA_MATCH) || Markable.hasMark(setB, Mark.IS_AN_EXTRA_MATCH)) {
          return new AYTO_PermutatorMARKED2<>(setA, setB, packingFunction);
        }
        return new AYTO_PermutatorMARKED<>(setA, setB, packingFunction);
      }
    }
    // throw new RuntimeException("Kein Permutationsalgorithmus für '" + zusatzType + "' gefunden!");
  }

  public void permutate(Supplier<Consumer<Set<R>>> pairConsumer) {
    Object[] current = createInitialConstellation();
    permutateInternImpl(current, pairConsumer, BRANCH_LEVEL);
    // System.out.println("ActiveThreads: " + ((ThreadPoolExecutor) executorService).getActiveCount());
    executorService.shutdown();
    try {
      while (!executorService.awaitTermination(10, TimeUnit.MINUTES)) {
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount() + " Threads are still active!");
        // Nothing to do, just wait
      }
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
    if (womenCount >= menCount) { // jede Frau ist nur einmal vorhanden, somit iterieren wir über Frauen->Männer
      iterateFirstGroup(0, currentConstellation, pairConsumerCreator, pairConsumer, branchLevel);
    }
    else { // jeder Mann ist nur einmal vorhanden, somit iterieren wir über Männer->Frauen
      iterateSecondGroup(0, currentConstellation, pairConsumerCreator, pairConsumer, branchLevel);
    }
  }

  protected boolean iterateFirstGroup(int frau, Object[] currentConstellation,
        Supplier<Consumer<Set<R>>> pairConsumerCreator, Consumer<Set<R>> pairConsumer, int branchLevel) {
    boolean addFound = false;
    for (int mann = 0; mann < menCount; mann++) {
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
    for (int frau = 0; frau < womenCount; frau++) {
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
    return packingFunction.apply(women.get(decodeFrau(number)), men.get(decodeMann(number)));
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
