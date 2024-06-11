package sec11.chap02;

public class Ex01 {
    public static void main(String[] args) {
        Thread incInterest = new Thread(new IncreaseInterest(40));

        incInterest.setName("캐피탈직원");

        incInterest.start();
    }
}
