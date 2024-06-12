package sec11.chap09;

import java.util.concurrent.atomic.AtomicInteger;

public class Ex03 {

    static int count = 0;
    static AtomicInteger atomicCount = new AtomicInteger(0);

    public static void main(String[] args) {

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                count++;
                atomicCount.getAndIncrement();
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.start(); t2.start(); t3.start();

        try {
            t1.join(); t2.join(); t3.join();
        } catch (InterruptedException e) {}

        int result = count;
        int atomicResult = atomicCount.get();
    }
}
