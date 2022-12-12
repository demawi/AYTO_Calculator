package demawi.ayto.events;

import java.util.Collection;

import demawi.ayto.modell.Pair;

/**
 * Marker type for all events
 */
public interface Event {

   boolean test(Collection<Pair> constellation);

}
