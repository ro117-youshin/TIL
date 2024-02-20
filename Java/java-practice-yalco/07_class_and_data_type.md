# Section 7. 클래스와 자료형
'재대로 파는 자바 - 얄코' 섹션6 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. Object
> 2. Wrapper 클래스들
> 3. 제네릭

## 1. Object

### 📌 ```toString``` 메서드
* 인스턴스에 대한 정보를 ```String```으로 제공할 목적으로 정의한 메서드이다.
* 인스턴스의 정보를 제공한다는 것은 대부분의 경우 인스턴스 변수에 저장된 값들을 ```String``` 으로 표현한다는 뜻이다.

#### Object클래스에 정의된 ```toString()```
```java
public String toString() {
  return getClass().getName() + "@" + Integer.toHexString(hashCode());
}
```
* ```toString()```을 오버라이딩 하지 않는다면 위의 코드를 그대로 사용. 즉, 클래스의 이름과 16진수의 해시코드를 얻게 된다.

#### ex01
###### ☕️ Button.java
```java
package sec07.chap01.ex01;

public class Button {
    public enum Mode {
        LIGHT("라이트"), DARK("다크");
        Mode(String indicator) { this.indicator = indicator; }
        String indicator;
    }

    private String name;
    private Mode mode;
    private int spaces;

    public Button(String name, Mode mode, int spaces) {
        this.name = name;
        this.mode = mode;
        this.spaces = spaces;
    }

//    @Override
//    public String toString() {
//        return "%s %s 버튼 (%d칸 차지)".formatted(mode.indicator, name, spaces);
//    }
}
```
###### ☕️ Main.java
```java
package sec07.chap01.ex01;

public class Main {
    public static void main(String[] args) {
        Button button1 = new Button("엔터", Button.Mode.DARK, 3);

        System.out.println(button1); // ⭐️ toString() 을 붙인 것과 같음
    }
}
```
###### console
```java
// 기존 toString
sec07.chap01.ex01.Button@5ca881b5
// 오버라이딩한 toString
다크 엔터 버튼 (3칸 차지)
```

### 📌 ```equals``` 메서드
* 기본적으로는 ```==``` 과 같이 레퍼런스 비교
* 인스턴스 내용을 비교하려면 클래스마다 오버라이드 해야 한다.

#### Object클래스에 정의된 ```equals()```
```java
public boolean equals(Object obj) {
  return (this==obj);
}
```
* 매개변수로 객체의 참조변수를 받아서 비교하여 그 결과를 ```boolean``` 값으로 알려주는 역할을 한다.
* 두 객체가 같고 다름을 참조변수의 값으로 판단한다. (참조변수에 저장된 값, 주소값)
* 때문에 서로 다른 두 객체를 ```equals```로 비교한다면 항상 ```false```를 결과로 얻는다.
* ```equals```로 인스턴스의 값을 비교하려면 오버라이딩하여 주소가 아닌 객체에 저장된 내용을 비교하도록 변경하면 된다.

#### ex02
###### ☕️ Click.java
```java
public class Click {
    int x;
    int y;
    int timestamp;

    public Click(int x, int y, int timestamp) {
        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Click)) return false;
      return this.x == ((Click) obj).x && this.y == ((Click) obj).y;
    }
}
```

###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {
        Click click1 = new Click(123, 456, 5323487);
        Click click2 = new Click(123, 456, 5323487);
        Click click3 = new Click(123, 456, 2693702);
        Click click4 = new Click(234, 567, 93827345);

        boolean bool1 = click1 == click1;
        boolean bool2 = click1 == click2;
        boolean bool3 = click1 == click3;
        boolean bool4 = click1 == click4;

        boolean boolA = click1.equals(click1);
        boolean boolB = click1.equals(click2);
        boolean boolC = click1.equals(click3);
        boolean boolD = click1.equals(click4);
    }
}
```
#### ex02 basic한 ```equals``` 
![img_1](https://github.com/ro117-youshin/TIL/blob/master/Java/java-practice-yalco/img/basic_equals.png)
* 같은 인스턴스의 비교만 ```true```를 반환, 매개변수의 값이 전부 동일하다하더라도 ```false```
* 즉, 주소값이 틀리면 무조건 ```false```

#### ex02 오버라이딩한 ```equals``` 
![img_2](https://github.com/ro117-youshin/TIL/blob/master/Java/java-practice-yalco/img/override_equals.png)
* ```==```는 레퍼런스 비교이기 때문에 basic한 ```equals```의 결과와 동일.
* 위 오버라이딩한 ```equals```에서는 ```x```와 ```y``` 값을 비교하도록 했기 때문에 ```boolA```, ```boolB```, ```boolC```는 ```true```

