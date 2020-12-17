package Java并发编程_JavaUtilConcurrent;

import java.util.HashMap;

/**
 * @author likeLove
 * @since 2020-10-08  10:57
 */
public class HashMapDemo {
    public static void main(String[] args) {
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 1; i <= 100; i++) {
            int finalI = i;
            new Thread(() -> {
                map.put(finalI, finalI);
            }, String.valueOf(i)).start();
        }
        System.out.println(map);
    }
}
