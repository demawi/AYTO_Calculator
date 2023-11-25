package demawi.ayto.modell.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import demawi.ayto.modell.AYTO_Pair;

public class EventWithImplicits
      implements Event {

   public final List<MatchBoxResult> implicits = new ArrayList<>();

   public boolean isValid(Collection<AYTO_Pair> constellation, PairInterpreter lookup) {
      for (MatchBoxResult implicit : implicits) {
         if (!implicit.isValid(constellation, lookup)) {
            return false;
         }
      }
      return true;
   }

}
