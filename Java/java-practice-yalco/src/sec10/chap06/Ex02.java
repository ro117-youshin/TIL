package sec10.chap06;

import java.util.*;
import java.util.stream.IntStream;

public class Ex02 {
    public static void main(String[] args) {
        Optional<String> catOpt = Optional.of("Cat");
//        catOpt = Optional.of(null); // NPE

        //  ofNullable : 담으려는 것이 null일 수도 있을 때
        Optional<String> dogOpt = Optional.ofNullable("Dog");
        Optional<String> cowOpt = Optional.ofNullable(null);

        Optional<String> henOpt = Optional.empty();

        catOpt = getCatOpt();

        System.out.println("\n- - - - -\n");

        List<Optional<Integer>> optInts = new ArrayList<>();
        IntStream.range(0, 20)
                .forEach(i -> {
                    optInts.add(Optional.ofNullable(
                            new Random().nextBoolean() ? i : null
                    ));
                });

        //  💡 Optional의 filter와 map 메소드
        optInts.stream()
                .forEach(opt -> {
                    System.out.println(
                            opt
                                    //  ⭐️ 걸러진 것은 null로 인식됨
                                    //  - 스트림의 filter처럼 건너뛰는 것이 아님!
                                    .filter(i -> i % 2 == 1)
                                    .map(i -> "%d 출력".formatted(i))
                                    .orElse("(SKIP)")
                    );
                });
    }


    public static Optional<String> getCatOpt() {
        return Optional.ofNullable(new Random().nextBoolean() ? "Cat" : null);
    }
}
