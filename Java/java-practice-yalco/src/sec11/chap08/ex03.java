package sec11.chap08;

import java.util.Arrays;
import java.util.List;

public class ex03 {
    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        System.out.println(
                intList.parallelStream().reduce(0, Integer::sum));

        System.out.println(

                Runtime.getRuntime().availableProcessors()
        );
    }
}
