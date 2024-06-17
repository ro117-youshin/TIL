package sec06.chap07.ex01;

//  기존처럼 클래스로 작성해야 했다면...
public class ChildClass {

    private final String name;
    private final int birthYear;
    private final Gender gender;

    public ChildClass(String name, int birthYear, Gender gender) {
        this.name = name;
        this.birthYear = birthYear;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }
    public int getBirthYear() {
        return birthYear;
    }
    public Gender getGender() {
        return gender;
    }
}