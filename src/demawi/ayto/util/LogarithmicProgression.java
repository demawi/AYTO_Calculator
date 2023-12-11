package demawi.ayto.util;

import demawi.ayto.print.Formatter;

public class LogarithmicProgression {

   private static final double PROGRESSION_VALUE = 1000000;

   /**
    * Try of an implementation to see the overall progression to compare the seasons.
    * <p>
    * In the beginning a lot of combinations where filtered out, later on it's reduced, due to the previously filtered out combinations.
    * So we can not use a linear formula for this. In the beginning it weighs much less to filter out one single combination, than later in the show.
    * In the end we want a linear metric output over the nights. So we can compare them each other.
    * <p>
    * Over the different shows we have different total combinations. To compare the seasons we should have a unified metric. So filtering out 10% of the total combinations should have the
    * same progression output.
    */
   public static String getProgression(long current, long max) {
      // First unify over all seasons (because of different max values)
      double relativeProgression = 1.0 + ((current - 1.0) / ((max - 1.0) / PROGRESSION_VALUE)); // [1, PROGRESSION_VALUE + 1]
      relativeProgression = relativeProgression / PROGRESSION_VALUE * (PROGRESSION_VALUE - 1); // [1, PROGRESSION_VALUE]
      // After that weigh with logarithmic function
      double progression = (1 - Math.log(relativeProgression) / Math.log(PROGRESSION_VALUE));
      // Rounding down with 2 decimal precision
      progression = Math.floor(progression * 10000) / (double) 10000;
      return Formatter.numberFormat(100.0 * progression) + "%";
   }

   public static void main(String[] args) {
      int maxValue = 3628800;
      System.out.println("0%  => "+getProgression(maxValue, maxValue));
      System.out.println("90% => "+getProgression((int) (maxValue * 0.9), maxValue)); // 90%
      System.out.println("50% => "+getProgression((int) (maxValue * 0.5), maxValue)); // 50%
      System.out.println("30% => "+getProgression((int) (maxValue * 0.3), maxValue)); // 10%
      System.out.println("20% => "+getProgression((int) (maxValue * 0.2), maxValue)); // 10%
      System.out.println("10% => "+getProgression((int) (maxValue * 0.1), maxValue)); // 10%
      System.out.println("1%  => "+getProgression((int) (maxValue * 0.01), maxValue)); // 1%
      System.out.println("2   => "+getProgression(2, 3628800));
      System.out.println("1   => "+getProgression(1, 3628800));

      maxValue = 199584000;
      System.out.println("0%  => "+getProgression(maxValue, maxValue));
      System.out.println("90% => "+getProgression((int) (maxValue * 0.9), maxValue)); // 90%
      System.out.println("50% => "+getProgression((int) (maxValue * 0.5), maxValue)); // 50%
      System.out.println("10% => "+getProgression((int) (maxValue * 0.1), maxValue)); // 10%
      System.out.println("1%  => "+getProgression((int) (maxValue * 0.01), maxValue)); // 1%
      System.out.println("2   => "+getProgression(2, 199584000));
      System.out.println("1   => "+getProgression(1, 199584000));
   }
}
