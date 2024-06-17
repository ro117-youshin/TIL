package sec04.chap04;

public class Ex02 {
    public static void main(String[] args) {

        //  100보다 작은 3의 배수들 출력해보기

        int i = 1;

        // ⚠️ 의도대로 작동하지 않음. 이유는?
        while (true) {
            if (i % 3 != 0) continue;  // 🔴
            System.out.println(i);

            if (i++ == 100) break;
        }
    }
}
