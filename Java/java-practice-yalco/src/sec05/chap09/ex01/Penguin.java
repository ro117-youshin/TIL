package sec05.chap09.ex01;

public class Penguin extends Bird implements Hunter, Swimmer {
    @Override
    public void hunt() {
        System.out.println(position + ": 작은 물고기 사냥");
    }

    @Override
    public void swim() {
        System.out.println("날개로 수영");
    }

    @Override
    public void speed() {
        Hunter.super.speed();
    }
}
