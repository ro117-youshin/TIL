package sec05.chap09.ex01;

public interface Hunter {
    String position = "포식자";
    void hunt();

    default void strong(){
        System.out.println("interface power!!");
    }

    default void speed() {
        System.out.println("사냥 스피드");
    }
}
