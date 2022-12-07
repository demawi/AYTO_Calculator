package demawi.ayto.modell;

public class CalculationOptions {
   public int tagNr;
   private int mitAnzahlMatchBoxen;
   public boolean mitMatchingNight;

   public CalculationOptions(int tagNr, int mitAnzahlMatchBoxen, boolean mitMatchingNight) {
      this.tagNr = tagNr;
      this.mitAnzahlMatchBoxen = mitAnzahlMatchBoxen;
      this.mitMatchingNight = mitMatchingNight;
   }

   public int getAnzahlMatchBoxen(int maxMatchBoxResults) {
      return Math.min(mitAnzahlMatchBoxen, maxMatchBoxResults);
   }
}
