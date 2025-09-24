package demawi.ayto.permutation;

public class Mark {
   /**
    * The persons marked with this, can have a shared match.
    */
   public static final Mark CAN_BE_AN_EXTRA_MATCH = new Mark("*");
   /**
    * Ist ein drittes Match
    */
   public static final Mark IS_A_THIRD_MATCH = new Mark("*");

   private final String val;

   public Mark(String val) {
      this.val = val;
   }

   @Override
   public String toString() {
      return val;
   }
}
