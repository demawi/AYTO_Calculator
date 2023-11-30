package demawi.ayto;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.SeasonData;
import demawi.ayto.print.DefaultMatchPrinter;
import demawi.ayto.print.Formatter;
import demawi.ayto.print.MatchPrinter;
import demawi.ayto.util.Language;
import demawi.ayto.util.LogarithmicProgression;

public class AllResultsPrinter {

   private static final NumberFormat numberFormat = new DecimalFormat("00");

   public static void print(Set<SeasonData> staffeln)
         throws Exception {

      File resultsDirectory = new File("results");
      Map<String, Map<Integer, List<AYTO_Result>>> allResults = new LinkedHashMap<>();
      for (SeasonData staffel : staffeln) {
         String subDir = getCountryDirectoryName(staffel); // z.B. "de"
         if (subDir != null) {
            Language lang = subDir.startsWith("/de") ? Language.DE : Language.EN;
            String season;
            String night;
            if (lang == Language.DE) {
               season = "Staffel";
               night = "Nacht";
            }
            else {
               season = "Season";
               night = "Night";
            }
            String dirName = subDir + "/" + season + " " + staffel.name;
            File seasonDirectory = new File("results" + dirName);
            System.out.println(seasonDirectory.getAbsoluteFile());
            seasonDirectory.mkdirs();
            DefaultMatchPrinter finder = new DefaultMatchPrinter(staffel);
            finder.setLanguage(lang);

            for (int tagNr = 1, l = staffel.getAnzahlTage(); tagNr <= l; tagNr++) {
               File file = new File(
                     seasonDirectory.getAbsoluteFile() + "/" + night + numberFormat.format(tagNr) + ".txt");
               System.out.println("Write file " + file.getAbsoluteFile() + "...");
               if (!file.exists()) {
                  writeDayViaMemoryBuffer(tagNr, finder, file);
               }
            }

            Map<Integer, List<AYTO_Result>> seasonResults = finder.getAllCalculatedResults();
            allResults.put(dirName, seasonResults);

            // Print Season Summary
            File file = new File(seasonDirectory.getAbsoluteFile() + "/_Summary.txt");
            if (!file.exists()) {
               FileWriter fileWriter = new FileWriter(file);
               seasonResults.forEach((tagNr, results) -> {
                  AYTO_Result lastDayResult = results.get(results.size() - 1);
                  try {
                     fileWriter.write(tagNr + ": PossiblePairs: " + lastDayResult.possiblePairCount.size()
                           + " Overall progression: " + LogarithmicProgression.getProgression(lastDayResult.possible,
                           lastDayResult.totalConstellations) + "\n");
                  }
                  catch (IOException e) {
                     throw new RuntimeException(e);
                  }
               });
               fileWriter.close();
            }
         }
      }

      // Print summary over all seasons
      File file = new File(resultsDirectory.getAbsoluteFile() + "/_SummaryAll.txt");
      if (!file.exists()) {
         FileWriter fileWriter = new FileWriter(file);
         fileWriter.write("Name;1;2;3;4;5;6;7;8;9;10\n");
         allResults.forEach((dirName, seasonResults) -> {
            StringBuilder line = new StringBuilder();
            line.append(dirName);
            seasonResults.forEach((tagNr, results) -> {
               AYTO_Result lastDayResult = results.get(results.size() - 1);
               line.append(";");
               line.append(
                     LogarithmicProgression.getProgression(lastDayResult.possible, lastDayResult.totalConstellations));
            });
            try {
               fileWriter.write(line + "\n");
            }
            catch (IOException e) {
               throw new RuntimeException(e);
            }
         });
         fileWriter.close();
      }
   }

   private static String getCountryDirectoryName(SeasonData staffelData) {
      String root = AllResultsPrinter.class.getPackageName();
      String staffel = staffelData.getClass()
            .getPackageName();
      if (!staffel.startsWith(root))
         return null;
      return staffel.substring(root.length())
            .replace(".", "/");
   }

   private static void writeDayViaMemoryBuffer(int tagNr, MatchPrinter finder, File file)
         throws IOException {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      Writer writer = new OutputStreamWriter(buffer);
      finder.setOut(str -> {
         try {
            writer.write(str + "\r\n");
         }
         catch (IOException e) {
            e.printStackTrace();
         }
      });
      finder.printLastDayResults(tagNr);
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
      LinkedHashSet<SeasonData> allInstances = new InstanceFinder().findAllInstances(SeasonData.class);
      allInstances.forEach(instance -> instance.setName(getName(instance)));
      print(allInstances);
      System.out.println("AllOutPrinter time: " + Formatter.minSecs(System.currentTimeMillis() - start));
   }

   private static String getName(SeasonData staffelData) {
      String simpleName = staffelData.getClass()
            .getSimpleName();
      if (simpleName.startsWith("AYTO_")) {
         return simpleName.substring(5);
      }
      return simpleName;
   }

}
