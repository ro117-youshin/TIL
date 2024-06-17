package sec04.chap03;

public class Ex03 {
    public static void main(String[] args) {
        //  4의 배수 차례로 10개 배열에 담기
        int count = 10;
        int[] multiOf4 = new int[count];

        for (int i = 1, c = 0; c < count; i++) {
            if (i % 4 == 0) {
                multiOf4[c++] = i;
            }
        }

        int sumOfArray = 0;
        for (int num : multiOf4) {
            sumOfArray += num;
        }


        int cnt = 10;
        int[] multiple4 = new int[cnt];

        for(int x = 1, index = 0; index < cnt; x++) {
            if(x % 4 == 0) {                // x가 4의 배수일 때 분기 진입.
                multiple4[index++] = x;     //
            }
        }
    }
}
