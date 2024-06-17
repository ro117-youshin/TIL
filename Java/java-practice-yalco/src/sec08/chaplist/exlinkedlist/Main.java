package sec08.chaplist.exlinkedlist;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //  💡 LinkedList에만 있는 메소드들 중...
        LinkedList<Integer> intNums = new LinkedList<>();
        for (int intNum : new int[]{2, 3, 4}) {
            intNums.add(intNum);
        }

        intNums.addFirst(1);
        intNums.addFirst(0);
        intNums.addLast(5); // add와 반환값만 다름. 코드에서 확인해 볼 것
        intNums.addLast(6);

        //  💡 앞에서/뒤에서 꺼내지 않고 반환
        //  - peek~ : 비어있을 경우 null 반환
        //  - get~ : 비어있을 경우 익셉션
        int peekedFirst = intNums.peekFirst();
        int gottenFirst = intNums.getFirst();
        int peekedLast = intNums.peekLast();
        int gottenLast = intNums.getLast();

        //  💡 앞에서/뒤에서 꺼내어 반환
        //  - poll~ : 비어있을 경우 null 반환
        //  - remove~ : 비어있을 경우 익셉션
        int polledFirst = intNums.pollFirst();
        int removedSecond = intNums.removeFirst();
        int polledLast = intNums.pollLast();
        int removedLast = intNums.removeLast();

        //  ⭐️ 위의 기능들 활용하여 Stack/Queue 구현

        LinkedList<Character> charLList = new LinkedList<>();

        //  💡 push & pop : 스택 간편하게 구현
        //  - 클래스 코드에서 살펴볼 것

        charLList.push('A');
        charLList.push('B');
        charLList.push('C');
        charLList.push('D');
        charLList.push('E');

        char pop1 = charLList.pop();
        char pop2 = charLList.pop();
        char pop3 = charLList.pop();
    }

    void testArrayList() {
        List<Integer> testIntList = Arrays.asList(1, 2, 3);
        //  ArrayList<Integer> testIntList = Arrays.asList(1,2,3); ⚠️ 불가
    }
}
