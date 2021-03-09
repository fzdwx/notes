package 再探JUC.test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-09 11:03
 */
public class Teacher11 {
    public static void main(String[] args) {
    }
}


class MyAtomicInt {
    private static Unsafe UNSAFE;
    public static long valueOffset;
    private volatile int value;

    public MyAtomicInt(int value) {
        this.value = value;
    }

    static {
        UNSAFE = UnsafeCreator.unsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInt.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public int get() {
        return value;
    }

    public void decrement(int amount) {
        while (true) {
            int prev = this.value;
            int next = prev - amount;

            if (UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }
    }
}

final class UnsafeCreator {
    private static Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Unsafe unsafe() {
        return unsafe;
    }
}