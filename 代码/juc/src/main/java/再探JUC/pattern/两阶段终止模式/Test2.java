package 再探JUC.pattern.两阶段终止模式;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-08 10:47
 * @deprecated 犹豫
 */
public class Test2 {

    public static void main(String[] args) {
        TwoPhaseTermination2 tpt = new TwoPhaseTermination2();
        tpt.start();

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tpt.stop();
    }
}

@Slf4j
class TwoPhaseTermination2 {

    /**
     * 监控线程
     */
    private Thread monitor;
    private boolean stop = false;

    /** 是否调用过start方法 */
    private boolean starting = false;

    /**
     * 启动监控线程
     */
    public void start() {
        if (starting) {
            return;
        }
        starting = true;
        monitor = new Thread(() -> {
            while (true) {
                Thread curr = Thread.currentThread();
                if (stop) {
                    log.info("料理后事");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("执行监控记录");
                } catch (Exception e) {
                    e.printStackTrace();
                    // 重新设置打断标记
                    curr.interrupt();
                }
            }
        }, "monitor");

        monitor.start();
    }

    /**
     * 停止监控线程
     */
    public void stop() {
        stop = true;
    }
}