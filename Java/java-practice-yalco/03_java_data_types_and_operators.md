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
