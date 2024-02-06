# Section 5. 객체지향 프로그래밍
> '제대로 파는 자바 - 얄코' 섹션5 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. 클래스 기초
> 2. 기초 활용예제들
> 3. 클래스(정적) 필드와 메소드
> 4. 접근 제어자
> 5. 상속
> 6. 다형성
> 7. 클래스의 final
> 8. 추상 클래스
> 9. 인터페이스
> 10. 싱글턴

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

---

## 2. 기초 활용예제들
### 📌 슬라임 클래스
* 생성자를 필요로 하지 않음
* 필드들이 기본 값을 가짐
* 인스턴스를 인자로 받는 메소드
#### ex01
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

	slime1.attack(slime2);	// 🔴
}
```
<img width="219" alt="스크린샷 2023-12-27 오전 10 13 04" src="https://github.com/ro117-youshin/TIL/assets/86038910/c0ea0dd8-ba3f-44b8-9418-cb0ccdc103c6">

* ⭐️ 객체는 참조형 - 인자로 전달될 시 내용이 변경될 수 있음
* 같은 클래스의 인스턴스지만, 필드의 값은 각기 별개임 주목

### 📌 정수배열 정보 클래스 (복잡한 과정을 거치는 생성자의 예시)
* (main 메소드의) 배열을 생성자 인자로 받아, 그것의 정보를 필드들로 저장

#### ex02
###### ☕️IntArrayInfo.java
```java
public class IntArrayInfo {
    int count;
    int max;
    int min;
    int sum; // 기본 0
    double average;

    IntArrayInfo(int[] nums) {
        count = nums.length;
        max = nums[0];
        min = nums[0];

        for (int num : nums) {
            max = max > num ? max : num;
            min = min < num ? min : num;
            sum += num;
        }
        // 소수부를 잃지 않도록 먼저 1.0을 곱하여 double로 만듦
        average = 1.0 * sum / nums.length;
    }
}
```
###### ☕️Main.java
```java
public static void main(Stinrg[] args) {
    	int[] ary1 = {3, 5, 9, 2, 8, 1, 4};
	int[] ary2 = {382, 194, 27, 915, 138};

        IntArrayInfo aryInf1 = new IntArrayInfo(ary1);
        IntArrayInfo aryInf2 = new IntArrayInfo(ary2);

        int ary1Max = aryInf1.max;
        double ary2Avg = aryInf2.average;
        int ary1n2Sum = aryInf1.sum + aryInf2.sum;
}
```

### 📌 치킨과 치킨메뉴 클래스
* 클래스의 필드로 다른 클래스의 인스턴스를 담은 배열을 가짐
  * 클래스가 인스턴스가 배열 등 다른 자료형에도, 그 반대로도 포함될 수 있음
* 클래스는 둘 이상의 생성자를 가질 수 있음
* 클래스의 인스턴스를 반환하는 메소드

#### ex03
###### ☕️Chicken.java
```java
public class Chicken {
    int no;
    String name;
    ChickenMenu[] menus;

    Chicken (int no, String name, ChickenMenu[] menus) {
        this.no = no;
        this.name = name;
        this.menus = menus;
    }

    ChickenMenu orderMenu (String name) {
        for (ChickenMenu menu : menus) {
            if (menu.name.equals(name)) { // 🔴
                return menu;
            }
        }
        return null;
    }
}
```
###### ☕️ChickenMenu.java
```java
public class ChickenMenu {
    String name;
    int price;
    String cook = "fry";

    ChickenMenu (String name, int price) {
        this.name = name;
        this.price = price;
    }

    ChickenMenu (String name, int price, String cook) {
        this.name = name;
        this.price = price;
        this.cook = cook;
    }
}
```
###### ☕️Main.java
```java
public static void main(String[] args) {
	ChickenMenu[] menus = {
                new ChickenMenu("후라이드", 10000),
                new ChickenMenu("양념치킨", 12000),
                new ChickenMenu("화덕구이", 15000, "bake")
        };
        YalcoChicken store1 = new YalcoChicken(3, "판교", menus);

        ChickenMenu order1 = store1.orderMenu("양념치킨");
        ChickenMenu order2 = store1.orderMenu("오븐구이");
}
```

<img width="256" alt="image" src="https://github.com/ro117-youshin/TIL/assets/86038910/f1760f73-691c-4610-b41f-13b31b683348">

### 📌 클래스의 인스턴스도 참조 자료형
###### ☕️Main.java (ex03 Main.java에 코드 추가)
```java
public static void main(String[] args) {
	int int1 = 1;
        int int2 = int1;
        int1 = 2;

        String str1 = "헬로";
        String str2 = str1;
        str2 = "월드";

        ChickenMenu menu1 = new ChickenMenu("후라이드", 1000);
        ChickenMenu menu2 = menu1;
        menu1.price = 1200;
}
```
```java
public static void raisePrice (int avg, ChickenMenu menu, int amount) {
    avg += amount;
    menu.price += amount;
}
```
```java
	int avgChickenPrice = 12000;
        ChickenMenu chickenMenu1 = new ChickenMenu("양념치킨", 12000);

        raisePrice(avgChickenPrice, chickenMenu1, 1000);
```
* 배열과 같이, 인스턴스도 필드로 들어간 데이터들을 포함하는 객체
* 메소드에 인자로 들어갈 시, 인스턴스의 주소값이 복사되어 들어감
  * 복사된 주소지만 같은 객체를 가리키므로...

---

## 3. 클래스(정적) 필드와 메소드
#### ex01
###### ☕️YalcoChicken.java
```java
public class YalcoChicken {

    //  ⭐️ 클래스/정적 필드와 메소드들 : 본사의 정보와 기능
    //  인스턴스마다 따로 갖고 있을 필요가 없는 것들에 사용
    static String brand = "얄코치킨";
    static String contact () {
        //  ⚠️ 정적 메소드에서는 인스턴스 프로퍼티 사용 불가
        //  System.out.println(name);

        return "%s입니다. 무엇을 도와드릴까요?".formatted(brand);
    }

    int no;
    String name;

    YalcoChicken(int no, String name) {
        this.no = no;
        this.name = name;
    }

    String intro () {
        //  💡 인스턴스 메소드에서는 정적 프로퍼티 사용 가능
        return "안녕하세요, %s %d호 %s점입니다."
                .formatted(brand, no, name);
    }
}
```
###### ☕️Main.java
```java
public static void main(String[] args) {
	//  💡 클래스 필드와 메소드는 인스턴스를 생성하지 않고 사용
        String ycBrand = YalcoChicken.brand;
        String ycContact = YalcoChicken.contact();

        // ⚠️ 인스턴스 메소드는 불가
        //  String ycName = YalcoChicken.name;
        //  String ycIntro = YalcoChicken.intro();

        YalcoChicken store1 = new YalcoChicken(3, "판교");
        String st1Intro = store1.intro();

        //  인스턴스에서는 클래스의 필드와 메소드 사용 가능
        //  ⚠️ 편의상 기능일 뿐, 권장하지 않음 (혼란 초래. IDE에서 자동완성 안 됨 주목)
        String st1Brand = store1.brand;
        String st1Contact = store1.contact();
}
```
![image](https://github.com/ro117-youshin/TIL/assets/86038910/ce745c99-8237-4534-bd2b-db6847303dc9)
* 클래스(정적 *static*) 요소: 메모리 중 한 곳만 차지
* 인스턴스 요소들: 각각이 메모리에 자리를 차지
  * 각각의 자신만의 프로퍼티 값을 가지고 있음
### 📌 static 메서드와 인스턴스 메서드
> 자바의 정석 CHAPTER 6 참조
* 간단하게, 메서드 앞에 static이 붙어 있으면 클래스 메서드이고 붙어 있지 않으면 인스턴스 메서드다.
* 클래스 메서드도 클래스 변수처럼, 객체를 생성하지 않고 *'클래스이름.메서드이름(매개변수)'* 와 같은 식으로 호출이 가능하다. 반면에 인스턴스 메서드는 반드시 객체를 생생해야만 호출할 수 있다.
* 그렇다면 클래스를 정의할 때, 어느 경우에 static을 사용해서 클래스 메서드로 정의해야하는 것일까?
  * 클래스는 '데이터(변수)와 데이터에 관련된 메서드의 집합'이므로, 같은 클래스 내에 있는 메서드와 멤버변수는 아주 밀접한 관계가 있다.
  * 인스턴스 메서드는 인스턴스 변수와 관련된 작업을 하는, 즉 메서드의 작업을 수행하는데 인스턴스 변수를 필요로 하는 메서드이다. 그런데 인스턴스 변수는 인스턴스(객체)를 생성해야만 만들어지므로 인스턴스 메서드 역시 인스턴스를 생성해야만 호출할 수 있다.
  * 반면에 메서드 중에서 인스턴스와 관계없는(인스턴스 변수나 인스턴스 메서를 사용하지 않는) 메서드를 클래스 메서드(static메서드)로 정의한다.
  * 인스턴스 변수를 사용하지 않는다고 해서 반드시 클래스 메서드로 정의해야하는 것은 아니지만 특별한 이유가 없는 한 그렇게 하는 것이 일반적이다.

### 📌 static을 언제 붙여야 할까?
> 자바의 정석 CHAPTER 6 참조
1. 클래스를 설계할 때, 멤버변수 중 모든 인스턴스에 공통으로 사용하는 것에 static을 붙인다.
   - 생성된 각 인스턴스는 서로 독립적이기 때문에 각 인스턴스의 변수는 서로 다른 값을 유지한다. 그러나 모든 인스턴스에서 같은 값이 유지되어야 하는 변수는 static을 붙여서 클래스변수로 정의해야 한다.
2. 클래스 변수(static변수)는 인스턴스를 생성하지 않아도 사용할 수 있다.
   - static이 붙은 변수(클래스변수)는 클래스가 메모리에 올라갈 때 이미 자동적으로 생성되기 때문이다.
3. 클래스 메서드(static메서드)는 인스턴스 변수를 사용할 수 없다.
   - 인스턴스변수는 인스턴스가 반드시 존재해야만 사용할 수 있는데, 클래스 메서드(static메서드)는 인스턴스 생성 없이 호출가능하므로 클래스 메서드가 호출되었을 때 인스턴스가 존재하지 않을 수도 있다. 그래서 클래스 메서드에서 인스턴스변수의 사용을 금지한다.
   - 반면에 인스턴스 변수나 인스턴스 메서드에서는 static이 붙은 멤버들을 사용하는 것이 언제나 가능하다. 인스턴스 변수가 존재한다는 것은 static변수가 이미 메모리에 존재한다는 것을 의미하기 때문이다.
4. 메서드 내에서 인스턴스 변수를 사용하지 않는다면, static을 붙이는 것을 고려한다.
   - 메서드 작업내용 중에서 인스턴스 변수를 필요로 한다면, static을 붙일 수 없다. 반대로 인스턴스 변수를 필요로 하지 않는다면 static을 붙이자. 메서드 호출시간이 짧아지므로 성능이 향상된다. static을 안 붙인 메서드(인스턴스 메서드)는 실행 시 호출되어야할 메서드를 찾는 과정이 추가적으로 필요하기 때문에 시간이 더 걸린다.
* 멤버변수 중 모든 인스턴스에 공통된 값을 유지해야하는 것이 있는지 살펴보고 있으면, static을 붙여준다.
* 작성한 메서드 중에서 인스턴스 변수나 인스턴스 메서드를 사용하지 않는 메서드에 static을 붙일 것을 고려한다.


### 📌 매장 번호 자동 생성
#### ex02
###### ☕️YalcoChicken.java
```java
public class YalcoChicken {

    static String brand = "얄코치킨";
    static String contact () {
        return "%s입니다. 무엇을 도와드릴까요?".formatted(brand);
    }
    static int lastNo = 0; // ⭐️

    int no;
    //int no = ++lastNo; // 이렇게 해도 됨

    String name;

    YalcoChicken(String name) {
        // 클래스 변수를 활용하여 생성마다 새 번호 부여 (또는 위처럼)
        no = ++lastNo;
        this.name = name;
    }

    String intro () {
        return "안녕하세요, %s %d호 %s호점입니다." // 🔴
                .formatted(brand, no, name);
    }
}
```
###### ☕️Main.java
```java
public static void main(String[] args) {
	YalcoChicken store1 = new YalcoChicken("판교");
        YalcoChicken store2 = new YalcoChicken("강남");
        YalcoChicken store3 = new YalcoChicken("제주");
}
```


#### ex03
###### ☕️Button.java
```java
public class Button {
    static String mode = "LIGHT";
    static void switchMode () {
        mode = mode.equals("LIGHT") ? "DARK" : "LIGHT";
    }

    char print;
    int space;

    Button (char print, int space) {
        this.print = print;
        this.space = space;
    }

    void place () {
        System.out.printf(
                "표시: %c, 공간: %d, 모드: %s%n",
                print, space, mode
        );
    }
}
```
###### ☕️Main.java
```java
public static void main(String[] args) {

	/* --- 1 --- */
	Button button1 = new Button('1', 1);
        Button buttonPlus = new Button('+', 3);
        Button buttonClear = new Button('C', 2);

        Button[] buttons = {button1, buttonPlus, buttonClear};

        System.out.println(Button.mode);
        for (Button button : buttons) { button.place(); }

	/* --- 2 --- */
	//  연속으로 붙여넣어 실행해볼 것
        Button.switchMode();
        
        System.out.println(Button.mode);
        for (Button button : buttons) { button.place(); }
}
```

---

## 4. 접근 제어자
|접근 가능|public|protected|default|private|
|-----|:-----:|:-----:|:-----:|:-----:|
|해당 클래스 안에서|✅|✅|✅|✅|
|동일 패키지 안에서|✅|✅|✅||
|동일 패키지 또는 자손 클래스 안에서|✅|✅|||
|다른 패키지 포함 어느 곳에서든|✅||||

---

## 5. 상속

#### ex01 드라이브스루를 갖춘 얄코치킨의 클래스를 만든다면?
* 기존 YalcoChicken 클래스의 모든 필드와 메소드 포함
* 드라이브스루 관련 필드와 메소드 추가
* YalcoChicken 을 부모로 하는 자식 클래스 YalcoChickenDT 만들기 (extends 연산자 사용)

###### ☕️ YalcoChicken.java
```java
public class YalcoChicken {
    protected int no;
    protected String name;

    public YalcoChicken (int no, String name) {
        this.no = no;
        this.name = name;
    }

    public void takeHallOrder () {
        System.out.printf("%d호 %s점 홀 주문 받음%n", no, name);
    }
}
```
###### ☕️ YalcoChickenDT.java
```java
public class YalcoChickenDT extends YalcoChicken {
    private boolean driveThruOpen = true;

    public YalcoChickenDT(int no, String name) {
        super(no, name);
    }

    public void setDriveThruOpen(boolean driveThruOpen) {
        this.driveThruOpen = driveThruOpen;
    }

    public void takeDTOrder () {
        System.out.printf(
                "%d호 %s점 드라이브스루 주문 %s%n",
                no, name,
                (driveThruOpen ? "받음" : "불가")
        );
    }
}
```
###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {
        YalcoChickenDT dtStore1 = new YalcoChickenDT(108, "철원");

        dtStore1.takeHallOrder();

        dtStore1.takeDTOrder();
        dtStore1.setDriveThruOpen(false);
        dtStore1.takeDTOrder();
    }
}
```
* 디버그 모드로 dtStore1 인스턴스를 살펴보면, 부모 클래스의 요소들을 갖고 있음을 확인 - *상속 inheritance*
  * (dtStore1 > driveThruOpen = false, no = 108, name = "철원")
* 부모 클래스의 protected 필드들을 private 으로 바꿔 볼 것
  * private 으로 바꾸면 YalcoChickenDT 클래스(자식 클래스) 의 takeDTOrder() 함수 안에서 에러가 발생한다.
  * 💡 상속이 안 되는 것은 아님 - 자식 클래스의 코드에서 사용하지 못할 뿐
  * Main 클래스를 실행해보면 dtStore1.takeHallOrder() 의 값인 "108호 철원점 홀 주문 받음" 을 출력
  * 자식 클래스의 인스턴스를 생성하여 부모 클래스의 함수를 호출하여 사용할 수 있음

### 메소드 오버라이딩
* 부모가 가진 같은 이름의 메소드를 자식이 다르게 정의
  * *'저는 제 방식대로 하겠습니다.'*
* (오버로딩과 혼동하지 말 것) 

#### ex02 
###### ☕️ Button.java
```java
public class Button {
    private String print;

    public Button(String print) {
        this.print = print;
    }

    public void func () {
        System.out.println(print + " 입력 적용");
    }
}
```
###### ☕️ ShutDownButton.java
```java
public class ShutDownButton extends Button {
    public ShutDownButton () {
        super("ShutDown"); // 💡 부모의 생성자 호출
    }

		//  💡 부모의 메소드를 override
		@Override
    public void func () {
        System.out.println("프로그램 종료");
    }
}
```
###### ☕️ ToggleButton.java
```java
public class ToggleButton extends Button {
    private boolean on;
    
    public ToggleButton(String print, boolean on) {
        super(print);
        this.on = on;
    }

    @Override
    public void func () {
        super.func(); // 💡 부모에서 정의한 메소드 호출
        this.on = !this.on;
        System.out.println(
                "대문자입력: " + (this.on ? "ON" : "OFF")
        );
    }
}
```
###### ☕️ Main.java
```java
public static void main(String[] args) {

	Button entrButton = new Button("Enter");
        ShutDownButton stdnButton = new ShutDownButton();
        ToggleButton tglButton = new ToggleButton("CapsLock", false);

        entrButton.func();

        System.out.println("\n- - - - -\n");

        stdnButton.func();

        System.out.println("\n- - - - -\n");

        tglButton.func();
        tglButton.func();
        tglButton.func();
}
```
```java
Enter 입력 적용

- - - - -

프로그램 종료

- - - - -

CapsLock 입력 적용
대문자입력: ON
CapsLock 입력 적용
대문자입력: OFF
CapsLock 입력 적용
대문자입력: ON
```
### super : 부모의 생성자/메소드 호출
* 부모 클래스에 생성자가 작성되었을 시
  * 자식 클래스에도 생성자 작성 필요
    * 생성자를 제거해 볼 것 - ⚠️ There is no default constructor available in 'sec05.chap05.ex02.Button'
  * super 를 사용해서 부모의 생성자를 먼저 호출
    * 이후 추가로 필요한 내용 작성
    * 즉 부모의 인스턴스부터 생성 후 이를 기반으로 자식 인스턴스 생성
    * 자식 클래스의 생성자는 super 로 시작해야 함
      * 순서 바꿔 볼 것 - ⚠️ Call to 'super()' must be first statement in constructor body

### 부모 클래스에 명시된 생성자가 없는 경우
* 자식 클래스에서도 작성할 필요 없음

#### ex03
###### ☕️ Slime.java
```java
public class Slime {
    protected double hp = 50;
    protected int attack = 8;
    protected double defense = 0.2;

    public void attack (Slime enemy) {
        enemy.hp -= this.attack * (1 - enemy.defense);
    }
}
```
###### ☕️ FireSlime.java
```java
public class FireSlime extends Slime {
    private int fireAttack = 4;

		@Override
    public void attack (Slime enemy) {
        enemy.hp -= (attack + fireAttack) * (1 - enemy.defense);
    }
}
```
###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {
        Slime slime = new Slime();
        FireSlime fireSlime = new FireSlime();

        slime.attack(fireSlime); // ⭐ 클래스가 다른데 가능한 이유 : 다음 강에서
        fireSlime.attack(slime);
    }
}
```
### 💡 상속의 또 다른 용도
* 자신이 만든 것이 아닌 클래스를 커스터마이즈

---

## 6. 다형성
* Polymorphism
### Button 클래스 상속 관계 (5강의 ex02)
* Button
  * ShutDownButton
  * ToggleButton
(부모/자식 관계는 범주의 포함 관계로 이어진다.)

#### ex01 (지난 강의 ex02 복사, Main 클래스만 작성)
###### ☕️ Main.java 
```java
public class Main {
    public static void main(String[] args) {

        //  💡 가능 - 자식 클래스는 부모 클래스에 속함
        Button button1 = new Button("Enter");
        Button button2 = new ShutDownButton();
        Button button3 = new ToggleButton("CapsLock", true);

        //  ⚠️ 불가
//        ShutDownButton button4 = new Button("Enter");
//        ToggleButton button5 = new ShutDownButton();

    }
}
```
![image](https://github.com/ro117-youshin/TIL/assets/86038910/cafb0d5c-5b0e-4169-8e52-ae939a120632)

* 자식 클래스의 인스턴스는 부모 클래스 자료형에 속한다. (디버깅)
  * (모든 ShutDownButton과 ToggleButton은 Button이다.)
* 다른 방향으로는 불가.
  * 모든 Button이 ShutDownButton이거나 ToggleButton이 아니다.
  * ShutDownButton은 ToggleButton이 아니다.  

###### ☕️ Main.java
```java
	//  ⭐️ 편의 : 모두 Button이란 범주로 묶어 배열 등에서 사용 가능
        Button[] buttons = {
                new Button("Space"),
                new ToggleButton("NumLock", false),
                new ShutDownButton()
        };

        for (Button button : buttons) {
            //  ⭐️ 모든 Button들은 func 메소드를 가지므로
            button.func();
        }
```
```java
Space 입력 적용
NumLock 입력 적용
대문자입력: ON
프로그램 종료
```
* 이처럼 특정 자료형의 자리에 여러 종류가 들어올 수 있는 것 (다형성)
  * 상속, 오버라이딩, 인터페이스 등을 통해 구현 가능

#### 5강 ex03
###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {
        Slime slime = new Slime();
        FireSlime fireSlime = new FireSlime();

        slime.attack(fireSlime); // ⭐ 클래스가 다른데 가능한 이유 : 다음 강에서
        fireSlime.attack(slime);
    }
}
```
* FireSlime은 Slime이므로
  * attack()의 인자로 사용 가능.
  * hp와 defence필드를 가졌다는 전제.

### 📌 instanceof 연산자
* 뒤에 오는 클래스의 자료형에 속하는 인스턴스인지 확인
  * 인터페이스에 대해서도 사용 가능
* 상속관계가 아닌 클래스끼리는 컴파일 오류

#### ☕️ Main.java
```java
	Button button = new Button("버튼");
        ToggleButton toggleButton = new ToggleButton("토글", true);
        ShutDownButton shutDownButton = new ShutDownButton();

        //  true
        boolean typeCheck1 = button instanceof Button;
        boolean typeCheck2 = toggleButton instanceof Button;
        boolean typeCheck3 = shutDownButton instanceof Button;

        //  false
        boolean typeCheck4 = button instanceof ShutDownButton;
        boolean typeCheck5 = button instanceof ToggleButton;

        //  ⚠️ 컴파일 에러
//        boolean typeCheck6 = toggleButton instanceof ShutDownButton;
//        boolean typeCheck7 = shutDownButton instanceof ToggleButton;
```
```java
        Button[] buttons = {
                new Button("Space"),
                new ToggleButton("NumLock", false),
                new ShutDownButton()
        };

        for (Button btn : buttons) {
            if (btn instanceof ShutDownButton) continue; // ⭐️
            btn.func();
        }
```
```java
Space 입력 적용
NumLock 입력 적용
대문자입력: ON
```
* shutDownButton이 실행되지 않음.

#### ex02 (지난 강의 ex01 복사)

###### ☕️ Main.java
```java
	YalcoChicken ycStores[] = {
                new YalcoChicken(3, "판교"),
                new YalcoChicken(17, "강남"),
                new YalcoChickenDT(108, "철원"),

        };

        for (YalcoChicken store : ycStores) {
            if (store instanceof YalcoChickenDT) {
                //  ⭐️ 자식 클래스의 기능을 사용하려면 명시적 타입 변환
                ((YalcoChickenDT) store).takeDTOrder();
            } else {
                store.takeHallOrder();
            }
        }
```
* ⭐️ 주석 부분의 명시적 타입 변환 필요.
  * store 인스턴스가 YalcoChicken 클래스로 인식하기 때문.
  * YalcoChicken 클래스에는 takeDTOrder() 메서드가 없기 때문에 error.

---

## 7. 클래스의 final

#### ex) 필드에 final
###### ☕️ JavaChicken.java
```java
public class JavaChicken {
    protected static final String CREED = "튀김옷 is Good.";

    private final int no;
    public String name;

//  ⭐️ 필수 - no가 final이고 초기화되지 않았으므로
//  생성자에서 no를 지운다면 필드 no에서 error.
//  만약 생성자에서 초기화를 하지 않는다면 필드에서 초기화를 해줘야 함.
    public JavaChicken (int no, String name) {
        this.no = no;
        this.name = name;
    }

    public void changeFinalFields() {
//        ⚠️ 불가
//        this.no++
    }

    public final void fryChicken() {
        System.out.println("염지, 반죽, 튀기기");
    }

}
```

#### ex) 메서드의 final
###### ☕️ JavaChickenDT.java
```java
public final class JavaChickenDT extends JavaChicken {
    public JavaChickenDT (int no, String name) {
        super(no, name);
    }

//    ⚠️ 불가 - 부모 JavaChicken 클래스에서 final로 생성되어 있음.
//    즉, 메서드의 final은 자손 클래스에서 상속되어서 오버라이드를 할 수 없다.
//    public void fryChicken() {
//        System.out.println("염지, 반죽, 튀기기, 다시 튀기기");
//    }
}

```

#### ex) 인스턴스의 final
###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {
        String ycCreed = JavaChicken.CREED;
//        JavaChicken.CREED = "우리의 튀김옷은 바삭하다"; // ⚠️ 불가

        // JavaChicken의 인스턴스를 final로 선언
        final JavaChicken store1 = new JavaChicken(3, "판교");

        //  ⚠️ 불가 - 인스턴스가 final로 선언되었기 때문에 다른 값으로 new 연산자를 통해 선언하는 것은 불가 
//        store1 = new JavaChicken(17, "강남");
        //  💡 요소 변경은 가능
        store1.name = "선릉";
    }
}
```

#### ex) 클래스의 final
###### ☕️ JavaChickenHighWayDT.java

```java
// ⚠️ 불가 - 클래스의 final은 자손 클래스가 상속할 수 없음.
//public class JavaChickenHighWayDT extends JavaChickenDT{
//}
```

---

## 8. 추상 클래스
> 자바의 정석 CHAPTER 7 참조
* 클래스를 설계도로 비유한다면, 추상 클래스는 미완성 설계도에 비유할 수 있다. (미완성 설계도란, 단어의 뜻 그대로 완성되지 못한 채로 남겨진 설계도를 말한다.) 
* 클래스가 미완성이라는 것은 멤버의 개수에 관계된 것이 아니라, 단지 미완성 메서드(추상 메서드)를 포함하고 있다는 의미이다.
* 미완성 설계도로 완성된 제품을 만들 수 없듯이 <u>추상 클래스로 인스턴스는 생성할 수 없다.</u>
* 추상 클래스는 상속을 통해서 자식 클래스에 의해서만 완성될 수 있다. (자식 클래스로 파생되기 위한 클래스)
  * 상속을 통해서 자식 클래스에 의해서만 완성될 수 있음.

#### ex01
###### ☕️ JavaGroup.java
```java
package sec05.chap08.ex01;

public abstract class JavaGroup {
    protected static final String CREED = "우리의 %s 얄팍하다";

    //  💡 클래스 메소드는 abstract 불가
    //  abstract static String getCreed ();

    protected final int no;
    protected final String name;

    public JavaGroup(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public String intro () {
        return "%d호 %s점입니다.".formatted(no, name);
    }
    //  이후 다른 패키지에서의 실습을 위해 public으로
    public abstract void takeOrder ();
}
```
###### ☕️ JavaChicken.java
```java
package sec05.chap08.ex01;

public class JavaChicken extends JavaGroup {
    public static String getCreed () {
        return CREED.formatted("튀김옷은");
    }
    protected static int lastNo = 0;

    public JavaChicken(String name) {
        super(++lastNo, name);
    }

    //  💡 반드시 구현 - 제거해 볼 것
    @Override
    public void takeOrder () {
        System.out.printf("자바치킨 %s 치킨을 주문해주세요.%n", super.intro());
    }
}
```
###### ☕️ JavaCafe.java
```java
package sec05.chap08.ex01;

public class JavaCafe extends JavaGroup {
    public static String getCreed () {
        return CREED.formatted("원두향은");
    }
    protected static int lastNo = 0;

    private boolean isTakeout;

    public JavaCafe(String name, boolean isTakeout) {
        super(++lastNo, name);
        this.isTakeout = isTakeout;
    }

    //  💡 반드시 구현 - 제거해 볼 것
    @Override
    public void takeOrder () {
        System.out.printf("자바카페 %s 음료를 주문해주세요.%n", super.intro());
        if (!isTakeout) System.out.println("매장에서 드시겠어요?");
    }
}
```
###### ☕️ Main.java
```java
package sec05.chap08.ex01;

public class Main {
    public static void main(String[] args) {

        //  ⚠️ 불가
//    JavaGroup JavaGroup = new JavaGroup(1, "서울");

        JavaChicken javaStore1 = new JavaChicken("판교");
        JavaChicken javaStore2 = new JavaChicken("강남");

        JavaCafe javafStore1 = new JavaCafe("울릉", true);
        JavaCafe javafStore2 = new JavaCafe("강릉", false);

        JavaGroup[] javaStores = {
                javaStore1, javaStore2,
                javafStore1, javafStore2
        };

        for (JavaGroup javaStore : javaStores) {
            javaStore.takeOrder();
        }

        System.out.println("\n- - - - -\n");

        System.out.println(JavaChicken.getCreed());
        System.out.println(JavaCafe.getCreed());
    }
}
```

### 📌 abstract 클래스
* 그 자체로 인스턴스 생성 불가
  * (ex01) 자바그룹에서 매장을 내지는 않음
* 부모 클래스로서는 일반 클래스와 같음
  * 다형성 역시 구현됨
    * 자바치킨과 자바카페의 매장은 자바그룹 소속

### 📌 abstract 메소드
* 추상 클래스에서만 사용 가능
* 스스로는 선언만 하고 구현하지 않음
  * ⭐️ 자식 클래스에서 반드시 구현
  * 구현하지 않을 시 컴파일 에러
    * 메뉴 - 코드 - 메서드 구현 / IDE의 오류 안내 클릭
  * 접근 제어자 의미 없음
* 클래스 메소드는 추상 메소드로 작성할 수 없음
  * 인스턴스를 생성해서 쓰는 것이 아니므로 맞지 않음

#### ex02 웹 사이트 UI/UX 요소들 FormElement 자바로 구현.
###### ☕️ FormElement.java
```java
public abstract class FormElement {
    protected int space;

    public FormElement(int space){
        this.space = space;
    }

    abstract void func();
}
```
###### ☕️ Button.java
```java
public class Button extends FormElement {
    private String print;

    public Button(int space, String print) {
        super(space);
        this.print = print;
    }

    @Override
    void func() {
        System.out.println(print + "입력 적용");
    }
}
```
###### ☕️ Switch.java
```java
public class Switch extends FormElement {

    private boolean on;

    public Switch(int space, boolean on) {
        super(space);
        this.on = on;
    }

    @Override
    void func() {
        on = !on;
        System.out.println((on ? "ON" : "OFF") + " 으로 전환");
    }
}
```
###### ☕️ Dropdown.java
```java
public class DropDown extends FormElement {
    String[] menus;

    public DropDown(int space, String[] menus) {
        super(space);
        this.menus = menus;
    }

    @Override
    void func() {
        System.out.println(" 메뉴 선택 \n ------- ");
        for(String menu: menus) {
            System.out.printf(" - %s%n", menu);
        }
    }
}
```
###### ☕️ Main.java
```java
public class Main {

    public static void main(String[] args) {

        Button button01 = new Button(2, "Enter");
        Switch switch01 = new Switch(3, true);
        DropDown dropdown01 = new DropDown(5, new String[] {
                "이름 오름차순", "이름 내림차순",
                "크기 오름차순", "크기 내림차순",
                "날짜 오름차순", "날짜 내림차순"
        });

        // 💡 다형성 적용 확인
        clickFormElement(button01);

        clickFormElement(switch01);
        clickFormElement(switch01);
        clickFormElement(switch01);

        clickFormElement(dropdown01);
    }

    public static void clickFormElement (FormElement fe) {
        fe.func();
    }
}
```
* clickFormElement 메서드의 파라미터 값 (FormElement fe)
  * 인자로 FormElement 값을 받는다.
  * FormElement 를 구현한, FormElement 를 부터 상속 받은 클래스의 인스턴스를 받는다. 

---

## 9. 인터페이스
> 자바의 정석 CHAPTER 7 인터페이스(interface) 참조

* 인터페이스는 일종의 추상클래스이다.
* 추상메서드를 갖지만 추상클래스보다 추상화 정도가 높아서 추상클래스와 달리 몸통을 갖춘 메서드 또는 멤버변수를 구성원으로 가질 수 없다.
* 오직 추상메서드와 상수만을 멤버로 가질 수 있다. (그 외의 다른 어떠한 요소도 허용하지 않음)
* 추상클래스를 부분적으로만 완성된 '미완성 설계도'라고 한다면, 인터페이스는 구현된 것은 아무 것도 없고 밑그림만 그려져 있는 '기본 설계도'라 할 수 있다.
* 인터페이스의 멤버들은 다음과 같은 제약사항이 있다.
  * 모든 멤버변수는 public static final 이어야 하며, 이를 생략할 수 있다.
  * 모든 메서드는 public abstract 이어야 하며, 이를 생략할 수 있다.
    * 단, static 메서드와 default 메서드는 예외 (JDK1.8부터)

#### 💡 추상 클래스와의 차이
*🔴 : 추상클래스 / 🔷 : 인터페이스*

* 🔴 포유류
  * 북극곰 - 🔷 사냥, 🔷 수영
  * 날다람쥐 - 🔷 비행
* 🔴 파충류
  * 거북 - 🔷 수영
  * 날도마뱀 - 🔷 사냥, 🔷 수영, 🔷 비행
* 🔴 조류
  * 독수리 - 🔷 사냥, 🔷 비행
  * 펭권 - 🔷 사냥, 🔷 수영
 
|       |추상 클래스|인터페이스|
|:-----:|:-----:|:-----:|
|기본 개념|물려 받는 것 (ex 혈통/가문/계열)|장착하는 것 (ex 학위/자격증)|
|다중 적용|불가 (모회사는 하나 뿐)|가능 (학위는 여럿 딸 수 있듯)|
|상속관계에 의한 제한|있음|없음|
|생성자|가짐|가지지 않음|
|메소드|구상, 추상 모두 가능| 추상 메소드, default 구상 메소드, 클래스 메소드|
|필드|모두 가능|상수만 가능 (final 명시 불필요)|
|적용 연산자|extends|implements|












