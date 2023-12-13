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
