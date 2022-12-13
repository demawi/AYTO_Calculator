package demawi.ayto.modell;

import java.util.LinkedHashMap;
import java.util.Map;

public class Pair {

  public Frau frau;
  public Mann mann;

  private Pair(Frau frau, Mann mann) {
    this.frau = frau;
    this.mann = mann;
  }

  public static Pair pair(Frau frau, Mann mann) {
    return new Pair(frau, mann);
  }

  @Override
  public String toString() {
    return frau.getName() + " & " + mann.getName();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((frau == null) ? 0 : frau.hashCode());
    result = prime * result + ((mann == null) ? 0 : mann.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Pair other = (Pair) obj;
    if (frau == null) {
      if (other.frau != null) return false;
    }
    else if (!frau.equals(other.frau)) return false;
    if (mann == null) {
      if (other.mann != null) return false;
    }
    else if (!mann.equals(other.mann)) return false;
    return true;
  }

}
