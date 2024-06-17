package sec05.chap07;

public class JavaChicken {
    protected static final String CREED = "튀김옷 is Good.";

    private final int no;
    public String name;

    public JavaChicken (int no, String name) {
        this.no = no;
        this.name = name;
    }

    public void changeFinalFields() {
//        ⚠️ 불가
//        this.no++
    }

    public final void fryChicken() {
        System.out.println("염지, 반죽, 튀기기");
    }

}
