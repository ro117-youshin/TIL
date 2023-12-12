# Section 3. 자바의 자료형과 연산자
> '제대로 파는 자바 - 얄코' 섹션3 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. 정수 자료형들과 관련 연산자
> 2. 실수 자료형들
> 3. 문자 자료형
> 4. 불리언 자료형과 관련 연산자
> 5. 

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

### 📌 형변환 casting

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

### 📌 이항 연산자
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

### 📌 변수 연산

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
### 📌 복합 대입 연산자
* 부수효과를 일으킴

| a += b | a = a + b |
| ------ | --------- |
| a -= b | a = a - b |
| a *= b | a = a * b |
| a /= b | a = a / b |
| a %= b | a = a % b |


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

```java
int x = 3;
x += 2;
x -= 3;       // 🔴 x: 5 
x *= 12;      // 🔴 x: 2
x /= 3;
x %= 5;
```

## 2. 실수 자료형들

```java
double a = 0.1, b = 0.2;
// ⚠️ 오차가 생김
double c = a + b;
```
| 자료형 | 크기  |
| ---- | ---- |
| float| 4바이트 |
| double | 8바이트 |
* double : float 보다 단순히 범위가 넓은 것이 아니라, 보다 정밀하게 표현 가능

#### 최대/최소값
```java
//  float의 최대값과 최소값
float fMin = -Float.MAX_VALUE;
float fMax = Float.MAX_VALUE;

//  double의 최대값과 최소값
double dMin = -Double.MAX_VALUE;
double dMax = Double.MAX_VALUE;

//  최소 절대값
float fAbsMin = Float.MIN_VALUE;
double dAbsMin = Double.MIN_VALUE;

// ⭐ double이 범위도 넓고, 정밀도도 높음 확인
boolean bool1 = Float.MAX_VALUE < Double.MAX_VALUE;
boolean bool2 = Float.MIN_VALUE > Double.MIN_VALUE; 
```
#### 최대 정밀도 테스트
```java
double dblNum = 0.123456789123456789;
float fltNum = 0.123456789123456789f;
```
```java
//  float은 뒤에 f 또는 F를 붙여 표현
float flt1 = 3.14f;
double dbl1 = 3.14;

//  ⚠️ float에는 double을 담을 수 없음
float flt2 = dbl1;
//  반대는 가능
double dbl2 = flt1;
```
```java
long lng1 = 123;

//  정수를 대입할 시 묵시적 변환
//  💡 float(4바이트)에도 long(8바이트)의 값 담을 수 있음
float flt3 = lng1;
double dbl3 = lng1;

long lng2 = Long.MAX_VALUE;

//  ⭐ 큰 수(정확히 표현가능한 한도를 넘어서는)일 경우
//  가능한 최대 정확도로
float flt4 = lng2;
double dbl4 = lng2;
```
```java
//  💡 복합 대입 연산자와 단항 연산자 
float fl5 = 123.45F;
fl5 += 6.78;
fl5++; // 🔴
fl5++;
fl5--;
```
```java
float flt01 = 4.124f;
float flt02 = 4.125f;
double dbl01 = 3.5;

// float끼리의 연산은 float 반환
float flt03 = flt01 + flt02;

//  float과 double의 연산은 double 반환
float flt04 = flt01 + dbl01; // ⚠️ 불가

//  부동소수점 방식상 오차 자주 있음
double dbl02 = 0.2 + 0.3f;
double dbl03 = 0.2f * 0.7f;
double dbl04 = 0.4 - 0.3;
double dbl05 = 0.9f / 0.3;
double dbl06 = 0.9 % 0.6;

//  소수부가 2의 거듭제곱인 숫자간 연산은 오차 없음
double dbl07 = 0.25 * 0.5f;
double dbl08 = 0.5 + 0.25 + 0.125 + 0.0625;
double dbl09 = 0.0625f / 0.125;
```

```java
int int1 = 5;
float flt1 = 2f;
double dbl1 = 3;
double dbl2 = 7;

//  💡 정수 자료형과 실수 자료형의 계산은 실수 반환
int flt2 = int1 / flt1; // ⚠️ 불가
double dbl3 = int1 / dbl1;
double dbl4 = dbl2 / int1;

//  💡 리터럴로 작성시 double임을 명시하려면 .0을 붙여줄 것
double dbl5 = 5 / 2;
double dbl6 = 5.0 / 2;
double dbl7 = (double) 5 / 2;

float fltNum = 4.567f;
double dblNum = 5.678;

//  💡 정수 자료형에 강제로 넣으면 소수부를 '버림'
int int2 = (int) fltNum;
int int3 = (int) dblNum;
```

### 📌 비교연산
```java
int int1 = 5;
float flt1 = 5f;
double dbl1 = 5.0;
double dbl2 = 7.89;

//  💡 정수/실수간, 다른 숫자 자료형간 사용 가능
boolean bool0 = 123 == 123F;

boolean bool1 = int1 == flt1;
boolean bool2 = flt1 == dbl1;
boolean bool3 = int1 == dbl2;

boolean bool4 = int1 > dbl2;
boolean bool5 = flt1 >= dbl2;
boolean bool6 = dbl1 < dbl2;
```

## 3. 문자 자료형

### 📌 char - 문자 character 자료형
* 2바이트 사용 - short와 동일
* 유니코드상 문자 - 🍿 https://youtu.be/1jo6q4dihoU
* 빈 문자 불가

#### 각 문자는 상응하는 정수를 가짐
```java
char ch1 = 'A';      // 65
char ch2 = 'B';      // 66
char ch3 = 'a';      // 97
char ch4 = 'a' + 1;  // ch4: 'b' 98 
char ch5 = '가';
char ch6 = '가' + 1;
char ch7 = '가' + 2;
char ch8 = '가' + 3;
char ch9 = '나';

int ch1Int = (int) ch1; // ch1: 'A' 65 ch1Int: 65
int ch9Int = (int) ch9; // ch9: '나' 45208 ch9Int: 45208
```
#### 문자 리터럴과 숫자, 유니코드로 표현 가능
```java
char ch10 = 'A';      // 65
char ch11 = 65;       // 65
char ch12 = '\u0041'; // 65
```
* 문자별 유니코드 정보: [List of Unicode characters - Wikipedia](https://en.wikipedia.org/wiki/List_of_Unicode_characters)

```java
char ch_a1 = 'A';
int int_a1 = (int) ch_a1;

//  정수값을 얻는 다른 방법들 - 정수값과 연산하기
int int_a2 = ch_a1 + 0;
int int_a3 = ch_a1 - 0;

//  💡 리터럴에 더할 때와 변수에 더할 때 반환 자료형이 다름
char ch_a2 = 'A' + 1;
//char ch_a3 = ch_a1 + 1; // ⚠️ 불가
int int_a4 = ch_a1 + 1;

//  💡 int 1과 char '1'은 다름! - 문자로서의 '1'
//  - 해당 문자의 정수값 확인
char ch_b1 = '1'; // 49
char ch_b2 = '2'; // 50

//  ⚠️ 숫자 문자에 사칙연산 - 문자 번호 기준 결과 반환
char ch_b3 = '1' + '2';      // ch_b3: 'c' 99
int int_b4 = ch_b1 + ch_b2;  // ch_b1: '1' 49  ch_b2: '2' 50  int_b4: 99

//  ⚠️ 빈 문자는 사용 불가, 공백(space)는 가능
char empty = ''; // error
char space = ' ';
```
### 📌 비교 연산자
```java
//  같은 문자열인지 여부 반환
boolean bool1 = 'A' == 'A'; // true
boolean bool2 = 'A' == '가'; // false

//  숫자와 비교할 시 해당 정수값 기준으로
boolean bool3 = 'A' == 65;  // true
boolean bool4 = 'A' > 64.0; // true
boolean bool5 = 'A' > 66f;  // false

//  사전순 상 먼저 오는 쪽이 작음
boolean bool6 = 'A' < 'B';  // true
boolean bool7 = '가' > '나'; // false
```

## 4. 불리언 자료형과 관련 연산자
### 📌 boolean 자료형
* 1바이트 (8비트) 공간 차지
  * 하드웨어 구조와의 호환성 - CPU가 수월히 다룰 수 있는 최소 단위
* 리터럴보다는 반환값으로 많이 사용됨
### 📌 부정 연산자
```java
boolean bool3 = !true;    // false
boolean bool4 = !false;   // true
boolean bool5 = !!bool3;  // false
boolean bool6 = !!!bool3; // true

boolean bool7 = !(1 > 2);                          // true
boolean bool8 = !((5 / 2) == 2.5);                 // true
boolean bool9 = !((3f + 4.0 == 7) != ('A' < 'B')); // true
```
### 📌 논리 연산자
|a && b| AND | a와 b 모두가 true일때만 ture 반환 |
| ---- | --- | ---------------------------- |
|a \|\| b | OR | a와 b 중 하나만 true면 true 반환 |

```java
boolean bool1 = true && true;   // true
boolean bool2 = true && false;  // false
boolean bool3 = false && true;  // false
boolean bool4 = false && false; // false

boolean bool5 = true || true;   // ture
boolean bool6 = true || false;  // true
boolean bool7 = false || true;  // true
boolean bool8 = false || false; // false

int num = 4;

boolean isPositiveAndOdd = num >= 0 && num % 2 == 1;  // false
boolean isPositiveOrOdd = num >= 0 || num % 2 == 1;   // true
boolean isPositiveAndEven = num >= 0 && num % 2 == 0; // true
boolean isPositiveOrEven = num >= 0 || num % 2 == 0;  // true
```
#### 💡 &&가 ||보다 우선순위 높음
```java
num = 6;

boolean boolA = (num % 3 == 0) && (num % 2 == 0) || (num > 0) && (num > 10);
boolean boolB = (num % 3 == 0) && ((num % 2 == 0) || (num > 0)) && (num > 10);
```
### 📌 단축평가 short circuit
* &&: 앞의 것이 false면 뒤의 것을 평가할 필요 없음
* ||: 앞의 것이 true면 뒤의 것 평가할 필요 없음
* 평가는 곧 실행 - 이 점을 이용한 간결한 코드
* 💡 연산 부하가 적은 코드를 앞에 - 리소스 절약

```java
int a = 1, b = 2, c = 0, d = 0, e = 0, f = 0;

boolean bool1 = a < b && c++ < (d += 3); // true
boolean bool2 = a < b || e++ < (f += 3); // true

boolean bool3 = a > b && c++ < (d += 3); // 🔴
boolean bool4 = a > b || e++ < (f += 3);
```

### 📌 삼항 연산자
```java
int num1 = 3, num2 = 4;

char num1OE = num1 % 2 == 1 ? '홀' : '짝';
char num2OE = num2 % 2 == 1 ? '홀' : '짝';
```
```java
int num = 3;
        
boolean mult2 = true;
//mult2 = false;
        
boolean plus5 = true;
//plus5 = false;

System.out.println(
    (!mult2 && !plus5) ? num
    : (mult2 && plus5) ? num * 2 + 5
    : mult2 ? num * 2
    : num + 5
);
```
```java
int x = 1, y = 2;

//  💡 단축평가 적용됨
int changed1 = x < y ? (x += 2) : (y += 2);
int changed2 = x < y ? (x += 2) : (y += 2); // 🔴
int changed3 = x < y ? (x += 2) : (y += 2);
int changed4 = x < y ? (x += 2) : (y += 2);
int changed5 = x < y ? (x += 2) : (y += 2);
```
