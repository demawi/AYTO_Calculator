package demawi.ayto.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import demawi.ayto.modell.AYTO_Pair;

public class EventWithImplicits
      implements Event {

   public final List<MatchBoxResult> implicits = new ArrayList<>();

   public boolean isValid(Collection<AYTO_Pair> constellation) {
      for (MatchBoxResult implicit : implicits) {
         if (!implicit.isValid(constellation)) {
            return false;
         }
      }
      return true;
   }

}
