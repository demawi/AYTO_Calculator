package demawi.ayto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Set;

import demawi.ayto.modell.StaffelData;
import demawi.ayto.print.DefaultMatchPrinter;
import demawi.ayto.print.Formatter;
import demawi.ayto.print.MatchPrinter;

public class AllResultsPrinter {

   private static final NumberFormat numberFormat = new DecimalFormat("00");

   public static void print(Set<StaffelData> staffeln)
         throws Exception {
      for (StaffelData staffel : staffeln) {
         String subDir = getSubDir(staffel); // z.B. "de"
         if (subDir != null) {
            File directory = new File("results" + subDir + "/Staffel " + staffel.name);
            System.out.println(directory.getAbsoluteFile());
            directory.mkdirs();
            MatchPrinter finder = new DefaultMatchPrinter();

            for (int tagNr = 1, l = staffel.getAnzahlTage(); tagNr <= l; tagNr++) {
               File file = new File(directory.getAbsoluteFile() + "/Nacht" + numberFormat.format(tagNr) + ".txt");
               System.out.println("Write file " + file.getAbsoluteFile() + "...");
               if (!file.exists()) {
                  writeViaMemoryBuffer(staffel, tagNr, finder, file);
               }
            }
         }
      }
   }

   private static String getSubDir(StaffelData staffelData) {
      String root = AllResultsPrinter.class.getPackageName();
      String staffel = staffelData.getClass()
            .getPackageName();
      if (!staffel.startsWith(root))
         return null;
      return staffel.substring(root.length())
            .replace(".", "/");
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

   public static void main(String[] args)
         throws Exception {
      long start = System.currentTimeMillis();
      print(new InstanceFinder().findAllInstances(StaffelData.class));
      System.out.println("AllOutPrinter time: " + Formatter.minSecs(System.currentTimeMillis() - start));
   }

}
