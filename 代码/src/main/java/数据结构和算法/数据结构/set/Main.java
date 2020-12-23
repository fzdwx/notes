package 数据结构和算法.数据结构.set;

/**
 * @author like
 * @date 2020-12-23 10:38
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>();

        set.add(10);
        set.add(12);
        set.add(11);
        set.add(12);
        set.add(12);
        set.add(14);

        set.traversal(el  ->{System.out.println(el );return false;});
    }

}
