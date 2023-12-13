package demawi.ayto.modell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import demawi.ayto.permutation.Mark;
import demawi.ayto.permutation.Markable;
import demawi.ayto.util.Named;

public class Person<T>
      implements Markable, Named {

  private final String name;
  public List<Mark> marks = new ArrayList<>();

  public Person(String name) {
    this.name = name;
  }

  public T mark(Mark mark) {
    if (mark != null) {
      marks.add(mark);
    }
    return (T) this;
  }

  public boolean hasMark(Mark mark) {
    return marks.contains(mark);
  }

  public String getName() {
    return name;
  }

  public String getNamePlusMark() {
    return name + (marks.isEmpty() ? "" : marks.stream()
          .map(Mark::toString)
          .collect(Collectors.joining("")));
  }

  public String getNameWithoutMark() {
    return name;
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
