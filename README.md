  # AYTO_Calculator
Multi-Threaded Java-basierter Kalkulator der Wahrscheinlichkeiten für die Serie "Are you the one"

Die Daten deutscher Staffeln sind eingepflegt.

Features:
<ul>
<li>Es werden verschiedene ("Permutations"-)Modi unterstützt:
  <ul>
    <li>Standard: keine Besonderheiten jede Person hat genau ein potenzielles Match</li>
    <li>Nur die 11te Person ist ein Doppelmatch zu jemand anderem</li>
    <li>Jede der größeren Personengruppe kann ein Doppelmatch zu jemand anderem sein</li>
    <li>Jede Person kann mit jeder anderen Person lieert sein (für die Bisexuell-Staffel)</li>    
  </ul>
<li>Ereignisse können in beliebiger Reihenfolge auftreten und eingepflegt werden.
<ul>
  <li>Ereignis "MatchBox": Wahrscheinlichkeiten für Ausgang des Ergebnisses (Ja/Nein)
  <li>Ereignis "MatchingNight": Wahrscheinlichkeiten für Ausgang des Ergebnisses (0-11 Lichter)
  <li>Ereignis "NeuePerson" bzw. Auszug aus der Villa bei MatchBox=true: zusäztliche Annahmen bei möglichen Doppelmatches, wenn andere Personen nicht mit ausziehen
</ul>
<li>Gesamtdarstellung inkl. Wahrscheinlichkeiten aller Paare
<li>Multi-threading Performance Optimierung. Eine komplette Staffel inkl. aller (Zwischen-)Wahrscheinlichkeiten wird üblicherweise innerhalb einer Minute durchkalkuliert.
</ul>
