package demawi.ayto.modell;

public class Markierung {
   public static final Markierung CAN_BE_A_DOUBLE = new Markierung("*");

   private final String val;

   public Markierung(String val) {
      this.val = val;
   }

   @Override
   public String toString() {
      return val;
   }
}
