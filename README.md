## Calculate probabilities for dating show Are You The One (with german and U.S. results)

Multi-threaded Java-based probability calculator for the series "Are you the one"

The probabilities for german and U.S. seasons were generated in the "results"-folder.

Features:
<ul>
<li>Various (“permutation”) modes are supported:
   <ul>
     <li>Standard: no special features, each person has exactly one potential match</li>
     <li>Only the 11th person is a double match with someone else</li>
     <li>Each of the larger group of people can be a double match to someone else</li>
     <li>Each person can be in a relationship with any other person (for the bisexual season)</li>
   </ul>
<li>Different event-types can occur and be entered in any order.
<ul>
   <li>Event "MatchBox": Probabilities for outcome of the result (yes/no)
   <li>Event "MatchingNight": Probabilities for outcome of the result (0-11 lights)
   <li>Event "New Person" or moving out of the villa if MatchBox=true: additional assumptions for possible double matches if other people do not move out
</ul>
<li>Overall table representation including probabilities of all potential pairs.
<li>Multi-threading performance optimization. A complete season including all (intermediate) probabilities is usually calculated within a minute.
</ul>
