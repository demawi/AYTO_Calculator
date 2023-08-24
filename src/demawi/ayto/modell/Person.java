package demawi.ayto.modell;

import java.util.Objects;

public class Person {

  public static final String MARK1 = "*";

  private final String name;
  public String marked = "";

  public Person(String name) {
    this.name = name;
  }

  public void mark(String mark) {
    this.marked = mark;
  }

  public String getName() {
    return name;
  }

  public String getNamePlusMark() {
    return name + marked;
  }

  public boolean isMarked() {
    return !"".equals(marked);
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
