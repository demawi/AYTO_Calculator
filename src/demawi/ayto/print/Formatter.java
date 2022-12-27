package demawi.ayto.print;

public class Formatter {

   public static String minSecs(long mills) {
      long min = Math.floorDiv(mills, 60 * 1000);
      long secs = Math.floorDiv(mills - min * 60 * 1000, 1000);
      return min + " min " + secs + " secs";
   }

   public static String prozent(double anteil, double von) {
      return prozent(anteil / von);
   }

   public static String prozent(double prozent) {
      return String.format("%.2f", 100 * prozent);
   }

   public static String numberFormat(double number) {
      return String.format("%5s", String.format("%.2f", number));
   }

}
