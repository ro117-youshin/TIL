## CHAPTER 5. 스트림 활용

> [모던자바인액션 - 라울-게이브리얼 우르마, 마리오 푸스코, 앨런 마이크로프트 지음, 우정은 옮김] 학습 후 기록

`### 📌 필터링(filter)
* 프레디케이트 필터링
* 고유 요소만 필터링

#### 💡 프리디케이트로 필터링
&nbsp; `filter` 메서드는 프레디케이트(불리언을 반환하는 함수)를 인수로 받아서 프리디케이트와 일치하는 모든 요소를 포함하는 스트림을 반환.

###### ex)
```java
List<Dish> proteinMenu
	= menu.stream()
	      .filter(Dish::isProtein)
	      .collect(toList());
```

#### 💡 고유 요소 필터링(distinct)
&nbsp; 스트림은 고유 요소로 이루어진 스트림을 반환하는 `distinct` 메서드 지원.

###### ex)
```java
List<Integer> numbers = Arrays.asList(1,2,3,4,2,4);
numbers.stream()
       .filter(i -> i % 2 == 0)
       .distinct()
       .forEach(System.out::println);
```

### 📌 스트림 슬라이싱
&nbsp; 스트림의 요소를 선택하거나 스킵하는 다양한 방법을 설명.
* 프레디케이트를 이용하는 방법
* 스트림의 처음 몇 개의 요소를 무시하는 방법
* 특정 크기로 스트림을 줄이는 방법
* ...

#### 💡 프레디케이트를 이용한 슬라이싱
* `takeWhile`
###### ex) 기존 필터링
```java
List<Dish> proteinMenu
	= Arrays.asList(
		new Dish("fish", true, 15, Dish.Type.ANIMALPROTEIN),
		new Dish("chicken", true, 20, Dish.Type.ANIMALPROTEIN),
		new Dish("bean curd", true, 14, Dish.Type.VEGETABLEPROTEIN),
		new Dish("beef", true, 35, Dish.Type.ANIMALPROTEIN),
		new Dish("bean", true, 8, Dish.Type.VEGETABLEPROTEIN)
	);

List<Dish> filteredMenu = 
	proteinMenu.stream()
			   .filter(dish -> dish.getProtein() < 20)
			   .collect(toList)); // 프로틴이 20미만인 모든 목록								
```

&nbsp; 위 `List`는 이미 프로틴 순으로 정렬되어 있다. `filter`연산을 이용하면 전체 스트림을 반복하면서 각 요소에 프레디케이트를 적용하게 된다.
따라서 `List`가 이미 정렬되어 있다는 사실을 이용해 20 protein보다 클 때 반복 작업을 중단할 수 있다.
작은 `List`에서는 이와 같은 동작이 별거 아닌 것처럼 보일 수 있지만 아주 많은 요소를 포함하는 큰 스트림에서는 상당한 차이가 될 수 있다. 
이 때, `takeWhile`연산을 이용하면 이를 간단하게 처리할 수 있다. 무한 스트림을 포함한 모든 스트림에 프레디케이트를 적용해 스트림을 슬라이스 할 수 있다.

###### ex) takeWhile을 이용한 스트림 슬라이스
```java
List<Dish> slicedMenu
	= proteinMenu.stream()
		     .takeWhile(dish -> dish.getProtein() < 20)
		     .collect(toList()); // NULL (첫 객체 'fish' 의 프로틴은 15이므로 false)
```

###### ex) filter() vs takeWhile()
```java
List<Integer> numbers = Arrays.asList(2,2,4,3,4,5,6,7,8,9);
  
      // Stream.of(1,2,3,4,5,6,7,8,9)
      //       .filter(n -> n % 2 == 0)
      //       .forEach(System.out::println);
      
      numbers.stream()
             .filter(n -> n % 2 == 0)
             .forEach(System.out::println);
      
      System.out.println("-------------");
      
      // Stream.of(2,6,3,4,5,6,7,8,9)
      //       .takeWhile(n -> n % 2 == 0)
      //       .forEach(System.out::println);
      
      numbers.stream()
             .takeWhile(n -> n % 2 == 0)
             .forEach(System.out::println);   
```
```text
Output:
2
2
4
4
6
8
-------------
2
2
4
```

* `dropWhile`
&nbsp; 프레디케이트가 처음으로 거짓이 되는 지점까지 발견된 요소를 버린다. 프레디케이트가 거짓이 되면 그 지점에서 작업을 중단하고 남은 모든 요소를 반환한다.

```java
List<Integer> numbers = Arrays.asList(2,2,4,3,4,5,6,7,8,9);

numbers.stream()
       .dropWhile(n -> n % 2 == 0)
       .forEach(System.out::println);
```
```text
Output:
3
4
5
6
7
8
9
```

#### 💡 스트림 축소
* `limit(n)`
&nbsp; 스트림이 정렬되어 있으면 최대 요소 n개를 반환.

```java
List<Integer> numbers = Arrays.asList(2,2,4,3,4,5,6,7,8,9);

numbers.stream()
       .dropWhile(n -> n % 2 == 0)
       .limit(3)
       .forEach(System.out::println);
```
```text
Output:
3
4
5
```

#### 💡 요소 건너뛰기
* `skip(n)`
&nbsp; 처음 n개 요소를 제외한 스트림을 반환하는 메서드. n개 이하의 요소를 포함하는 스트림에 skip(n)을 호출하면 빈 스트림이 반환된다.

```java
List<Integer> numbers = Arrays.asList(2,2,4,3,4,5,6,7,8,9);

numbers.stream()
       .dropWhile(n -> n % 2 == 0)
       .skip(3)
       .forEach(System.out::println);
```
```text
Output:
6
7
8
9
```

### 📌 매핑
&nbsp; 특정 객체에서 특정 데이터를 선택하는 작업은 데이터 처리 과정에서 자주 수행되는 연산.
* `map`
* `flatMap`
위 두 메서드는 특정 데이터를 선택하는 기능을 제공.

#### 💡 스트림의 각 요소에 함수 적용하기

```java
List<String> words = Arrays.asList("Morden", "Java", "In", "Action");
List<Integer> wordLengths = words.stream()
			         .map(String::length)
			         .collect(Collectors.toList());
       
System.out.println(wordLengths);
```
```text
Output:
[6, 4, 2, 6]
```

#### 💡 스트림 평면화
&nbsp; 리스트에서 고유 문자로 이루어진 리스트를 반환해보자.
* ["Hello", "World"] 리스트가 있다면
* 결과로 ["H", "e", "l", "o", "W", "r", "d"]를 포함하는 리스트가 반환되어야 한다.

&nbsp; 그렇다면 리스트에 있는 각 단어를 문자로 매핑한 다음, `distinct`로 중복된 문자를 필터링해서 쉽게 문제를 해결할 수 있을까?
```java
List<String> words = Arrays.asList("Hello", "World");
List<String[]> splitWord = words.stream()
   .map(word -> word.split(""))
   .distinct()
   .collect(Collectors.toList());

splitWord.forEach(array -> System.out.println(Arrays.toString(array)));
```
```text
Output:
[H, e, l, l, o]
[W, o, r, l, d]
```
&nbsp; 위 코드에서 `map`으로 전달한 람다는 각 단어의 `String[]` 문자열 배열을 반환한다는 점이 문제다.
따라서 `map`메서드가 반환한 스트림의 형식은 `Stream<String[]>` 이다. 
우리가 원하는 것은 문자열의 스트림을 표현할 `Stream<String>` 이다.

이 문제를 `flatMap` 메서드를 이용해서 해결해보자.

* `map`과 `Arrays.stream` 활용
&nbsp; 우선 배열 스트림 대신 문자열 스트림이 필요하다. 아래 코드에서 보여주는 것처럼 문자열을 받아 스트림을 만드는 `Arrays.stream()`메서드가 있다.
```java
List<Stream<String>> splitWord2 = words.stream()
  .map(word -> word.split(""))
  .map(Arrays::stream)
  .distinct()
  // .toList();
  .collect(Collectors.toList());

splitWord2.forEach(stream -> {
System.out.println(stream.collect(Collectors.toList()));  
});
```
```text
Output:
[H, e, l, l, o]
[W, o, r, l, d]
```
&nbsp; 이 시도에서도 스트림 리스트(`List<Stream<String>>`)가 만들어지면서 문제가 해결되지 않았다.
문제를 해결하려면 먼저 각 단어를 개별 문자열로 이루어진 배열로 만든 다음에 각 배열을 별도의 스트림으로 만들어야 한다.

* flatMap 사용

```java
List<String> uniqueCharacters =
words.stream()
     .map(word -> word.split(""))
     .flatMap(Arrays::stream)
     .distinct()
     .collect(Collectors.toList());

uniqueCharacters.forEach(character -> System.out.println(character));
```
```text
Output:

H
e
l
o
W
r
d
```

`flatMap`은 각 배열을 스트림이 아니라 스트림의 콘텐츠로 매핑한다. 즉, `map` `(Arrays::stream)`과 달리 `flatMap`은 하나의 평면화된 스트림을 반환한다.
`flatMap` 메서드는 스트림의 각 값을 다른 스트림으로 만든 다음에 모든 스트림을 하나의 스트림으로 연결하는 기능을 수행한다.

###### Quiz
```java
// 1. 주어진 숫자 리스트의 각 숫자의 제곱근으로 리스트를 반환.
// ex) [1,2,3,4,5] -> [1,4,9,16,25]
List<Integer> numbers = Arrays.asList(1,2,3,4,5);
List<Integer> squares = numbers.stream()
                               .map(n -> n * n)
                               .collect(Collectors.toList());
System.out.println(squares);

// 2. 두 개의 숫자 리스트가 있을 때 모든 숫자 쌍의 리스트를 반환.
// ex) [1,2,3], [3,4]가 주어지면 [(1,3), (1,4), (2,3), (2,4) ...]
List<Integer> numbersOne = Arrays.asList(1,2,3);
List<Integer> numbersTwo = Arrays.asList(3,4);
List<int[]> pairs1 = numbersOne.stream()
                              .flatMap(i -> numbersTwo.stream()
                                                      .map(j -> new int[]{i,j})
                              ).collect(Collectors.toList());

// 3. 이전 예제 합이 3으로 나누어 떨어지는 쌍만 반환.
// ex) (2,4) (3,3)
List<int[]> pairs2 = numbersOne.stream()
                              .flatMap(i -> numbersTwo.stream()
                                                      .filter(j -> (i+j) % 3 == 0)
                                                      .map(j -> new int[]{i,j})
                              ).collect(Collectors.toList());

```

### 📌 검색과 매칭

#### 💡 프레디케이트가 적어도 한 요소와 일치하는지 확인

#### 💡 프레디케이트가 모든 요소와 일치하는지 검사

















