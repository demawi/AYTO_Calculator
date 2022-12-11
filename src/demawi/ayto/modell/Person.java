package demawi.ayto.modell;

public class Person {

  public static final String MARK1 = "*";

  private String name;
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

}
