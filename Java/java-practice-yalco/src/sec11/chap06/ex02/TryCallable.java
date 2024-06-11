package sec11.chap06.ex02;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TryCallable {
    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<Integer>();
        IntStream.range(0,10).forEach(i -> {
            try {
                intList.add(new RollDiceCall().call());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(String.join(",", intList.stream().map(String::valueOf).toArray(String[]::new)));
    }
}
