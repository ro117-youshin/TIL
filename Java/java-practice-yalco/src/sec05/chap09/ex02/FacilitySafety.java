package sec05.chap09.ex02;

public interface FacilitySafety {

    default void regularInspection () {
        System.out.println("시설 정기 체크");
    }

    default void supportFund() {
        System.out.println("시설 공단 지원금");
    }
}
