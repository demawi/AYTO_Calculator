package demawi.ayto.modell;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import demawi.ayto.perm.AYTO_Permutator;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;

public class CalculationOptions {
   private final AYTO_Data data;
   public final int tagNr;
   private final int mitAnzahlMatchBoxen;
   public final boolean mitMatchingNight;

   public CalculationOptions(AYTO_Data data, int tagNr, int mitAnzahlMatchBoxen, boolean mitMatchingNight) {
      this.data = data;
      this.tagNr = tagNr;
      this.mitAnzahlMatchBoxen = mitAnzahlMatchBoxen;
      this.mitMatchingNight = mitMatchingNight;
   }

   public AYTO_Data getData() {
      return data;
   }

   public int getAnzahlMatchBoxen(int maxMatchBoxResults) {
      return Math.min(mitAnzahlMatchBoxen, maxMatchBoxResults);
   }

   public Tag getTag() {
      return data.getTag(tagNr);
   }

   public List<Frau> getFrauen() {
      return data.getFrauen(tagNr);
   }

   public List<Mann> getMaenner() {
      return data.getMaenner(tagNr);
   }

   public MatchingNight getMatchingNight() {
      if (tagNr == 0)
         return null;
      return getTag().matchingNight;
   }

   public AYTO_Permutator.ZUSATZTYPE getZusatztype() {
      return data.getZusatztype();
   }

   /**
    * Testet ob die übergebene Paar-Konstellation zu einem Widerspruch führt.
    */
   public boolean test(Collection<Pair> constellation, boolean debug) {
      for (int tagNr = 1; tagNr < this.tagNr + 1; tagNr++) {
         Tag tag = data.getTag(tagNr);
         boolean istVorherigerTag = tagNr < this.tagNr; // wird komplett abgearbeitet

         int matchBoxes = istVorherigerTag ? tag.boxResults.size() : getAnzahlMatchBoxen(tag.boxResults.size());
         if (matchBoxes > 0) {
            for (int matchBoxNr = 0; matchBoxNr < matchBoxes; matchBoxNr++) {
               MatchBoxResult matchBoxResult = tag.boxResults.get(matchBoxNr);
               Boolean result = matchBoxResult.result;
               if (result != null) {
                  if (result != constellation.contains(matchBoxResult.pair)) {
                     return false;
                  }
               }
            }
         }

         for (Map.Entry<Pair, Boolean> entry : tag.implicits.entrySet()) {
            if (entry.getValue() != constellation.contains(entry.getKey())) {
               return false;
            }
         }

         if (istVorherigerTag || mitMatchingNight) {
            MatchingNight night = tag.matchingNight;
            if (night != null) {
               for (int nightNr = 0, l = night.constellation.size(); nightNr < l; nightNr++) {
                  int lights = data.getLights(constellation, night.constellation);
                  if (lights != night.lights) {
                     if (debug) {
                        System.out.println(
                              "Falsch aufgrund von Matching Night Nr. " + (nightNr + 1) + " Erwartete Lichter: "
                                    + lights + ". Es waren aber: " + night.lights);
                     }
                     return false;
                  }
               }
            }
         }
      }
      return true;
   }

}
