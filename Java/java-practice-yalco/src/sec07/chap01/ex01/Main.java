package sec07.chap01.ex01;

public class Main {
    public static void main(String[] args) {
        Button button1 = new Button("엔터", Button.Mode.DARK, 3);

//        System.out.println(button1); // ⭐️ toString() 을 붙인 것과 같음
        System.out.println(button1.toString());

    }
}
