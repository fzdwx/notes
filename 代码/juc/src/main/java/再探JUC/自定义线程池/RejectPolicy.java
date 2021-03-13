package 再探JUC.自定义线程池;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-10 11:39
 * @Description:  拒绝策略
 */
@FunctionalInterface
public interface RejectPolicy<T> {

    void reject(BlockingQueue<T> tasks, T task);
}

