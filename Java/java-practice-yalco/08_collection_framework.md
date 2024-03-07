# Section 8. 컬렉션 프레임워크
> '재대로 파는 자바 - 얄코' 섹션8 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. List
> 2. Set
> 3. Map
> 4. Comparable & Comparator
> 5. Iterator

## 1. List
* 순서가 있는 데이터의 집합, 데이터의 중복을 허용한다.

### 📌 ```ArrayList```
* 배열과 달리, 크기가 동적으로 증가 가능.
  * 지정하지 않을 시 초기 공간 10
    * 공간 (capacity) ≠ 요소의 수 (size)
  * 배열처럼 메모리상에 연속으로 나열
  * 공간 초과 시 추가 공간 확보
    * 더 넓은 공간을 확보한 뒤 요소들 복사
  * 중간의 요소 제거 시 이후 요소들을 당겨옴
* 용도
  * 장점 : 각 요소돌로의 접근이 빠르다.
  * 단점 : 요소 추가/제거 시 성능 부하, 메모리를 비교적 더 차지
  * 변경이 드물고 빠른 접근이 필요한 곳에 적합하다.

#### 💡 ```ArrayList``` 의 추가와 삭제
> 자바의 정석 CHAPTER 11 참조
* 삭제
  * 삭제할 객체의 바로 아래에 있는 데이터를 한 칸씩 위로 복사해서 삭제할 객체를 덮어쓰는 방식.
  * 만일 삭제할 객체가 마지막 데이터라면, 복사할 필요 없이 단순히 null로 변경해주기만 하면 됨.
  * remove() 수행 과정
    1. 삭제할 데이터의 아래에 있는 데이터를 한 칸씩 위로 복사해서 삭제할 데이터를 덮어쓴다.
    2. 데이터가 모두 한 칸씩 위로 이동하였으므로 마지막 데이터는 null로 변경해야한다.
    3. 데이터가 삭제되어 데이터의 개수(size)가 줄었으므로 size의 값을 1 감소시킨다. 
* 추가
  * 먼저 추가할 위치 이후의 요소들을 모두 한 칸씩 이동시킨 후에 저장. 
* 배열에 객체를 순차적으로 저장할 때와 객체를 마지막에 저장된 것부터 삭제하면 데이터를 옮기지 않아도 되기 때문에 작업시간이 짧다.
* 하지만 배열의 중간에 위치한 객체를 추가하거나 삭제하는 경우 다른 데이터의 위치를 이동시켜 줘야 하기 때문에 다루는 데이터의 개수가 많을수록 작업시간이 오래 걸린다.

#### 📁 ```ArrayList```의 메서드
```java
        //  ⭐️ 제네릭을 사용하여 타입 지정
        //  - 붙이지 않을 시 <Object>와 동일
        ArrayList<Integer> ints1 = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Number> numbers = new ArrayList<>();
        ArrayList<Knight> knights = new ArrayList<>();

        //  add 메소드로 요소 추가
        ints1.add(11);
        ints1.add(22);
        ints1.add(33);
        ints1.add(44);
        ints1.add(55);
```
```java
        //  요소 중복 허용
        for (String str : "바니 바니 바니 바니 당근 당근".split(" ")) {
            strings.add(str);
        }
```
```java
        //  for-each 문 사용 가능
        for (int i : ints1) {
            System.out.println(i);
        }
```
```java
        int ints1Size = ints1.size(); // 요소 개수
        boolean ints1IsEmpty = ints1.isEmpty(); // size가 0인지 여부 반환
        int ints12nd = ints1.get(1); // 인덱스로 요소 접근
        boolean ints1Con3 = ints1.contains(33); // 포함 여부
        boolean ints1Con6 = ints1.contains(66);
```
```java
        ints1.set(2, 444); // 위치의 요소 수정
        ints1.add(0, 11); // 위치에 요소 추가 (다음 요소들 밀어냄)
```
```java
        //  ⭐️ 간략한 생성 및 초기화 방법들
        ArrayList<Integer> ints2A = new ArrayList<>(
                Arrays.asList(1, 2, 3, 4, 5)
        ); // 💡 Arrays 클래스 : 배열 관련 각종 기능 제공

        ArrayList<Integer> ints2B = new ArrayList<>(
                List.of(1, 2, 3, 4, 5)
        ); // 💡 자바9에서부터 가능

        ArrayList<Integer> ints2C = new ArrayList<>();
        Collections.addAll(ints2C, 1, 2, 3, 4, 5);
```
```java
        //  💡 다른 Collection 인스턴스를 사용하여 생성
        ArrayList<Integer> ints3 = new ArrayList<>(ints1);

        //  스스로를 복제하여 반환 (⚠️ 얕은 복사)
        ArrayList<Integer> ints4 = (ArrayList<Integer>) ints3.clone();
```
```java
        ints3.remove(4); // int: 인덱스로 지우기
        ints3.remove((Integer) 55); // 클래스 자료형: 요소로 지우기
```
```java
        ints1.removeAll(ints3); // 주어진 콜렉션에 있는 요소들 지우기
```
```java
        ints1.addAll(ints3); // 콜렉션 이어붙이기
```
```java
        //  💡 toArray - Object 배열 반환
        Object[] intsAry2_Obj = ints1.toArray();

        //  ⭐️ 특정 타입의 배열로 반환하려면?
        //  Integer[] ints1Ary1 = (Integer[]) ints1.toArray(); // ⚠️ 이렇게는 불가
        //  💡 인자로 해당 타입 배열의 생성자를 넣어줌
        //  - 다음 섹션에 배울 메소드 참조 사용 (9-3강 수강 후 다시 볼 것)
        Integer[] ints1Ary2 = ints1.toArray(Integer[]::new);
```
```java
        ints1.clear(); // 리스트 비움
```
```java
        //  제네릭 적용
        numbers.add(Integer.valueOf(123));
        numbers.add(3.14);
//        numbers.add("Hello"); // ⚠️ 불가

//        knights.add(new Swordman(Side.BLUE)); // ⚠️ 불가
        knights.add(new Knight(Side.BLUE));
        knights.add(new MagicKnight(Side.RED));
```
```java
        //  와일드카드 적용
        //  기사 이상의 그룹으로만 편성될 수 있는 정예분대
        ArrayList<? extends Knight> eliteSquad;

//        eliteSquad = new ArrayList<Swordman>(); // ⚠️ 불가
        eliteSquad = new ArrayList<Knight>();
        eliteSquad = new ArrayList<MagicKnight>();
```
```java
        //  ⭐️ 인스턴스 요소를 지울 때는 참조를 기준으로
        //  - 내용이 같다고 같은 인스턴스가 아님
        Knight knight1 = new Knight(Side.RED);
        knights.add(knight1);

        //  요소가 하나 지워졌는지 여부 반환
        boolean removed1 = knights.remove(new Knight(Side.RED));
        boolean removed2 = knights.remove(knight1);
```
###### 📁 ArrayList에만 있는 메소드 (자주 쓰이지 않음)
```java
	//  ⭐️ LinkedList 와의 차이와 연관지어 생각해 볼 것

        ArrayList<Attacker> attackers = new ArrayList<>();
        attackers.ensureCapacity(5); // 자리수 미리 확보
        attackers.trimToSize(); // 남는 자리 없애기 (메모리 회수)
```

### 📌 ```LinkedList```
* 불연속적으로 존재하는 데이터를 서로 연결(link)한 형태로 구성되어 있음.
* 각 요소(node)들은 <ins>자신과 연결된 다음 요소에 대한 참조(주소값)</ins>와 <ins>데이터</ins>로 구성되어 있다.
* 배열의 단점을 보완하기 위해서 나온 자료구조.
```bash
* 배열의 단점
  - 크기를 변경할 수 없다.
  - 비순차적인 데이터의 추가 또는 삭제에 시간이 많이 걸린다. 
```
* Queue를 구현하는 용도로 사용 가능.
* 용도
  * 장점: 요소의 추가와 제거가 빠름, 메모리 절약.
  * 단점: 요소 접근이 느림.
  * 요소들의 추가/제거가 잦은 곳에 적합.

#### 📁 ```LinkedList```의 메서드
```java
        //  💡 LinkedList에만 있는 메소드들 중...
        LinkedList<Integer> intNums = new LinkedList<>();
        for (int intNum : new int[] {2, 3, 4}) {
	    intNums.add(intNum);
	}

        intNums.addFirst(1);
        intNums.addFirst(0);
        intNums.addLast(5); // add와 반환값만 다름. 코드에서 확인해 볼 것
        intNums.addLast(6);
```
```java
        //  💡 앞에서/뒤에서 꺼내지 않고 반환
        //  - peek~ : 비어있을 경우 null 반환
        //  - get~ : 비어있을 경우 익셉션
        int peekedFirst = intNums.peekFirst();	// 0
        int gottenFirst = intNums.getFirst();	// 0
        int peekedLast = intNums.peekLast();	// 6
        int gottenLast = intNums.getLast();	// 6	intNums: size = 7
```
* 위의 코드로 보면 마지막 라인 ```gottenLast``` 변수가 초기화 되어도 ```0``` 부터 ```6``` 까지 list에 들어가 있어 ```intNums: size = 7``` 이다.
```java
        //  💡 앞에서/뒤에서 꺼내어 반환
        //  - poll~ : 비어있을 경우 null 반환
        //  - remove~ : 비어있을 경우 익셉션
        int polledFirst = intNums.pollFirst();		// 0
        int removedSecond = intNums.removeFirst();	// 1
        int polledLast = intNums.pollLast();		// 6
        int removedLast = intNums.removeLast();		// 5	intNums: size = 3

        //  ⭐️ 위의 기능들 활용하여 Stack/Queue 구현
```
* 맨 위의 코드부터 이어서 보면 마지막 라인에서 ```intNums: size = 3``` 이다.
```java
        LinkedList<Character> charLList = new LinkedList<>();

        // 💡 push & pop : 스택 간편하게 구현
	// 클래스 코드를 살펴보면..
	// - push
	// public void push(E e) {
	//     addFirst(e);
	// }
	// - pop
	// public E pop() {
        //     return removeFirst();
    	// }

        charLList.push('A');
        charLList.push('B');
        charLList.push('C');
        charLList.push('D');
        charLList.push('E');

        char pop1 = charLList.pop();
        char pop2 = charLList.pop();
        char pop3 = charLList.pop();
```

#### ⭐️ 실무에서는 컬렉션 자료형을 인터페이스로
```java
	List<Integer> intList = new ArrayList<>();
        intList = new LinkedList<>();
        
        Set<String> strSet = new HashSet<>();
        strSet = new TreeSet<>();
        
        Map<Integer, String> intStrMap = new HashMap<>();
        intStrMap = new TreeMap<>();
```
* ```List```, ```Set```, ```Map``` 등의 인터페이스로 변수, 인자, 제네릭 등의 자료형 지정
  * 상세구현이 어떤 알고리즘으로 되어 있는지 굳이 드러내지 않음
  * 필요에 따라 다른 종류로 교체가 용이

#### ⚠️ ```Arrays``` 의 ```ArrayList```
* ```Arrays.asList``` 가 반환하는 ```ArrayList```
* ```java.util.ArrayList``` 와 다름
  * ```java.util.Arrays``` 의 정적 내부 클래스
  * 사이즈 변경 불가 (요소 추가 안 됨)
```java
	List<Integer> testIntList = Arrays.asList(1, 2, 3);
        //  ArrayList<Integer> testIntList = Arrays.asList(1,2,3); ⚠️ 불가
```
```java
	List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        ArrayList<Integer> list2 = new ArrayList<>(list1);

        String list1Type = list1.getClass().getName();
        String list2Type = list2.getClass().getName();

        list1.add(6); // ⚠️ 런타임 오류
```

---

## 2. Set
* 순서를 유지하지 않는 데이터의 집합, 데이터의 중복을 허용하지 않는다.

|주요 클래스|장점|단점|
|:-----:|:-----|:-----|
|```HashSet```|성능이 빠르고 메모리 적게 사용|순서 관련 기능 없음(보장하지 않음)|
|```LinkedHashSet```|요소들을 입력 순서대로 정렬(내부적으로 링크 사용)|```HashSet``` 보다는 성능이 떨어짐|
|```TreeSet```|요소들을 특정 기준대로 정렬(기본 오름차순)|데이터 추가/삭제에 시간 더 소모|

#### 💡 해시 hash - HashSet이 사용하는 방식
* 동일한 입력 값 👉🏻 언제나 동일한 출력값
* 값마다의 고유한 정수값을 해시값으로 저장
  * 이 값을 기준으로 정렬
* 일정 개수까지는 정렬(된 형태), 이를 넘어서면 재조정(클래스 코드 확인)
  * 정렬을 목적으로는 사용하지 말 것

#### 📁 ```Set``` 메서드
```java
	Set<Integer> intHashSet1 = new HashSet<>();
        intHashSet1.add(1);
        intHashSet1.add(1);
        intHashSet1.add(2);
        intHashSet1.add(3);  // intHashSet1: size = 3 

        List<Integer> intList = new ArrayList(  // intList: size = 10
                Arrays.asList(1, 1, 2, 2, 3, 3, 4, 5, 6, 7)
        );
        Set<Integer> intHashSet2 = new HashSet<>(intList);  // intHashSet2: size = 7   intList: size = 10
```
```java
	//  💡 for-each문 사용 가능
        for (Integer i :intHashSet1) {
            System.out.println(i);
        }
```
```java
	//  ⭐️ 아래와 같이 응용 가능
        //  - 중복을 제거한 ArrayList
	//  - ArrayList에 HashSet을 addAll해서 사용 가능.
        intList.clear();
        intList.addAll(intHashSet2);
```
* ```addAll()``` 은 클래스 코드를 살펴보면
  * ```boolean addAll(Collection<? extends E> c);```
  * ```Collection``` 을 인자로 받기 때문에 가능

```java
	//  포함 여부
        boolean has2 = intHashSet1.contains(2);
        boolean has4 = intHashSet1.contains(4);

        //  요소 삭제, 있었는지 여부 반환
        boolean rm3 = intHashSet1.remove(3);
        boolean rm4 = intHashSet1.remove(4);
```
```java
	//  다른 콜렉션 기준으로 내용 삭제
        intHashSet2.removeAll(intHashSet1);
```
```java
	//  💡 그 외
        intHashSet1.size();
        intHashSet2.isEmpty();
        intHashSet1.clear();
```

#### 📁 참조형 관련
```java
        Set<Swordman> swordmenSet = new HashSet<>();
        Swordman swordman = new Swordman(Side.RED);

        swordmenSet.add(swordman);
        swordmenSet.add(swordman);
        swordmenSet.add(new Swordman(Side.RED));
        swordmenSet.add(new Swordman(Side.RED));
```
* 위 ```swordmenSet``` 의 ```size = 3```
* 참조형 데이터들은 같은 인스턴스인가 여부에 따라 중복으로 추가될지 안될지 결정
* ```Set``` 에서 ```swordman``` 을 삭제하려면
  * ```swordmenSet.remove(swordman);```
  * ```new``` 연산자로 생성한 두 ```Swordman``` 은 외부에 주소가 저장된 변수가 없기 때문에 ```clear()``` 메서드 사용하여 삭제      


---

## 3. Map
* key와 value의 쌍(pair)으로 이루어진 데이터의 집합.
* 순서는 유지되지 않으며, key는 중복을 허용하지 않고, value는 중복을 허용한다.













