package sec05.chap09.ex02;

public class JavaCafe extends Government implements FoodSafety, FacilitySafety {

    public JavaCafe() {
        super(true);
    }

    @Override
    public void cleanKitchen() {
        System.out.println("매일 주방 청소");
    }

    @Override
    public void employeeEducation() {
        System.out.println("직원 위생 교육");
    }

    @Override
    public void regularInspection() {
        FacilitySafety.super.regularInspection();
    }
}
