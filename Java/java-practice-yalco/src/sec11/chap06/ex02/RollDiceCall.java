package sec11.chap06.ex02;

import java.util.Random;
import java.util.concurrent.Callable;

public class RollDiceCall implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {

        Thread.sleep(1000);

        int result = new Random().nextInt(0, 6) + 1;
        System.out.println(result);

        return result;
    }
}
