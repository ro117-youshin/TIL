# Section 5. 객체지향 프로그래밍
> '제대로 파는 자바 - 얄코' 섹션5 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. 클래스 기초
> 2. 기초 활용예제들
> 3. 클래스(정적) 필드와 메소드
> 4. 접근 제어자 (접근 제한자, access modifier)

## 1. 클래스 기초
* 객체 *object* / 인스턴스 *instance* : 속성(프로퍼티)들과 기능(메소드)들의 묶음
  * 자바에서는 객체와 인스턴스를 같은 것으로 이해해도 됨
* 인스턴스는 클래스에서 정의한 방식으로 양산됨 
### 💡클래스 &  인스턴스 -> 프랜차이즈 본사 & 매장
#### ex02
###### ☕️YalcoChicken.java
```java
//  본사의 코드
public class YalcoChicken {
    //  인스턴스가 가질 필드(field)들
    int no;
    String name;

    //  인스턴스가 가질 메소드 - 💡 static을 붙이지 않음
    String intro () {
        // no와 name 앞에 this를 붙인 것과 같음
        return "안녕하세요, %d호 %s점입니다."
                .formatted(no, name);
    }
}
```
###### ☕️Main.java
```java
public static void main(String[] args) {
    //  본사 소속의 매장을 내는 코드
    YalcoChicken store1 = new YalcoChicken();
    store1.no = 3; // 🔴 
    store1.name = "판교";

    YalcoChicken store2 = new YalcoChicken();
    store2.no = 17;
    store2.name = "강남";


    //  인스턴스의 필드들에 접근
    int store1No = store1.no;
    String store2Name = store2.name;

    //  인스턴스의 메소드 호출
    String store1Intro = store1.intro();
}
```
* 디버깅 툴로 인스턴스들 살펴볼 것
---
### ⭐️생성자 메소드 - 매장을 내는 메소드
> Java의 정석 CHAPTER 6 생성자(constructor) 참조
* 생성자는 인스턴스가 생성될 때 호출되는 '인스턴스 초기화 메서드'이다. 따라서 인스턴스변수의 초기화 작업에 주로 사용되며, 인스턴스 생성 시에 실행되어야 하는 작업을 위해서도 사용된다.
  * *인스턴스 초기화란, 인스턴스 변수들을 초기화하는 것을 뜻함.*
* 메서드처럼 클래스 내에 선언되며, 구조도 메서드와 유사하지만 리턴값이 없다. (void도 적지 않고, 단지 아무것도 적지 않는다.)
#### 생성자의 조건
1. 생성자의 이름은 클래스의 이름과 같아야 한다.
2. 생성자는 리턴 값이 없다.

* 생성자도 오버로딩이 가능하므로 하나의 클래스에 여러 개의 생성자가 존재할 수 있다.
* **연산자 new가 인스턴스를 생성하는 것이지 생성자가 인스턴스를 생성하는 것이 아니다.** 생성자라는 용어 때문에 오해하기 쉬운데, 생성자는 단순히 인스턴스변수들의 초기화에 사용되는 조금 특별한 메서드일 뿐 생성자가 갖는 몇 가지 특성만 제외하면 메서드와 다르지 않다.

### ⭐️this - 만들어질 인스턴스를 가리킴(객체 자신을 가리키는 참조변수) 
> Java의 정석 CHAPTER 6 객체 자신을 가리키는 참조변수 - this 참조
* 아래 ex03 코드의 생성자의 매개변수로 선언된 지역변수 no의 값을 인스턴스변수 this.no에 저장한다. 이 때 매개변수로 선언된 지역변수와 인스턴스변수의 이름이 다르다면 서로 구별이 되므로 아무런 문제가 없다.
* 하지만, ex03 코드와 같이 생성자의 매개변수로 선언된 변수의 이름이 no로 인스턴스변수 no와 같을 경우에는 인스턴스변수 앞에 'this'를 사용하면 된다.
  * 만일 'this.no = no' 대신 'no = no'와 같이 작성하면 둘 다 지역변수로 간주된다.
  * 이처럼 생성자의 매개변수로 인스턴스변수들의 초기값을 제공받는 경우가 많기 때문에 매개변수와 인스턴스변수의 이름이 일치하는 경우가 자주 있다. 이때는 변수이름을 다르게 사용하는 것보다 'this'를 사용해서 구별되도록 하는 것이 의미가 더 명확하고 이해하기 쉽다.
* 'this'는 참조변수로 인스턴스 자신을 가리킨다. 참조변수를 통해 인스턴스의 멤버에 접근할 수 있는 것처럼, 'this'로 인스턴스 변수에 접근할 수 있는 것이다.
  * 하지만, 'this'를 사용할 수 있는 것은 인스턴스멤버뿐이다. static메서드(클래스 메서드)에서는 인스턴스 멤버들을 사용할 수 없는 것처럼, 'this' 역시 사용할 수 없다.
  * 왜냐하면, static메서드는 인스턴스를 생성하지 않고도 호출될 수 있으므로 static메서드가 호출된 시점에 인스턴스가 존재하지 않을 수도 있기 때문이다.
  * 사실 생성자를 포함한 모든 인스턴스메서드는 자신이 관련된 인스턴스를 가리키는 참조변수 'this'가 지역변수로 숨겨진 채로 존재한다.

#### ex03 
###### ☕️YalcoChicken.java
```java
public class YalcoChicken {
    int no;
    String name;

    //  ⭐ 생성자(constructor) : 인스턴스를 만드는 메소드
		//  ⭐ this : 생성될 인스턴스를 가리킴
    YalcoChicken (int no, String name) {
        this.no = no;
        this.name = name;
    }

    String intro () {
				//  String name = "몽고반"; // 주석해제 시 name 대체
        return "안녕하세요, %d호 %s점입니다." // 🔴
                .formatted(no, name);
    }
}
```
###### ☕️Main.java
```java
public static void main (String[] args) {
    //  클래스로 인스턴스를 생성 - 💡 new 연산자 + 생성자 호출
    //  본사의 방침대로 매장을 내는 것
    YalcoChicken store1 = new YalcoChicken(3, "판교");
    YalcoChicken store2 = new YalcoChicken(17, "강남");
    YalcoChicken store3 = new YalcoChicken(24, "제주");

    String[] intros = {store1.intro(), store2.intro(), store3.intro()};
}
```
---
#### ex01
###### ☕️Button.java
```java
public class Button {

    char print;
    int space;
    String mode;

    Button (char print, int space, String mode) {
        this.print = print;
        this.space = space;
        this.mode = mode;
    }

    void place () {
        System.out.printf(
                "표시: %c, 공간: %s, 모드: %s%n",
                print,space, mode
        );
    }
}
```
###### ☕️Main.java
```java
public static void main(String[] args) {
    Button button1 = new Button('1', 1, "DARK");
    Button buttonPlus = new Button('+', 3, "DARK");
    Button buttonClear = new Button('C', 2, "DARK");

    button1.place();
    buttonPlus.place();
    buttonClear.place();
}
```

## 2. 기초 활용예제들
### 📌 슬라임 클래스
* 생성자를 필요로 하지 않음
* 필드들이 기본 값을 가짐
* 인스턴스를 인자로 받는 메소드

###### ☕️Slime.java
```java
public class Slime {
    double hp = 50;
    int attack = 8;
    double defense = 0.2;

    void attack (Slime enemy) { // 💡 다른 슬라임의 인스턴스를 인자로 받음
        enemy.hp -= attack * (1 - enemy.defense);
    }
}
```
###### ☕️Main.java
```java
public static void main(String[] args) {

	Slime slime1 = new Slime();
	Slime slime2 = new Slime();

	slime1.attack(slime2);
}
```
* ⭐️ 객체는 참조형 - 인자로 전달될 시 내용이 변경될 수 있음
* 같은 클래스의 인스턴스지만, 필드의 값은 각기 별개임 주목
