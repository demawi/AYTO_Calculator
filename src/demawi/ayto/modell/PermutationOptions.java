package demawi.ayto.modell;

import java.util.List;

import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.permutation.AYTO_Permutator;

public interface PermutationOptions {

   List<Frau> getFrauen();

   List<Mann> getMaenner();

   AYTO_Permutator.ZUSATZTYPE getZusatztype();
}
