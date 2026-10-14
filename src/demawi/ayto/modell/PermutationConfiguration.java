package demawi.ayto.modell;

import demawi.ayto.permutation.AYTO_Permutator;

public class PermutationConfiguration {

   private final Integer maxMatches;
   private final AYTO_Permutator.Mode mode;
   private final Integer parallelism;

   public PermutationConfiguration(AYTO_Permutator.Mode mode, Integer maxMatches) {
      this(mode, maxMatches, null);
   }

   public PermutationConfiguration(AYTO_Permutator.Mode mode, Integer maxMatches, Integer parallelism) {
      if (parallelism != null && parallelism < 1) {
         throw new IllegalArgumentException("parallelism must be at least 1");
      }
      this.mode = mode;
      this.maxMatches = maxMatches;
      this.parallelism = parallelism;
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

   public Integer getParallelism() {
      return parallelism;
   }

}
