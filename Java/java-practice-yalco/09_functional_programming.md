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

## 2. java.util.function 패키지
* 자바에서는 람다식을 위한 함수형 인터페이스가 정의되어 있어야 함.
  *  필요할 때마다 정의해야 하는 번거로움이 있음.
* 일반적으로 자주 쓰이는 형식의 메소드를 함수형 인터페이스로 미리 정의해 놓음.

| 함수형 인터페이스 | 메서드 | 인자(들) 타입 | 반환값 타입 |
| --- | --- | :---: | :---: |
| ```Runnable``` (java.lang 패키지) | ```run``` |  |  |
| ```Supplier<T>``` | ```get``` |  | T |
| ```Consumer<T>``` | ```accept``` | T |  |
| ```BiConsumer<T, U>``` | ```accept``` | T, U |  |
| ```Function<T, R>``` | ```apply``` | T | R |
| ```BiFunction<T, U, R>``` | ```apply``` | T, U | R |
| ```Predicate<T>``` | ```test``` | T | boolean |
| ```BiPredicate<T, U>``` | ```test``` | T, U | boolean |
| ```UnaryOperator<T>``` | ```apply``` | T | T |
| ```BinaryOperator<T>``` | ```apply``` | T, T | T |

> * 타입 문자 'T'는 'Type'을, 'R'은 'Return Type' 을 의미. 
> * 매개 변수의 타입으로 보통 'T'를 사용하므로, 알파벳에서 'T' 다음 문자인 'U', 'V', 'W' 를 매개 변수의 타읍으로 사용하는 것. (별다른 의미는 없음)

#### 📌 ```Runnable```
* 매개변수, 반환값 모두 없음.

###### ☕️ Main.java
```java
Runnable dogButtonFunc = () -> System.out.println("멍멍");
Runnable catButtonFunc = () -> System.out.println("야옹");
Runnable cowButtonFunc = () -> System.out.println("음메");

dogButtonFunc.run();
catButtonFunc.run();
cowButtonFunc.run();
```
```java
멍멍
야옹
음메
```
###### ☕️ Button.java
```java
public class Button {
    private String text;
    public Button(String text) { this.text = text; }
    public String getText() { return text; }

    private Runnable onClickListener;
    public void setOnClickListener(Runnable onClickListener) {
        this.onClickListener = onClickListener;
    }
    public void onClick () {
        onClickListener.run();
    }
}
```
###### ☕️ Main.java
```java
System.out.println("\n- - - - -\n");

Button dogButton = new Button("강아지");
dogButton.setOnClickListener(dogButtonFunc);

Button catButton = new Button("고양이");
catButton.setOnClickListener(() -> {
    System.out.println("- - - - -");
    System.out.println(catButton.getText() + " 울음소리: 야옹야옹");
    System.out.println("- - - - -");
});

dogButton.onClick();
catButton.onClick();
```
```java
멍멍
- - - - -
고양이 울음소리: 야옹야옹
- - - - -
```

#### 📌 ```Supplier```
* 매개변수는 없고, 반환값만 있음.

```java
Supplier<String> appName = () -> "얄코오피스";
Supplier<Double> rand0to10 = () -> Math.random() * 10;
Supplier<Boolean> randTF = () -> Math.random() > 0.5;

String supp1 = appName.get();   // "얄코오피스
Double supp2 = rand0to10.get(); // 7.673529874025304
Boolean supp3 = randTF.get();   // false
```

#### 📌 ```Consumer``` & ```BiConsumer```
* ```Consumer``` : ```Supplier``` 와 반대로 매개변수만 있고, 반환값이 없음.
* ```BiConsumer``` : 두 개의 매개변수만 있고, 반환값이 없음.

```java
Consumer<Integer> sayOddEven = i -> System.out.printf(
    "%d은 %c수입니다.%n", i, "짝홀".charAt(i % 2)
);

Consumer<Button> clickButton = b -> b.onClick();

BiConsumer<Button, Integer> clickButtonNTimes = (b, n) -> {
    for (int i = 0; i < n; i++) { b.onClick(); }
};

sayOddEven.accept(3);
sayOddEven.accept(4);
clickButton.accept(catButton);
clickButtonNTimes.accept(dogButton, 5);
```
```java
3은 홀수입니다.
4은 짝수입니다.
- - - - -
고양이 울음소리: 야옹야옹
- - - - -
멍멍
멍멍
멍멍
멍멍
멍멍
```

#### 📌 ```Function``` & ```BiFunction```
* ```Function``` : 일반적인 함수, 하나의 매개변수를 받아서 결과를 반환.
* ```BiFunction``` : 두 개의 매개변수를 받아서 하나의 결과를 반환.

> Unit, Horse는 [Section 7. - 4. 다음 섹션을 위한 게임예제](https://github.com/ro117-youshin/TIL/blob/master/Java/java-practice-yalco/07_class_and_data_type.md#4-다음-섹션을-위한-게임예제) 코드에서 import

```java
Function<Integer, Boolean> isOdd = i -> i % 2 == 1;      // 홀수 여부
Function<String, Button> getButton = s -> new Button(s); // 

// Horse를 탄 Unit은 ExtraHp 효과를 구현.
BiFunction<Unit, Horse, Integer> getfullHP = (u, h) -> {
    h.setRider(u);
    return u.hp;
};

BiFunction<String, Runnable, Button> getButtonWFunc = (s, r) -> {
    Button b = new Button(s);
    b.setOnClickListener(r);
    return b;
};

Boolean isOdd3 = isOdd.apply(3); // true
Boolean isOdd4 = isOdd.apply(4); // false

Button goatButton = getButton.apply("염소");

Integer unitFullHP = getfullHP.apply( // unitFullHP: 200
    new MagicKnight(Side.RED), new Horse(80)
);

getButtonWFunc
        .apply("오리", () -> System.out.println("꽥꽥"))
        .onClick();
```

#### 📌 ```Predicate``` & ```BiPredicate```
* ```Predicate``` : 조건식을 표현하는데 사용됨. 매개변수는 하나, 반환 타입은 boolean.
* ```BiPredicate``` : 조건식을 표현하는데 사용됨. 매개변수는 둘, 반환 타입은 boolean.

> Unit, Swordman, Knight, MagicKnight은 [Section 7. - 4. 다음 섹션을 위한 게임예제](https://github.com/ro117-youshin/TIL/blob/master/Java/java-practice-yalco/07_class_and_data_type.md#4-다음-섹션을-위한-게임예제) 코드에서 import

```java
Predicate<Integer> isOddTester = i -> i % 2 == 1;
Predicate<String> isAllLowerCase = s -> s.equals(s.toLowerCase());

BiPredicate<Character, Integer> areSameCharNum = (c, i) -> (int) c == i;
BiPredicate<Unit, Unit> areSameSide = (u1, u2)  -> u1.getSide() == u2.getSide();

boolean isOddT3 = isOddTester.test(3);        // true
boolean isOddT4 = isOddTester.test(4);        // false
boolean isAL1 = isAllLowerCase.test("Hello"); // false
boolean isAL2 = isAllLowerCase.test("world"); // true

boolean areSameCN1 = areSameCharNum.test('A', 64); // false
boolean areSameCN2 = areSameCharNum.test('A', 65); // true

boolean areSameSide1 = areSameSide.test( // false
    new Knight(Side.RED), new Knight(Side.BLUE)
);
boolean areSameSide2 = areSameSide.test( // true
    new Swordman(Side.BLUE), new MagicKnight(Side.BLUE)
);
```

#### 📌 ```UnaryOperator``` & ```BinaryOerator```
* ```UnaryOperator``` : Function의 자손, Function과 달리 매개변수와 결과의 타입이 같다.
* ```BinaryOerator``` : BiFunction의 자손, BiFunction과 달리 매개변수와 결과의 타입이 같다.

> Unit, Swordman, Knight, MagicKnight은 [Section 7. - 4. 다음 섹션을 위한 게임예제](https://github.com/ro117-youshin/TIL/blob/master/Java/java-practice-yalco/07_class_and_data_type.md#4-다음-섹션을-위한-게임예제) 코드에서 import

```java
UnaryOperator<Integer> square = i -> i * i;
UnaryOperator<Swordman> respawn = s -> {
    if (s instanceof MagicKnight) return new MagicKnight(s.getSide());
    if (s instanceof Knight) return  new Knight(s.getSide());
    return new Swordman(s.getSide());
};

Integer squared = square.apply(3); // 9
Swordman respawned1 = respawn.apply(new Knight(Side.BLUE));     // "BLUE 진영 기사"
Swordman respawned2 = respawn.apply(new MagicKnight(Side.RED)); // "RED 진영 마법기사"

```
```java
BinaryOperator<Double> addTwo = (i, j) -> i + j;
BinaryOperator<Swordman> getWinner = (s1, s2) -> {
    while (s1.hp > 0 && s2.hp > 0) {
        s1.defaultAttack(s2);
        s2.defaultAttack(s1);
        if (s1 instanceof MagicKnight) {
            ((MagicKnight) s1).lighteningAttack(new Swordman[] {s2});
        }
        if (s2 instanceof MagicKnight) {
            ((MagicKnight) s2).lighteningAttack(new Swordman[] {s1});
        }
    }
    if (s1.hp > 0) return s1;
    if (s2.hp > 0) return s2;
    return null;
};

var added = addTwo.apply(12.34, 23.45); // 35.79

Swordman winner1 = getWinner.apply(new Swordman(Side.RED), new Knight(Side.BLUE));          // "BLUE 진영 기사"
Swordman winner2 = getWinner.apply(new MagicKnight(Side.RED), new Knight(Side.BLUE));       // "RED 진영 마법기사"
Swordman winner3 = getWinner.apply(new Swordman(Side.RED), new MagicKnight(Side.BLUE));     // "BLUE 진영 마법기사"
Swordman winner4 = getWinner.apply(new MagicKnight(Side.RED), new MagicKnight(Side.BLUE));  // null
```

#### 📌 컬렉션에 활용해보기
###### ```ArrayList``` 에서
```java
// 💡 Iterable 인터페이스의  forEach 메소드
// - 곧 배울 스트림의 forEach 와는 다름 (기능은 같음, 다른 곳에 선언되어 있을 뿐)
// - Consumer를 인자로 받아 실행
// - 이터레이터를 쓸 수 있는 클래스에서 사용 가능

new ArrayList<>(
    Arrays.asList("하나", "둘", "셋", "넷", "댜섯")
).forEach(s -> System.out.println(s));
```
###### ```Map``` 에서
```java
HashMap<String, Character> subjectGradeHM = new HashMap<>();
subjectGradeHM.put("English", 'B');
subjectGradeHM.put("Math", 'C');
subjectGradeHM.put("Programming", 'A');

//  💡 BiConsumer를 받음
subjectGradeHM.forEach(
        (s, g) -> System.out.println(
                "%s : %c".formatted(s, g)
        )
);
```

---

## 3. 메소드 참조

### Method reference
* 람다식이 어떤 메소드 하나만 호출할 때 코드를 간편화
  * 즉 해당 람다식과 메소드의 의미가 사실상 같을 때
* 해당 메소드가 인터페이스와 인자, 리턴값이 구성이 동일할 때

```bash
# 클래스 메소드 호출
{클래스명}::{클래스 메소드명}

# 인스턴스 메소드 호출
{클래스명}::{인스턴스메소드명}
{인스턴스}::{인스턴스메소드명}

# 클래스 생성자 호출
{클래스}::new
```
#### 기본예제
###### ☕️ Main.java
```java
//  클래스의 클래스 메소드에 인자 적용하여 실행
Function<Integer, String> intToStrLD = (i) -> String.valueOf(i);
Function<Integer, String> intToStrMR = String::valueOf;
String intToStr = intToStrMR.apply(123);

//  인자로 받은 인스턴스의 메소드 실행
UnaryOperator<String> toLCaseLD = s -> s.toLowerCase();
UnaryOperator<String> toLCaseMR = String::toLowerCase;
String toLCase = toLCaseMR.apply("HELLO");
```

#### 💡 클래스의 생성자 실행
###### ☕️ Button.java
```java
public class Button {
    private String text;
    public Button(String text) { this.text = text; }
    public Button(String text, String sound) {
        this(text);
        onClickListener = () -> System.out.println(sound + " " + sound);
    }
    public String getText() { return text; }

    private Runnable onClickListener;
    public void setOnClickListener(Runnable onClickListener) {
        this.onClickListener = onClickListener;
    }
    public void onClick () {
        onClickListener.run();
    }
}
```
###### ☕️ Main.java
```java
Function<String, Button> strToButtonLD = s -> new Button(s);
Function<String, Button> strToButtonMR = Button::new;
Button button1 = strToButtonMR.apply("Dog");

BiFunction<String, String, Button> twoStrToButtonLD = (s1, s2) -> new Button(s1, s2);
BiFunction<String, String, Button> twoStrToButtonMR = Button::new;
Button button2 = twoStrToButtonMR.apply("고양이", "야옹");
button2.onClick();

System.out.println("\n- - - - -\n");

//  현존하는 인스턴스의 메소드 실행
Runnable catCryLD = () -> button2.onClick();
Runnable catCryMR = button2::onClick;
catCryMR.run();
```

#### 💡 또 다른 형태
```java
//  💡 임의의 인스턴스의 메소드 참조
TreeSet<String> treeSetLD = new TreeSet<>((s1, s2) -> s1.compareTo(s2));
TreeSet<String> treeSetMD = new TreeSet<>(String::compareTo);
```

---

## 4. 스트림
* 연속되는 요소들의 흐름
  * 배열, 컬렉션, 파일 등에서 만들어질 수 있음


#### 기본예제: 홀수만 골라내서 정렬하기
###### ☕️ Ex01.java
```java
public static void main(String[] args) {
    List<Integer> int0To9 = new ArrayList<>(
        Arrays.asList(5, 2, 0, 8, 4, 1, 7, 9, 3, 6)
    );
}
```
###### 기존의 방식
```java
List<Integer> odds = new ArrayList<>();
for(Integer i : int0To9) {
    if(i % 2 == 1) {
        odds.add(i);
    }
}
odds.sort(Integer::compare);

List<String> oddsStrs = new ArrayList<>();
for(Integer i : odds) {
    oddsStrs.add(String.valueOf(i));
}
String oddsStr = String.join(", ", oddsStrs);

System.out.println(oddsStr); // 1, 3, 5, 7, 9
```
###### 💡 스트림을 사용한 방식
```java
String oddsStrStreamed = int0To9
        .stream()
        .filter(i -> i % 2 == 1)
        .sorted(Integer::compareTo)
        .map(String::valueOf)
        .collect(Collectors.joining(", "));
System.out.println("스트림을 사용한 방식 :: " + oddsStrStreamed); // 스트림을 사용한 방식 :: 1, 3, 5, 7, 9
```
* 일련의 데이터를 연속적으로 가공하는데 유용
  * 내부적으로 수행 - 중간과정이 밖으로 드러나지 않음
    * 외부에 변수 등이 만들어지지 않음
  * 배열, 컬렉션, I/O 등을 동일한 프로세스로 가공
  * 함수형 프로그래밍을 위한 다양한 기능들 제공
  * 가독성 향상
  * ⭐ 원본을 수정하지 않음 - *정렬 등에 영향받지 않음*
* 멀티쓰레딩에서 병렬처리 가능 









