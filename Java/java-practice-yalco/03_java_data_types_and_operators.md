# Section 3. 자바의 자료형과 연산자
> '제대로 파는 자바 - 얄코' 섹션3 학습 (인프런)
> 1. 정수 자료형들과 관련 연산자
> 2. 실수 자료형들
> 3. 

## 1. 정수 자료형들과 관련 연산자

```java
byte _1b_byte = 1;
short _2b_short = 2;
int _4b_int = 3; // ⭐️ 일반적으로 널리 사용
long _8b_long = 4;
```
* 대부분의 컴퓨터에서 1바이트 = 8비트 (일부 다른 시스템 존재)
#### 묵시적(암시적) 형변환
```java
//  큰 자료형에 작은 자료형의 값을 넣을 수 있음
//  변수의 값만 들어가는 것이 아니라 자료형도 바뀜
_2b_short = _1b_byte;  // byte to short
_4b_int = _1b_byte;    // byte to int
_4b_int = _2b_short;   // short to int
_8b_long = _1b_byte;   // byte to long
_8b_long = _2b_short;  // short to long
_8b_long = _4b_int;    // int to long

//  ⚠️ 6 errors, 작은 자료형에 큰 자료형의 값을 '그냥' 넣을 수 없음
//  들어있는 값의 크기와 무관
_1b_byte = _2b_short;
_1b_byte = _4b_int;
_1b_byte = _8b_long;
_2b_short = _4b_int;
_2b_short = _8b_long;
_4b_int = _8b_long;
```
#### int의 범위를 벗어나는 수에는 리터럴에도 명시 필요
```java
long _8b_long1 = 123456789123456789; // error, long으로 선언해도 리터럴에 L을 붙이지 않아 int로 인식.
long _8b_long1 = 123456789123456789L;
```
#### 💡 가독성을 위해 아래와 같이 표현 가능 (자바7부터)
```java
int _4b_int2 = 123_456_789;
long _8b_long2 = 123_456_789_123_456_789L;
```

### 형변환 casting

#### 명시적(강제) 형변환
(개발자: "내가 책임질테니까 그냥 넣으세요.")
```java
byte byteNum;
int smallIntNum = 123;
byteNum = (byte) smallIntNum;
```
```java
int intNum = 12345;

//  ⚠️ 강제로 범주 외의 값을 넣을 경우 값 손실
byteNum = (byte) intNum; // 💡 12345 % 128    byteNum: 57    intNum:12345
```

### 이항 연산자
* 좌우의 두 값을 계산한 뒤 결과를 반환 return
  * A가 B를 반환 (return) : 코드상 A를 B로 '바꿔 쓸 수 있다'는 의미  
* 부수효과를 일으키지 않음
  * 연산을 한다고 해서 연산에 사용되는 변수에 영향을 미치지 않음
  * (아래의 코드에서 변수 b에 담을 리터럴 연산에서 a가 사용되었다고 변수 a의 값이 변하지는 않는다)

```java
int a = 1 + 2;
int b = a - 1;
int c = b * a;
int d = a + b * c / 3;
int e = (a + b) * c / 3;
int f = e % 4;
```

### 변수 연산

#### 연산하여 반환할때
```java
int a = 1;
int b = 2;

// int끼리 연산은 int를 반환
int c = a + b;

long d = a + b;            // 더 큰 범위의 변수에는 가능
short e = a + b;           // ⚠️ error, 묵시적으로는 불가능
short f = (short) (a + b); // 명시적으로 가능, casting 필요
short f = (short) a + b;   // ⚠️ error, 괄호가 없다면 변수 a에 대해서만 casting이 됨
```

#### byte와 short의 연산들은 int로 반환
```java
byte b1 = 1;
byte b2 = 2;
short s1 = 1;
short s2 = 2;
        
//  ⚠️ 아래는 모두 불가
byte b3 = b1 + b2;  // error
short s3 = b1 + b2; // error
short s4 = b1 + s2; // error
short s5 = s1 + s2; // error
        
//  그냥 int를 많이 쓰는 이유 중 하나
int i1 = b1 + b2;
int i2 = s1 + s2;
int i3 = b1 + s1;
```
* 메모리를 크게 절약해야 하는 상황이 아닌 이상 int를 널리 사용함

#### int를 널리 사용하는 기타 이유들
* 자바(및 다수 언어들)에서 기본 자료형으로 지정됨
    * 라이브러리와 API 등에서 사용됨
* 다른 언어들과 호환 (널리 사용되는 자료형)
* 연산 속도가 타 자료형보다 빠름
    * 32비트 (4 바이트) : 대부분의 CPU에서 처리하기 적합한 크기
 
```java
long l1 = 1;
long l2 = 2;

// long끼리의 연산은 long 반환
int l3 = l1 + l2; // error
long l3 = l1 + l2;
```

```java
//  ⚠️ 정수 자료형의 계산은 소수점 아래를 '버림'
byte int1 = 5/2;          // int1: 2
int int2 = 10;            // int2: 10
int int3 = 3;             // int3: 3
int int4 = int2 / int3;   // int2: 10    int3: 3    int4: 3
```

```java
// 💡 홀수와 짝수 구분에 널리 사용
int aa = 1 % 2;  // aa: 1
int bb = 2 % 2;  // bb: 0
int cc = 3 % 2;  // cc: 1
int dd = 4 % 2;  // dd: 0
int ee = 5 % 2;  // ee: 1
int ff = 6 % 2;  // ff: 0
int gg = 7 % 2;  // gg: 1
```
### 복합 대입 연산자
* 부수효과를 일으킴

```java
int a = 1;       // 🔴(breakpoint) 1.일때 a: 3    2.일때 a: 6
a = a + 2;

a += 3;          // 🔴 1. a: 3

// 부수효과를 일으키며 다른 변수에 반환까지 함.
int b = a += 4;  // 🔴 2. a: 6
                 // 🔴 3. a: 10    b: 10 
```
#### 💡 추가, 일반 대입 연산자도 값을 반환함
```java
int i1 = 0;          // i1: 1
int i2 = 1;          // i2: 1
int i3 = (i1 = i2);  // 괄호를 제거해도 같음    i1: 1    i2: 1    i3: 1

String s1 = "ABC";      // s1: "가나다"
String s2 = "가나다";     // s2: "가나다"
String s3 = (s1 = s2);  // s1: "가나다"    s2: "가나다"    s3: "가나다"
```
