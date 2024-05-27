# 11. 멀티태스킹
> '제대로 파는 자바 - 얄코' 섹션11 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. 쓰레드 만들기
> 2. 쓰레드 다루기
> 3. 쓰레드 그룹과 데몬 쓰레드
> 4. 동기

## 1. 쓰레드 만들기

### 💡 프로세스와 쓰레드
* 프로세스 *process*
  * 각 프로그램마다 진행
  * 각각 메모리 공간을 할당받음
    * 코드, 데이터, 기타 시스템 자원
    * 기본적으로는 프로세스간 공유되지 않음
  * 생성시 비교적 많은 시간과 메모리 소모
  * 종료시 프로그램 종료 
* 쓰레드 *thread*
  * 한 프로세스 안에 여럿 생성되어 진행될 수 있음
    * 업데이트를 받으면서 코딩을 계속할 수 있는 이유
  * 프로세스 내의 자원을 여러 쓰레드가 공유
    * 잘못 다루면 위험
  * 프로세스보다 생성 부담이 적음

#### 💡 쓰레드 만들기
* 두 가지 방법
  * `Thread` 클래스 상속
  * `Runnable` 인터페이스 구현
    * 인터페이스의 유연함 때문에 보다 많이 사용

###### ☕️ Thread1.java
```java
public class Thread1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            // 😴
            System.out.print(1);
        }
    }
}

```

###### ☕️ MyRunnable.java
```java
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            // 😴

            System.out.print(2);
        }
    }
}
```

###### ☕️ Main.java
```java
public class Main {

    public static void main(String[] args) {
        Thread thread1 = new Thread1(); // Thread 상속시
        Thread thread2 = new Thread(new MyRunnable()); // Runnable 구현시

        // Runnable의 익명 클래스로 생성
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 20; i++) {
                    // 😴

                    System.out.print(3);
                }
            }
        });

        //  ⚠️ 해당 쓰레드에 지정된 run 메소드를
        //  현재 메인 쓰레드에서 실행
        //  - 쓰레드 사용 의미 없음
        thread1.run();
        thread2.run();
        thread3.run();

        //  💡 각각 새로운 쓰레드를 생성하여 동시에 진행
        //  - 실행시마다 결과 다를 수 있음
        //thread1.start();
        //thread2.start();
        //thread3.start();

        for (int i = 0; i < 20; i++) {
            // 😴

            System.out.print('M');
        }
    }
}
```
###### console: run() 실행
```
11111111111111111111222222222233333333333333333333MMMMMMMMMMMMMMMMMMMM
```
###### console: start() 실행
```
M2222222222333111113MMMMMMMMMMMMMMMMMMM3333333333333333111111111111111
```

#### 💡 `sleep` 메서드
* `Thread`의 정적 메서드
* 주어진 밀리초 동안 해당 쓰레드를 멈춤
* 😴 밑에 아래의 코드를 붙여넣고 다시 실행해보자.(각 쓰레드, 총 4곳)
  * 예외처리 필요 - 예외를 던지는 메서드
```java
// 😴
try {
    Thread.sleep(500);
} catch (InterruptedException e) {
    throw new RuntimeException(e);
}
```

#### ⚠️ `sleep` 잘못된 예시
###### ☕️ WrongSleep.java
```java
public class WrongSleep {

    public static void main(String[] args) {
        Thread sleeper = new Thread(
                () -> IntStream.range(0, 5)
                        .forEach(i -> {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println('쿨');
                        })
        );
        sleeper.start();

        try {
            //  ⚠️ sleeper 쓰레드를 재우려고 했지만
            //  - sleep은 정적 메소드!
            //  - 현 쓰레드가 자버림
            sleeper.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("잘잤다!");

    }
}

```
* 쓰레드의 인스턴스에 sleep()을 걸어주었다.
  * 결과는 메인 쓰레드가 자버림.
  * 쓰레드의 인스턴스에 `sleep()`을 걸어주어도 `Thread` 클래스의 `sleep()`을 걸어주는 것과 같다.
  * 그래서 `sleep()` 코드를 가지고 있는 메인 쓰레드가 자는 효과를 가지고 있다.
* ⭐️ 쓰레드를 `sleep` 하려면, 어떤 `Thread`의 안에서 `Thread`의 클래스 메서드로 `sleep` 해야 한다.
```java
Thread sleeper = new Thread(
        () -> IntStream.range(0, 5)
                .forEach(i -> {
                    try {
                        Thread.sleep(100); // ⭐️⭐️
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println('쿨');
                })
);
```
###### console
```
쿨
쿨
쿨
쿨
쿨
잘잤다!
```
---

## 2. 쓰레드 다루기

#### 💡 쓰레드에 이름 부여
###### ☕️ IncreaseInterest.java
```java
public class IncreaseInterest implements Runnable {

    int max;
    public IncreaseInterest(int max) {this.max = max;}

    @Override
    public void run() {
        String interest = "%s : 금리가 %d에서 %d로 올라가요.";

        for (int i = 0; i < max; i++) {
            try {
                Thread.sleep(2000);
                System.out.printf( (interest) + "%n", Thread.currentThread().getName(), i, (i + 1));
            } catch (InterruptedException e) {

            }
        }
    }
}
```
###### ☕️ Ex01.java
```java
public class Ex01 {
    public static void main(String[] args) {
        Thread incInterest = new Thread(new IncreaseInterest(40));

//        incInterest.setName("캐피탈직원");

        incInterest.start();
    }
}
```
###### console
```
Thread-0 : 금리가 0에서 1로 올라가요.
Thread-0 : 금리가 1에서 2로 올라가요.
Thread-0 : 금리가 2에서 3로 올라가요.
Thread-0 : 금리가 3에서 4로 올라가요.
...
```
###### console: `setName("캐피탈직원")`
```
캐피탈직원 : 금리가 0에서 1로 올라가요.
캐피탈직원 : 금리가 1에서 2로 올라가요.
캐피탈직원 : 금리가 2에서 3로 올라가요.
캐피탈직원 : 금리가 3에서 4로 올라가요.
...
```

#### 💡 쓰레드의 우선순위
#### 💡  `yield` & `setPriority()`
* 환경과 상황마다 무의미할 수 있다.
* 힌트만 줄 뿐 결정은 OS가 내리기 때문.

#### 💡 쓰레드를 사용한 멀티태스킹
#### 💡 `run()` & `start()`
###### ☕️ Ex03.java
```java
public class Ex03 {
    public static void main(String[] args) {
        Thread incInterest = new Thread(new IncreaseInterest(10));
//        incInterest.run();
        // ⭐️ run()
        // 금리 공지가 끝나야 입력에 응답 가능.
        // (IncreaseInterest 쓰레드가 끝나야 main 쓰레드 진행)
        incInterest.start();
        // ⭐️ start()
        // 입력 응답과 동시 진행 가능해짐.
        // (금리 공지를 하는 IncreaseInterest 쓰레드와 main 쓰레드가 동시 진행)

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNext()) {
                String line =  sc.nextLine();

                if(line.equalsIgnoreCase("quit")) break;
                System.out.println(line);
            }
        }
    }
}
```

#### 💡 `isAlive()`
###### ☕️ Ex03.java
```java
public class Ex03 {
    public static void main(String[] args) {
        Thread incInterest = new Thread(new IncreaseInterest(10));
        incInterest.start(); 

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNext()) {
                String line =  sc.nextLine();

                if (line.equalsIgnoreCase("check")) {
                    System.out.println("금리 공지가 끝났나요?");
                    System.out.println(
                            //  💡 isAlive : 해당 쓰레드가 진행중인지 여부
                            incInterest.isAlive() ? "아직 공지하고 있습니다." : "끝났습니다."
                    );
                }

                if(line.equalsIgnoreCase("quit")) break;
            }
        }
    }
}
```
###### console
```
...
Thread-0 : 금리가 5에서 6로 올라가요.
Thread-0 : 금리가 6에서 7로 올라가요.
check
금리 공지가 끝났나요?
아직 공지하고 있습니다.
Thread-0 : 금리가 7에서 8로 올라가요.
Thread-0 : 금리가 8에서 9로 올라가요.
...
```

#### 💡 `join()`
* 다른 쓰레드의 작업을 기다린다.
* 쓰레드 자신이 하던 작업을 잠시 멈추고 다른 쓰레드가 지정된 시간동안 작업을 수행하도록 할 때 사용한다.
  * 아래 예시: `join()` 메서드를 호출하는 쓰레드의 인스턴스 `incInterest` 쓰레드가 종료된 후에 `main` 쓰레드 진행.
* 시간을 지정하지 않으면, 해당 쓰레드가 작업을 모두 마칠 때까지 기다리게 된다.
* 작업 중에 다른 쓰레드의 작업이 먼저 수행되어야할 필요가 있을 때 사용한다.

```java
public class Ex03 {
    public static void main(String[] args) {
        Thread incInterest = new Thread(new IncreaseInterest(10));
        incInterest.start();

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNext()) {
                String line =  sc.nextLine();

                if (line.equalsIgnoreCase("enjoy")) {

                    System.out.println("금리가 인상될 필요가 있다.");

                    //  💡 join() - 다른 쓰레드의 작업을 기다린다.
                    //  ⚠️ catch 블록 요구됨 - InterruptedException 처리
                    try {
                        incInterest.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //incInterest.join(5000); // 일정시간 동안만 조인 가능
                }

                if(line.equalsIgnoreCase("quit")) break;
                System.out.println(line);
            }
        }
    }
}
```
###### console
```
Thread-0 : 금리가 0에서 1로 올라가요.
hi // 입력
hi // 출력
Thread-0 : 금리가 1에서 2로 올라가요.
go // 입력
go // 출력
Thread-0 : 금리가 2에서 3로 올라가요.
enjoy // 입력
Thread-0 : 금리가 3에서 4로 올라가요.
금리가 인상될 필요가 있다.
Thread-0 : 금리가 4에서 5로 올라가요.
Thread-0 : 금리가 5에서 6로 올라가요.
Thread-0 : 금리가 6에서 7로 올라가요.
stop? // 입력
Thread-0 : 금리가 7에서 8로 올라가요.
no? // 입력
Thread-0 : 금리가 8에서 9로 올라가요.
yes? // 입력
Thread-0 : 금리가 9에서 10로 올라가요.
enjoy // 출력
stop? // 출력
no? // 출력
yes? // 출력
```

#### 💡 `interrupt()`
* 해당 쓰레드의 `run()`메서드에서 `InterruptedException`을 발생시킴
* 쓰레드에게 작업을 멈추라고 요청한다.
  * 강제하는 것이 아니라 메세지를 던지는 것, 단지 멈추라고 요청만 하는 것일 뿐 쓰레드를 강제로 종료시키지는 못한다.

###### ☕️ IncreaseInterest.java
```java
public class IncreaseInterest implements Runnable {

    int max;
    public IncreaseInterest(int max) {this.max = max;}

    @Override
    public void run() {
        String interest = "%s : 금리가 %d에서 %d로 올라가요.";

        for (int i = 0; i < max; i++) {
            try {
                Thread.sleep(2000);
                System.out.printf( (interest) + "%n", Thread.currentThread().getName(), i, (i + 1));
            } catch (InterruptedException e) {
                System.out.println("알겠습니다.");
                return;
            }
        }
    }
}

```
###### ☕️ Ex03.java
```java
public class Ex03 {
    public static void main(String[] args) {
        Thread incInterest = new Thread(new IncreaseInterest(10));
        incInterest.start();

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNext()) {
                String line =  sc.nextLine();

                if(line.equalsIgnoreCase("stop")) {
                    System.out.println("그만 올려요.");
                    incInterest.interrupt();
                }

                if(line.equalsIgnoreCase("quit")) break;
                System.out.println(line);
            }
        }
    }
}
```
###### console
```
Thread-0 : 금리가 0에서 1로 올라가요.
Thread-0 : 금리가 1에서 2로 올라가요.
sopt // 입력
sopt // 출력
Thread-0 : 금리가 2에서 3로 올라가요.
stop // 입력
그만 올려요. 
stop // 출력
알겠습니다. 
```

---

## 3. 쓰레드 그룹과 데몬 쓰레드

---

## 4. 동기화
