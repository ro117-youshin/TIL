package sec08.chapcompare.ex01;

import sec07.chap04.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Integer int1 = Integer.valueOf(1);
        Integer int2 = Integer.valueOf(2);
        Integer int3 = Integer.valueOf(3);

        int _1_comp_3 = int1.compareTo(3);
        int _2_comp_1 = int2.compareTo(1);
        int _3_comp_3 = int3.compareTo(3);

        int _t_comp_f = Boolean.valueOf(true).compareTo(Boolean.valueOf(false));

        int _abc_comp_def = "ABC".compareTo("DEF");
        int _efgh_comp_abcd = "efgh".compareTo("abcd");

        Integer[] nums = {3, 8, 1, 7, 4, 9, 2, 6, 5};
        String[] strs = {
                "Fox", "Banana", "Elephant", "Car", "Apple", "Game", "Dice"
        };

        //  ⭐️ Arrays 클래스의 sort 메소드
        //  - 기본적으로 compareTo에 의거하여 정렬
        //  - 인자가 없는 생성자로 생성된 TreeSet, TreeMap도 마찬가지
//        Arrays.sort(nums);
//        Arrays.sort(strs);

//        Arrays.sort(nums, new IntDescComp());
//        Arrays.sort(nums, new CloseToInt(5));

        Arrays.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });

        ArrayList<Integer> numsArray = new ArrayList<>(Arrays.asList(nums));
        numsArray.sort(new IntDescComp());

        // 짝수 우선 정렬
        numsArray.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return (o1 % 2) - (o2 % 2);
            }
        });

        TreeSet<Unit> unitTSet = new TreeSet<>(new UnitSorter());
        for(Unit u : new Unit[] {
            new Knight(Side.BLUE),
            new Knight(Side.BLUE),  // 중복
            new Swordman(Side.RED),
            new Swordman(Side.RED), // 중복
            new MagicKnight(Side.RED),
            new Knight(Side.RED),
            new Swordman(Side.BLUE),
        }) {
            unitTSet.add(u);
        }
    }
}
