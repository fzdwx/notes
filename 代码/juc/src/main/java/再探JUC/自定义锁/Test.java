package 再探JUC.自定义锁;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-14 11:21
 */
public class Test {

}

/**
 * 不重入锁
 * @author pdd20
 * @date 2021/03/14
 */
class DontReentrantLock implements Lock {

    /**
     * 不可重入锁  同步器
     */
    class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                // 如果能修改成功，为当前线程 加锁成功,
                setExclusiveOwnerThread(Thread.currentThread());
            }
            return false;
        }

        /**
         * 试着释放
         */
        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 是否持有独占锁
         * @return boolean
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 新条件
         * @return {@link Condition}
         */
        public Condition newCondition() {
            return new ConditionObject();
        }
    }

    Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.tryRelease(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
