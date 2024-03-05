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
  * 

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
 

## 2. Set
* 순서를 유지하지 않는 데이터의 집합, 데이터의 중복을 허용하지 않는다.

## 3. Map
* key와 value의 쌍(pair)으로 이루어진 데이터의 집합.
* 순서는 유지되지 않으며, key는 중복을 허용하지 않고, value는 중복을 허용한다.