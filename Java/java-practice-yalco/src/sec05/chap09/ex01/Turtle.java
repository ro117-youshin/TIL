package sec05.chap09.ex01;

public class Turtle extends Reptile implements Swimmer {

    @Override
    public void swim() {
        System.out.println("천천히 장거리 수영");
    }
}
