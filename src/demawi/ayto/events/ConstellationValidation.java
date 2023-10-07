package demawi.ayto.events;

import java.util.Collection;

import demawi.ayto.modell.AYTO_Pair;

public interface ConstellationValidation {

   boolean isValid(Collection<AYTO_Pair> constellation, PairInterpreter lookup);

}
