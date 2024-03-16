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










