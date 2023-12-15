# Section 4. 제어문과 메소드
> '제대로 파는 자바 - 얄코' 섹션4 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. if / else
> 2. switch
> 3. for & for-each
> 4. while & do while
> 5. 메소드
> 6. 

## 1. if / else
#### Ex01
```java
boolean open = true;
int saleFrom = 10, saleTo = 20;
int today = 15;

//  💡 if : 괄호 안의 boolean 값이 true면 다음 명령 실행
if (open) System.out.println("영업중");
if (!open) System.out.println("영업종료");

//  💡 실행할 명령이 한 줄 이상일 경우 블록 사용
if (today >= saleFrom && today <= saleTo) {
    System.out.println("세일중입니다.");
    System.out.println("전품목 20% 할인");
}

//  💡 else : if문 안의 boolean 값이 false일 경우 실행
if (open) {
    System.out.println("영업중");
} else {
    System.out.println("영업종료");
}
```
#### Ex02
```java
boolean willOrderCoffee = true;
boolean likeMilk = false;
boolean likeIce = true;
boolean likeSweet = false;
boolean angry = false;
int myRank = 2;

//  💡 중첩 사용
if (willOrderCoffee) {

    //  ⭐️ 블록 내에서만 사용되는 변수 (이후 스코프에서 배움)
    String toOrder = "";

    toOrder = likeMilk ? "라떼" : "아메리카노";
    if (likeIce) toOrder = "아이스 " + toOrder;
    if (!likeSweet) toOrder += " 안단티노";

    System.out.printf("저는 %s 할게요%n", toOrder);
} else {
    if (!angry || myRank > 3) {
        System.out.println("저는 괜찮아요.");
    } else {
        System.out.println("너님들끼리 드세요.");
    }
}
```
#### Ex03
```java
int score = 85;

//  💡 else if : 첫 if문이 false일 때 다른 조건을 연속 사용
//  else만 사용하는 것은 맨 마지막에
if (score > 90) {
    System.out.println('A');
} else if (score > 80) {
    System.out.println('B');
} else if (score > 70) {
    System.out.println('C');
} else if (score > 60) {
    System.out.println('D');
} else {
    System.out.println('F');
}
```
#### Ex04 ⭐ 보다 가독성 좋은 방식
#### return문: 해당 메소드를 종료하고 빠져나옴
```java
int score = 85;
        
if (score > 90) {
    System.out.println('A');
    return;
}
if (score > 80) {
    System.out.println('B');
    return;
}
if (score > 70) {
    System.out.println('C');
    return;
}
if (score > 60) {
    System.out.println('D');
    return;
}
System.out.println('F');
```
## 2. switch
* 💡 괄호 안에 기준이 될 변수를 받음
* 가능한 자료형: byte, short, int, char, String, enum
*  💡 case : 기준에 일치하는 case로 바로 이동
*  💡 break : switch문 실행을 종료
*  💡 default: 해당하는 case가 없을 때 - 마지막에 작성

#### Ex01
```java
byte fingersOut = 2;

switch (fingersOut) {

    case 2:
        System.out.println("가위");
        break;
    case 0:
        System.out.println("바위");
        break;
    case 5:
        System.out.println("보");
        break;
    default:
        System.out.println("무효");
}
```
#### Ex02
```java
String direction = "north";
String dirKor;

//  break 제거하고 실행해 볼 것
switch (direction) {
    case "north":
        dirKor = "북";
        break;
    case "south":
        dirKor = "남";
        break;
    case "east":
        dirKor = "동";
        break;
    case "west":
        dirKor = "서";
        break;
    default:
        dirKor = null;
}
// break문이 없을 경우 해당 case문을 갔다가 default 걸리기 때문에 무효처리.
System.out.println(
    dirKor != null ? dirKor : "무효"
);
```
#### Ex03
* 💡 break 관련 동작방식을 이용
```java
char yutnori = '도';

switch (yutnori) {
    case '모': System.out.println("앞으로");
    case '윷': System.out.println("앞으로");
    case '걸': System.out.println("앞으로");
    case '개': System.out.println("앞으로");
    case '도': System.out.println("앞으로"); break;
    default:
        System.out.println("무효");
}
```
#### Ex04
```java
byte month = 1;
byte season;

switch (month) {
    case 1: case 2: case 3:
        season = 1;
        break;
    case 4: case 5: case 6:
        season = 2;
        break;
    case 7: case 8: case 9:
        season = 3;
        break;
    case 10: case 11: case 12:
        season = 4;
        break;
    default:
        season = 0;
}

System.out.println(
    season > 0
    ? "지금은 %d분기입니다.".formatted(season)
    : "무효한 월입니다."
);
```
#### Ex05
```java
byte startMonth = 1;  
String holidays = "";

switch (startMonth) {
    case 1:
        holidays += "설날, ";
    case 2:
    case 3:
        holidays += "3·1절, ";
        break;
    case 4:
    case 5:
        holidays += "어린이날, ";
    case 6:
        holidays += "현충일, ";
        break;
    case 7:
    case 8:
        holidays += "광복절, ";
    case 9:
        holidays += "추석, ";
        break;
    case 10:
        holidays += "한글날, ";
    case 11:
    case 12:
        holidays += "크리스마스, ";
        break;
    default:
        holidays = null; // 휴일이 없는 분기와 구분
}

String result = holidays == null
        ? "(잘못된 월입니다)"
        : "분기 내 휴일: " + holidays
        .substring(0, holidays.lastIndexOf(", "));
```
## 3. for & for-each
#### Ex01
```java
// 반대로 변수를 빼서 루프 돌리기
for (double d = 123.45; d > 0; d -= 32.1) {
    System.out.println(d);
}
// 문자열 길이만큼 더하기
for (String s = ""; s.length() < 11; s += s.length()) {
    System.out.println(s);
}
//  💡 쉼표로 구분하여 여럿 사용 가능
//  ⚠️ 변수의 자료형은 한 종류만 가능 (혼용 안 됨)
for (byte a = 0, b = 10; a <= b;) {
    System.out.printf("a: %d, b: %d%n", a++, b--);
}
```
```java
//  윷놀이 예제에 적용 (2. switch Ex03와 동일한 결과)
String yuts = "도개걸윷모";
char yut = '도';

boolean isValid = false;
for (int i = 0; i <= yuts.indexOf(yut); i++) {
    isValid = true;
    System.out.println("앞으로");
}
if (!isValid) System.out.println("무효");
```
### 📌 무한 루프
#### 종료조건이 없는 for 루프
```java
for (;;) {
    System.out.println("영원히");
}
System.out.println("닿지 않아"); // ⚠️ 실행되지 않음
```
#### 종료조건을 만족시키지 못하는 무한루프
```java
for (int i = 0; i < 10; i++) {
    System.out.println("그래도");
    i--;
}
System.out.println("닿지 않아"); // ⚠️ 실행되지 않음
```
* 무한루프는 프로그램을 정지시킴
### 📌 배열의 루프
```java
//  4의 배수 차례로 10개 배열에 담기 
int count = 10;
int[] multiOf4 = new int[count];    // [4, 8, 12, ... 40]

for (int i = 1, c = 0; c < count; i++) {
    if (i % 4 == 0) {
        multiOf4[c++] = i;
    }
}

//  💡 배열 순환 (기본적인 방법)
for (int i = 0; i < multiOf4.length; i++) {
    System.out.println(multiOf4[i]);
}

// for each
for (int num : multiOf4) {
    System.out.println(num);
}
```
### 📌 중첩 루프
```java
String[][] quotesInLangs = {
        {
            "I am vengeance.",
            "I am night.",
            "I am Batman.",
        },
        {
            "나는 복수를 하지.",
            "나는 밤이지.",
            "나는 배트맨이지.",
        },
};

String result = "";
    for (String[] quotes : quotesInLangs) {
        for (String quote : quotes) {
            result += quote + " "; // 🔴
        }
        result = result.trim().concat("\n");
}

System.out.println(result);
```
### 📌 continue와 break + label
#### 💡 label : 중첩 루프에서 어느쪽을 continue, break 할 지 구분
* label은 변수와 같이 임의로 기입
```java
outer:
for (int i = 0; i < 10; i++) {

    inner:
    for (int j = 0; j < 10; j++) {
        if (j % 2 == 0) continue inner;
        if (i * j >= 30) continue outer;

        if (j > 8) break inner;
        if (i - j > 7) break outer;

        System.out.printf("i: %d, j: %d%n", i, j);
    }
}
```
## 4. while & do while
### 📌 while : 조건이 true일 동안 반복 수행
* 의도적인 무한 루프에 널리 쓰이는 코드
#### Ex01
```java
double popInBillion = 7.837;

//  ⭐️ break 를 통한 반복 탈출
while (true) {
    System.out.println("세계인구: " + (popInBillion -= 0.1));
    if (popInBillion <= 0) break;

    System.out.println("인간의 욕심은 끝이 없고");
    System.out.println("같은 실수를 반복한다.");
}

System.out.println("인류 멸종");
```
#### Ex02 100보다 작은 3의 배수들 출력
```java
int i = 1;

// ⚠️ 의도대로 작동하지 않음. 이유는?
// continue로 while문 안에 continue까지 무한루프로 빠짐. 그 아래 코드는 실행x
while (true) {
    if (i % 3 != 0) continue;  // 🔴
    System.out.println(i);

    if (i++ == 100) break;
}
```
#### Ex02-1 무한루프 해결
```java
int i = 1;

while (true) {
    if (i++ == 100) break;
    if ((i - 1) % 3 != 0) continue;

    System.out.println(i - 1);
}
```
#### Ex02-2 보다 가독성을 높이고 의도를 잘 드러낸 코드
```java
int i = 1;

while (true) {
    int cur = i++;

    if (cur == 100) break;
    if (cur % 3 != 0) continue;
            
    System.out.println(cur);
}
```
### 📌 do ... while : 일단 수행하고 조건을 봄
#### Ex03
```java
int enemies = 0;

System.out.println("일단 사격");

do {
    System.out.println("탕");
    if (enemies > 0) enemies--;
} while (enemies > 0);

System.out.println("사격중지 아군이다");
```
```java
int x = 1; // 10 이상으로 바꿔서 다시 실행해 볼 것
int y = x;

while (x < 10) {
    System.out.println("while 문: " + x++);
}

do {
    System.out.println("do ... while 문: " + y++);
} while (y < 10);
```
### 📌 중첩 예제
#### Ex04
```java
final int LINE_WIDTH = 5;

int lineWidth = LINE_WIDTH;

while (lineWidth > 0) {
    int starsToPrint = lineWidth--;
    while (starsToPrint-- > 0) {
        System.out.print("*");
    }
System.out.println();
}
```
#### Ex04-1 for문으로 작성
```java
for (int i = LINE_WIDTH; i > 0; i--) {
    for (int j = i; j > 0; j--) {
        System.out.print("@");
    }
    System.out.println();
}
```
