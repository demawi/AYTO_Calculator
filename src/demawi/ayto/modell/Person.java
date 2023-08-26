package demawi.ayto.modell;

import java.util.Objects;

public class Person {

  public enum Markierung {
    CAN_BE_A_DOUBLE("*");

    private String val;

    Markierung(String val) {
      this.val = val;
    }

    @Override
    public String toString() {
      return val;
    }
  }

  private final String name;
  public Markierung marked;

  public Person(String name) {
    this.name = name;
  }

  public void mark(Markierung mark) {
    this.marked = mark;
  }

  public String getName() {
    return name;
  }

  public String getNamePlusMark() {
    return name + (marked != null ? marked : "");
  }

  public boolean isMarked() {
    return marked != null;
  }

  @Override
  public String toString() {
    return "@@@" + getNamePlusMark();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null)
      return false;
    if (!Person.class.equals(getClass()) && !Person.class.equals(o.getClass())) {
      if (getClass() != o.getClass())
        return false;
    }
    Person person = (Person) o;
    return name.equals(person.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
