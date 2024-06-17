package sec08.chapset.exhashset;

import sec07.chap04.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Set<Integer> intHashSet1 = new HashSet<>();
        intHashSet1.add(1);
        intHashSet1.add(1);
        intHashSet1.add(2);
        intHashSet1.add(3);

        List<Integer> intList = new ArrayList(
                Arrays.asList(1, 1, 2, 2, 3, 3, 4, 5, 6, 7)
        );
        Set<Integer> intHashSet2 = new HashSet<>(intList);

        //  💡 for-each문 사용 가능
        for (Integer i :intHashSet1) {
            System.out.println(i);
        }

        //  ⭐️ 아래와 같이 응용 가능
        //  - 중복을 제거한 ArrayList
        intList.clear();
        intList.addAll(intHashSet2);

        //  포함 여부
        boolean has2 = intHashSet1.contains(2);
        boolean has4 = intHashSet1.contains(4);

        //  요소 삭제, 있었는지 여부 반환
        boolean rm3 = intHashSet1.remove(3);
        boolean rm4 = intHashSet1.remove(4);

        //  다른 콜렉션 기준으로 내용 삭제
        intHashSet2.removeAll(intHashSet1);

        //  💡 그 외
        intHashSet1.size();
        intHashSet2.isEmpty();
        intHashSet1.clear();

        //  참조형 관련
        Set<Swordman> swordmenSet = new HashSet<>();
        Swordman swordman = new Swordman(Side.RED);

        swordmenSet.add(swordman);
        swordmenSet.add(swordman);
        swordmenSet.add(new Swordman(Side.RED));
        swordmenSet.add(new Swordman(Side.RED));
        //  swordmenSet.remove(swordman); // 실행해보기

        HashSet<Integer> intHashSet = new HashSet<>();
        LinkedHashSet<Integer> intLinkedHashSet = new LinkedHashSet<>();
        TreeSet<Integer> intTreeSet = new TreeSet<>();

        for (int i : new int[] { 3, 1, 8, 5, 4, 7, 2, 9, 6}) {
            intHashSet.add(i);
            intLinkedHashSet.add(i);
            intTreeSet.add(i);
        }
        for (Set s : new Set[] {intHashSet, intLinkedHashSet, intTreeSet}) {
            System.out.println(s);
        }
        //  ⭐️ LinkedHashSet : 입력된 순서대로 / TreeSet : 오름차순
        //  ⚠️ HashSet이 정렬된 것처럼 보이지만 보장된 것이 아님
        //  - Hash 방식에 의한 특정 조건에서의 정렬일 뿐

        Set<String> strHashSet = new HashSet<>();
        Set<String> strLinkedHashSet = new LinkedHashSet<>();
        Set<String> strTreeSet = new TreeSet<>();

        for (String s : new String[] {
                "Fox", "Banana", "Elephant", "Car", "Apple", "Game", "Dice"
        }) {
            strHashSet.add(s);
            strLinkedHashSet.add(s);
            strTreeSet.add(s);
        }
        for (Set s : new Set[] {strHashSet, strLinkedHashSet, strTreeSet}) {
            System.out.println(s);
        }
    }
}