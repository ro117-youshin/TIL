package sec09.chap03;

import java.io.FileNotFoundException;

public class Ex02 {

    public static void main(String[] args) {

        registerDutyMonth("홍길동", 10);
        registerDutyMonth("김유신", 13);

        openMyFile("잘나온얼굴.png");
        openMyFile("야구동영상.avi");
    }

    public static void registerDutyMonth (String name, int month) {
        if (month < 1 || month > 12) {
            throw new IndexOutOfBoundsException(
                    "%s씨 잘못 입력하셨어요..."
                            .formatted(name)
            );
        }
        System.out.printf("%s씨 %d월 담당으로 배정되셨어요.%n", name, month);
    }

    public static void openMyFile (String fileName) {
        if (fileName.contains("야구동영상")) {
            //  💡 try 문으로 감싸야 컴파일되는 예외
            try {
                throw new FileNotFoundException(
                        "제 컴퓨터엔 그런 거 없어요."
                );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("👨‍🏫 인강 프로그램을 실행합니다...");
            }
            return;
        }
        System.out.printf("%s 파일 열람%n", fileName);
    }
}
