package 再探JUC.test;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-09 10:55
 */
public class Test10 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher t = new Teacher();
        unsafe.compareAndSwapInt(t, idOffset,0,1);
        unsafe.compareAndSwapObject(t, nameOffset, null, "like");

        System.out.println(t);
    }
}

@Data
class Teacher{
    volatile int id;
    volatile String name;
}