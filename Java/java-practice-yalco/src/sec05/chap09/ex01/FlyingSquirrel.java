package sec05.chap09.ex01;

public class FlyingSquirrel extends Mamal implements Flyer {

    public FlyingSquirrel() {
        super(true);
    }
    @Override
    public void fly() {
        System.out.println("나무에서 뛰어 비행");
    }
}
