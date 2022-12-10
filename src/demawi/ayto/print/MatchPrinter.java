package demawi.ayto.print;

import java.util.function.Consumer;

import demawi.ayto.modell.AYTO_Data;

public abstract class MatchPrinter {

   protected Consumer<String> out = System.out::println;

   public void setOut(Consumer<String> out) {
      this.out = out;
   }

   public void printDayResults(AYTO_Data data) {
      data.checkAllDayConsistency();
      printDayResults(data, data.getAnzahlTage());
   }

   public abstract void printDayResults(AYTO_Data data, int tagNr);

}
