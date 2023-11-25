package demawi.ayto.modell;

/**
 * References a specific point in time (day and event).
 */
public class Timepoint {

   private final int tagNr;
   private final int eventCount;

   public Timepoint(int tagNr, int eventCount) {
      this.tagNr = tagNr;
      this.eventCount = eventCount;
   }

   public int getDayNr() {
      return tagNr;
   }

   public int getEventCount() {
      return eventCount;
   }

}
