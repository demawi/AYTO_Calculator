package demawi.ayto.modell;

public class AYTO_Pair {

  public final Person frau;
  public final Person mann;

  private AYTO_Pair(Person frau, Person mann) {
    this.frau = frau;
    this.mann = mann;
  }

  public static AYTO_Pair pair(Woman frau, Man mann) {
    return new AYTO_Pair(frau, mann);
  }

  public static AYTO_Pair pair(Person frau, Person mann) {
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
