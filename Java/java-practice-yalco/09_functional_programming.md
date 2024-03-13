# Section 9. 함수형 프로그래밍
> '제대로 파는 자바 - 얄코' 섹션9 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. 람다식과 함수형 인터페이스
> 2. java.util.function 패키지
> 3. 메소드 참조
> 4. 스트림
> 5. 스트림 연산

## 1. 람다식과 함수형 인터페이스

```java
(인자1, 인자2, ...) -> { 반환값 }
```

### 📌 람다식 lambda expression
* 자바8에 추가된 기능
* 메소드를 간략히 하나의 '식 (expression)' 으로 표현
* 익명 함수 anonymous function 이라고도 부름
* 자바에서는 인터페이스의 익명 클래스를 간략히 표현하는데 사용됨
  * 모든 메소드는 클래스에 포함되어야 하므로 클래스도 새로 만들어야 하고, 객체도 생성해야만 비로소 메소드를 호출할 수 있다. 그러나 람다식은 이 모든 과정없이 오직 람다식 자체만으로 메서드의 역할을 대신할 수 있다.
* 람다식은 메소드의 매개변수로 전달되어지는 것이 가능하고, 메소드의 결과로 반환될 수도 있다. (메소드를 변수처럼 다룸)

### 📌 함수형 인터페이스 FunctionalInterface
* 람다식 형태로 익명 클래스가 만들어질 수 있는 인터페이스
  * 조건: 추상 메소드가 하나(만) 있어야 함
    * 람다식과 1:1로 대응될 수 있어야 하기 때문
    * ```@FunctionalInterface``` 로 강제
    * 클래스 메소드와 ```default``` 메소드는 여럿 있을 수 있음  

#### 📁 람다식 기본 문법
###### ☕️ Printer.java
```java
@FunctionalInterface
public interface Printer {
    void Print();

//    void saySomething(); // ⚠️둘 이상의 메소드는 불가
}
```
###### ☕️ Main.java
```java
Printer printer1 = new Printer() {
    @Override
    public void Print() {
        System.out.println("람다식이 없었을 때 방식");
    }
};

Printer printer2 = () -> {
    System.out.println("인자, 반환값 없는 람다식");
};
Printer printer3 = () -> System.out.println("반환값 없을 시 { } 중괄호 생략 가능");
Printer printer4 = () -> {
    System.out.println("코드가 여러 줄일 때는");
    System.out.println("{ } 중괄호 필요");
};

for (Printer p : new Printer[] {printer1, printer2, printer3, printer4}) {
    p.print();
}
```

#### 📁 반환값만 있을 경우
###### ☕️ Returner.java
```java
@FunctionalInterface
public interface Returner {
    Object returnObj();
}
```
###### ☕️ Main.java
```java
Returner returner1 = () -> 1; // {return 1;};
Returner returner2 = () -> "반환 코드만 있을 시 { } 중괄호와 return 생략가능";

Object returned1 = returner1.returnObj(); // 1
Object returned2 = returner2.returnObj(); // 반환 코드만 있을 시 { } 중괄호와 return 생략가능
```

#### 📁 매개변수가 하나일 때
###### ☕️ SingleParam.java
```java
@FunctionalInterface
public interface SingleParam {
    int func (int i);
}
```
###### ☕️ Main.java
```java
SingleParam square = (i) -> i * i;
SingleParam cube = i -> i * i * i; // 인자가 하나일 시 괄호 생략 가능

int _3_squared = square.func(3); // 9
int _3_cubed = cube.func(3);     // 27
```

#### 📁 매개변수가 두 개일 때
###### ☕️ DoubleParam.java
```java
@FunctionalInterface
public interface DoubleParam {
    int func(int a, int b);
}
```
###### ☕️ Main.java
```java
DoubleParam add = (a, b) -> a + b;
DoubleParam multAndPrint = (a, b) -> {
    int result = a * b;
    System.out.printf("%d * %d = %d%n", a, b, result);
    return result;
};

int added = add.func(2, 3);
int multiplied = multAndPrint.func(2, 3);
```
