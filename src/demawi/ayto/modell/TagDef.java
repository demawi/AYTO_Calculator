package demawi.ayto.modell;

public class TagDef {
   public int tagNr;
   public boolean mitMatchbox;
   public boolean mitMatchingNight;

   public TagDef(int tagNr, boolean mitMatchbox, boolean mitMatchingNight) {
      this.tagNr = tagNr;
      this.mitMatchbox = mitMatchbox;
      this.mitMatchingNight = mitMatchingNight;
   }
}
