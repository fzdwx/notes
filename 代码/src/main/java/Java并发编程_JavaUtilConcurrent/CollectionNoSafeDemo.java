package Java并发编程_JavaUtilConcurrent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author likeLove
 * @time 2020-09-22  18:24
 * 集合类不安全
 */
public class CollectionNoSafeDemo {
    public static void main(String[] args) {
        Map<Object, Object> objectObjectMap = Collections.synchronizedMap(new HashMap<>());
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, "name").start();
        }
    }
    private static void unSafeList() {
        List<String> list = new CopyOnWriteArrayList<>();;
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

}
