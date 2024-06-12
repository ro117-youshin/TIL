package sec11.chap02;

public class IncreaseInterest implements Runnable {

    int max;
    public IncreaseInterest(int max) {this.max = max;}

    @Override
    public void run() {
        String interest = "%s : 금리가 %d에서 %d로 올라가요.";

        for (int i = 0; i < max; i++) {
            try {
                Thread.sleep(2000);
                System.out.printf( (interest) + "%n", Thread.currentThread().getName(), i, (i + 1));
            } catch (InterruptedException e) {
                System.out.println("알겠습니다.");
                return;
            }
        }
    }
}
