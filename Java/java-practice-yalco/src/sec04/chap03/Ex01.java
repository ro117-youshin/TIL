package sec04.chap03;

public class Ex01 {
    public static void main(String[] args) {
        //  루프 블록 안에서 변수값을 바꾸는 것으로 4번 과정 대신 가능
        for (int i = 0; i < 10;) {
            System.out.println(i);
            i += 2;
        }

        System.out.println("\n- - - - -\n");

        for (double d = 123.45; d > 0; d -= 32.1) {
            System.out.println(d);
        }

        for (String s = ""; s.length() < 11; s += s.length()) {
            System.out.println(s);
        }
    }
}
