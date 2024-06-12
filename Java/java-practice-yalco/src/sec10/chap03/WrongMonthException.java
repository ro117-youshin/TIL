package sec10.chap03;

public class WrongMonthException extends RuntimeException {
    public WrongMonthException(int month) {
        //  💡 최고조상인 Throwable의 생성자 확인
        //  - detailMessage 에 값을 넣는 오버로드
        super(
                ("%d월은 존재하지 않습니다." +
                        " 1 ~ 12월 중에서 정확하게 입력해주세요.")
                        .formatted(month, month)
        );
    }
}