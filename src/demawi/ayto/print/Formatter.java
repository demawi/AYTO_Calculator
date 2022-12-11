package demawi.ayto.print;

public class Formatter {

   public static String minSecs(long mills) {
      long min = Math.floorDiv(mills, 60 * 1000);
      long secs = Math.floorDiv(mills - min * 60 * 1000, 1000);
      return min + " min " + secs + " secs";
   }

   public static String prozent(double anteil, double von) {
      return String.format("%.2f", 100 * anteil / von);
   }

   public static String numberFormat(double number) {
      return String.format("%5s", String.format("%.2f", number));
   }

}
