package sec10.chap03;

import java.io.FileNotFoundException;

public class Ex02 {
    public static void main(String[] args) {
        registerDutyMonth("정핫훈", 7);

//        registerDutyMonth("김돌준", 13);
        //  ⭐️ try 문으로 감싸지 않았음
        //  - 다음 코드들이 실행되려면 주석처리해야 함
        //  - try 문으로 감싸주어야 함
        try {
            registerDutyMonth("김돌준", 13);
        } catch (Exception ignored) {} // 예외 후속처리에 아무것도 하지 않음

        openMyFile("잘나온얼굴.png");
        openMyFile("야구동영상.avi");
    }
    public static void registerDutyMonth (String name, int month) {
        if (month < 1 || month > 12) {
            throw new IndexOutOfBoundsException(
                    "%s님은 담당배정의 개월을 잘못 입력하셨어요."
                            .formatted(name)
            );
        }
        System.out.printf("%s님 %d월 담당으로 배정되셨어요.%n", name, month);
    }

    public static void openMyFile (String fileName) {
        if (fileName.contains("야구동영상")) {
            //  💡 try 문으로 감싸야 컴파일되는 예외
            try {
                throw new FileNotFoundException(
                        "파일이 존재하지 않습니다."
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
