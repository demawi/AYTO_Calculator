package demawi.ayto.print;

import java.util.List;

public class TableFormatter {

   public static String formatAsTable(List<List<String>> rows) {
      int[] maxLengths = new int[rows.get(0)
            .size()];
      for (List<String> row : rows) {
         for (int i = 0; i < row.size(); i++) {
            maxLengths[i] = Math.max(maxLengths[i], row.get(i)
                  .length());
         }
      }

      StringBuilder formatBuilder = new StringBuilder();
      for (int maxLength : maxLengths) {
         formatBuilder.append("%-")
               .append(maxLength + 2)
               .append("s");
      }
      String format = formatBuilder.toString();

      StringBuilder result = new StringBuilder();
      for (List<String> row : rows) {
         result.append(String.format(format, row.toArray(new String[row.size()])))
               .append("\r\n");
      }
      return result.toString();
   }


}
