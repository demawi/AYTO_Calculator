package demawi.ayto.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import demawi.ayto.Formatter;
import demawi.ayto.events.MatchBoxResult;
import demawi.ayto.events.MatchingNight;
import demawi.ayto.modell.AYTO_Data;
import demawi.ayto.modell.AYTO_Result;
import demawi.ayto.modell.Frau;
import demawi.ayto.modell.Mann;
import demawi.ayto.modell.Pair;
import demawi.ayto.modell.Tag;
import demawi.ayto.perm.AYTO_Permutator;

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

}
