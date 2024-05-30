# 11. 멀티태스킹
> '제대로 파는 자바 - 얄코' 섹션11 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. 쓰레드 만들기
> 2. 쓰레드 다루기
> 3. 쓰레드 그룹과 데몬 쓰레드
> 4. 동기화
> 5. wait & notify
> 6. 쓰레드 풀과 Future
> 7. CompletableFuture
> 8. 병렬 스트림
> 9. Thread-safe한 클래스

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

### 💡 쓰레드 그룹
* 연관된 쓰레드들을 그룹으로 묶기 위해 사용됨
* 쓰레드 그룹이 다른 쓰레드 그룹에 포함될 수 있음
* 쓰레드를 일괄적으로 다루거나 보안상 분리하기 위해 사용


###### ☕️ Ex01.java
```java
public class Ex01 {
    public static void main(String[] args) {
        Thread thr1 = new Thread(() -> {});

        //  💡 따로 그룹을 지정해주지 않은 쓰레드
        //  - main 쓰레드그룹에 속함
        ThreadGroup mainThrGroup = thr1.getThreadGroup();
        String mainThreadName = mainThrGroup.getName();

        //  💡 쓰레드 그룹 직접 생성하기
        ThreadGroup threadGroup1 = new ThreadGroup("TG_1");
        String thrGroup1Name = threadGroup1.getName();

        //  💡 그룹에 속한 쓰레드를 만드는 생성자
        Thread thr2 = new Thread(threadGroup1, () -> {});
        String thr2GroupName = thr2.getThreadGroup().getName();

        //  💡 또 다른 쓰레드 그룹에 속한 쓰레드 그룹 만들기
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "TG_2");
        String thrGroup2ParentName = threadGroup2.getParent().getName();
    }
}
```
```
> mainThreadName = "main"
> thrGroup1Name = "TG_1"
> thr2GroupName = "TG_1"
> thrGroup2ParentName = "TG_1"
```


###### ☕️ PrintThread.java
```java
public class PrintThread implements Runnable {
    static int lastNo = 0;
    String groupName;
    int no;

    public PrintThread(String groupName) {
        this.groupName = groupName;
        this.no = ++lastNo;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.printf("[%s] %d%n", groupName, no);
            } catch (InterruptedException e) {
                System.out.printf("🛑 %s 종료%n", groupName);
                return;
            }
        }
    }
}
```

###### ☕️ Ex02.java
```java
public class Ex02 {
    public static void main(String[] args) {
        ThreadGroup groupA = new ThreadGroup("A");
        ThreadGroup groupB = new ThreadGroup("B");
        ThreadGroup groupBB = new ThreadGroup(groupB, "BB");
        ThreadGroup groupC = new ThreadGroup("C");

        for (ThreadGroup tg : new ThreadGroup[] { groupA, groupB, groupBB, groupC }) {
            for (int i = 0; i < 3; i++) {
                new Thread(tg, new PrintThread(tg.getName())).start();
            }
        }

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNext()) {
                String line = sc.nextLine();

                if (line.length() == 1) {
                    ThreadGroup[] groups = new ThreadGroup[] {
                            groupA, groupB, groupC
                    };

                    if ("abc".contains(line)) {
                        ThreadGroup group = groups["abc".indexOf(line)];

                        //  💡 그룹의 현황 파악
                        //  - 다른 메소드들도 살펴볼 것
                        System.out.printf(
                                "%s : %d / %d%n",
                                group.getName(),
                                group.activeCount(),
                                //  내부의 쓰레드들이 멈춰도 active로 카운트
                                group.activeGroupCount()
                        );
                    }

                    if ("ABC".contains(line)) {
                        //  그룹 일괄 종료
                        ThreadGroup group = groups["ABC".indexOf(line)];
                        group.interrupt();
                    }
                }

                if (line.equalsIgnoreCase("quit")) break;
            }
        }
    }
}
```
###### console: 소문자 입력
```
a // 입력
A : 3 / 0 // 출력
b // 입력
B : 6 / 1 // 출력
c // 입력
C : 3 / 0 // 출력
```
###### console: 대문자 입력
```
A // 입력
🛑 A 종료
🛑 A 종료
🛑 A 종료
[BB] 7
[B] 5
[BB] 9
[C] 12
[C] 11
[BB] 8
[B] 6
[C] 10
[B] 4
```
```
B // 입력
🛑 BB 종료
🛑 BB 종료
🛑 B 종료
🛑 B 종료
🛑 BB 종료
🛑 B 종료
[C] 11
[C] 10
[C] 12
```
```
C // 입력
🛑 C 종료
🛑 C 종료
🛑 C 종료
```


### 💡 데몬 쓰레드
* 다른 쓰레드(주 쓰레드)의 작업을 보조하는 역할
* 주 쓰레드의 작업이 끝나면 자동 종료
* 이 점을 제외하고는 데몬 쓰레드와 주 쓰레드는 다르지 않음

###### ☕️ Ex03.java
```java
public class Ex03 {
    public static void main(String[] args) {
        Runnable rythmRun = () -> {
            int index = 0;
            String rythm = "쿵쿵짝";

            while (true) {
                System.out.print(rythm.charAt(index++) + " ");
                index %= rythm.length();

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread SingThread = new Thread(() -> {
            String[] lines = new String[] {
                    "푸른하늘 은하수", "하얀 쪽배엔",
                    "계수나무 한나무", "토끼 세마리",
                    "한마리는 구워먹고", "한마리는 튀겨먹고",
                    "한마리는 도망간다", "서쪽나라로"
            };

            Thread rythmThread = new Thread(rythmRun);

            //  💡 리듬 쓰레드를 본 노래 쓰레드의 데몬으로 지정
            //  - 이 부분이 없으면 노래가 끝나도 리듬이 멈추지 않음
            //rythmThread.setDaemon(true);

            rythmThread.start();

            for (int i = 0; i < lines.length; i++) {
                System.out.println("\n" + lines[i]);
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        SingThread.start();
    }
}

```
###### console: 데몬으로 지정하지 않을 경우
```
푸른하늘 은하수
쿵 쿵 짝 쿵 쿵 짝 
하얀 쪽배엔
쿵 쿵 짝 쿵 쿵 짝 
계수나무 한나무
쿵 쿵 짝 쿵 쿵 짝 
토끼 세마리
쿵 쿵 짝 쿵 쿵 짝 
한마리는 구워먹고
쿵 쿵 짝 쿵 쿵 짝 
한마리는 튀겨먹고
쿵 쿵 짝 쿵 쿵 짝 
한마리는 도망간다
쿵 쿵 짝 쿵 쿵 짝 
서쪽나라로
쿵 쿵 짝 쿵 쿵 짝 쿵 쿵 짝 쿵 쿵 짝 쿵 쿵 짝 쿵 쿵 짝 쿵 쿵 짝 쿵 쿵 짝 쿵 쿵 짝 쿵 쿵 짝 쿵 쿵 짝 쿵 쿵 짝 ... 
```
###### console: 데몬으로 지정
```
푸른하늘 은하수
쿵 쿵 짝 쿵 쿵 짝 
하얀 쪽배엔
쿵 쿵 짝 쿵 쿵 짝 
계수나무 한나무
쿵 쿵 짝 쿵 쿵 짝 
토끼 세마리
쿵 쿵 짝 쿵 쿵 짝 
한마리는 구워먹고
쿵 쿵 짝 쿵 쿵 짝 
한마리는 튀겨먹고
쿵 쿵 짝 쿵 쿵 짝 
한마리는 도망간다
쿵 쿵 짝 쿵 쿵 짝 
서쪽나라로
쿵 쿵 짝 쿵 쿵 짝
```

---

## 4. 동기화

### 💡 동기화 *synchronization*
* 특정 자원에 여러 쓰레드가 동시에 접근하는 것을 방지
* 쓰레드의 동기화 - 한 쓰레드가 진행중인 작업을 다른 쓰레드가 간섭하지 못하게 막는 것

#### 📁 Ex01
###### ☕️ ATM.java
```java
public class ATM {
    private int balance = 0;
    public void addMoney(int amount) {
        balance += amount;
    }
    public int getBalance() {
        return balance;
    }

    public void withdraw (String name, int amount) {

        if (balance < amount) return;

        System.out.printf(
                "💰 %s 인출 요청 (현 잔액 %d)%n",
                name, balance
        );
        try {
            Thread.sleep(new Random().nextInt(700, 1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        balance -= amount;
        System.out.printf(
                "✅ %s 인출 완료 (현 잔액 %d)%n",
                name, balance
        );
    }
}
```
###### ☕️ CustomerRun.java
```java
public class CustomerRun implements Runnable {
    String name;
    ATM atmToUse;
    int needed;

    public CustomerRun(String name, ATM atmToUse, int needed) {
        this.name = name;
        this.atmToUse = atmToUse;
        this.needed = needed;
    }

    @Override
    public void run() {
        while (atmToUse.getBalance() > needed) {
            atmToUse.withdraw(name, needed);

            try {
                Thread.sleep(100);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
```
###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {

        ATM atm = new ATM();
        atm.addMoney(5000);

        Thread thr1 = new Thread(new CustomerRun("철수", atm, 500));
        Thread thr2 = new Thread(new CustomerRun("마이크", atm, 300));
        Thread thr3 = new Thread(new CustomerRun("호나우두", atm, 400));

        thr1.start();
        thr2.start();
        thr3.start();
    }
}
```
###### console
```
💰 철수 인출 요청 (현 잔액 5000)
💰 마이크 인출 요청 (현 잔액 5000)
💰 호나우두 인출 요청 (현 잔액 5000)
✅ 호나우두 인출 완료 (현 잔액 4600)
💰 호나우두 인출 요청 (현 잔액 4600)
✅ 철수 인출 완료 (현 잔액 4100)
✅ 마이크 인출 완료 (현 잔액 3800)
💰 철수 인출 요청 (현 잔액 3800)
💰 마이크 인출 요청 (현 잔액 3800)
✅ 마이크 인출 완료 (현 잔액 3500)
✅ 호나우두 인출 완료 (현 잔액 3100)
💰 마이크 인출 요청 (현 잔액 3100)
💰 호나우두 인출 요청 (현 잔액 3100)
✅ 철수 인출 완료 (현 잔액 2600)
💰 철수 인출 요청 (현 잔액 2600)
✅ 마이크 인출 완료 (현 잔액 2300)
💰 마이크 인출 요청 (현 잔액 2300)
✅ 호나우두 인출 완료 (현 잔액 1900)
✅ 철수 인출 완료 (현 잔액 1400)
💰 호나우두 인출 요청 (현 잔액 1400)
💰 철수 인출 요청 (현 잔액 1400)
✅ 마이크 인출 완료 (현 잔액 1100)
💰 마이크 인출 요청 (현 잔액 1100)
✅ 호나우두 인출 완료 (현 잔액 700)
💰 호나우두 인출 요청 (현 잔액 700)
✅ 철수 인출 완료 (현 잔액 200)
✅ 마이크 인출 완료 (현 잔액 -100)
✅ 호나우두 인출 완료 (현 잔액 -500)
```
* 잔액이 적자로 동기화 필요.

###### ☕️ ATM.java
* 동기화를 위해 `withdraw()` 메서드에 `synchronized` 붙이고 실행.
###### console
```
💰 철수 인출 요청 (현 잔액 5000)
✅ 철수 인출 완료 (현 잔액 4500)
💰 호나우두 인출 요청 (현 잔액 4500)
✅ 호나우두 인출 완료 (현 잔액 4100)
💰 마이크 인출 요청 (현 잔액 4100)
✅ 마이크 인출 완료 (현 잔액 3800)
💰 호나우두 인출 요청 (현 잔액 3800)
✅ 호나우두 인출 완료 (현 잔액 3400)
💰 철수 인출 요청 (현 잔액 3400)
✅ 철수 인출 완료 (현 잔액 2900)
💰 호나우두 인출 요청 (현 잔액 2900)
✅ 호나우두 인출 완료 (현 잔액 2500)
💰 마이크 인출 요청 (현 잔액 2500)
✅ 마이크 인출 완료 (현 잔액 2200)
💰 호나우두 인출 요청 (현 잔액 2200)
✅ 호나우두 인출 완료 (현 잔액 1800)
💰 철수 인출 요청 (현 잔액 1800)
✅ 철수 인출 완료 (현 잔액 1300)
💰 호나우두 인출 요청 (현 잔액 1300)
✅ 호나우두 인출 완료 (현 잔액 900)
💰 마이크 인출 요청 (현 잔액 900)
✅ 마이크 인출 완료 (현 잔액 600)
💰 호나우두 인출 요청 (현 잔액 600)
✅ 호나우두 인출 완료 (현 잔액 200)
```
* 또는 동기화를 필요로 하는 메서드 내의 특정 작업만 할 경우, `synchronized() {}` 블록 안에 작성할 것.
```java
public void withdraw(String name, int amount) {

        //  💡 동기
        //  - this는 현 쓰레드를 의미함
        //  - 메소드 내의 특정 작업만 동기화가 필요할 때
        synchronized (this) {

            if (balance < amount) return;

            System.out.printf(
                    "💰 %s 인출 요청 (현 잔액 %d)%n",
                    name, balance
            );
            try {
                Thread.sleep(new Random().nextInt(700, 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            balance -= amount;
            System.out.printf(
                    "✅ %s 인출 완료 (현 잔액 %d)%n",
                    name, balance
            );
        }
}
```

### 💡 캐싱에 의한 문제 방지하기

###### ☕️ Cache1.java
```java
public class Cache1 {

    static boolean stop = false;
    public static void main(String[] args) {
        new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;

                // ⭐️ 아래를 주석처리하고 다시 실행해보기
                System.out.println(i);
            }

            System.out.println("- - - 쓰레드 종료 - - -");
        }).start();

        try { Thread.sleep(1000);
        } catch (InterruptedException e) {}

        stop = true;

        //  💡 JVM의 캐시 방식에 따라 멈출 수도 안 멈출 수도 있음
        //  - stop으로의 접근이 동기화되지 않았을 시
        //  - 한 쓰레드가 그 값을 바꿔도 다른 쓰레드는 캐시(CPU와 메모리 사이)에 저장된
        //  - 바뀌기 이전 값을 참조할 수 있음
        //    - println 메소드는 위 코드에서 캐시를 비우는 이유 제공
    }
}
```
* 위 예시 코드에서 `while`문 안에 `println()` 메서드를 주석처리 할 경우 `i`변수가 변경되더라도 사용하지 않아 쓰레드가 종료되지 않는다. (JVM Run 상태) 

#### ⭐️ 해결책 1. `volatile` 사용
- 변수의 값이 변경될 때마다 메모리에 업데이트
- 멀티쓰레딩 환경에서 캐싱에 의한 문제 방지
- 동기화와는 다름! 값 변경만 바로 확인시켜줌
###### ☕️ Cache2.java
```java
public class Cache2 {

    volatile static boolean stop = false;
    public static void main(String[] args) {
        new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;
            }

            System.out.println("- - - 쓰레드 종료 - - -");
        }).start();

        try { Thread.sleep(1000);
        } catch (InterruptedException e) {}

        stop = true;
    }
}
```

#### ⭐️ 해결책 2. 동기화 사용
* 동기화된 메소드로 변수에 접근 시
  * 캐시 재사용에 의한 문제가 발생하지 않음
###### ☕️ Cache3.java
```java
public class Cache3 {

    static boolean stop = false;

    //  💡 동기화된 클레스 메소드들 (getter & setter)
    synchronized public static boolean isStop() {
        return stop;
    }
    synchronized public static void setStop(boolean stop) {
        Cache3.stop = stop;
    }

    public static void main(String[] args) {
        new Thread(() -> {
            int i = 0;
            while (!isStop()) {
                i++;
            }

            System.out.println("- - - 쓰레드 종료 - - -");
        }).start();

        try { Thread.sleep(1000);
        } catch (InterruptedException e) {}

        setStop(true);
    }
}
```

---

## 5. wait & notify
#### `Object`의 쓰레드 관련 메서드들
* `wait`: 동기화 메서드 사용 중 자기 일을 멈춤
  * 다른 쓰레드가 사용할 수 있도록 양보
  * 실행 중이던 쓰레드는 해당 객체의 대기실(waiting pool)에서 통지를 기다린다.
* `notify`: 일을 멈춘 상태의 쓰레드에게 자리가 비었음을 통보
  * 대기열의 쓰레드 중 하나에만 통보
    * 상황에 따라서는 무한대기상태가 될 수 있음
* `notifyAll`: 대기중인 모든 쓰레드에 통보
  * 모든 쓰레드에게 통보를 하지만, 하나의 쓰레드만 lock을 얻고 나머지 쓰레드는 통보를 받긴 했지만, lock을 얻지 못하면 다시 lock을 기다린다.
* 추가 설명을 적자면, 오래 기다린 쓰레드가 lock을 얻는다는 보장이 없다는 것.
* 3가지 메서드 모두 동기화 블록(synchronized블록)내에서만 사용할 수 있다.

#### 예제: 군인이 공중전화 이용하기
###### ☕️ PhoneBooth.java
```java
public class PhoneBooth {
    synchronized public void phoneCall (SoldierRun soldier) {
        System.out.println("☎️ %s 전화 사용중...".formatted(soldier.title));

        try { Thread.sleep(500);
        } catch (InterruptedException e) {}

        System.out.println("👍 %s 전화 사용 완료".formatted(soldier.title));


        //notifyAll();
        //try {
        //    //  💡 현 사용자를 폰부스에서 내보냄
        //    //  - sleep처럼 아래의 예외 반환 확인
        //    wait();
        //} catch (InterruptedException e) {
        //    throw new RuntimeException(e);
        //}
    }
}
```
###### ☕️ SoldierRun.java
```java
public class SoldierRun implements Runnable {
    String title;
    PhoneBooth phoneBooth;

    public SoldierRun(String title, PhoneBooth phoneBooth) {
        this.title = title;
        this.phoneBooth = phoneBooth;
    }
    @Override
    public void run() {
        while (true) {
            phoneBooth.phoneCall(this);
        }
    }
}
```
###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {
        PhoneBooth booth = new PhoneBooth();
        Arrays.stream("김병장,이상병,박일병,최이병".split(",")).forEach(s -> new Thread(
                new SoldierRun(s, booth)
        ).start());
    }
}
```
* `PhoneBooth.java` 클래스에서 주석을 해제하지 않으면 첫 사용자가 다 사용한다.
  * 단, JDK 제품마다 차이가 있을 수 있다.
* 주석을 해제하고 실행했을 때, 여러 쓰레드가 사용.

#### 예제: 생산자와 소비자 모델
* 같은 인스턴스의 다른 메서드에도 적용.
  * 예제에서 살펴보면, 커피를 채우는 동안은 테이크아웃을 할 수 없음.
###### ☕️ CoffeeMachine.java
```java
public class CoffeeMachine {
    final int CUP_MAX = 10;
    int cups = CUP_MAX;

    synchronized public void takeout (CustomerRun customer) {
        if (cups < 1) {
            System.out.printf(
                    "[%d] 😭 %s 커피 없음%n", cups, customer.name
            );
        } else {
            try { Thread.sleep(1000);
            } catch (InterruptedException e) {}

            System.out.printf(
                    "[%d] ☕️ %s 테이크아웃%n", cups, customer.name
            );
            cups--;
        }

        notifyAll();
        try { wait(); // 커피를 타고 나감
        } catch (InterruptedException e) {}
    }

    synchronized public void fill () {
        if (cups > 3) {
            System.out.printf(
                    "[%d] 👌 재고 여유 있음...%n", cups
            );
        } else {
            try { Thread.sleep(1000);
            } catch (InterruptedException e) {}

            System.out.printf(
                    "[%d] ✅ 커피 채워넣음%n", cups
            );
            cups = CUP_MAX;
        }

        notifyAll();
        try { wait(); // 커피를 채우고 나감
        } catch (InterruptedException e) {}
    }
}
```
###### ☕️ CustomerRun.java
```java
public class CustomerRun implements Runnable {
    String name;
    CoffeeMachine coffeeMachine;

    public CustomerRun(String name, CoffeeMachine coffeeMachine) {
        this.name = name;
        this.coffeeMachine = coffeeMachine;
    }

    @Override
    public void run() {
        while (true) {
            coffeeMachine.takeout(this);
        }
    }
}
``` 
###### ☕️ ManagerRun.java
```java
public class ManagerRun implements Runnable {
    CoffeeMachine coffeeMachine;
    public ManagerRun(CoffeeMachine coffeeMachine) {
        this.coffeeMachine = coffeeMachine;
    }

    @Override
    public void run() {
        while (true) {
            coffeeMachine.fill();
        }
    }
}
```
###### ☕️ Main.java
```java
public class Main {
    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();

        Arrays.stream("철수,영희,돌준,병미,핫훈,짱은,밥태".split(","))
                .forEach(s -> new Thread(
                        new CustomerRun(s, coffeeMachine)
                ).start());

        new Thread(new ManagerRun(coffeeMachine)).start();
    }
}
```
###### console
```
[10] ☕️ 철수 테이크아웃
[9] 👌 재고 여유 있음...
[9] ☕️ 밥태 테이크아웃
[8] ☕️ 핫훈 테이크아웃
[7] ☕️ 짱은 테이크아웃
[6] ☕️ 병미 테이크아웃
[5] ☕️ 돌준 테이크아웃
[4] ☕️ 영희 테이크아웃
[3] ☕️ 돌준 테이크아웃
[2] ☕️ 병미 테이크아웃
[1] ☕️ 짱은 테이크아웃
[0] 😭 핫훈 커피 없음
[0] 😭 밥태 커피 없음
[0] ✅ 커피 채워넣음
[10] ☕️ 철수 테이크아웃
[9] ☕️ 밥태 테이크아웃
[8] ☕️ 핫훈 테이크아웃
[7] ☕️ 짱은 테이크아웃
[6] ☕️ 병미 테이크아웃
[5] ☕️ 돌준 테이크아웃
[4] ☕️ 영희 테이크아웃
...
```





