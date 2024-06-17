package sec03.chap07;

public class Ex05 {
    public static void main(String[] args) {
        // # null 문자열
        String emptyStr = "";
        String nullStr = null;

        //  ⭐️ 빈 문자열과 null은 다름
        boolean bool1 = emptyStr == nullStr;

        //  ⚠️ null은 문자열 인스턴스 메소드 사용 불가
        //  아래의 코드들은 모두 런타임 에러를 발생시킴
        //  int int1 = nullStr.length();
        //boolean bool2 = nullStr.equals(emptyStr);
        //String str1 = nullStr.concat("ABC");
    }
}
