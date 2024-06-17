package sec05.chap08.ex01;

public class Main {
    public static void main(String[] args) {

        //  ⚠️ 불가
//    JavaGroup JavaGroup = new JavaGroup(1, "서울");

        JavaChicken javaStore1 = new JavaChicken("판교");
        JavaChicken javaStore2 = new JavaChicken("강남");

        JavaCafe javafStore1 = new JavaCafe("울릉", true);
        JavaCafe javafStore2 = new JavaCafe("강릉", false);

        JavaGroup[] javaStores = {
                javaStore1, javaStore2,
                javafStore1, javafStore2
        };

        for (JavaGroup javaStore : javaStores) {
            javaStore.takeOrder();
        }

        System.out.println("\n- - - - -\n");

        System.out.println(JavaChicken.getCreed());
        System.out.println(JavaCafe.getCreed());
    }
}
