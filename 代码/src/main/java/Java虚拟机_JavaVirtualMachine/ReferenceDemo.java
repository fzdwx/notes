package Java虚拟机_JavaVirtualMachine;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author likeLove
 * @since 2020-09-26  21:13
 */
public class ReferenceDemo {
    public static void main(String[] args) {
      /*  //强引用 只要有一个强引用指向对象，就不能回收
        Object o1 = new Object();
        //软引用 内存够用就保留，不够就回收
        Object o2 = new Object();
        SoftReference<Object> softRef = new SoftReference<Object>(o2);
        //弱引用 gc的时候直接回收
        Object o3 = new Object();
        WeakReference<Object> weakRef = new WeakReference<>(o3);
        o3 = null;
        System.out.println(weakRef.get());
        System.gc();
        System.out.println("========");
        System.out.println(weakRef.get());*/
        //虚引用 和referenceQueue一起使用，主要作用是跟踪对象被垃圾回收的状态，提供一种确保对象被 finalize以后，做某些事的机制。
        //PhantomReference的get方法总是返回null，因此无法访问对应的引用对象，其意义在与说明一个对象已经进入finalization阶段。
        //可以被gc回收，用来实现比finalization机制更灵活的回收操作
        //作用：在这个对象被回收的时候收到一个系统通知或者后续添加更进一步的处理，有点像Spring Aop 的 after
        Object o4 = new Object();
        ReferenceQueue<Object> redQ = new ReferenceQueue<>();
        PhantomReference<Object> phantomRef = new PhantomReference<>(o4, redQ);


        System.out.println(o4);
        System.out.println(phantomRef.get());
        System.out.println(redQ.poll());
        System.out.println("====gc=====");
        System.gc();
        try {TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace();}
        System.out.println(o4);
        System.out.println(phantomRef.get());
        System.out.println(redQ.poll());

    }
}
