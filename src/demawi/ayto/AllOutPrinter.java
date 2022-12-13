package demawi.ayto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
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
         File directory = new File("./results/Staffel " + staffel.name);
         System.out.println(directory.getAbsoluteFile());
         directory.mkdirs();
         MatchPrinter finder = new EventbasedMatchPrinter();

         for (int tagNr = 1, l = staffel.getAnzahlTage(); tagNr <= l; tagNr++) {
            File file = new File(directory.getAbsoluteFile() + "/Nacht" + numberFormat.format(tagNr) + ".txt");
            System.out.println("Write file " + file.getAbsoluteFile() + "...");
            if (!file.exists()) {
               writeViaMemoryBuffer(staffel, tagNr, finder, file);
            }
         }
      }
   }

   private static void writeViaMemoryBuffer(StaffelData staffel, int tagNr, MatchPrinter finder, File file)
         throws IOException {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      Writer writer = new OutputStreamWriter(buffer);
      finder.setOut(str -> {
         try {
            writer.write(str + "\n");
         }
         catch (IOException e) {
            e.printStackTrace();
         }
      });
      finder.printDayResults(staffel, tagNr);
      writer.flush();
      writer.close();

      OutputStream out = new FileOutputStream(file);
      out.write(buffer.toByteArray());
      out.flush();
      out.close();
   }

   private static void writeDirect(StaffelData staffel, int tagNr, MatchPrinter finder, File file)
         throws IOException {
      Writer out = new FileWriter(file);
      finder.setOut(str -> {
         try {
            out.write(str + "\n");
         }
         catch (IOException e) {
            e.printStackTrace();
         }
      });
      finder.printDayResults(staffel, tagNr);
      out.flush();
      out.close();
   }

   public static void main(String[] args)
         throws Exception {
      long start = System.currentTimeMillis();
      print(new AYTO_VIP01(), new AYTO_VIP02(), new AYTO_1(), new AYTO_2(), new AYTO_3(), new AYTO_4());
      System.out.println("AllOutPrinter time: " + Formatter.minSecs(System.currentTimeMillis() - start));
   }

}
