package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import demawi.ayto.AYTO_Permutator;

public class AYTO_Data {

  public String name;
  private AYTO_Permutator.ZUSATZTYPE zusatztype;
  public List<Tag> tage = new ArrayList<>();
  public List<Pair> pairsToTrack;

  protected static Frau frau(String name) {
    return new Frau(name);
  }

  protected static Mann mann(String name) {
    return new Mann(name);
  }

  public AYTO_Data(String name, AYTO_Permutator.ZUSATZTYPE zusatztype) {
    this.name = name;
    this.zusatztype = zusatztype;
  }

  public void track(Pair... pairs) {
    this.pairsToTrack = Arrays.asList(pairs);
  }

  public Tag add(Tag tag) {
    tage.add(tag);
    return tag;
  }

  public Tag add(Boolean b, Pair pair, int i, Pair... pairs) {
    Tag tag = new Tag(pair, b, pairs == null ? null : new MatchingNight(i, Arrays.asList(pairs)));
    tage.add(tag);
    return tag;
  }

  public List<Frau> getFrauen(int tag) {
    List<Frau> result = new ArrayList<>();
    getPairs(tag, pair -> {
      if (!result.contains(pair.frau)) {
        result.add(pair.frau);
      }
    });
    return result;
  }

  public List<Mann> getMaenner(int tag) {
    List<Mann> result = new ArrayList<>();
    getPairs(tag, pair -> {
      if (!result.contains(pair.mann)) {
        result.add(pair.mann);
      }
    });
    return result;
  }

  private void getPairs(int tagNr, Consumer<Pair> consumer) {
    for (int i = 0; i < tagNr; i++) {
      Tag tag = tage.get(i);
      tag.boxPairs.forEach((pair, result) -> consumer.accept(pair));
      if (tag.matchingNight != null) {
        for (Pair pair : tag.matchingNight.constellation) {
          consumer.accept(pair);
        }
      }
    }
  }

  /**
   * Testet ob die übergebene Paar-Konstellation zu einem Widerspruch führt.
   */
  public boolean test(Collection<Pair> constellation, TagDef tagDef, boolean debug) {
    for (int i = 0; i < tagDef.tagNr; i++) {
      Tag tag = tage.get(i);

      boolean aktuell = i == tagDef.tagNr - 1;
      if (!aktuell || tagDef.mitMatchbox) {
        for (Map.Entry<Pair, Boolean> cur : tag.boxPairs.entrySet()) {
          Boolean result = cur.getValue();
          if (result != null && result != constellation.contains(cur.getKey())) {
            return false;
          }
        }
        for (Map.Entry<Pair, Boolean> entry : tag.implicits.entrySet()) {
          if (entry.getValue() != constellation.contains(entry.getKey())) {
            return false;
          }
        }
      }
      if (!aktuell || tagDef.mitMatchingNight) {
        MatchingNight night = tag.matchingNight;
        if (night != null) {
          for (int nightNr = 0, l = night.constellation.size(); nightNr < l; nightNr++) {
            int lights = getLights(constellation, night.constellation);
            if (lights != night.lights) {
              if (debug) {
                System.out.println(
                      "Falsch aufgrund von Matching Night Nr. " + (nightNr + 1) + " Erwartete Lichter: " + lights
                            + ". Es waren aber: " + night.lights);
              }
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  public int getLights(Collection<Pair> assumptionModell, Collection<Pair> testConstellation) {
    int lights = 0;
    for (Pair pair : testConstellation) {
      if (assumptionModell.contains(pair)) {
        lights++;
      }
    }
    return lights;
  }

  public AYTO_Permutator.ZUSATZTYPE getZusatztype() {
    return zusatztype;
  }

}
