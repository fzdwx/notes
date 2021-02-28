package 再探JUC.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-28 11:31
 */
public class Test3 {

    public static void main(String[] args) {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
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
class TwoPhaseTermination {

    /**
     * 监控线程
     */
    private Thread monitor;

    /**
     * 启动监控线程
     */
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread curr = Thread.currentThread();
                if (curr.isInterrupted()) {
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
        monitor.interrupt();
    }
}