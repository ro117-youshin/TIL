# 10. 오류에 대비하기
> '제대로 파는 자바 - 얄코' 섹션10 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. 예외처리
> 2. try 문 더 알아보기
> 3. 예외 정의하고 발생시키기
> 4. 예외 떠넘기기와 되던지기기

## 1. 예외처리

#### 💡 자바 프로그램의 오류 error.
* 프로그램이 실행 중 어떤 원인에 의해서 오작동을 하거나 비정상적으로 종료되는 경우가 있다.
* 이러한 결과를 초래하는 원인을 에러 또는 오류라고 한다.
* 이를 발생시점에 따라 나눌 수 있는데,
  * 컴파일 오류(compile-time error) - 컴파일 시에 발생하는 에러.
    * 문법 오류, 자료형 오류, 잘못된 식별자 (오타) 등...
  * 런타임 오류(runtime error) - 실행 시에 발생하는 에, 아래의 두 종류로 구분됨.
    * 에러 error
    * 예외 exception
  * 논리적 오류(logical error) - 실행은 되지만, 의도와 다르게 동작하는 것.

#### ⭐️ 에러와 예외
* 둘 다 `Throwable` 의 자식 클래스.
* 에러 `error`  - 프로그램 코드에 의해서 수습될 수 없는 심각한 오류, 해결 불가능한 문제.
  * ex) 회사에 가다가 천재지변으로 죽음. (회사에 죽었으니까 죽어도 못 감)
  * ex) 무한루프, 메모리 한도 초과, 스택오버플로우 등...
* 예외 `exception` - 프로그램 코드에 의해서 수습될 수 있는 다소 미약한 오류, 대비하여 해결할 수 있는 문제.
  * ex) 가려던 출근길이 공사로 막힘, 지하철 시위로 인한 운행 중단. (대책을 마련해두면 회사에 갈 수 있음)
  * ex) 읽어오려는 파일이 없음, 배열의 길이 이상으로 접근 등...

#### 🌲 상속도
`Throwable`

- `Error`
    - `VirtualMachineError`
        - `OutOfMemoryError`
        - `StackOverflowError`
        - …
    - …
- `Exception`
    - ⭐️ `RuntimeException`
        - `IndexOutOfBoundException`
        - `NullPointerException`
        - `ClassCastException`
        - …
    - `ReflectiveOperationException`
        - `ClassNotFoundException`
        - `NoSuchMethodException`
        - …
    - `IOException`
        - `FileNotFoundException` - *[java.io](http://java.io) 패키지*
    - ...

#### 💡 예외의 두 종류
* Unchecked Exception
  * `RuntimeException` 의 하위 클래스들
  * 개발자의 실수에 의해 발생할 수 있는 예외들
    * null 체크, 배열 등의 범위 벗어남, 0으로 나눔 등...
  * 예외처리가 필수는 아님 (실수를 안 하면 되므로)
* Checked Exception
  * 기타 예외들
  * 주로 외적인 요인으로 발생
  * 발생 가능한 부분에는 반드시 예외처리를 해야 함
    * 처리하지 않을 시 컴파일 단계에서 반려 


## 2. try문 더 알아보기

#### 📁 Ex. 예외 타입별로 대응하기
###### ☕️ Ex01.java - tryThings1
```java
public class Ex01 {

    public static void main(String[] args) {
        IntStream.rangeClosed(0, 4)
                .forEach(Ex01::tryThings1);
    }

    public static void tryThings1 (int i) {
        try {
            switch (i) {
                //  💡 예외가 발생하면 바로 실행을 멈추고 catch 문으로 감
                //  - 아래 케이스들은 각각 예외가 발생하므로 break 넣지 않았음
                case 1: System.out.println((new int[1])[1]);
                case 2: System.out.println("abc".charAt(3));
                case 3: System.out.println((Knight) new Swordman(Side.RED));
                case 4: System.out.println(((String) null).length());
            }

            //  ⭐️ 아래의 코드는 예외가 발생하면 실행되지 않음
            System.out.printf("%d: 🎉 예외 없이 정상실행됨%n", i);

        } catch (Exception e) {

            //  💡 예외 발생시에만 실행되는 블록
            System.out.printf(
                    "%d: 🛑 [ %s ] %s%n", i, e.getClass(), e.getMessage()
            );
            e.printStackTrace();
        }
    }
}
```
```java
0: 🎉 예외 없이 정상실행됨
1: 🛑 [ class java.lang.ArrayIndexOutOfBoundsException ] Index 1 out of bounds for length 1
2: 🛑 [ class java.lang.StringIndexOutOfBoundsException ] String index out of range: 3
3: 🛑 [ class java.lang.ClassCastException ] class sec07.chap04.Swordman cannot be cast to class sec07.chap04.Knight (sec07.chap04.Swordman and sec07.chap04.Knight are in unnamed module of loader 'app')
4: 🛑 [ class java.lang.NullPointerException ] Cannot invoke "String.length()" because "null" is null
```
###### ☕️ Ex01.java - tryThings2
```java
public class Ex01 {

    public static void main(String[] args) {
 
        IntStream.rangeClosed(0, 4)
                .forEach(Ex01::tryThings2);
    }

    public static void tryThings2 (int i) {
        try {
            switch (i) {
                case 1: System.out.println((new int[1])[1]);
                case 2: System.out.println("abc".charAt(3));
                case 3: System.out.println((Knight) new Swordman(Side.RED));
                case 4: System.out.println(((String) null).length());
            }
            System.out.printf("%d: 🎉 예외 없이 정상실행됨%n", i);

            //  💡 오류의 타입마다 다른 처리를 하고자 할 때
            //  ⭐️ 위에서 처리한 종류에 속하는 오류를 아래에 넣으면 컴파일 에러
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.printf("%d : 🍡 배열의 크기를 벗어남%n", i);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.printf("%d : 🔤 문자열의 길이를 벗어남%n", i);
        } catch (ClassCastException e) {
            System.out.printf("%d : 🎭 해당 클래스로 변환 불가%n", i);
        } catch (Exception e) {
            //  💡 위에서 처리되지 못한 모든 타입의 오류
            //  ⭐️ 가장 아래에 있어야 함
            System.out.printf("%d : 🛑 기타 다른 오류%n", i);
        }
    }
}
```
```java
0: 🎉 예외 없이 정상실행됨
1 : 🍡 배열의 크기를 벗어남
2 : 🔤 문자열의 길이를 벗어남
3 : 🎭 해당 클래스로 변환 불가
4 : 🛑 기타 다른 오류
```
###### ☕️ Ex01.java - tryThings3
```java
public class Ex01 {

    public static void main(String[] args) {

        IntStream.rangeClosed(0, 4)
                .forEach(Ex01::tryThings3);
    }

    public static void tryThings3 (int i) {
        try {
            switch (i) {
                case 1: System.out.println((new int[1])[1]);
                case 2: System.out.println("abc".charAt(3));
                case 3: System.out.println((Knight) new Swordman(Side.RED));
                case 4: System.out.println(((String) null).length());
            }
            System.out.printf("%d : 🎉 예외 없이 정상실행됨%n", i);

        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
            //  💡 둘 이상의 예외 타입들에 동일하게 대응할 때
            System.out.printf("%d : 🤮 범위를 벗어남%n", i);
        } catch (ClassCastException e) {
            System.out.printf("%d : 🎭 해당 클래스로 변환 불가%n", i);
        } catch (Exception e) {
            System.out.printf("%d : 🛑 기타 다른 오류%n", i);
        }
    }
}
```
```java
0 : 🎉 예외 없이 정상실행됨
1 : 🤮 범위를 벗어남
2 : 🤮 범위를 벗어남
3 : 🎭 해당 클래스로 변환 불가
4 : 🛑 기타 다른 오류
```

## 3. 예외 정의하고 발생시키기
* 예외 던지기 *throw*
* 컴퓨터가 문제라고 인식하지 못하는 상황에서 인위적으로 예외 발생시키기

###### ☕️ Ex01.java
```java
throw new RuntimeException();
```
* 예외/오류가 던져지면 그 아래의 코드는 작성할 수 없음
```java
throw new RuntimeException("에러 메시지 작성!");
```
* `console`: `Exception in thread "main" java.lang.RuntimeException: 에러 메시지 작성!` 
```java
throw new RuntimeException("에러 메시지",
        new IOException(
                new NullPointerException()
        )
);
```
* `console`: <br>
`Exception in thread "main" java.lang.RuntimeException: 에러 메시지
	at sec10.chap03.Ex01.main(Ex01.java:14)`<br>
`Caused by: java.io.IOException: java.lang.NullPointerException
	... 1 more`<br>
`Caused by: java.lang.NullPointerException
	... 1 more`
* Exception 파라미터로 error message와 다른 Exception을 넣어줄 수도 있음.

###### ☕️ Ex02.java
```java
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
```
###### console
```
정핫훈님 7월 담당으로 배정되셨어요.
잘나온얼굴.png 파일 열람
👨‍🏫 인강 프로그램을 실행합니다...
java.io.FileNotFoundException: 파일이 존재하지 않습니다.
	   at sec10.chap03.Ex02.openMyFile(Ex02.java:34)
	   at sec10.chap03.Ex02.main(Ex02.java:18)
```

#### 💡 사용자 정의 예외 만들기
###### ☕️ Ex03.java 
```java
public class Ex03 {

    public static void main(String[] args) {
        try {
            registerDutyMonth("김돌준", 13);
        } catch (WrongMonthException we) {
            we.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerDutyMonth (String name, int month) {
        if (month < 1 || month > 12) {
            throw new WrongMonthException(month);
        }
        System.out.printf("%s씨 %d월 담당으로 배정되셨어요.%n", name, month);
    }
}
```
###### ☕️ WrongMonthException.java
```java
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
```
###### console
```
sec10.chap03.WrongMonthException: 13월은 존재하지 않습니다. 1 ~ 12월 중에서 정확하게 입력해주세요.
```



