package sec11.chap04.ex01;

public class Main {
    public static void main(String[] args) {

        ATM atm = new ATM();
        atm.addMoney(5000);

        Thread thr1 = new Thread(new CustomerRun("철수", atm, 500));
        Thread thr2 = new Thread(new CustomerRun("마이크", atm, 300));
        Thread thr3 = new Thread(new CustomerRun("호나우두", atm, 400));

        thr1.start();
        thr2.start();
        thr3.start();
    }
}
