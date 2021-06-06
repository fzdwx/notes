package cn.like.code.testCase.haperloglog;

import cn.hutool.core.util.RandomUtil;

import java.text.SimpleDateFormat;
import java.util.*;

import static cn.like.code.testCase.Redis.sync;

/**
 * .____    .__ __
 * |    |   |__|  | __ ____
 * |    |   |  |  |/ // __ \
 * |    |___|  |    <\  ___/
 * |_______ \__|__|_ \\___  >
 * \/       \/    \/
 *
 * desc
 *
 * @author like
 */
public class 周活跃用户数月活跃用户数年活跃用户数的统计_28 {

    public static void main(String[] args) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String date = sdf.format(calendar.getTime());
            init(date);

            System.out.println("日期為：" + date + "的uv是:" + getUV(date));
        }
        System.out.println("周活用户："+getWeeklyUV());
    }

    /**
     * 初始化 某一天的uv数据
     *
     * @param today 今天
     */
    private static void init(String today) {
        for (int i = RandomUtil.randomInt(0, 1000); i < 1323; i++) {
            sync().pfadd("user:access:" + today, String.valueOf(i + 1));
        }
    }

    public static Long getUV(String date) {
        return sync().pfcount("user:access:" + date);
    }

    public static Long getWeeklyUV() {
        List<String> keys = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        for (int i = 0; i < 7; i++) {

            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String date = sdf.format(calendar.getTime());
            keys.add("user:access:" + date);
        }

        String key = "uv:weekly:" + keys.get(0) + "-" + keys.get(keys.size() - 1);
        String[] array = keys.toArray(new String[0]);
        System.out.println("array = " + Arrays.toString(array));
        sync.pfmerge(key,array);

        System.out.println("key = " + key);
        return sync.pfcount(key);
    }
}
