## Calculate probabilities for dating show "Are You The One" (with german and U.S. data and results)

Multi-threaded Java-based probability calculator for the series "Are you the one"

The probabilities for german and U.S. seasons were generated in the "results"-folder.

Features:
<ul>
<li>Various (“permutation”) modes are supported:
   <ul>
     <li>Standard: no special features, each person has exactly one potential match</li>
     <li>Marked: Additional persons can be marked, which means they can be a double match with someone else (this can be all of one group, or only one, or only two...)</li>
     <li>Bisexual: Each person can be in a relationship with any other person (for the bisexual season)</li>
   </ul>
<li>Different event-types can occur and can be entered in any order.
<ul>
   <li>Event "MatchBox": Probabilities for outcome of the result (yes/no). Implicit assumptions for possible double matches, if someone move out and others not.</li>
   <li>Event "MatchingNight": Probabilities for outcome of the result (0-11 lights)</li>
   <li>Event "NewPerson" (a new person comes into play): Implicit assumptions for possible double matches, if someone move out and others not.</li>
   <li>Event "SameMatch" (when someone is offered to eliminate their double match): all previous events will be recalculated with this additional information</li>
</ul>
<li>Overall table representation including probabilities of all potential pairs.
<li>Multi-threading performance optimization. A complete season including all intermediate probabilities is usually calculated within a minute.
</ul>
