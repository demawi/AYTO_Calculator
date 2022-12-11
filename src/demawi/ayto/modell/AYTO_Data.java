package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import demawi.ayto.events.Event;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.events.NewPerson;
import demawi.ayto.permutation.AYTO_Permutator;

public class AYTO_Data {

  public String name;
  private final AYTO_Permutator.ZUSATZTYPE zusatztype;
  private final List<Tag> tage = new ArrayList<>();
  public List<Pair> pairsToTrack;
  public List<Frau> initialFrauen = new ArrayList<>();
  public List<Mann> initialMaenner = new ArrayList<>();
  private int basePairCount = 0;

  private boolean closed = false;

  protected Frau frau(String name) {
    Frau frau = new Frau(name);
    initialFrauen.add(frau);
    return frau;
  }

  protected Mann mann(String name) {
    Mann mann = new Mann(name);
    initialMaenner.add(mann);
    return mann;
  }

  public AYTO_Data(String name, AYTO_Permutator.ZUSATZTYPE zusatztype) {
    this.name = name;
    this.zusatztype = zusatztype;
  }

  protected Tag newTag() {
    Tag tag = Tag.create();
    tage.add(tag);
    return tag;
  }

  public int getBasePairCount() {
    return basePairCount;
  }

  public AYTO_Permutator.ZUSATZTYPE getZusatztype() {
    return zusatztype;
  }

  public Tag getTag(int index) {
    return tage.get(index - 1);
  }

  public int getAnzahlTage() {
    return tage.size();
  }

  public List<Tag> getTage() {
    return Collections.unmodifiableList(tage);
  }

  public void track(Pair... pairs) {
    this.pairsToTrack = Arrays.asList(pairs);
  }

  /**
   * Close for input and validate all given data.
   */
  public void closeForInput() {
    if (closed)
      return;
    closed = true;
    boolean markAll = getZusatztype() == AYTO_Permutator.ZUSATZTYPE.JEDER;
    if (initialMaenner.size() > initialFrauen.size()) {
      Mann mann = initialMaenner.remove(initialMaenner.size() - 1);
      mann.mark("*");
      if (markAll) {
        initialMaenner.forEach(p -> p.mark("*"));
      }
    }
    else if (initialMaenner.size() < initialFrauen.size()) {
      Frau frau = initialFrauen.remove(initialFrauen.size() - 1);
      frau.mark("*");
      if (markAll) {
        initialFrauen.forEach(p -> p.mark("*"));
      }
    }
    basePairCount = initialFrauen.size();
    assert initialMaenner.size() == initialFrauen.size();
    validateDays();
  }

  private int currentConsistenceDay = 0;

  private void validateDays() {
    List<Frau> curFrauen = new ArrayList<>(initialFrauen);
    List<Mann> curMaenner = new ArrayList<>(initialMaenner);
    Consumer<Person> checkPerson = (person) -> {
      if (person != null) {
        if (person instanceof Frau) {
          if (!curFrauen.contains(person)) {
            throw new IllegalStateException(
                  "Frau existiert noch nicht rechtzeitig: " + person + " an Tag " + (currentConsistenceDay + 1));
          }
        }
        else { // Mann
          if (!curMaenner.contains(person)) {
            throw new IllegalStateException(
                  "Mann existiert noch nicht rechtzeitig: " + person + " an Tag " + (currentConsistenceDay + 1));
          }
        }
      }
    };
    for (int i = 0, l = tage.size(); i < l; i++) {
      Tag tag = tage.get(i);
      currentConsistenceDay = i;
      for (Event evt : tag.getEvents()) {
        if (evt instanceof NewPerson) {
          NewPerson event = (NewPerson) evt;
          if (event.person != null) {
            if (event.person instanceof Frau) {
              curFrauen.add((Frau) event.person);
            }
            else {
              curMaenner.add((Mann) event.person);
            }
          }
        }
        else if (evt instanceof MatchBoxResult) {
          MatchBoxResult event = (MatchBoxResult) evt;
          checkPerson.accept(event.pair.frau);
          checkPerson.accept(event.pair.mann);
        }
        else if (evt instanceof MatchingNight) {
          MatchingNight event = (MatchingNight) evt;
          for (Pair pair : event.constellation) {
            checkPerson.accept(pair.frau);
            checkPerson.accept(pair.mann);
            validateMatchingPairs(event.constellation);
          }
        }
        else {
          throw new IllegalArgumentException("Event-Klasse kann noch nicht verarbeitet werden: " + evt.getClass()
                .getSimpleName());
        }
      }
    }
  }

  private void validateMatchingPairs(Collection<Pair> constellation) {
    Set<Frau> frauen = new LinkedHashSet<>();
    Set<Mann> maenner = new LinkedHashSet<>();
    for (Pair pair : constellation) {
      frauen.add(pair.frau);
      maenner.add(pair.mann);
    }
    if (frauen.size() != basePairCount) {
      throw new RuntimeException(
            "Falsche Anzahl an Frauen in Matching Night: " + frauen.size() + " benötigt: " + basePairCount);
    }
    if (maenner.size() != basePairCount) {
      throw new RuntimeException(
            "Falsche Anzahl an Männern in Matching Night: " + maenner.size() + " benötigt: " + basePairCount);
    }
  }

  public List<Frau> getFrauen(int tag) {
    List<Frau> result = new ArrayList<>();
    getPairs(tag, pair -> {
      if (!result.contains(pair.frau)) {
        result.add(pair.frau);
      }
    });
    if (tag > 0) {
      Person newExtraPerson = getTag(tag).newExtraPerson;
      if (newExtraPerson instanceof Frau && !result.contains(newExtraPerson)) {
        result.add((Frau) newExtraPerson);
      }
    }
    return result;
  }

  public List<Mann> getMaenner(int tag) {
    List<Mann> result = new ArrayList<>();
    getPairs(tag, pair -> {
      if (!result.contains(pair.mann)) {
        result.add(pair.mann);
      }
    });
    if (tag > 0) {
      Person newExtraPerson = getTag(tag).newExtraPerson;
      if (newExtraPerson instanceof Mann && !result.contains(newExtraPerson)) {
        result.add((Mann) newExtraPerson);
      }
    }
    return result;
  }

  private void getPairs(int tagNr, Consumer<Pair> consumer) {
    for (int i = 0; i < tagNr; i++) {
      Tag tag = tage.get(i);
      tag.boxResults.forEach((matchBoxResult) -> consumer.accept(matchBoxResult.pair));
      if (tag.matchingNight != null) {
        for (Pair pair : tag.matchingNight.constellation) {
          consumer.accept(pair);
        }
      }
    }
  }

  public void preProcess(Consumer<String> out, int tagNr) {
    List<Pair> previousPerfectMatches = new ArrayList<>();
    for (int i = 1; i <= tagNr; i++) {
      Tag tag = getTag(i);
      for (MatchBoxResult boxResult : tag.boxResults) {
        Boolean result = boxResult.result;
        if (result != null && result) {
          previousPerfectMatches.add(boxResult.pair);
        }
      }
    }

    Tag tag = getTag(tagNr);
    Person extraPerson = tag.newExtraPerson;
    if (extraPerson != null) {
      if (getZusatztype() == AYTO_Permutator.ZUSATZTYPE.NUR_LETZTER) {
        // Annahme, dass eine Person die neu hinzukommt auch ein Perfect Match noch hat.
        if (extraPerson instanceof Frau) {
          for (Pair perfectMatch : previousPerfectMatches) {
            Pair implicitPair = Pair.pair((Frau) extraPerson, perfectMatch.mann);
            tag.implicitDerived(false, implicitPair);
            out.accept("Implizite Annahme durch neue Person: " + implicitPair + " => No match!");
          }
        }
        else {
          for (Pair perfectMatch : previousPerfectMatches) {
            Pair implicitPair = Pair.pair(perfectMatch.frau, (Mann) extraPerson);
            tag.implicitDerived(false, implicitPair);
            out.accept("Implizite Annahme durch neue Person: " + implicitPair + " => No match!");
          }
        }
      }
      else { // AYTO_Permutator.ZUSATZTYPE.JEDER
        // Currently nothing to do
      }
    }
  }
}

