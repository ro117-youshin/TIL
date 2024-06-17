package sec05.chap09.ex02;

public class Main {
    public static void main(String[] args) {

        FoodSafety.announcement();

        JavaCafe store = new JavaCafe();

        store.regularInspection();
        store.employeeEducation();
        store.cleanKitchen();
        store.supportFund();
    }
}
