package sec05.chap08.ex02;

public class Main {

    public static void main(String[] args) {

        Button button01 = new Button(2, "Enter");
        Switch switch01 = new Switch(3, true);
        DropDown dropdown01 = new DropDown(5, new String[] {
                "이름 오름차순", "이름 내림차순",
                "크기 오름차순", "크기 내림차순",
                "날짜 오름차순", "날짜 내림차순"
        });

        // 💡 다형성 적용 확인
        clickFormElement(button01);

        clickFormElement(switch01);
        clickFormElement(switch01);
        clickFormElement(switch01);

        clickFormElement(dropdown01);
    }
    public static void clickFormElement (FormElement fe) {
        fe.func();
    }
}
