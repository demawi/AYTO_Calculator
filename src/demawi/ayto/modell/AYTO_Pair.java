package demawi.ayto.modell;

public class AYTO_Pair {

  public Frau frau;
  public Mann mann;

  private AYTO_Pair(Frau frau, Mann mann) {
    this.frau = frau;
    this.mann = mann;
  }

  public static AYTO_Pair pair(Frau frau, Mann mann) {
    return new AYTO_Pair(frau, mann);
  }

  @Override
  public String toString() {
    return frau.getName() + " & " + mann.getName();
  }

  @Override
  public int hashCode() {
    return frau.hashCode() + mann.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    AYTO_Pair other = (AYTO_Pair) obj;
    if (frau == null) {
      if (other.frau != null) return false;
    }
    if (mann == null) {
      if (other.mann != null) return false;
    }
    return frau.equals(other.frau) && mann.equals(other.mann) || frau.equals(other.mann) && mann.equals(other.frau);
  }

}
