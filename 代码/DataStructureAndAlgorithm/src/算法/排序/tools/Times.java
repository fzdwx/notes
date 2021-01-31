package 算法.排序.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Times<E> {
    private static final SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss.SSS");

    public E test(String title, Task<E> task) {
        if (task == null) return null;
        title = (title == null) ? "" : ("【" + title + "】");
        System.out.println(title);
        System.out.println("开始：" + fmt.format(new Date()));
        long begin = System.currentTimeMillis();
        E e = task.execute();
        long end = System.currentTimeMillis();
        System.out.println("结束：" + fmt.format(new Date()));
        double delta = (end - begin) / 1000.0;
        System.out.println("耗时：" + delta + "秒");
        System.out.println("-------------------------------------");
        return e;
    }

}
