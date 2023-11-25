package demawi.ayto.modell;

import java.util.List;

import demawi.ayto.permutation.AYTO_Permutator;

public interface PermutationOptions {

   List<Person> getFrauen();

   List<Person> getMaenner();

   AYTO_Permutator.MODE getZusatztype();
}
