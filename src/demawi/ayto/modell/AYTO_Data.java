package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class AYTO_Data {

  public String name;
  public List<Tag> tage = new ArrayList<>();
  public List<Pair> pairsToTrack;

  public static Frau newFrau(String name) {
    return new Frau(name);
  }

  public static Mann newMann(String name) {
    return new Mann(name);
  }

  public AYTO_Data(String name) {
    this.name = name;
  }

  public void track(Pair... pairs) {
    this.pairsToTrack = Arrays.asList(pairs);
  }

  public void add(Boolean b, Pair pair, int i, Pair... pairs) {
    tage.add(new Tag(pair, b, pairs == null ? null : new MatchingNight(i, Arrays.asList(pairs))));
  }

  public List<Frau> getFrauen(int tagDef) {
    List<Frau> result = new ArrayList<>();
    getPairs(tagDef, pair -> {
      if (!result.contains(pair.frau)) {
        result.add(pair.frau);
      }
    });
    return result;
  }

  public List<Mann> getMaenner(int tagDef) {
    List<Mann> result = new ArrayList<>();
    getPairs(tagDef, pair -> {
      if (!result.contains(pair.mann)) {
        result.add(pair.mann);
      }
    });
    return result;
  }

  private void getPairs(int tagNr, Consumer<Pair> consumer) {
    for (int i = 0; i < tagNr; i++) {
      Tag tag = tage.get(i);
      consumer.accept(tag.matchingPair);
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
        if (tag.perfectMatch != null) {
          if (tag.perfectMatch != constellation.contains(tag.matchingPair)) {
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

}
