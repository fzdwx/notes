package 数据结构和算法.数据结构.set;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import 数据结构和算法.数据结构.set.file.FileInfo;
import 数据结构和算法.数据结构.set.file.Files;

/**
 * @author like
 * @date 2020-12-23 10:38
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {

    public static void main(String[] args) {
        String like  = "李可";
        char[] chars = like.toCharArray();
        for (char aChar : chars) {
            System.out.println(((int) (aChar)));
        }
        System.out.println("like.hashCode() = " + like.hashCode());
    }

    private static void initSet() {
        String[] strings = new String[]{};
        FileInfo info = Files.read("D:\\Java\\project\\src\\java.base", strings);

        TreeSet<String> treeSet = new TreeSet<>();
        ListSet<String> listSet = new ListSet<>();
        System.out.println("info.words().length = " + info.words().length);
        test(listSet, info.words());
        System.out.println("listSet.size() = " + listSet.size());
        System.out.println("=====");
        test(treeSet, info.words());
        System.out.println("treeSet.size() = " + treeSet.size());
    }

    public static void test(Set<String> set, String[] words) {
        long start = System.currentTimeMillis();
        System.out.println("開始:"+DateTime.now());
        for (String word : words) {
            set.add(word);
        }
        for (String word : words) {
            set.contains(word);
        }
        long end = System.currentTimeMillis();
        System.out.println("結束:"+DateTime.now());
        DateUtil.now();
        System.out.println(end -start);
    }
}