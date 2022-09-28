package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TablePrinter {

   private List<List<String>> table = new ArrayList<>();

   public void addRow(String... args) {
      table.add(Arrays.asList(args));
   }

   public static void main(String[] args) {
      TablePrinter table = new TablePrinter();
      table.addRow("", "Cecilia", "Karina","Isabelle","Anna","Gina","Luisa","Celina","Ricarda","Zoe","Franziska");
      table.addRow("Calvin","2,23 %","25,56 %","11,29 %","6,33 %","24,07 %","-","9,31 %","0,25 %","8,93 %","12,03 %");
      // System.out.print(formatAsTable(table.table));
      String format = "%.2f";
      System.out.println("|  -  |");
      System.out.println("|  \uD83D\uDDF8  |");
      System.out.println("|  ✅  |");
      //System.out.println("|"+ String.format("%5s", String.format(format, 4.7))+"|");
   }

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
               .append("\n");
      }
      return result.toString();
   }


}
