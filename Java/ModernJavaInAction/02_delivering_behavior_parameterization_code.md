## CHAPTER 2. 동작 파라미터화 코드 전달하기

> [모던자바인액션 - 라울-게이브리얼 우르마, 마리오 푸스코, 앨런 마이크로프트 지음, 우정은 옮김] 학습 후 기록


### 🧑🏻‍💻 변화하는 요구사항에 대응하기 

자주 바뀌는 요구사항에 효과적으로 대응할 수 있는 것, 
_**동작 파라미터화** behavior parameterization_ 이는 아직은 어떻게 실행할 것인지 결정하지 않은 코드 블록을 의미한다.
결과적으로 코드 블록에 따라 메서드의 동작이 파라미터화된다. 예를 들어 컬렉션을 처리할 때 다음과 같은 메서드를 구현한다고 가정하자.
* 리스트의 모든 요소에 대해서 '어떤 동작'을 수행할 수 있음
* 리스트 관련 작업을 끝낸 다음에 '어떤 다른 동작'을 수행할 수 있음
* 에러가 발생하면 '정해진 어떤 다른 동작'을 수행할 수 있음

이후에 살펴보다가 동작 파라미터화를 추가하려면 쓸데없는 코드가 늘어나는데, 자바 8은 람다 표현식으로 이 문제를 해결한다. 

#### 📌 첫 번째 시도 : 녹색 사과 필터링

```java
enum Color { RED, GREEN }
```
```java
public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>(); // 사과 누적 리스트
    for (Apple apple: inventory) {
        if(GREEN.equals(apple.getColor())) { // 녹색 사과만 선택
            result.add(apple);
        }    
    }
    return result;
}
```

&nbsp;농부(고객)의 변심으로 빨간 사과도 필터링하고 싶어졌다고 하자. 
크게 고민하지 않는 개발자라면 메서드를 복사해 `filterRedApples`라는 새로운 메서드를 만들고, `if`문의 조건을 바꾸는 방식을 선택할 것이다.
그런데 여기서 더 나아가 농부(고객)이 더 다양한 색을 원한다면 변화에 적절하게 대응할 수 없다.
이런 상황에서는 다음과 같은 좋은 규칙이 있다.

_거의 비슷한 코드가 반복 존재한다면 그 코드를 추상화한다._

#### 📌 두 번째 시도 : 색을 파라미터화

&nbsp; 기존 코드를 반복하여 사용하지 않고(복붙), 
색을 파라미터화 할 수 있도록 메서드에 파라미터를 추가하면 변화하는 요구사항에 좀 더 유연하게 대응하는 코드가 된다.

```java
public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
    List<Apple> result = new ArrayList<>();3927
    for (Apple apple: inventory) {
        if (apple.getColor().equals(color)) {
            result.add(apple);
        }
    }
    return result;
}
```
```java
List<Apple> greenApples = filterApplesByColor(inventory, GREEN);
List<Apple> redApples = filterApplesByColor(inventory, RED);
...
```

_필터링 할 분류가 추가 되었을 때,_
* 색 Color
* 무게 Weight

```java
public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple: inventory) {
        if (apple.getWeight() > weight) {
        result.add(apple);
        }
    }
    return result;
}
```

&nbsp; 위 코드도 좋은 해결책이지만, 구현 코드를 자세히 보면 목록을 검색하고, 
각 사과에 필터링 조건을 적용하는 부분의 코드가 색 필터링 코드와 대부분 중복된다. 

&nbsp;이는 소프트웨어 공학의 ‼️ _DRY, don't repeat yourself (같은 것을 반복하지 말 것)_ 원칙을 어기고 있다.

&nbsp;탐색 과정을 고쳐서 성능을 개선하려면 한 줄이 아니라 메서드 전체 구현을 고쳐야 한다. 즉, 엔지니어링적으로 비싼 대가를 치러야 한다.

&nbsp;색과 무게를 `filter`라는 메서드로 합치는 방법도 있다. 그러면 어떤 기준으로 사과를 필터링할지 구분하는 또 다른 방법이 필요하다.
따라서 색이나 무게 중 어떤 것을 기준으로 필터링할지 가리키는 플래그를 추가할 수 있다(하지만 실전에서는 절대 이 방법을 사용하지 말아야 한다).

#### 📌 세 번째 시도 : 가능한 모든 속성으로 필터링
```java
public static List<Apple> filterApples(List<Apple> inventory, Color color, int weight, boolean flag) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple: inventory) {
        if((flag && apple.getColor().equals(color)) ||
        (!flag && apple.getWeight() > weight)) { // 색이나 무게를 선택하는 방법이 마음에 들지 않음.
            result.add(apple);
        }
    }
    return result;
}
```

```java
List<Apple> greenApples = filterApplesByColor(inventory, GREEN, 0, true);
List<Apple> redApples = filterApplesByColor(inventory, null, 150, false);
...
```

정말 형편없는 코드.

### 🏃🏻동작 파라미터화

#### 📌 네 번째 시도: 추상적 조건으로 필터링

```java
public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
    
    List<Apple> result = new ArrayList<>();
    for(Apple apple : inventory) {
        if(p.test(apple)) { // 프레디케이트 객체로 사과 검사 조건을 캡슐화 함.
            result.add(apple);
        }    
    }
    return result;
}
```

#### 📌다섯 번째 시도: 익명 클래스 사용

#### 📌 여섯 번째 시도: 람다 표현식 사용

#### 📌 일곱 번째 시도: 리스트 형식으로 추상화

### 📚 실전 예제


