package sec06.chap04.ex01;
import sec05.chap08.ex01.*;

public class Main {
    public static void main(String[] args) {

        //  💡 와일드카드로 임포트 - import sec05.chap08.ex01.*;
        JavaGroup store1 = new JavaChicken("울산");
        JavaGroup store2 = new JavaCafe("창원", true);
        JavaGroup store3 = new JavaGroup(1, "포항") {

            //  원본 메소드에 public 추가
            @Override
            public void takeOrder() {
                System.out.printf(
                        "유일한 자바과메기 %s 과메기를 주문해주세요.%n",
                        super.intro()
                );
            }

            public void dryFish() {
                System.out.println("과메기 말리기");
            }
        };

        String store3Intro = store3.intro();

        store3.takeOrder();
        //store3.dryFish // ⚠️ 불가

        System.out.println("\n- - - - -\n");

        for (JavaGroup store : new JavaGroup[]{store1, store2, store3}) {

            store.takeOrder();
        }
    }
}
