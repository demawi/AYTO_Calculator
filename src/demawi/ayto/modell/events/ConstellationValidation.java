package demawi.ayto.modell.events;

import java.util.Collection;

import demawi.ayto.modell.AYTO_Pair;

/**
 * Any event or collection of events, where we can ask if a constellation is valid.
 */
public interface ConstellationValidation {

   boolean isValid(Collection<AYTO_Pair> constellation, PairInterpreter lookup);

}
