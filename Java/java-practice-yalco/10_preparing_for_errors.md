# 10. 오류에 대비하기
> '제대로 파는 자바 - 얄코' 섹션10 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. 예외처리
> 2. try 문 더 알아보기
> 3. 예외 정의하고 발생시키기
> 4. 예외 떠넘기기와 되던지기기
> 5. try with resources
> 6. NPE와 Optional

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

---

## 4. 예외 떠넘기기와 되던지기

### 💡 Checked Exception VS Unchecked Exception
> 넥스트리소프트 : Java 예외(Exception) 처리에 대한 작은 생각 [(링크)](https://www.nextree.co.kr/p3239/)
* Checked Exception
  * 반드시 예외를 처리해야 함
  * 확인시점: 컴파일 단계
  * 예외 발생 시 트랜잭션 처리 roll-back 하지 않음
  * 대표 예외: Exception의 상속받는 하위 클래스 중 `RuntimeException` 제외한 모든 예외
    * `IOException`
    * `SQLException`
* Unchecked Exception
  * 명시적인 처리를 강제하지 않음
  * 확인시점: 실행단계
  * 예외 발생 시 트랜잭션 처리 roll-back 함
  * 대표 예외: `RuntimeException` 하위 예외
    * `NullPointerException`
    * `IllegalArgumentException`
    * `IndexOutOfBoundException`
    * `SystemException`   
* Checked Exception과 Unchecked Exception의 가장 명확한 구분 기준은 '꼭 처리를 해야 하느냐'이다.
  * Checked Exception이 발생할 가능성이 있는 메서드라면 반드시 로직을 `try/catch`로 감싸거나 `throw`로 던져서 처리해야 한다.
  * 반면에 Unchecked Exception은 명시적인 예외처리를 하지 않아도 된다. 이 예외는 피할 수 있지만 개발자가 부주의해서 발생하는 경우가 대부분이고, 미리 예측하지 못했던 상황에서 발생하는 예외가 아니기 때문에 굳이 로직으로 처리를 할 필요가 없도록 만들어져 있다.
* 한 가지 더 인지하고 있으면 좋은 것. 바로 예외 발생 시 트랜잭션의 roll-back 여부이다.
  * 기본적으로 Checked Exception은 예외가 발생하면 트랜잭션을 roll-back 하지 않고 예외를 던져준다.
  * 하지만 Unchecked Exception은 예외 발생 시 트랜잭션을 roll-back 한다는 점에서 차이가 있다.
  * 트랜잭션의 전파방식 즉, 어떻게 묶어놓느냐에 따라서 Checked Exception이냐 Unchecked Exception이냐의 영향도가 크다.
  * roll-back이 되는 범위가 달라지기 때문에 개발자가 이를 인지하지 못하면, 실행결과가 맞지 않거나 예상치 못한 예외가 발생할 수 있다.
  * 그러므로 이를 인지하고 트랜잭션을 적용시킬 때 전파방식(propagation behavior)과 롤백규칙 등을 적절히 사용하면 더욱 효율적인 애플리케이션을 구현할 수 있을 것이다.

###### ☕️ Ex01.java
```java
public class Ex01 {

    public static void main(String[] args) {
        try {
            maybeException2();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //  💡 RuntimeException 과 그 자손들 : unchecked 예외
    //  - 주로 개발자의 실수로 일어나는 예외 (실수 안 하면 됨)
    //  - 예외처리가 필수가 아님 (하지 않아도 컴파일 가능)
    public static void maybeException1 () {
        if (new Random().nextBoolean()) {
            throw new RuntimeException();
        }
    }

    //  ⚠️ 다른 예외들은 checked 예외
    //  - 해당 메소드 내에서, 또는 호출한 곳에서 예외처리 필수
    //  - 외적 요인으로 발생하는 예외 (조심해도 소용없으므로 대비해야 함)
    //  - ⭐️ IDE의 안내에 따라 두 가지 옵션 실행해보기
    //    - 메서드에서 자체에서 던지고 호출부를 try/catch로 감싸기
    //    - 메서드 내에서 try/catch로 감싸기
    public static void maybeException2 () throws FileNotFoundException {
        if (new Random().nextBoolean()) {
            throw new FileNotFoundException();
        }
    }
}
```

#### 💡 예외를 메서드 외부로 떠넘기기

###### ☕️ WrongMonthException.java
```java
public class WrongMonthException extends Exception {
    public WrongMonthException(String message) {
        super(message);
    }

    public WrongMonthException(String message, Throwable cause) {
        super(message, cause);
    }
}
```
###### ☕️ Ex02.java
```java
public class Ex02 {

    public static void main(String[] args) {

        Map<String, Integer> dutyRegMap = new HashMap<>();
        dutyRegMap.put("정핫훈", 7);
        dutyRegMap.put("김돌준", 13);

        dutyRegMap.forEach((name, month) -> {

            //  💡 실행부에서, 혹은 또 이를 호출한 외부에서 처리해주어야 함
            try {
                registerDutyMonth(name, month);
            } catch (WrongMonthException we) {
                we.printStackTrace();
                System.out.printf("%s님 다시 입력해주세요.%n", name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //  💡 예외를 던질 가능성이 있지만 스스로 처리하지는 않는 메소드
    public static void registerDutyMonth (String name, int month) throws WrongMonthException {
        if (month < 1 || month > 12) {
            throw new WrongMonthException("잘못 입력하셨습니다.");
        }
        System.out.printf("%s님 %d월 담당으로 배정되셨어요.%n", name, month);
    }
}
```
###### console
```java
sec10.chap04.WrongMonthException: 잘못 입력하셨습니다.
	at sec10.chap04.Ex02.registerDutyMonth(Ex02.java:30)
	at sec10.chap04.Ex02.lambda$main$0(Ex02.java:17)
	at java.base/java.util.HashMap.forEach(HashMap.java:1421)
	at sec10.chap04.Ex02.main(Ex02.java:13)
김돌준님 다시 입력해주세요.
정핫훈님 7월 담당으로 배정되셨어요.
```

#### 💡 예외 되던지기
* 메서드와 호출부 모두에서 예외를 처리
* 메서드에서는 예외처리를 한 뒤 이를 다시 던짐

###### ☕️ Ex02.java
```java
public class Ex02 {

    public static void main(String[] args) {

        Map<String, Integer> dutyRegMap = new HashMap<>();
        dutyRegMap.put("정핫훈", 7);
        dutyRegMap.put("김돌준", 13);

        dutyRegMap.forEach((name, month) -> {
            try {
                registerDutyMonthSafer(name, month);
            } catch (WrongMonthException we) {
                we.printStackTrace();
                System.out.println("호출부에서 다시 호출");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //  💡 일단 자기 선에서도 처리하고 외부로도 던지는 메소드
    //  - 필요한 일은 하되, 정상적으로 진행된 것은 아님을 호출부에 알리기 위함
    //  - 예외를 양쪽에서 처리해줄 필요가 있을 때
    public static void registerDutyMonthSafer (String name, int month) throws WrongMonthException {
        try {
            if (month < 1 || month > 12) {
                throw new WrongMonthException(
                        "우선 임의로 업무기간을 배정하겠습니다."
                );
            }
            System.out.printf("%s님 %d월 담당으로 배정되셨어요.%n", name, month);
        } catch (WrongMonthException we) {
            System.out.printf(
                    "%s님 %d월 담당으로 배정되셨어요.%n",
                    name, new Random().nextInt(1, 12 + 1)
            );
            throw we;
        }
    }
}
```
###### console
```java
김돌준님 4월 담당으로 배정되셨어요.
호출부에서 다시 호출
정핫훈님 7월 담당으로 배정되셨어요.
sec10.chap04.WrongMonthException: 우선 임의로 업무기간을 배정하겠습니다.
	at sec10.chap04.Ex02.registerDutyMonthSafer(Ex02.java:31)
	at sec10.chap04.Ex02.lambda$main$0(Ex02.java:15)
	at java.base/java.util.HashMap.forEach(HashMap.java:1421)
	at sec10.chap04.Ex02.main(Ex02.java:13)
```

#### 💡 예외의 버블링
* 하위 메서드에서 처리하지 못한 예외는 윗선에서 처리

###### ☕️ SmallException.java
```java
public class SmallException extends Exception {
    public SmallException() {
        super("사원급 문제");
    }
}
```
###### ☕️ MediumException.java
```java
public class MediumException extends Exception {
    public MediumException() {
        super("대리급 문제");
    }
}
```
###### ☕️ LargeException.java
```java
public class LargeException extends Exception {
    public LargeException() {
        super("부장급 문제");
    }
}
```
###### ☕️ XLargeException.java
```java
public class XLargeException extends Exception {
    public XLargeException() {
        super("사장급 문제");
    }
}
```

###### ☕️ Ex03.java
```java
public class Ex03 {
    public static void main(String[] args) {
        IntStream.rangeClosed(0, 4)
                .forEach(Ex03::ceo);
    }
    //  스택 최상위의 메서드, 맨 나중에 호출
    //  사원 메서드에서는 SmallException만 처리하고 나머지는 외부로 넘긴다.
    public static void sawon (int i) throws XLargeException, LargeException, MediumException {
        try {
            switch (i) {
                case 1: throw new SmallException();
                case 2: throw new MediumException();
                case 3: throw new LargeException();
                case 4: throw new XLargeException();
                default:
                    System.out.println("저 가 봐도 되죠?");
            }
        } catch (SmallException se) {
            System.out.println(se.getMessage() + ": 저 이건 알아요!");
        } catch (Exception e) {
            throw e;
        }
    }

    public static void daeri (int i) throws XLargeException, LargeException {
        try {
            sawon(i);
        } catch (MediumException me) {
            System.out.println(me.getMessage() + ": 내가 처리할 테니 가 봐요.");
        } catch (Exception e) {
            throw e;
        }
    }

    public static void bujang (int i) throws XLargeException {
        try {
            daeri(i);
        } catch (LargeException le) {
            System.out.println(le.getMessage() + ": 잘 하자. 응?");
        } catch (Exception e) {
            throw e;
        }
    }

    public static void ceo (int i) {
        try {
            bujang(i);
        } catch (XLargeException xe) {
            System.out.println(xe.getMessage() + ": 전원 집합");
        }
    }
}
```

###### console
```
저 가 봐도 되죠?
사원급 문제: 저 이건 알아요!
대리급 문제: 내가 처리할 테니 가 봐요.
부장급 문제: 잘 하자. 응?
사장급 문제: 전원 집합
```

#### 💡 연결된 예외 *chained exception*
* 특정 예외가 발생할 때 이를 원인으로 하는 다른 예외를 던짐

###### ☕️ Ex04.java
```java
public class Ex04 {

    public static void main(String[] args) {
        Map<String, Integer> dutyRegMap = new HashMap<>();
        dutyRegMap.put("정핫훈", 3);
        dutyRegMap.put("김돌준", 8);

        dutyRegMap.forEach((name, month) -> {

            //  💡 실행부에서, 혹은 또 이를 호출한 외부에서 처리해주어야 함
            try {
                chooseDutyMonth(name, month);
            } catch (WrongMonthException we) {
                we.printStackTrace(); // ⭐️ 로그에서 Caused By 항목 확인해 볼 것
                System.out.printf("%s씨, 해보자는 거지?%n", name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void chooseDutyMonth (String name, int index) throws WrongMonthException {
        int[] availables = {1, 3, 4, 7, 9, 12};

        try {
            int month = availables[index - 1];
            System.out.printf("%s씨 %d월 담당으로 배정되셨어요.%n", name, month);
        } catch (ArrayIndexOutOfBoundsException ae) {
            WrongMonthException we = new WrongMonthException(
                    "%d번은 없어요.".formatted(index)
            );

            //  💡 예외의 원인이 되는 예외를 지정 (이를 수행하는 생성자가 없을 경우)
            we.initCause(ae);
            //  이 예외는 cause를 입력받는 생성자를 지정해놓았음
            //  - IDE의 안내를 따라 바꿔 볼 것

            //  ⭐️ 다른 종류의 예외가 발생해도 이 예외의 원인으로 등록해서
            //  통일된 타입(WrongMonthException)의 예외로 반환 가능

            throw we;
        }
    }
}
```
###### console
```java
sec10.chap04.WrongMonthException: 8번은 없어요.
	at sec10.chap04.Ex04.chooseDutyMonth(Ex04.java:34)
	at sec10.chap04.Ex04.lambda$main$0(Ex04.java:16)
	at java.base/java.util.HashMap.forEach(HashMap.java:1421)
	at sec10.chap04.Ex04.main(Ex04.java:12)
Caused by: java.lang.ArrayIndexOutOfBoundsException: Index 7 out of bounds for length 6
	at sec10.chap04.Ex04.chooseDutyMonth(Ex04.java:30)
	... 3 more
김돌준씨, 해보자는 거지?
정핫훈씨 4월 담당으로 배정되셨어요.
```

---

## 5. try with resources
* 사용한 뒤 닫아주어야 하는 리소스 접근에 사용
  * 파일 열람, 데이터베이스 접근 등
  * 기존에 `finally` 블록으로 명시해야 했던 것을 간편화
* try문에서 호출, 초기화하면 Closable의 close 메서드를 실행하여 자동으로 닫아준다. 

###### turtle.txt
```
거북이 날아라
거북이 날아라
```
###### ☕️ Ex01.java
```java
public class Ex01 {

    public static void main(String[] args) {
        String correctPath = "./src/sec09/chap04/turtle.txt";
        String wrongPath = "./src/sec09/chap04/rabbit.txt";

        openFile1(correctPath);
        openFile1(wrongPath);
    }

    public static void openFile1 (String path) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.printf("⚠️ %s 파일 없음%n", path);
        } finally {
            System.out.println("열었으면 닫아야지 ㅇㅇ");
            if (scanner != null) scanner.close();

            //  💡 만약 이 부분을 작성하는 것을 잊는다면?
        }
    }
}
```
###### console
```
거북이 날아라
거북이 날아라
열었으면 닫아야지 ㅇㅇ
⚠️ ./src/sec09/chap04/rabbit.txt 파일 없음
열었으면 닫아야지 ㅇㅇ
java.io.FileNotFoundException: .\src\sec09\chap04\rabbit.txt (지정된 파일을 찾을 수 없습니다)
	at java.base/java.io.FileInputStream.open0(Native Method)
	at java.base/java.io.FileInputStream.open(FileInputStream.java:216)
	at java.base/java.io.FileInputStream.<init>(FileInputStream.java:157)
	at java.base/java.util.Scanner.<init>(Scanner.java:639)
	at sec10.chap05.Ex01.openFile1(Ex01.java:21)
	at sec10.chap05.Ex01.main(Ex01.java:14)
```
###### ☕️ Ex01.java
```java
public class Ex01 {

    public static void main(String[] args) {
        String correctPath = "./src/sec09/chap04/turtle.txt";
        String wrongPath = "./src/sec09/chap04/rabbit.txt";

        openFile2(correctPath);
        openFile2(wrongPath);
    }

    public static void openFile2 (String path) {
        //  ⭐ Scanner가 Closable - AutoClosable를 구현함 확인

        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.printf("⚠️ %s 파일 없음%n", path);
        }

        // 💡 .close를 작성하지 않아도 자동으로 호출됨
    }
}
```
###### console
```java
거북이 날아라
거북이 날아라
⚠️ ./src/sec09/chap04/rabbit.txt 파일 없음
java.io.FileNotFoundException: .\src\sec09\chap04\rabbit.txt (지정된 파일을 찾을 수 없습니다)
	at java.base/java.io.FileInputStream.open0(Native Method)
	at java.base/java.io.FileInputStream.open(FileInputStream.java:216)
	at java.base/java.io.FileInputStream.<init>(FileInputStream.java:157)
	at java.base/java.util.Scanner.<init>(Scanner.java:639)
	at sec10.chap05.Ex01.openFile2(Ex01.java:25)
	at sec10.chap05.Ex01.main(Ex01.java:19)
```

#### 직접 만들어보기
* 작전 실패/성공 여부와 상관없이 무조건 "💣 전원 폭사"
###### ☕️ OpFailException.java
```java
public class OpFailException extends Exception {
    public OpFailException() {
        super("💀 작전 실패");
    }
}
```
###### ☕️ SuicideSquad.java
```java
public class SuicideSquad implements AutoCloseable {
    public void doSecretTask () throws OpFailException {
        if (new Random().nextBoolean()) {
            throw new OpFailException();
        };
        System.out.println("🔫 비밀 작전 수행");
    }

    @Override
    public void close() throws Exception {
        System.out.println("💣 전원 폭사\n- - - - -\n");
    }
}
```
###### ☕️ Ex02.java
```java
public class Ex02 {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            dirtyOperation();
        }
    }

    public static void dirtyOperation () {
        try (SuicideSquad sc = new SuicideSquad()) {
            sc.doSecretTask();
        } catch (OpFailException e) {
            //  💡 예외상황은 아만다 윌러가 책임짐
            e.printStackTrace();
            System.out.println("🗑️ 증거 인멸\n- - - - -\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```
###### console
```java
💣 전원 폭사
- - - - -

🗑️ 증거 인멸
- - - - -

🔫 비밀 작전 수행
💣 전원 폭사
- - - - -

🔫 비밀 작전 수행
💣 전원 폭사
- - - - -

🔫 비밀 작전 수행
💣 전원 폭사
- - - - -

💣 전원 폭사
- - - - -

🗑️ 증거 인멸
- - - - -

🔫 비밀 작전 수행
💣 전원 폭사
- - - - -

💣 전원 폭사
- - - - -

🗑️ 증거 인멸
- - - - -

🔫 비밀 작전 수행
💣 전원 폭사
- - - - -

🔫 비밀 작전 수행
💣 전원 폭사
- - - - -

💣 전원 폭사
- - - - -

🗑️ 증거 인멸
- - - - -

sec10.chap05.OpFailException: 💀 작전 실패
	at sec10.chap05.SuicideSquad.doSecretTask(SuicideSquad.java:8)
	at sec10.chap05.Ex02.dirtyOperation(Ex02.java:13)
	at sec10.chap05.Ex02.main(Ex02.java:7)
sec10.chap05.OpFailException: 💀 작전 실패
	at sec10.chap05.SuicideSquad.doSecretTask(SuicideSquad.java:8)
	at sec10.chap05.Ex02.dirtyOperation(Ex02.java:13)
	at sec10.chap05.Ex02.main(Ex02.java:7)
sec10.chap05.OpFailException: 💀 작전 실패
	at sec10.chap05.SuicideSquad.doSecretTask(SuicideSquad.java:8)
	at sec10.chap05.Ex02.dirtyOperation(Ex02.java:13)
	at sec10.chap05.Ex02.main(Ex02.java:7)
sec10.chap05.OpFailException: 💀 작전 실패
	at sec10.chap05.SuicideSquad.doSecretTask(SuicideSquad.java:8)
	at sec10.chap05.Ex02.dirtyOperation(Ex02.java:13)
	at sec10.chap05.Ex02.main(Ex02.java:7)
```

---

## 6. NPE와 Optional

### `NullPointerException`
* `null`인 것으로부터 필드나 메서드 등을 호출하려 할 때 발생
  * Ex) 폐업한 중국집에 배민 주문
* 컴파일러 선에서 방지되지 않음
  * `RuntimeException`

###### ☕️ Ex01.java
```java
String nulStr = null;
System.out.println(nulStr.length()); // ⚠️ NPE
```
```java
public class Ex01 {

    public static void main(String[] args) {
        System.out.println(
                catOrNull().length()  // 반복실행해 볼 것
        );
    }

    public static String catOrNull() {
        //  슈뢰딩거의 고양이
        return new Random().nextBoolean() ? "Cat" : null;
    }
}
```
* 위 코드를 반복해서 실행했을 때, 삼항연산자가 `false`일 경우 NPE
```java
public class Ex01 {

    public static void main(String[] args) {
	try {
            System.out.println(
                    catOrNull().length()
            );
        } catch (NullPointerException ne) {
            ne.printStackTrace();
            System.out.println(0);
        }
    }

    public static String catOrNull() {
        //  슈뢰딩거의 고양이
        return new Random().nextBoolean() ? "Cat" : null;
    }
}
```
* `try/catch` 문으로 NPE 대비하기 (오류가 발생하면 대안으로 0을 출력)
* 그러나 `null`이 발생하는 모든 곳에 대비할 수는 없다. 그래서 나온 것이 `Optional`

### `Optional`
* `Optional<T>`: `null` 일 수도 있는 `T` 타입의 값
* `null` 일 수 있는 값을 보다 안전하고 간편하게 사용하기 위

#### of: 담으려는 것이 확실히 있을 때
```java
Optional<String> catOpt = Optional.of("Cat");
catOpt = Optional.of(null); // ⚠️ of로 null을 담으면 NPE
```

#### ofNullable: 담으려는 것이 null일 수도 있을 때
```java
Optional<String> dogOpt = Optional.ofNullable("Dog"); // "Optional[Dog]"
Optional<String> cowOpt = Optional.ofNullable(null); // "Optional.empty"
```

#### empty: 명시적으로 null을 담으려면
```java
Optional<String> henOpt = Optional.empty(); // "Optional.empty"
```

###### `Optional`을 사용하여 Ex01.java 슈뢰딩거 고양이 구현
```java
public static void main(String[] args) {

        catOpt = getCatOpt();
}

public static Optional<String> getCatOpt() {
	return Optional.ofNullable(new Random().nextBoolean() ? "Cat" : null);
}
```
* `return`에는 `Optional`상자에 값이 담겨오기 때문에 NPE 대비 

#### `Optional`의 `filter`와 `map` 메서드
```java
public static void main(String[] args) {
    
        List<Optional<Integer>> optInts = new ArrayList<>();
        IntStream.range(0, 20)
                .forEach(i -> {
                    optInts.add(Optional.ofNullable(
                            new Random().nextBoolean() ? i : null
                    ));
                });

        //  💡 Optional의 filter와 map 메소드
        optInts.stream()
                .forEach(opt -> {
                    System.out.println(
                            opt
                                    //  ⭐️ 걸러진 것은 null로 인식됨
                                    //  - 스트림의 filter처럼 건너뛰는 것이 아님!
                                    .filter(i -> i % 2 == 1)
                                    .map(i -> "%d 출력".formatted(i))
                                    .orElse("(SKIP)")
                    );
                });
}
```
###### console
```java
(SKIP)
(SKIP)
(SKIP)
3 출력
(SKIP)
(SKIP)
(SKIP)
7 출력
(SKIP)
(SKIP)
(SKIP)
11 출력
(SKIP)
13 출력
(SKIP)
15 출력
(SKIP)
17 출력
(SKIP)
19 출력
```

### `Optional`을 반환하는 스트림의 메서드들
* 반환할 값이 없을 수도 있는 메서드들 - 빈 스트림일 때 

###### ☕️ Ex03.java
```java
public class Ex03 {

    public static void main(String[] args) {
        // 메소드마다 반환값이 다를 수 있으므로 var 사용
        var numFromOpt = IntStream.range(0, 100)

                .filter(i -> i % 2 == 1)
                //.filter(i -> i > 100) // 주석해제 후 다시 실행해 볼 것

                //  💡 첫 번째 요소를 반환
                .findFirst() // 항상 순서상 첫번 째 것을 반환, 'OptionalInt'

                //.max() // 'OptionalInt'
                //.min() // 'OptionalInt'

                //  평균값을 ⭐️ Double로 반환
                //.average() // 'OptionalDouble'

		// 초기값이 없다면 'OptionalInt'
		// 초기값이 잆으면 'Int'
                //.reduce((prev, curr) -> prev + curr)

                .orElse(-1); // Optional이 반환되므로
        //  혹은 기타 Optional의 인스턴스 메소드 사용
    }
}
```

```java
public class Ex03 {

    public static void main(String[] args) {
        // 메소드마다 반환값이 다를 수 있으므로 var 사용
        var numFromOpt = IntStream.range(0, 100)
                .parallel() // 병렬 실행 (이후 배움), 주석해제 해 볼 것

                .filter(i -> i % 2 == 1)

                //  💡 첫 번째 요소를 반환
                .findAny() // ⭐️ 병렬작업 시 먼저 나오는 것 반환
                // 병렬작업 시 findAny가 보다 효율적
                // (순서가 중요하지 않다면)

                .orElse(-1); // Optional이 반환되므로
        //  혹은 기타 Optional의 인스턴스 메소드 사용
    }
}
```
###### console
```
65
```
```java
public class Ex03 {
	String[] names = {
                "강백호", "서태웅", "채치수", "송태섭", "정대만",
                "윤대협", "변덕규", "황태산", "안영수", "허태환",
                "이정환", "전호장", "신준섭", "고민구", "홍익현",
                "정우성", "신현철", "이명헌", "최동오", "정성구"
        };

        Stream<String> nameStream = Arrays.stream(names);

        Random random = new Random();
        random.setSeed(4); // 균일한 결과를 위해 지정된 시드값
        List<Person> people = nameStream
                .map(name -> new Person(
                        name,
                        random.nextInt(18, 35),
                        random.nextDouble(160, 190),
                        random.nextBoolean()
                ))
                .sorted()
                .toList();

        Person personFromOpt = people.stream()
                //.filter(p -> !p.isMarried() && p.getAge() < 20 && p.getHeight() > 189)

                .findFirst()

                //.max(Comparator.comparingDouble(Person::getHeight))
                //.min(Comparator.comparingInt(Person::getAge))

                .orElse(new Person("엄친아", 19, 189.9, false));
    }
}
```
###### console
```
// 그대로 실행할 경우
personFromOpt = no: 1, name: 강백호, age: 26, height: 187.227096, married: true
// filter(), max(), min() 주석해제할 경우
personFromOpt = no: 21, name: 엄친아, age: 19, height: 189.900000, married: false
```






