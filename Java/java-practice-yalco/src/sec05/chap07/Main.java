package sec05.chap07;

public class Main {
    public static void main(String[] args) {
        String ycCreed = JavaChicken.CREED;
//        JavaChicken.CREED = "우리의 튀김옷은 바삭하다"; // ⚠️ 불가

        final JavaChicken store1 = new JavaChicken(3, "판교");

        //  ⚠️ 불가
//        store1 = new JavaChicken(17, "강남");
        //  💡 요소 변경은 가능
        store1.name = "선릉";
    }
}
