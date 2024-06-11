package sec11.chap02;

import java.util.Scanner;

public class Ex03 {
    public static void main(String[] args) {
        Thread incInterest = new Thread(new IncreaseInterest(10));
//        incInterest.run(); // 금리 공지가 끝나야 입력에 응답 가능.
        incInterest.start(); // 입력 응답과 동시 진행 가능해짐. (금리 공지를 하는 IncreaseInterest 쓰레드와 main 쓰레드가 동시 진행)

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNext()) {
                String line =  sc.nextLine();

                if (line.equalsIgnoreCase("check")) {
                    System.out.println("금리 공지가 끝났나요?");
                    System.out.println(
                            //  💡 isAlive : 해당 쓰레드가 진행중인지 여부
                            incInterest.isAlive() ? "아직 공지하고 있습니다." : "끝났습니다."
                    );
                }

                if (line.equalsIgnoreCase("enjoy")) {

                    System.out.println("금리가 인상될 필요가 있다.");

                    //  💡 join() - 다른 쓰레드의 작업을 기다린다.
                    //  ⚠️ catch 블록 요구됨 - InterruptedException 처리
                    try {
                        incInterest.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //incInterest.join(5000); // 일정시간 동안만 조인 가능
                }

                if(line.equalsIgnoreCase("stop")) {
                    System.out.println("그만 올려요.");
                    incInterest.interrupt();
                }

                if(line.equalsIgnoreCase("quit")) break;
                System.out.println(line);
            }
        }
    }
}
