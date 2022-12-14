package demawi.ayto.modell;

public class Zeitpunkt {

   private final int tagNr;
   private final int eventCount;

   public Zeitpunkt(int tagNr, int eventCount) {
      this.tagNr = tagNr;
      this.eventCount = eventCount;
   }

   public int getTagNr() {
      return tagNr;
   }

   public int getEventCount() {
      return eventCount;
   }

}
