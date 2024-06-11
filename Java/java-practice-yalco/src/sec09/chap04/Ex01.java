package sec09.chap04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ex01 {
    public static void main(String[] args) {
        List<Integer> int0To9 = new ArrayList<>(
                Arrays.asList(5, 2, 0, 8, 4, 1, 7, 9, 3, 6)
        );

        // 기존의 방식
        List<Integer> odds = new ArrayList<>();
        for(Integer i : int0To9) {
            if(i % 2 == 1) {
                odds.add(i);
            }
        }
        odds.sort(Integer::compare);

        List<String> oddsStrs = new ArrayList<>();
        for(Integer i : odds) {
            oddsStrs.add(String.valueOf(i));
        }
        String oddsStr = String.join(", ", oddsStrs);

        System.out.println(oddsStr);

        // 스트림을 사용한 방식
        String oddsStrStreamed = int0To9
                .stream()
                .filter(i -> i % 2 == 1)
                .sorted(Integer::compareTo)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        System.out.println("스트림을 사용한 방식 :: " + oddsStrStreamed);
    }
}
