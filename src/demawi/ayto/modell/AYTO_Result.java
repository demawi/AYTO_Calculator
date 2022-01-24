package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AYTO_Result {

  public List<Set<Pair>> possibleConstellations = new ArrayList<>();
  public Map<Pair, Integer> pairCount = new HashMap<Pair, Integer>();
  public Map<Frau, Set<Mann>> frauCount = new HashMap<>();
  public Map<Mann, Set<Frau>> mannCount = new HashMap<>();
  public int yes = 0;
  public int no = 0;
  public int totalConstellations = 0;

  public AYTO_Result() {
  }

  public int getYes() {
    return yes;
  }

  public Integer getCount(Frau frau, Mann mann) {
    return getCount(Pair.pair(frau, mann));
  }

  public Integer getCount(Pair pair) {
    Integer result = pairCount.get(pair);
    return result == null ? 0 : result;
  }

  public void addResult(boolean result, Set<Pair> pairs) {
    totalConstellations++;
    if (result) {
      addYes(pairs);
      yes++;
    }
    else {
      no++;
    }
  }

  /**
   * Fügt die Konsteallation als gültig ein.
   */
  public void addYes(Set<Pair> pairs) {
    possibleConstellations.add(pairs);

    // directly update pair-,frau-,mannCount
    for (Pair current : pairs) {
      Integer count = pairCount.get(current);
      if (count == null) {
        count = 0;
      }
      count++;
      pairCount.put(current, count);

      Set<Mann> frauSet = frauCount.get(current.frau);
      if (frauSet == null) {
        frauSet = new HashSet<>();
        frauCount.put(current.frau, frauSet);
      }
      frauSet.add(current.mann);

      Set<Frau> mannSet = mannCount.get(current.mann);
      if (mannSet == null) {
        mannSet = new HashSet<>();
        mannCount.put(current.mann, mannSet);
      }
      mannSet.add(current.frau);
    }
  }

}
