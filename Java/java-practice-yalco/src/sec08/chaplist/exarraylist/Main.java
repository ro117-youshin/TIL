package sec08.chaplist.exarraylist;

import sec07.chap04.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        //  ⭐️ 제네릭을 사용하여 타입 지정
        //  - 붙이지 않을 시 <Object>와 동일
        ArrayList<Integer> ints1 = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Number> numbers = new ArrayList<>();
        ArrayList<Knight> knights = new ArrayList<>();

        //  add 메소드로 요소 추가
        ints1.add(11);
        ints1.add(22);
        ints1.add(33);
        ints1.add(44);
        ints1.add(55);

        //  요소 중복 허용
        for (String str : "바니 바니 바니 바니 당근 당근".split(" ")) {
            strings.add(str);
        }

        //  for-each 문 사용 가능
        for (int i : ints1) {
            System.out.println(i);
        }

        int ints1Size = ints1.size(); // 요소 개수
        boolean ints1IsEmpty = ints1.isEmpty(); // size가 0인지 여부 반환
        int ints12nd = ints1.get(1); // 인덱스로 요소 접근
        boolean ints1Con3 = ints1.contains(33); // 포함 여부
        boolean ints1Con6 = ints1.contains(66);

        ints1.set(2, 444); // 위치의 요소 수정
        ints1.add(0, 11); // 위치에 요소 추가 (다음 요소들 밀어냄)

        //  ⭐️ 간략한 생성 및 초기화 방법들
        ArrayList<Integer> ints2A = new ArrayList<>(
                Arrays.asList(1, 2, 3, 4, 5)
        ); // 💡 Arrays 클래스 : 배열 관련 각종 기능 제공

        ArrayList<Integer> ints2B = new ArrayList<>(
                List.of(1, 2, 3, 4, 5)
        ); // 💡 자바9에서부터 가능

        ArrayList<Integer> ints2C = new ArrayList<>();
        Collections.addAll(ints2C, 1, 2, 3, 4, 5);

        //  💡 다른 Collection 인스턴스를 사용하여 생성
        ArrayList<Integer> ints3 = new ArrayList<>(ints1);

        //  스스로를 복제하여 반환 (⚠️ 얕은 복사)
        ArrayList<Integer> ints4 = (ArrayList<Integer>) ints3.clone();

        ints3.remove(4); // int: 인덱스로 지우기
        ints3.remove((Integer) 55); // 클래스 자료형: 요소로 지우기

        ints1.removeAll(ints3); // 주어진 콜렉션에 있는 요소들 지우기

        ints1.addAll(ints3); // 콜렉션 이어붙이기

        //  💡 toArray - Object 배열 반환
        Object[] intsAry2_Obj = ints1.toArray();

        //  ⭐️ 특정 타입의 배열로 반환하려면?
        //  Integer[] ints1Ary1 = (Integer[]) ints1.toArray(); // ⚠️ 이렇게는 불가
        //  💡 인자로 해당 타입 배열의 생성자를 넣어줌
        //  - 다음 섹션에 배울 메소드 참조 사용 (9-3강 수강 후 다시 볼 것)
        Integer[] ints1Ary2 = ints1.toArray(Integer[]::new);

        ints1.clear(); // 리스트 비움

        //  제네릭 적용
        numbers.add(Integer.valueOf(123));
        numbers.add(3.14);
//        numbers.add("Hello"); // ⚠️ 불가

//        knights.add(new Swordman(Side.BLUE)); // ⚠️ 불가
        knights.add(new Knight(Side.BLUE));
        knights.add(new MagicKnight(Side.RED));

        //  와일드카드 적용
        //  기사 이상의 그룹으로만 편성될 수 있는 정예분대
        ArrayList<? extends Knight> eliteSquad;

//        eliteSquad = new ArrayList<Swordman>(); // ⚠️ 불가
        eliteSquad = new ArrayList<Knight>();
        eliteSquad = new ArrayList<MagicKnight>();

        //  ⭐️ 인스턴스 요소를 지울 때는 참조를 기준으로
        //  - 내용이 같다고 같은 인스턴스가 아님
        Knight knight1 = new Knight(Side.RED);
        knights.add(knight1);

        //  요소가 하나 지워졌는지 여부 반환
        boolean removed1 = knights.remove(new Knight(Side.RED));
        boolean removed2 = knights.remove(knight1);
    }
}
