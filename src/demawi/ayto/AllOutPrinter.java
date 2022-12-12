package demawi.ayto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import demawi.ayto.modell.StaffelData;
import demawi.ayto.print.EventbasedMatchPrinter;
import demawi.ayto.print.Formatter;
import demawi.ayto.print.MatchPrinter;

public class AllOutPrinter {

   private static NumberFormat numberFormat = new DecimalFormat("00");

   public static void print(StaffelData... staffeln)
         throws Exception {
      for (StaffelData staffel : staffeln) {
         staffel.closeForInput();
         File directory = new File("./results/Staffel " + staffel.name);
         System.out.println(directory.getAbsoluteFile());
         directory.mkdirs();
         MatchPrinter finder = new EventbasedMatchPrinter();

         for (int i = 0, l = staffel.getAnzahlTage(); i < l; i++) {
            File file = new File(directory.getAbsoluteFile() + "/Nacht" + numberFormat.format(i + 1) + ".txt");
            System.out.println("Write file " + file.getAbsoluteFile() + "...");
            if (!file.exists()) {
               FileWriter out = new FileWriter(file);
               finder.setOut(str -> {
                  try {
                     out.write(str + "\n");
                  }
                  catch (IOException e) {
                     e.printStackTrace();
                  }
               });
               finder.printDayResults(staffel, i + 1);
               out.flush();
               out.close();
            }
         }
      }
   }

   public static void main(String[] args)
         throws Exception {
      long start = System.currentTimeMillis();
      print(new AYTO_VIP01(), new AYTO_VIP02(), new AYTO_1(), new AYTO_2(), new AYTO_3(), new AYTO_4());
      System.out.println("AllOutPrinter time: " + Formatter.minSecs(System.currentTimeMillis() - start));
   }

}
