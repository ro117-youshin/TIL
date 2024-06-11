package sec10.chap04;

import java.util.*;

public class Ex02 {

    public static void main(String[] args) {

        Map<String, Integer> dutyRegMap = new HashMap<>();
        dutyRegMap.put("정핫훈", 7);
        dutyRegMap.put("김돌준", 13);

        dutyRegMap.forEach((name, month) -> {
            try {
                registerDutyMonthSafer(name, month);
            } catch (WrongMonthException we) {
                we.printStackTrace();
                System.out.println("호출부에서 다시 호출");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //  💡 일단 자기 선에서도 처리하고 외부로도 던지는 메소드
    //  - 필요한 일은 하되, 정상적으로 진행된 것은 아님을 호출부에 알리기 위함
    //  - 예외를 양쪽에서 처리해줄 필요가 있을 때
    public static void registerDutyMonthSafer (String name, int month) throws WrongMonthException {
        try {
            if (month < 1 || month > 12) {
                throw new WrongMonthException(
                        "우선 임의로 업무기간을 배정하겠습니다."
                );
            }
            System.out.printf("%s님 %d월 담당으로 배정되셨어요.%n", name, month);
        } catch (WrongMonthException we) {
            System.out.printf(
                    "%s님 %d월 담당으로 배정되셨어요.%n",
                    name, new Random().nextInt(1, 12 + 1)
            );
            throw we;
        }
    }

    //  💡 예외를 던질 가능성이 있지만 스스로 처리하지는 않는 메소드
    public static void registerDutyMonth (String name, int month) throws WrongMonthException {
        if (month < 1 || month > 12) {
            throw new WrongMonthException("잘못 입력하셨습니다.");
        }
        System.out.printf("%s님 %d월 담당으로 배정되셨어요.%n", name, month);
    }
}
