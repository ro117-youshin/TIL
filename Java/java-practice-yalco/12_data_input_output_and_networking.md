# 12. 파일 입출력과 네트워킹
> '제대로 파는 자바 - 얄코' 섹션12 학습 [(인프런)](https://www.inflearn.com/course/%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%8C%8C%EB%8A%94-%EC%9E%90%EB%B0%94/dashboard)
> 1. 기본적인 파일/폴더 다루기
> 2. NIO2로 파일과 폴더 다루기
> 3. I/O 스트림
> 4. Reader와 Writer
> 5. 직렬화
> 6. URL로 접속하기
> 7. 소켓 프로그래밍

---
## 1. 기본적인 파일/폴더 다루기

### `java.io`패키지
* `File` 클래스를 사용하여 파일들을 다룸
* 여러 문제점과 한계를 갖고 있었음
  * 멀티쓰레드에서 안전하지 않음
  * 기능 한정
  * OS간의 이식성 문제 (경로 처리 이슈)
* `java.nio.file` 패키지의 기능들로 대체

### 1. 파일 다루기

#### 📁 파일 생성
###### ☕️ Ex01.java
```java
import java.io.File;
import java.io.IOException;

public class Ex01 {
    // 프로젝트에 맞게
    public static final String CUR_PATH = "src/sec12/chap01/";

    public static void main(String[] args) {
        String filePath = "file1.txt";
        filePath = CUR_PATH + filePath; //  ⭐️ 상대경로

        //  💡 파일명만 적으면 프로젝트 파일 바로 안에 저장
        //  - 상대경로 또는 절대경로를 앞에 추가하여 지정

        File file1 = new File(filePath);
        System.out.println(file1.exists()); // 생성 후 다시 실행해 볼 것

        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                System.out.println("🛑 파일 생성 실패");
                // CUR_PATH를 틀리게 작성하고 실행해 볼 것
                throw new RuntimeException(e);
            }
        }
    }
}
```
#### 💡 `File` 인스턴스의 메소드들
```java
        boolean file1Exist = file1.exists();
        String file1Name = file1.getName();
        boolean file1IsFile = file1.isFile(); // 파일인가 여부
        boolean file1IsDir = file1.isDirectory(); // 디렉토리인가 여부
        long file1Length = file1.length();

        String file1AbsPath = file1.getAbsolutePath(); // 절대경로
        String file1CnnPath = ""; // ⭐️ 완전 절대경로
        try {
            file1CnnPath = file1.getCanonicalPath();
        } catch (IOException e) {
            // 💡 파일의 읽기 권한이 없을 시
            throw new RuntimeException(e);
        }  // 🔴
```
#### 💡 CanonicalPath - 완전 절대경로
* 경로 중 포함된 상대경로를 모두 절대경로로 변환
* 심볼릭 링크(바로가기)도 절대경로로 치환
* 읽기 권한 없을 시 예외 던짐

#### 📁 완전 절대경로로 파일 생성
```java
        File file2 = new File(
                file1CnnPath.replace("file1.txt", "file2.txt")
        );
        try {
            boolean result = file2.createNewFile();
            //  💡 해당 이름의 파일이 있다면 예외를 발생시키지는 않고 false 반환
            System.out.println(result ? "파일 생성됨" : "이미 있음");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
```

#### 📁 파일 이름 변경
###### ☕️ Ex02.java
```java
	String newName = "name_changed.txt" ;

        File file2 = new File(Ex01.CUR_PATH + "file2.txt");
        File nameChange = new File(Ex01.CUR_PATH + newName);

        //  💡 파일의 이름 변경하고 결과 반환
        boolean renameResult = file2.renameTo(nameChange);
        System.out.println(renameResult ? "이름 변경됨" : "해당 파일 없음");
```

### 2. 폴더 다루기

###### ☕️ Ex03.java
```java
	String folderName = "myFolder";
        File folder = new File(Ex01.CUR_PATH + folderName);

        //  💡 디렉토리 만들기
        boolean folderMade = folder.mkdir();

        boolean isFile = folder.isFile();
        boolean isDir = folder.isDirectory();
```
* index 3의 배수는 dir 생성, 나머지는 file 생성
```java
	IntStream.range(0, 10).forEach(i -> {
            try {
                if (i % 3 == 0) {
                    new File(
                            "%s%s/folder%d"
                            .formatted(Ex01.CUR_PATH, folderName, i + 1)
                    ).mkdir();
                    return;
                }
                new File(
                        "%s%s/file%d.txt"
                        .formatted(Ex01.CUR_PATH, folderName, i + 1)
                ).createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //  💡 폴더 안의 파일/폴더들 배열로 출력
        File[] filesInFolder = folder.listFiles();
        String[] fileNamesInFolder = folder.list();
```
* 생성 결과 꺼내보기
```java
	for (File item : filesInFolder) {
            System.out.println(
                    (item.isFile() ? " 📄" : "📁")
                    + " " + item.getName()
            );
        }
```

---

## 2. NIO2로 파일과 폴더 다루기

### `java.nio.file` 패키지
* 자바7 도입
* 기존의 `java.io` 패키지보다 안정적이고 다양한 기능

### 1. `Paths` 클래스
* 파일 시스템 경로를 인스턴스화한 `Path` 객체 생성
* 경로를 보다 편리하고 직관적으로 다루는 기능들 제공

#### `Paths` 클래스 예제
###### ☕️ Main.java
```java
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static final String CUR_PATH = "src/sec12/chap02/";

    public static void main(String[] args) {

        // 💡 프로젝트 폴더 경로
        // 내 PC의 프로젝트 경로 (java-practice-yalco의 경로)
        Path path1 = Paths.get("");
        Path path1Abs = path1.toAbsolutePath();

        // 프로젝트 폴더 경로에 my_file.txt 파일을 갖고 있는 인스턴스를 생성
        // 파일이 생성되어 있지 않아도 인스턴스로 생성되어 있는 것임.
        Path path2 = Paths.get("my_file.txt");
        Path path2Abs = path2.toAbsolutePath();

        // 💡 인자로 들어온 문자열을 각각 내부 폴더로
        // chap02 폴더 안에 sub1 폴더 안에 sub2 폴더 안에 sub3 폴더가 생성
        Path path3 = Paths.get(CUR_PATH, "sub1", "sub2", "sub3");

        // 💡 두 경로를 통합
        // path3의 경로에 path2에 담긴 파일명으로 생성
        Path path4 = path3.resolve(path2);

        // 💡 부모 경로
        // path4의 객체 경로를 가져옴, 그래서 아직 만들어지지 않더라도 경로를 자유자재로 다룰 수 있음
        Path path5 = path4.getParent();

        // 💡 한 경로에서 다른 경로로의 상대 경로
        // path4 -> path2로 갈 수 있는 경로를 보여줌
        Path path6 = path4.relativize(path2);

        // 💡 끝단 파일/폴더명
        Path path7 = path4.getFileName();

        // 💡 서브경로
        // 경로 depth를 문자열처럼 잘라서 보관
        Path path8 = path4.subpath(3, 5);
    }
}
```

## 5. 직렬화 Serialization
* 인스턴스를 바이트 스트림으로 변환
* 다른 곳에 보내거나 파일 등으로 저장하기 위해 사용

#### 직렬화 예제
###### ☕️ Person.java
```java
public class Person implements Serializable {

    //  💡 serialVersionUID : 클래스의 버전 번호로 사용
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private double height;
    private boolean married;

    //  💡 transient : 직렬화에서 제외
    transient private String bloodType;
    transient private double weight;

    //  ⭐️ 직렬화에 포함되려면 해당 클래스도 Serializable 구현
    private Career career;

    public Person(String name, int age, double height, boolean married, String bloodType, double weight, Career career) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.married = married;
        this.bloodType = bloodType;
        this.weight = weight;
        this.career = career;
    }

}
```
###### ☕️ Career.java
```java
public class Career implements Serializable {
    private static final long serialVersionUID = 1L;
    private String company;
    private int years;

    public Career(String company, int years) {
        this.company = company;
        this.years = years;
    }
}
```
###### ☕️ Ex01.java
```java
public class Ex01 {

    public static String PEOPLE_PATH = "src/sec12/chap05/people.ser";

    public static void main(String[] args) {
        Person person1 = new Person(
                "홍길동", 20, 175.5, false,
                "AB", 81.2,
                new Career("ABC Market", 2)
        );
        Person person2 = new Person(
                "전우치", 35, 180.3, true,
                "O", 74.3,
                new Career("Macrosoft", 14)
        );

        List<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);
        people.add(new Person(
                "임꺽정", 45, 162.8, true,
                "A", 68.3,
                new Career("Koryeo Inc.", 20)
        ));
        people.add(new Person(
                "붉은매", 24, 185.3, false,
                "B", 79.3,
                new Career("Cocoa", 30)
        ));

        //  💡 ObjectOutputStream : 직렬화 구현한 인스턴스를 스트림으로 출력
        try (
                FileOutputStream fos = new FileOutputStream(PEOPLE_PATH);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
        ) {
            oos.writeObject(person1);
            oos.writeObject(person2);
            oos.writeObject(people);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
```
#### 역직렬화 예제
###### ☕️ Ex02.java
```java
public class Ex02 {
    public static String PEOPLE_PATH = "src/sec12/chap05/people.ser";

    public static void main(String[] args) {
        Person person1Out;
        Person person2Out;
        List<Person> peopleOut;

        //  ⭐️ 다시 인스턴스로 역직렬화
        try (
                FileInputStream fis = new FileInputStream(PEOPLE_PATH);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
        ) {

            //  ⚠️ 직렬화할 때와 순서 동일해야 함
            //  - 순서 바꾸고 재실행 해 볼 것
            person1Out = (Person) ois.readObject();
            person2Out = (Person) ois.readObject();
            peopleOut = (ArrayList) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(person1Out);
        System.out.println(person2Out);
        System.out.println(peopleOut);
    }

}
```

###### 💡 serialVersionUID : 직렬화하여 주고받을 클래스의 버전
* 직접 지정하지 않으면 자동으로 생성됨
* 클래스를 보낼 쪽과 받을 쪽에 명시된 클래스의 내용이 다를 때를 대비
* `Ex01.java` 직렬화 한 뒤 `Ex02.java` 역직렬화로 테스트 진행
  * `Person`클래스의 내용은 유지하되 버전 번호를 바꿔서 실행해볼 것.
    * ⚠️ 예외발생 (클래스의 변경내용이 공유되어야 함을 받는 쪽에 알림)
  * 버전 번호는 유지하되, `Person` 클래스의 `name` 필드의 변수이름을 변경
    * 예외는 발생하지 않으나 디버깅 해보면 해당 필드 값이 `null`임
  * 버전 번호는 유지하되, `Person` 클래스 `age`필드의 타입을 변경
    * ⚠️ 예외 발생














