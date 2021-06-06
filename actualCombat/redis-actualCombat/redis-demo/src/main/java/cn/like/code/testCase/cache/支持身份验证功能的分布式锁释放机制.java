package cn.like.code.testCase.cache;

import org.springframework.util.StringUtils;

import static cn.like.code.testCase.Redis.sync;

/**
 * @author: like
 * @since: 2021/5/19 20:10
 * @email: 980650920@qq.com
 * @desc:
 */
public class 支持身份验证功能的分布式锁释放机制 {

    public boolean unlock(String key, String value) {
        try {
            sync().watch(key);

            String val = sync.get(key);
            if (StringUtils.hasText(val) && val.equals(value)) {
                sync.multi();
                sync.del(key);
                sync.exec();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            sync.reset();
        }
    }

    public static void main(String[] args) {
        支持身份验证功能的分布式锁释放机制 test = new 支持身份验证功能的分布式锁释放机制();
        String key = "lock";
        String value = "1";
        long timeout = 10;
        String lock1 = sync.setex(key, timeout, value);
        String lock2 = sync.setex(key, timeout, value);

        boolean unlock1 = test.unlock(key, "2222");
        boolean unlock2 = test.unlock(key, value);


        System.out.println("lock1 = " + lock1);
        System.out.println("lock2 = " + lock2);

        System.out.println("unlock1 = " + unlock1);
        System.out.println("unlock2 = " + unlock2);
    }
}
