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

## 2. Wrapper 클래스들
![img_3](https://github.com/ro117-youshin/TIL/blob/master/Java/java-practice-yalco/img/wrapper.png)

* 각 원시 자료형에는 그에 해당하는 래퍼 클래스가 있다.
  * 해당 자료형에 관련된 클래스/인스턴스 기능들을 제공한다.
  * 클래스 인스턴스를 받는 곳에서 사용된다.
    * 제네릭 등에서
* 각 자료형의 원시값은 해당 래퍼 클래스의 인스턴스와 서로 변환 가능하다.
* 💡 원시값의 존재 이유 : 더 높은 성능을 위해.
  * 대신 순수한 객체지향 언어는 아니게 됨.
 

#### Ex01
###### ☕️ Ex01.java
```java

int int1 = 117;
double dbl1 = 3.14;
char chr1 = 'A';
boolean bln = true;

// 해당 래퍼 클래스의 인스턴스
// 기존의 생성자 방식 - java 9버전 이후부터 deprecated (성능상 좋지 않음)
// Integer int2 = new Integer(123);
// Double dbl2 = new Double(3.14);
// Character chr2 = new Character('A');
// Boolean bln2 = new Boolean(true);

// 💡 아래의 클래스 메소드들이 권장된다. 
Integer int3 = Integer.valueOf(123);
Double dbl3 = Double.valueOf(3.14);
Character chr3 = Character.valueOf('A');
Boolean bln3 = Boolean.valueOf(true);
```

### 📌 박싱과 언박싱
* 원시값을 래퍼 클래스의 인스턴스로 boxing
* 래퍼 클래스의 인스턴스를 원시값으로 unboxing

#### Ex02
###### ☕️ Ex02.java
```java
	//  💡 박싱 : 원시값을 래퍼 클래스의 인스턴스로
        //  ⭐ 과거에는 생성자를 사용했으나 deprecated
        int intPrim1 = 123;
        Integer intInst1 = Integer.valueOf(intPrim1);

        char chrPrim1 = 'A';
        Character chrInst1 = Character.valueOf(chrPrim1);

        //  💡 언박싱 : 래퍼 클래스의 인스턴스를 원시값으로
        Double dblInst1 = Double.valueOf(3.14);
        double dblPrim1 = dblInst1.doubleValue();

        Boolean blnInst1 = Boolean.valueOf(true);
        boolean blnPrim1 = blnInst1.booleanValue();
```

### 📌 오토박싱과 언박싱
* 명시적으로 박싱/언박싱을 하지 않아도 컴파일러가 자동으로 처리해준다.
* 성능상으로는 떨어지므로 자주 사용하지는 말 것(반복문 안에서 등). 조금만 생각하면 될 수준..

```java
	static int add(Integer a, Integer b) { return a + b; }
```
```java
	//  💡 오토박싱
        Integer intInst2 = 234;
        Double dblInst2 = 1.414213;

        //  💡 오토언박싱
        char chrPrim2 = Character.valueOf('B');
        boolean blnPrim2 = Boolean.valueOf(false);

        //  원시값과 래핑 클래스 인스턴스끼리의 연산
        int intPrim2 = intPrim1 + intInst2;
        Integer intInst3 = intPrim2 + intInst2;

        //  메소드 등 사용처들에 혼용 가능
        Integer intInst4 = add(3, 5);
```

### 📌 래퍼 클래스의 대표적/유용한 메소드들

#### Ex03
###### Ex03.java
```java
	//  💡 숫자 클래스 메소드들

        //  CharSequence로부터 인스턴스 반환
        //  ⭐ CharSequence : String 등이 구현하는 인터페이스
        Integer int1 = Integer.valueOf("123"); // 문자열로부터 인스턴스 반환

        //  CharSequence로부터 원시값 반환
        //  💡 다른 숫자, 불리언 래퍼 자료형들에도 존재 (parseDouble, parseBoolean...)
        int int2 = Integer.parseInt("123"); // 원시값 반환

        //  parseInt(CharSequence, 진수)
        //  정수 자료형들에만 존재
        //  ⭐ CharSequence : String 등이 구현하는 인터페이스
        int int_123_oct = Integer.parseInt("123", 8);
        int int_123_dec = Integer.parseInt("123", 10);
        int int_123_hex = Integer.parseInt("123", 16);

        //  parseInt(CharSequence, 시작위치, 끝위치, 진수)
        int int3 = Integer.parseInt("1234567", 3, 5, 10);
```
```java
	//  💡 문자 클래스 메소드들

        String strSample = "Ab가1 .";
        for (int i = 0; i < strSample.length(); i++) {
            Character c = strSample.charAt(i);
            System.out.printf(
                    "[%c] : L: %b, U: %b, L: %b, D: %b, S: %b%n",
                    c,
                    Character.isLetter(c),
                    Character.isUpperCase(c),
                    Character.isLowerCase(c),
                    Character.isDigit(c),
                    Character.isSpaceChar(c)
            );
        }
```
```java
	//  💡 인스턴스 메소드들

        //  문자열 반환 (Object에서 오버라이드)
        String intStr = int1.toString();
        String dblStr = Double.valueOf(3.14).toString();
        String blnStr = ((Boolean) false).toString();
        String chrStr = new Character('A').toString();
```
```java
	//  인스턴스끼리의 value 비교
        Integer intA = 12345;
        Integer intB = 12345;

        boolean compByOp1 = intA == intB; // ⚠️ 값은 같으나 다른 참조
        boolean compByEq1 = intA.equals(intB);

        Short srtA = 12345;

        //  ⚠️ 자료형이 다르면 false 반환 (메소드 코드 확인)
        boolean compByOp2 = intA.equals(srtA);
```
```java
	//  숫자 자료형 간 변환 - Number의 추상 메소드들

	Byte int1Byt = int1.byteValue();
        Double int1Dbl = int1.doubleValue();

        Integer int4 = 123456789;
        Byte int4Byt = int4.byteValue(); // ⚠️ 자료형보다 값이 큼

        Float flt1 = 1234.5678f;
        Integer flt1Int = flt1.intValue(); // ⚠️ 소수점 이하 버림
        Short int1DblSrt = int1Dbl.shortValue();
```

## 3. 제네릭
> 자바의 정석 CHAPTER 12 참조
* 다양한 타입의 객체들을 다루는 메서드나 컬렉션 클래스에 컴파일 시의 타입 체크 (compile-time type check)를 해주는 기능이다.
* 객체의 타입을 컴파일 시에 체크하기 때문에 객체의 타입 안정성을 높이고 형변환의 번거로움이 줄어든다.
* 장점으로, <ins>타입 안정성을 제공</ins>하고 <ins>타입체크와 형변환을 생략</ins>할 수 있으므로 코드가 간결해진다.
  * 타입 안정성을 높인다는 것은 의도하지 않은 타입의 객체를 저장하는 것을 막고, 저장된 객체를 꺼내올 때 원래의 타입과 다른 타입으로 형변환되어 발생할 수 있는 오류를 줄여준다는 뜻이다.

### 📌 제네릭 타입과 다형성
> 자바의 정석 CHAPTER 12 참조

* 제네릭 클래스의 객체를 생성할 때, 참조변수에 지정해준 제네릭 타입과 생성자에 지정해준 제네릭 타입은 일치해야 한다.
* 아래의 클래스 ```Mac``` 과 ```Product``` 가 서로 상속관계에 있어도 일치해야 한다.

```java
ArrayList<Mac> macList = new ArrayList<Mac>();	// 일치
ArrayList<Product> productList = new ArrayList<Mac>(); // 불일치, error
// ...
class Product {}
class Mac extends Product {}
class iPhone extends Product {}
```

* 제네릭 타입이 아닌 클래스의 타입 간에 다형성은 적용 가능하다.
* 이러한 경우에도 제네릭 타입은 일치해야 한다.

```java
List<Mac> macArrayList = new ArrayList<Mac>();
List<Mac> macLinkedList = new LinkedList<Mac>();
```

* ArrayList에 자식 객체만 저장해서 사용하고 싶다면,
* 제네릭 타입에 ```Product``` 인 ArrayList를 생성하고, ```Mac``` 과 ```iPhone``` 의 객체를 저장하면 된다. 

```java
ArrayList<Product> productList = new ArrayList<Product>();
productList.add(new Product());
productList.add(new Tv());
productList.add(new iPhone());
```
* 대신 저장된 객체를 꺼낼 때는 형변환이 필요하다.

```java
Product pd = list.get(0);
Mac mac = (Mac)list.get(1);
```

### 📌 제네릭 메소드 
#### ex01
###### ☕️ Main.java
```java
//  제네릭 메소드
//  T : 타입변수. 원하는 어떤 이름으로든 명명 가능
public static <T> T pickRandom (T a, T b) {
	return Math.random() > 0.5 ? a : b;
}
```
```java
	int randNum = pickRandom(123, 456);
        boolean randBool = pickRandom(true, false);
        String randStr = pickRandom("마루치", "아라치");

	//  import sec05.chap08.ex01.YalcoChicken;
        JavaChicken store1 = new JavaChicken("판교");
        JavaChicken store2 = new JavaChicken("역삼");
        JavaChicken randStore = pickRandom(store1, store2);

        //  ⚠️ 타입이 일관되지 않고 묵시적 변환 불가하면 오류
        //  double randFlt = pickRandom("hello", "world");
        double randDbl = pickRandom(12, 34);
```
```java
public static <T> void arraySwap (T[] array, int a, int b) {
	if (array.length <= Math.max(a, b)) return;
        T temp = array[a];
        array[a] = array[b];
        array[b] = temp;
}
```
```java
	//  원시값 배열(double[])을 쓰면 오류 - 배열로는 오토박싱이 안 되므로
        Double[] array1 = new Double[] {
                1.2, 2.3, 3.4, 4.5, 5.6, 6.7, 7.8
        };
        Character[] array2 = new Character[] {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'
        };

        arraySwap(array1, 3, 5);
        arraySwap(array2, 2, 7);
```
```java
	// 셔플
        for (int i = 0; i < 100; i++) {
            arraySwap(
                    array2,
                    (int) Math.floor(Math.random() * array2.length),
                    (int) Math.floor(Math.random() * array2.length)
            );
        }
```

### 📌 제네릭 클래스
#### 📁 ex02
###### ☕️ Pocket.java
```java
//  원하는 자료형들로 세 개의 필드를 갖는 클래스
public class Pocket<T1, T2, T3> {
    private T1 fieldA;
    private T2 fieldB;
    private T3 fieldC;

    public Pocket(T1 fieldA, T2 fieldB, T3 fieldC) {
        this.fieldA = fieldA;
        this.fieldB = fieldB;
        this.fieldC = fieldC;
    }

    public T1 getFieldA() {
        return fieldA;
    }

    public T2 getFieldB() {
        return fieldB;
    }

    public T3 getFieldC() {
        return fieldC;
    }
}
```
###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {
        //  선언시 아래와 같이 자료형에 각 타입변수의 자료형을 명시
        //  - 제내릭에는 원시값이 아닌 클래만 사용 가능
        //  - (래퍼 클래스의 또 다른 존재 이유)
        Pocket<Double, Double, Double> size3d1 =
                new Pocket<>(123.45, 234.56, 345.67);

        //  타입추론도 가능은 함
        Pocket<Double, Double, Double> size3d2 =
                new Pocket<>(123.45, 234.56, 345.67);

        double width = size3d1.getFieldA();
        double height = size3d1.getFieldB();
        double depth = size3d1.getFieldC();

        Pocket<String, Integer, Boolean> person =
                new Pocket<>("홍길동", 20, false);

        //  제네릭 클래스는 배열 생성시 new로 초기화 필수
        Pocket<String, Integer, Boolean>[] people = new Pocket[]{
                new Pocket<>("홍길동", 20, false),
                new Pocket<>("전우치", 30, true),
                new Pocket<>("임꺽정", 27, true),
        };
    }
}
```

### 📌 제한된 제네릭
#### 📁 ex03
###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {
        double sum1 = add2Num(12, 34.56);
        double sum2 = add2Num("1" + true); // ⚠️ 불가
    }

    //  💡 T는 Number를 상속한 클래스이어야 한다는 조건
    public static <T extends Number> double add2Num(T a, T b) {
        return a.doubleValue() + b.doubleValue();
    }
    //  ❓ 그냥 Number를 인자 자료형으로 하면 되지 않을까?
}
```
```java
import sec05.chap09.ex01.*;

public class Main {
    public static void main(String[] args) {
		
	descHuntingMamal(new PolarBear());
	descHuntingMamal(new GlidingLizard()); // ⚠️ 불가
		
	descFlyingHunter(new Eagle());
	descFlyingHunter(new GlidingLizard());
	descFlyingHunter(new PolarBear()); // ⚠️ 불가
    }

    //  ⭐ 상속받는 클래스와 구현하는 인터페이스(들)을 함께 조건으로
    //  여기서는 클래스와 인터페이스 모두 extends 뒤에 &로 나열
    public static <T extends Mamal & Hunter & Swimmer>
    void descHuntingMamal (T animal)  {
        //  ⭐️ 조건에 해당하는 필드와 메소드 사용 가능
        System.out.printf("겨울잠 %s%n", animal.hibernation ? "잠" : "자지 않음");
        animal.hunt();
    }

    public static <T extends Flyer & Hunter>
    void descFlyingHunter (T animal) {
        animal.fly();
        animal.hunt();
    }
}
```

### 📌 실무에서 사용할만한 간단한 예제
#### 📁 ex04
###### ☕️ FormElement.java
```java
public abstract class FormElement {
    public enum MODE { LIGHT, DARK }

    private static MODE mode = MODE.LIGHT;

    public void printMode () {
        System.out.println(mode);
    }

    abstract void func ();
}
```
###### ☕️ Clickable.java
```java
public interface Clickable {
    void onClick();
}

```
###### ☕️ Button.java
```java
public class Button extends FormElement implements Clickable {
    @Override
    public void onClick() { func(); }

    @Override
    void func() { System.out.println("버튼 클릭");}
}
```
###### ☕️ Switch.java
```java
public class Switch extends FormElement implements Clickable {
    private boolean isOn;

    public Switch(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    public void onClick() { func(); }

    @Override
    void func() {
        isOn = !isOn;
        System.out.printf("%s(으)로 전환%n", isOn ? "ON" : "OFF");
    }
}
```
###### ☕️ TextInput.java
```java
public class TextInput extends FormElement {
    @Override
    void func() {
        System.out.println("텍스트 입력 받음");
    }
}
```
###### ☕️ HyperLink.java
```java
public class HyperLink implements Clickable {
    @Override
    public void onClick() {
        System.out.println("링크로 이동");
    }
}
```
###### ☕️ FormClicker.java
```java
public class FormClicker<T extends FormElement & Clickable> {
    private T formClicker;

    public FormClicker(T formClicker) {
        this.formClicker = formClicker;
    }

    //  ⭐️ 조건의 클래스와 인터페이스의 기능 사용 가능
    //  - 자료형의 범위를 특정해주므로
    public void printElemMode () {
        formClicker.printMode();
    }

    public void clickElem () {
        formClicker.onClick();
    }
}
```
* ```printElemMode()``` 함수와 ```clickElem()``` 함수가 핵심

###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {
        FormClicker<Button> fc1 = new FormClicker<>(new Button());
        FormClicker<Switch> fc2 = new FormClicker<>(new Switch(true));

        fc1.printElemMode();
        fc2.clickElem();

        //  ⚠️ 조건에 부합하지 않는 클래스 사용 불가
//        FormClicker<TextInput> fc3 = new FormClicker<>(new TextInput());
//        FormClicker<HyperLink> fc4 = new FormClicker<>(new HyperLink());
    }
}
```




