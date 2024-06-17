package sec05.chap09.ex02;

public abstract class Government {
    public boolean domesticCompany;

    public Government(boolean domesticCompany) {
        this.domesticCompany = domesticCompany;
    }

    public void supportFund() {
        System.out.println("정부 지원금");
    }
}
