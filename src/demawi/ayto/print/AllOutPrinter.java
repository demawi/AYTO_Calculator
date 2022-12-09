package demawi.ayto.print;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import demawi.ayto.AYTO_1;
import demawi.ayto.AYTO_2;
import demawi.ayto.AYTO_3;
import demawi.ayto.AYTO_4;
import demawi.ayto.AYTO_VIP01;
import demawi.ayto.AYTO_VIP02;
import demawi.ayto.modell.AYTO_Data;

public class AllOutPrinter {

   private static NumberFormat numberFormat = new DecimalFormat("00");

   public static void print(AYTO_Data... staffeln)
         throws Exception {
      for (AYTO_Data staffel : staffeln) {
         File directory = new File("./results/Staffel " + staffel.name);
         System.out.println(directory.getAbsoluteFile());
         directory.mkdirs();
         DefaultMatchPrinter finder = new DefaultMatchPrinter();

         for (int i = 0, l = staffel.getAnzahlTage(); i < l; i++) {
            File file = new File(directory.getAbsoluteFile() + "/Nacht" + numberFormat.format(i + 1) + ".txt");
            System.out.println("Write file " + file.getAbsoluteFile() + "...");
            if(!file.exists()) {
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
      print(new AYTO_VIP01(), new AYTO_1(), new AYTO_2(), new AYTO_3(), new AYTO_VIP02(), new AYTO_4());
   }

}
