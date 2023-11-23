package demawi.ayto.modell;

import java.util.Objects;

public class Person {

  private final String name;
  public Markierung marked;

  public Person(String name) {
    this.name = name;
  }

  public void mark(Markierung mark) {
    this.marked = mark;
  }

  public boolean hasMark(Markierung mark) {
    return marked == mark;
  }

  public String getName() {
    return name;
  }

  public String getNamePlusMark() {
    return name + (marked != null ? marked : "");
  }

  public String getNameWithoutMark() {
    return name;
  }

  public boolean isMarked() {
    return marked != null;
  }

  /**
   * For a normal print you have to decide: getNamePlusMark or getNameWithoutMark
   */
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
