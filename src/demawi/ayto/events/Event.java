package demawi.ayto.events;

/**
 * Marker type for all events
 */
public interface Event
      extends ConstellationValidation {

   default boolean needsRecalculation() {
      return false;
   }

}
