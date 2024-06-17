package sec05.chap09.ex02;

public interface FoodSafety {

    //  ⭐️
    //  static 제거해 볼 것 -> 구현 클래스인 JavaCafe 에서 오버라이드 메서드를 구현해야 하고 이 인터페이스에서는 메서드의 바디를 지워야 함.
    //  static abstract는 역시 불가 (추상 클래스처럼)
    static void announcement () {
        System.out.println("식품안전 관련 공지");
    }

    //  ⭐️
    //  default 제거해 볼 것 -> interface의 추상 메서드는 바디를 갖을 수 없다.
    default void regularInspection () {
        System.out.println("식품 정기 체크");
    }

    void cleanKitchen ();
    void employeeEducation ();
}
