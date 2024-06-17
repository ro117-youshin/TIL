package sec12.chap02;

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
