package sec11.chap02;

public class PrintThrNoRun implements Runnable {

    int no;
    public PrintThrNoRun(int no) {this.no = no;}

    @Override
    public void run() {
        for(int i = 1; i <= 10; i++) {
            System.out.print(no);

            // 시간지연
            for(int j = 1; j < Integer.MAX_VALUE; j++) {}
        }
    }
}
