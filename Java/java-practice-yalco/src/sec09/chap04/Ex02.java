package sec09.chap04;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ex02 {
    public static void main(String[] args) {
        // 배열로부터 생성
        Integer[] integerArry = {1,2,3,4,5};
        Stream<Integer> fromArray = Arrays.stream(integerArry);
        Object[] fromArray_Arr = fromArray.toArray();
        // 런타임 에러
//         Object[] ifReuse = fromArray.toArray();

        // 원시값의 배열로부터는 스트림의 클래스가 달라짐
        int[] intArray = {1,2,3,4,5};
        IntStream fromIntArray = Arrays.stream(intArray);
        int[] fromIntArray_Arr = fromIntArray.toArray();

        double[] dblArray = {1.1,2.2,3.3,4.4,5.5};
        DoubleStream fromDoubleArray = Arrays.stream(dblArray);
        double[] fromDoubleArray_Arr = fromDoubleArray.toArray();

        // 컬렉션으로부터 생성
        List<Integer> intArrayList = new ArrayList<>(Arrays.asList(integerArry));
        Stream fromCollection = intArrayList.stream();
        Object[] fromCollection_Arr = fromCollection.toArray();
        // 맵의 경우 엔트리의 스트림으로 생성
        Map<String, Character> subjectGradeHM = new HashMap<String, Character>();
        subjectGradeHM.put("English", 'B');
        subjectGradeHM.put("French", 'C');
        subjectGradeHM.put("Italian", 'A');
        Object[] fromSubjectGradeHM = subjectGradeHM.entrySet().stream().toArray();

        //  💡 빌더로 생성
        Stream.Builder<Character> builder = Stream.builder();
        builder.accept('스');
        builder.accept('트');
        builder.accept('림');
        builder.accept('빌');
        builder.accept('더');
        Stream<Character> withBuilder = builder.build();
        Object[] withBuilder_Arr = withBuilder.toArray();

        //  💡 concat 메소드로 생성
        Stream<Integer> toConcat1 = Stream.of(11, 22, 33);
        Stream<Integer> toConcat2 = Stream.of(44, 55, 66);
        Stream<Integer> withConcatMethod = Stream.concat(toConcat1, toConcat2);
        Object[] withConcatMethod_Arr = withConcatMethod.toArray();

        IntStream randomInts = new Random().ints(5, 0, 100);
        int[] randomInts_Arr = randomInts.toArray();

        DoubleStream randomDbls = new Random().doubles(5, 2, 3);
        double[] randomDbls_Arr = randomDbls.toArray();

        Stream<Double> emptyDblStream = Stream.empty();
    }
}
