package Java并发编程_JavaUtilConcurrent;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author likeLove
 * @time 2020-09-18  13:21
 * compare and set(swap)
 * 比较和交换
 */

public class CasDemo {
    static AtomicInteger number = new AtomicInteger(5);

    public static void main(String[] args) {
        System.out.println(test());

    }

    private static int test() {
        try {
            int i = 10/0;
        } catch (Exception e) {
            return 1;
        } finally {
            return 2;
        }
    }

    private static void atomicStamp() {
        /* AtomicStampedReference<User> userAtomicStampedReference = new AtomicStampedReference<>();*/
        AtomicReference<User> atomicUser = new AtomicReference<>();
        User u1 = new User("like",18);
        User u2 = new User("ke", 20);
        atomicUser.set(u1);
        boolean b1 = atomicUser.compareAndSet(u1, u2);//期望是u1，设置新的为u2
        System.out.println(b1 +"\t" + atomicUser.get());
        boolean b2 = atomicUser.compareAndSet(u1, u2);//期望是u1，设置新的为u2
        System.out.println(b2 + "\t" + atomicUser.get());
        //怎么解决ABA问题
        //增加新增一种机制，修改版本号
    }

    private static void increment() {
        // public final int getAndIncrement() {
        //        return unsafe.getAndAddInt(this, valueOffset, 1);
        //    }
        // var1   this
        // var2   valueofset(在内存中的地址)
        // var4   需要变动的量
        // var5   通过var1 和 var2 找出的内存中真实的值
        // public final int getAndAddInt(Object var1, long var2, int var4) {
        //        int var5;
        //        do {
        //            var5 = this.getIntVolatile(var1, var2);
        //        } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
        //
        //        return var5;
        //    }
        System.out.println(number.getAndIncrement());
        System.out.println(number);
    }

    private static void cas() {
        //compareAndSet(int 期望值, int 修改后的值)，如果实际值和期望值相同，就修改
        System.out.println(number.compareAndSet(4, 2020));
        System.out.println(number);
    }
}

class User {
    private String name;
    private int age;

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", age=" + age + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
