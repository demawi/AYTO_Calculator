package demawi.ayto.modell;

import java.util.*;
import java.util.function.Consumer;

import demawi.ayto.modell.events.*;
import demawi.ayto.permutation.AYTO_Permutator;
import demawi.ayto.permutation.Mark;

public class SeasonData {

   public String name;
   private final PermutationConfiguration permCfg;
   private final int matchingPairCount;
   private final boolean validiation;

   private final List<Day> tage = new ArrayList<>();
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

   protected Woman frau(String name) {
      return frau(name, false);
   }

   protected Woman frau(String name, boolean late) {
      return frau(name, null, late);
   }

   protected Woman frau(String name, Mark mark) {
      return frau(name, mark, false);
   }

   protected Woman frau(String name, Mark mark, boolean lateCheckin) {
      Woman frau = new Woman(name);
      frau.mark(mark);
      if (!lateCheckin) {
         initialFrauen.add(frau);
      }
      return frau;
   }

   protected Man mann(String name) {
      return mann(name, false);
   }

   protected Man mann(String name, boolean late) {
      return mann(name, null, late);
   }

   protected Man mann(String name, Mark mark) {
      return mann(name, mark, false);
   }

   protected Man mann(String name, Mark mark, boolean lateCheckin) {
      Man mann = new Man(name);
      mann.mark(mark);
      if (!lateCheckin) {
         initialMaenner.add(mann);
      }
      return mann;
   }

   public SeasonData(PermutationConfiguration permCfg) {
      this(permCfg, 10);
   }

   public SeasonData(PermutationConfiguration permCfg, int matchingPairCount) {
      this.permCfg = permCfg;
      this.matchingPairCount = matchingPairCount;
      this.validiation = true;
   }

   public SeasonData(PermutationConfiguration permCfg, int matchingPairCount, boolean validation) {
      this.permCfg = permCfg;
      this.matchingPairCount = matchingPairCount;
      this.validiation = validation;
   }

   public void setName(String name) {
      this.name = name;
   }

   protected Day newDay() {
      Day tag = Day.create(matchingPairCount);
      tage.add(tag);
      return tag;
   }

   public int getMatchingPairCount() {
      return matchingPairCount;
   }

   public PermutationConfiguration getPermCfg() {
      return permCfg;
   }

   public Day getTag(int tagNr) {
      return tage.get(tagNr - 1);
   }

   public int getAnzahlTage() {
      return tage.size();
   }

   public List<Day> getTage() {
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
         if (event instanceof NewPerson && ((NewPerson) event).person instanceof Woman) {
            result.add(((NewPerson) event).person);
         }
         if (event instanceof SameMatch && ((SameMatch) event).getSecond() instanceof Woman) {
            result.remove(((SameMatch) event).getSecond());
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
         if (event instanceof NewPerson && ((NewPerson) event).person instanceof Man) {
            result.add(((NewPerson) event).person);
         }
         if (event instanceof SameMatch && ((SameMatch) event).getSecond() instanceof Man) {
            result.remove(((SameMatch) event).getSecond());
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
         Day tag = tage.get(i);
         List<Event> curEvents = tag.getEvents();
         for (int j = 0, l = (i == tagNr - 1) ? eventCount : curEvents.size(); j < l; j++) {
            result.add(curEvents.get(j));
         }
      }
      return result;
   }

   public List<Person> getZusatzpersonen(int tagNr) {
      return getPermCfg().getMode()
            .getExtraMatchPersons(getFrauen(tagNr), getMaenner(tagNr));
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
      Consumer<Person> personValidationCheck = (person) -> {
         if (person != null) {
            if (person instanceof Woman) {
               if (!curFrauen.contains(person)) {
                  throw new IllegalStateException(
                        "Frau existiert noch nicht rechtzeitig: " + person + " an Tag " + (currentConsistenceDay + 1)
                              + ". Die Person muss lateCheckIn=false aufweisen und im Nachhinein über ein NewPerson-Event hinzugefügt werden!");
               }
            }
            else if (person instanceof Man) {
               if (!curMaenner.contains(person)) {
                  throw new IllegalStateException(
                        "Mann existiert noch nicht rechtzeitig: " + person + " an Tag " + (currentConsistenceDay + 1)
                              + ". Die Person muss lateCheckIn=false aufweisen und im Nachhinein über ein NewPerson-Event hinzugefügt werden!");
               }
            }
         }
      };
      for (int i = 0, l = tage.size(); i < l; i++) {
         Day tag = tage.get(i);
         currentConsistenceDay = i;
         for (Event evt : tag.getEvents()) {
            if (evt instanceof NewPerson) {
               NewPerson event = (NewPerson) evt;
               Person newPerson = event.person;
               if (newPerson instanceof Woman) {
                  curFrauen.add(newPerson);
               }
               else {
                  curMaenner.add(newPerson);
               }
            }
            else if (evt instanceof MatchBoxResult) {
               MatchBoxResult event = (MatchBoxResult) evt;
               AYTO_Pair pair = event.pair;
               personValidationCheck.accept(pair.frau);
               personValidationCheck.accept(pair.mann);
            }
            else if (evt instanceof MatchingNight) {
               MatchingNight event = (MatchingNight) evt;
               Set<AYTO_Pair> constellation = event.constellation;
               for (AYTO_Pair pair : constellation) {
                  personValidationCheck.accept(pair.frau);
                  personValidationCheck.accept(pair.mann);
                  if (permCfg.getMode() != AYTO_Permutator.Mode.BISEXUAL) {
                     validateMatchingPairs(constellation);
                  }
               }
            }
            else if (evt instanceof SameMatch) {
               SameMatch event = (SameMatch) evt;
               personValidationCheck.accept(event.getFirst());
               personValidationCheck.accept(event.getSecond());
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
      if (validiation) {
         if (frauen.size() != matchingPairCount) {
            throw new RuntimeException(
                  "Falsche Anzahl an Frauen in Matching Night: " + frauen.size() + " benötigt: " + matchingPairCount
                        + " sind: " + frauen.size() + " " + frauen);
         }
         if (maenner.size() != matchingPairCount) {
            throw new RuntimeException(
                  "Falsche Anzahl an Männern in Matching Night: " + maenner.size() + " benötigt: " + matchingPairCount
                        + " sind: " + maenner.size() + " " + maenner);
         }
      }
   }

   /**
    * Wenn es ein PerfectMatch gibt, zieht das Paar aus. Wenn es jemand geben sollte der nun
    * kein PerfectMatch mehr hat, zieht dieser ebenfalls aus.
    * <p>
    * Bei ZUSATZTYPE.NUR_LETZTER heißt dies im Umkehrschluss, dass es definitiv auch kein
    * PerfektMatch zwischen der ZusatzPerson geben kann.
    * <p>
    * Keine NewPerson-Events bei {@link AYTO_Permutator.Mode#BISEXUAL}
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

                  Person resultFrau = (result.pair.mann instanceof Woman) ? result.pair.mann : result.pair.frau;
                  Person resultMann = (result.pair.frau instanceof Woman) ? result.pair.mann : result.pair.frau;
                  if (newPerson != null) {
                     if (newPerson instanceof Woman) {
                        for (Person frau : getZusatzpersonen(tagNr)) {
                           addImplicitForPerfectMatchEvent(result, frau, resultMann, result.additionalMoveouts);
                        }
                     }
                     else {
                        for (Person mann : getZusatzpersonen(tagNr)) {
                           addImplicitForPerfectMatchEvent(result, resultFrau, mann, result.additionalMoveouts);
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
                  if (newPerson instanceof Woman) {
                     for (AYTO_Pair perfectMatch : previousPerfectMatches) {
                        addImplicitForPerfectMatchEvent(personEvent, newPerson, perfectMatch.mann, null);
                     }
                  }
                  else {
                     for (AYTO_Pair perfectMatch : previousPerfectMatches) {
                        addImplicitForPerfectMatchEvent(personEvent, perfectMatch.frau, newPerson, null);
                     }
                  }
               }
            }
         }
      }
   }

   private void addImplicitForPerfectMatchEvent(EventWithImplicits implicitEvent, Person frau, Person mann,
         AYTO_Pair[] pairWeitererAuszuege) {
      AYTO_Pair implicitPair = AYTO_Pair.pair(frau, mann);
      if (implicitEvent instanceof MatchBoxResult && ((MatchBoxResult) implicitEvent).pair.equals(implicitPair)) {
         return;
      }

      if (pairWeitererAuszuege != null && Arrays.asList(pairWeitererAuszuege)
            .contains(implicitPair)) {
         implicitEvent.implicits.add(new MatchBoxResult(implicitPair, true));
      }
      else {
         implicitEvent.implicits.add(new MatchBoxResult(implicitPair, false));
      }
   }

}

