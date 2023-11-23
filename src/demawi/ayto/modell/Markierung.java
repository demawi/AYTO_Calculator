package demawi.ayto.modell;

public class Markierung {
   /**
    * The persons marked with this, can have a shared match.
    */
   public static final Markierung CAN_BE_AN_EXTRA_MATCH = new Markierung("*");

   private final String val;

   public Markierung(String val) {
      this.val = val;
   }

   @Override
   public String toString() {
      return val;
   }
}
