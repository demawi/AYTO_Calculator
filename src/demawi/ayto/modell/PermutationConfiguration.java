package demawi.ayto.modell;

import demawi.ayto.permutation.AYTO_Permutator;

public class PermutationConfiguration {

   private final Integer maxMatches;
   private final AYTO_Permutator.Mode mode;

   public PermutationConfiguration(AYTO_Permutator.Mode mode, Integer maxMatches) {
      this.mode = mode;
      this.maxMatches = maxMatches;
   }

   public PermutationConfiguration(AYTO_Permutator.Mode mode) {
      this(mode, null);
   }

   public Integer getMaxMatches() {
      return maxMatches;
   }

   public AYTO_Permutator.Mode getMode() {
      return mode;
   }
}
