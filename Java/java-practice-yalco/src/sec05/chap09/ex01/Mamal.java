package sec05.chap09.ex01;

public abstract class Mamal {

    // 겨울잠
    public boolean hibernation;

    public Mamal(boolean hibernation) {
        this.hibernation = hibernation;
    }

    public void strong() {
        System.out.println("power !! ");
    }
}
