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
import demawi.ayto.events.EventWithImplicits;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.events.NewPerson;
import demawi.ayto.permutation.AYTO_Permutator;

public class StaffelData {

  public String name;
  private final AYTO_Permutator.ZUSATZTYPE zusatztype;
  private final int matchingPairCount;

  private final List<Tag> tage = new ArrayList<>();
  public List<AYTO_Pair> pairsToTrack;
  public List<Person> initialFrauen = new ArrayList<>();
  public List<Person> initialMaenner = new ArrayList<>();

  private boolean closed = false;

  protected Person person(String name) {
    Person person = new Person(name);
    initialFrauen.add(person);
    initialMaenner.add(person);
    return person;
  }

  protected Frau frau(String name) {
    return frau(name, false);
  }

  protected Frau frau(String name, boolean late) {
    Frau frau = new Frau(name);
    if (!late) {
      initialFrauen.add(frau);
    }
    return frau;
  }

  protected Mann mann(String name) {
    return mann(name, false);
  }

  protected Mann mann(String name, boolean late) {
    Mann mann = new Mann(name);
    if (!late) {
      initialMaenner.add(mann);
    }
    return mann;
  }

  public StaffelData(AYTO_Permutator.ZUSATZTYPE zusatztype) {
    this(zusatztype, 10);
  }

  public StaffelData(AYTO_Permutator.ZUSATZTYPE zusatztype, int matchingPairCount) {
    this.zusatztype = zusatztype;
    this.matchingPairCount = matchingPairCount;
  }

  public void setName(String name) {
    this.name = name;
  }

  protected Tag newTag() {
    Tag tag = Tag.create(matchingPairCount);
    tage.add(tag);
    return tag;
  }

  public int getMatchingPairCount() {
    return matchingPairCount;
  }

  public AYTO_Permutator.ZUSATZTYPE getZusatztype() {
    return zusatztype;
  }

  public Tag getTag(int tagNr) {
    return tage.get(tagNr - 1);
  }

  public int getAnzahlTage() {
    return tage.size();
  }

  public List<Tag> getTage() {
    return Collections.unmodifiableList(tage);
  }

  public void track(AYTO_Pair... pairs) {
    this.pairsToTrack = Arrays.asList(pairs);
  }

  public List<Person> getFrauen(int tagNr) {
    return getFrauen(tagNr, getTag(tagNr).getEvents()
          .size());
  }

  public List<Person> getFrauen(int tagNr, int eventCount) {
    List<Person> result = new ArrayList<>(initialFrauen);
    for (Event event : getAllEventsTill(tagNr, eventCount)) {
      if (event instanceof NewPerson && ((NewPerson) event).person instanceof Frau) {
        result.add((Frau) ((NewPerson) event).person);
      }
    }
    return result;
  }

  public List<Person> getMaenner(int tagNr) {
    return getMaenner(tagNr, getTag(tagNr).getEvents()
          .size());
  }

  public List<Person> getMaenner(int tagNr, int eventCount) {
    List<Person> result = new ArrayList<>(initialMaenner);
    for (Event event : getAllEventsTill(tagNr, eventCount)) {
      if (event instanceof NewPerson && ((NewPerson) event).person instanceof Mann) {
        result.add((Mann) ((NewPerson) event).person);
      }
    }
    return result;
  }

  public List<Event> getAllEventsTill(int tagNr) {
    return getAllEventsTill(tagNr, getTag(tagNr).getEvents()
          .size());
  }

  public List<Event> getAllEventsTill(int tagNr, int eventCount) {
    List<Event> result = new ArrayList<>();
    for (int i = 0; i < tagNr; i++) {
      Tag tag = tage.get(i);
      List<Event> curEvents = tag.getEvents();
      for (int j = 0, l = (i == tagNr - 1) ? eventCount : curEvents.size(); j < l; j++) {
        result.add(curEvents.get(j));
      }
    }
    return result;
  }

  public List<Person> getZusatzpersonen(int tagNr) {
    return getZusatztype().getZusatzpersonen(getFrauen(tagNr), getMaenner(tagNr));
  }

  /**
   * Close for input and validate all given data.
   */
  public void ensureDataIsClosed() {
    if (closed)
      return;
    closed = true;

    // Zusatz-Person erstmal wieder entfernen und markieren. Diese Person muss später per NewPerson-Event wieder reinkommen.
    if (initialMaenner.size() > initialFrauen.size()) {
      initialMaenner.remove(initialMaenner.size() - 1);
    }
    else if (initialMaenner.size() < initialFrauen.size()) {
      initialFrauen.remove(initialFrauen.size() - 1);
    }
    assert initialMaenner.size() == initialFrauen.size();
    validateDays();
    checkForImplicits();
  }

  private int currentConsistenceDay = 0;

  private void validateDays() {
    List<Person> curFrauen = new ArrayList<>(initialFrauen);
    List<Person> curMaenner = new ArrayList<>(initialMaenner);
    Consumer<Person> checkPersonAlreadyKnown = (person) -> {
      if (person != null) {
        if (person instanceof Frau) {
          if (!curFrauen.contains(person)) {
            throw new IllegalStateException(
                  "Frau existiert noch nicht rechtzeitig: " + person + " an Tag " + (currentConsistenceDay + 1));
          }
        }
        else if(person instanceof Mann) { // Mann
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
          Person newPerson = event.person;
          if (newPerson instanceof Frau) {
            curFrauen.add((Frau) newPerson);
            if (curFrauen.size() > 10) {
              if (getZusatztype() == AYTO_Permutator.ZUSATZTYPE.JEDER) {
                initialFrauen.forEach(f -> f.mark(Person.MARK1));
              }
              if (event.zusatzperson) {
                newPerson.mark(Person.MARK1);
              }
            }
          }
          else {
            curMaenner.add((Mann) newPerson);
            if (curMaenner.size() > 10) {
              if (getZusatztype() == AYTO_Permutator.ZUSATZTYPE.JEDER) {
                initialMaenner.forEach(f -> f.mark(Person.MARK1));
              }
              if (event.zusatzperson) {
                newPerson.mark(Person.MARK1);
              }
            }
          }
        }
        else if (evt instanceof MatchBoxResult) {
          MatchBoxResult event = (MatchBoxResult) evt;
          AYTO_Pair pair = event.pair;
          checkPersonAlreadyKnown.accept(pair.frau);
          checkPersonAlreadyKnown.accept(pair.mann);
        }
        else if (evt instanceof MatchingNight) {
          MatchingNight event = (MatchingNight) evt;
          Set<AYTO_Pair> constellation = event.constellation;
          for (AYTO_Pair pair : constellation) {
            checkPersonAlreadyKnown.accept(pair.frau);
            checkPersonAlreadyKnown.accept(pair.mann);
            if (zusatztype != AYTO_Permutator.ZUSATZTYPE.BISEXUAL) {
              validateMatchingPairs(constellation);
            }
          }
        }
        else {
          throw new IllegalArgumentException("Event-Klasse kann noch nicht verarbeitet werden: " + evt.getClass()
                .getSimpleName());
        }
      }
    }
  }

  private void validateMatchingPairs(Collection<AYTO_Pair> constellation) {
    Set<Person> frauen = new LinkedHashSet<>();
    Set<Person> maenner = new LinkedHashSet<>();
    for (AYTO_Pair pair : constellation) {
      frauen.add(pair.frau);
      maenner.add(pair.mann);
    }
    if (frauen.size() != matchingPairCount) {
      throw new RuntimeException(
            "Falsche Anzahl an Frauen in Matching Night: " + frauen.size() + " benötigt: " + matchingPairCount + " sind: "
                  + frauen.size() + " " + frauen);
    }
    if (maenner.size() != matchingPairCount) {
      throw new RuntimeException(
            "Falsche Anzahl an Männern in Matching Night: " + maenner.size() + " benötigt: " + matchingPairCount + " sind: "
                  + maenner.size() + " " + maenner);
    }
  }

  /**
   * Wenn es ein PerfectMatch gibt, zieht das Paar aus. Wenn es jemand geben sollte der nun
   * kein PerfectMatch mehr hat, zieht dieser ebenfalls aus.
   * <p>
   * Bei ZUSATZTYPE.NUR_LETZTER heißt dies im Umkehrschluss, dass es definitiv auch kein
   * PerfektMatch zwischen der ZusatzPerson geben kann.
   * <p>
   * Keine NewPerson-Events bei {@link demawi.ayto.permutation.AYTO_Permutator.ZUSATZTYPE#BISEXUAL}
   */
  private void checkForImplicits() {
    List<AYTO_Pair> previousPerfectMatches = new ArrayList<>();
    Person newPerson = null;
    for (int tagNr = 1, l = tage.size(); tagNr <= l; tagNr++) {
      for (Event event : getTag(tagNr).getEvents()) {
        if (event instanceof MatchBoxResult) {
          MatchBoxResult result = (MatchBoxResult) event;
          if (result.result != null && result.result) {
            previousPerfectMatches.add(result.pair);

            if (newPerson != null) {
              if (newPerson instanceof Frau) {
                for (Person frau : getZusatzpersonen(tagNr)) {
                  addImplicitForPerfectMatchEvent(result, frau, result.pair.mann, result.pairWeitererAuszug);
                }
              }
              else {
                for (Person mann : getZusatzpersonen(tagNr)) {
                  addImplicitForPerfectMatchEvent(result, result.pair.frau, mann, result.pairWeitererAuszug);
                }
              }
            }
          }
        }
        else if (event instanceof NewPerson) {
          NewPerson personEvent = (NewPerson) event;
          newPerson = personEvent.person;
          if (personEvent.implicits.isEmpty()) {
            // Annahme: alle vorherigen PerfectMatches sind kein Partner zu der neuen Person.
            // tritt bei ZusatzType.JEDER nicht auf, da die Person von vornherein dabei ist.
            if (newPerson instanceof Frau) {
              for (AYTO_Pair perfectMatch : previousPerfectMatches) {
                addImplicitForPerfectMatchEvent(personEvent, (Frau) newPerson, perfectMatch.mann, null);
              }
            }
            else {
              for (AYTO_Pair perfectMatch : previousPerfectMatches) {
                addImplicitForPerfectMatchEvent(personEvent, perfectMatch.frau, (Mann) newPerson, null);
              }
            }
          }
        }
      }
    }
  }

  private void addImplicitForPerfectMatchEvent(EventWithImplicits implicitEvent, Person frau, Person mann,
        AYTO_Pair pairWeitererAuszug) {
    AYTO_Pair implicitPair = AYTO_Pair.pair(frau, mann);
    if (implicitEvent instanceof MatchBoxResult && ((MatchBoxResult) implicitEvent).pair.equals(implicitPair)) {
      return;
    }

    if (pairWeitererAuszug != null && pairWeitererAuszug.equals(implicitPair)) {
      implicitEvent.implicits.add(new MatchBoxResult(implicitPair, true));
    }
    else {
      implicitEvent.implicits.add(new MatchBoxResult(implicitPair, false));
    }
  }

}

