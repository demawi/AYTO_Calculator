package demawi.ayto.print;

public class Formatter {

   public static String minSecs(long mills) {
      long totalSeconds = Math.max(0, mills / 1000);
      long secs = totalSeconds % 60;
      long totalMinutes = totalSeconds / 60;
      long min = totalMinutes % 60;
      long hours = totalMinutes / 60;
      if (hours == 0) {
         return min + " min " + secs + " secs";
      }
      long days = hours / 24;
      hours %= 24;
      if (days == 0) {
         return hours + " hours " + min + " min " + secs + " secs";
      }
      return days + " days " + hours + " hours " + min + " min " + secs + " secs";
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
