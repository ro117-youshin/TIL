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






















