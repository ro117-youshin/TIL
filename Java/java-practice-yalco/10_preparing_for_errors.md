# 10. 오류에 대비하기
> '제대로 파는 자바 - 얄코' 섹션10 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. 예외처리
> 2. try 문 더 알아보기

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







