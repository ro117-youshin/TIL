# Section 6. 클래스 더 알아보기
> '재대로 파는 자바 - 얄코' 섹션6 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. 블록과 스코프
> 2. 패키지
> 3. 내부 클래스
> 4. 익명 클래스
> 5. 메인 메소드
> 6. 열거형

---

## 3. 내부 클래스
> 자바의 정석 CHAPTER 7 참조

* 내부 클래스는 클래스 내에 선언된 클래스이다.
* 내부 클래스를 사용하는 이유는 두 클래스가 서로 긴밀한 관계가 있기 때문.
* 장점으로는, 
  * 두 클래스의 멤버들 간에 서로 쉽게 접근할 수 있다.
  * 외부에는 불필요한 클래스를 감춤으로써 코드의 복잡성을 줄일 수 있다(캡슐화).

#### 💡 내부 클래스의 종류와 특징

| 내부 클래스 | 특징 |
| :-----:  | :-----:|
| 인스턴스 클래스 (instance class) | 외부 클래스의 멤버변수 선언위치에 선언하며, 외부 클래스의 인스턴스 멤버처럼 다루어진다. 주로 외부 클래스의 인스턴스 멤버들과 관련된 작업에 사용될 목적으로 선언됨. |
| 스태틱 클래스 (static class) | 외부 클래스의 멤버변수 선언위치에 선언하며, 외부 클래스의 static 멤버처럼 다루어진다. 주로 외부 클래스의 static 멤버, 특히 static 메서드에서 사용될 목적으로 선언된다. |
| 지역 클래스 (local class) | 외부 클래스의 메서드나 초기화블럭 안에 선언하며, 선언된 영역 내부에서만 사용될 수 있다. |
| 익명 클래스 (anonymous class) | 클래스의 선언과 객체의 생성을 동시에 하는 이름없는 클래스(일회용) |

#### ex01
###### ☕️ Outer.java
```java
public class Outer {
    private String inst = "인스턴스";
    private static String sttc = "클래스";

    //  💡 1. 멤버 인스턴스 클래스
    class InnerInstMember {
        //  ⭐️ 외부 클래스의 필드와 클래스 접근 가능
        private String name = inst + " 필드로서의 클래스";
        private InnerSttcMember innerSttcMember = new InnerSttcMember();

        public void func () {
            System.out.println(name);
        }
    }

    //  💡 2. 정적(클래스) 내부 클래스
    //  ⭐️  내부 클래스에도 접근자 사용 가능. private으로 바꿔 볼 것
    public static class InnerSttcMember {
        //  ⭐️ 외부 클래스의 클래스 필드만 접근 가능
        private String name = sttc + " 필드로서의 클래스";

        //  ⚠️ static이 아닌 멤버 인스턴스 클래스에도 접근 불가!
        //  private InnerInstMember innerInstMember = new InnerInstMember();

        public void func () {
            // ⚠️ 인스턴스 메소드지만 클래스가 정적(클래스의)이므로 인스턴스 필드 접근 불가
            //  name += inst;
            System.out.println(name);
        }
    }

    public void memberFunc () {
        //  💡 3. 메소드 안에 정의된 클래스
        //  스코프가 메소드 내로 제한됨
        class MethodMember {
            //  외부의 필드와 클래스에 접근은 가능
            String instSttc = inst + " " + sttc;
            InnerInstMember innerInstMember = new InnerInstMember();
            InnerSttcMember innerSttcMember = new InnerSttcMember();

            public void func () {
                innerInstMember.func();
                innerSttcMember.func();
                System.out.println("메소드 안의 클래스");

                //  new Outer().memberFunc(); // ⚠️ 스택오버플로우 에러!!
            }
        }

        new MethodMember().func();
    }

    public void innerFuncs () {
        new InnerInstMember().func();
        new InnerSttcMember().func();
    }

    public InnerInstMember getInnerInstMember () {
        return new InnerInstMember();
    }
}
```
###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {
        //  ⭐️ 클래스가 클래스 필드인 것 - 아래의 변수는 인스턴스
        Outer.InnerSttcMember staticMember = new Outer.InnerSttcMember();
        staticMember.func();

        System.out.println("\n- - - - -\n");

        Outer outer = new Outer();
        outer.innerFuncs();

        System.out.println("\n- - - - -\n");


        //  ⚠️  아래와 같은 사용은 불가
        //  Outer.InnerInstMember innerInstMember = new outer.InnerInstMember();

        //  💡 인스턴스 내부 클래스는 이렇게 얻을 수 있음
        Outer.InnerInstMember innerInstMember = outer.getInnerInstMember();
        innerInstMember.func();

        System.out.println("\n- - - - -\n");

        outer.memberFunc();
    }
}
```
* 보다 강력한 캡슐화
  * 외부/내부 클래스간의 관계가 긴밀할 때 사용
* 적절히 사용시 유지보수가 용이하고 가독성을 높여줌
  * 과하게 사용하면 클래스 비대화  

---

## 4. 익명 클래스
* 다른 클래스나 인터페이스로부터 상속받아 만들어짐
  * 주로 오버라이드한 메소드를 사용
* 한 번만 사용되고 버려질 클래스
  * 따로 클래스명이 부여되지 않음
  * 이후 다시 인스턴스를 생성할 필요가 없으므로
* 이후 배울 람다식이 나오기 전 널리 사용

#### ex01
###### ☕️ Main.java
```java
package sec06.chap04.ex01;
import sec05.chap08.ex01.*;

public class Main {
    public static void main(String[] args) {

        //  💡 와일드카드로 임포트 - import sec05.chap08.ex01.*;
        JavaGroup store1 = new JavaChicken("울산");
        JavaGroup store2 = new JavaCafe("창원", true);
        JavaGroup store3 = new JavaGroup(1, "포항") {

            //  원본 메소드에 public 추가
            @Override
            public void takeOrder() {
                System.out.printf(
                        "유일한 자바과메기 %s 과메기를 주문해주세요.%n",
                        super.intro()
                );
            }

            public void dryFish() {
                System.out.println("과메기 말리기");
            }
        };

        String store3Intro = store3.intro();

        store3.takeOrder();
        //store3.dryFish // ⚠️ 불가

        System.out.println("\n- - - - -\n");

        for (JavaGroup store : new JavaGroup[]{store1, store2, store3}) {

            store.takeOrder();
        }
    }
}
```
* 💡 익명클래스의 인스턴스는 상속받거나 오버라이드 된 메소드만 호출 가능
*  익명클래스의 자체적인 메서드(*dryFish()*)는 호출하여 사용할 수 없다.

### 📌 안드로이드 자바로 개발시 볼 수 있던 코드
#### ex02
###### ☕️ OnClickListener.java
```java
public interface OnClickListener {
    void onClick ();
}
```
###### ☕️ Button.java
```java
public class Button {
    String name;
    public Button(String name) {
        this.name = name;
    }

		//  ⭐️ 인터페이스를 상속한 클래스 자료형
    private OnClickListener onClickListener;
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void func () {
        onClickListener.onClick();
    }
}
```
###### ☕️ Main.java
```java
public static void main(String[] args) {
        Button button1 = new Button("Enter");
        Button button2 = new Button("CapsLock");
        Button button3 = new Button("ShutDown");

				//  ⭐️ IDE에서 회색으로 표시 : 이후 배울 람다로 대체
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                System.out.println("줄바꿈");
                System.out.println("커서를 다음 줄에 위치");
            }
        });

        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                System.out.println("기본입력 대소문자 전환");
            }
        });

        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                System.out.println("작업 자동 저장");
                System.out.println("프로그램 종료");
            }
        });

        for (Button button : new Button[] {button1, button2, button3}) {
            button.func();
        }
}
```

---

## 6. 열거형

* 지정된 선택지 내의 값을 받을 변수 사용시

```java
//  문자열로 설정: 불안정함
String mode = "LIGHT";
mode = "DARK";
        
mode = "lite"; // 실수를 간편히 방지할 방법이 없음
```

```java
//  1: LIGHT, 2: DARK
//  위 정보를 숙지해야 함 - 가독성 현저히 떨어짐
int mode = 1;
mode = 2;
        
//  타 변수에 사용되는 값들과 구분되지 않음
//  잘못된 범위의 값 입력에 대응하기 번거로움
int spaces = 3;
        
mode = spaces; // 이러한 실수를 방지하기 어려움
```

#### ex01
###### ☕️ ButtonMode.java
```java
public enum ButtonMode {
    LIGHT, DARK
}
```
###### ☕️ ButtonSpace.java
```java
public enum ButtonSpace {
    SINGLE, DOUBLE, TRIPLE
}
```
###### ☕️ Button.java
```java
public class Button {
    private ButtonMode buttonMode = ButtonMode.LIGHT;
    private ButtonSpace buttonSpace = ButtonSpace.SINGLE;

    public void setButtonMode(ButtonMode buttonMode) {
        this.buttonMode = buttonMode;
    }

    public void setButtonSpace(ButtonSpace buttonSpace) {
        this.buttonSpace = buttonSpace;
    }
}
```
###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {
        Button button1 = new Button();

        button1.setButtonMode(ButtonMode.DARK);
        button1.setButtonSpace(ButtonSpace.TRIPLE);

        //  ⚠️ 아래와 같은 오용이 방지됨
//        button1.setButtonMode(ButtonSpace.DOUBLE);
    }
}
```

### 📌 클래스 내부에 작성하여 오용 여지 제거하기
* 한 클래스 내에서 사용할 경우.
* enum을 사용하는 클래스와 응집도가 더 높아짐.

#### ex02
###### ☕️ Button.java
```java
public class Button {

    enum Mode { LIGHT, DARK }
    enum Space { SINGLE, DOUBLE, TRIPLE }

    private Mode mode = Mode.LIGHT;
    private Space space = Space.SINGLE;

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setSpace(Space space) {
        this.space = space;
    }
}
```

###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {
        Button button1 = new Button();

        button1.setMode(Button.Mode.DARK);
        button1.setSpace(Button.Space.DOUBLE);
    } // 🔴 디버깅
}
```

### enum 추가 기능들
#### ex03











