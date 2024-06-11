package sec11.chap09;

import java.util.concurrent.atomic.AtomicReference;

public class Ex04 {

    static Counter counter = new Counter(0);

    static AtomicReference atomicCounter = new AtomicReference(new Counter(0));

    public static void main(String[] args) {
        Runnable nonAtomic = () -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        };

        Runnable atomic = () -> {
            for (int i = 0; i < 10000; i++) {

                Counter before, after;
                do {
                    before = (Counter) atomicCounter.get();
                    after = new Counter(before.getCount() + 1);
                } while (!atomicCounter.compareAndSet(before, after));
            }
        };

        Thread t1 = new Thread(nonAtomic);
        Thread t2 = new Thread(nonAtomic);
        Thread t3 = new Thread(nonAtomic);

        Thread t4 = new Thread(atomic);
        Thread t5 = new Thread(atomic);
        Thread t6 = new Thread(atomic);

        t1.start(); t2.start(); t3.start(); t4.start(); t5.start(); t6.start();

        try {
            t1.join(); t2.join(); t3.join(); t4.join(); t5.join(); t6.join();
        } catch (InterruptedException e) {}

        int result = counter.getCount();
        int atomicResult = ((Counter) atomicCounter.get()).getCount();
    }
}
