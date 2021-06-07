package cn.like.code.concurrent.case_1;


import cn.hutool.poi.excel.cell.CellEditor;
import io.vavr.Function2;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;

import java.util.Arrays;
import java.util.function.Function;

/**
 * @author like
 * @date 2021/6/7 16:21
 */
public class ThreadUnsafeExample {

    private int cnt = 0;

    public void add() {
        this.cnt++;
    }

    public int getCnt() {
        return cnt;
    }

    public static void main(String[] args) {
        String str = """
                123
                """;
        final Tuple2<String, Integer> java8 = Tuple.of("Java", 8);

        final Tuple2<String, Integer> map = java8.map(s -> s.substring(2) + "va",
                i -> i / 8);

        final Tuple2<char[], Integer> integerTuple2 = java8.map1(String::toCharArray);

        final String s1 = java8.apply((s, i) -> "hello");
        System.out.println(s1);
        System.out.println(Arrays.toString(integerTuple2._1));
        System.out.println(str);
        System.out.println(map);

        final Function2<Integer,Integer,Integer> f1 = (a, b) -> a + b;
    }
}
