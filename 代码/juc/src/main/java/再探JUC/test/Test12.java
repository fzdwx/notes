package 再探JUC.test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: fork join
 * @since 2021-03-13 11:51
 */

public class Test12 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(3);

        System.out.println(pool.invoke(new MyTask(4)));
    }
}

class MyTask extends RecursiveTask<Integer> {

    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n < 1) {
            return 1;
        }
        MyTask myTask = new MyTask(n - 1);
        myTask.fork();  // fork
        Integer joinRes = myTask.join(); // join
        return n + joinRes;
    }
}