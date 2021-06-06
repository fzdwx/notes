package cn.like.code.testCase.geo;

import io.lettuce.core.GeoArgs;

import static cn.like.code.testCase.Redis.sync;

/**
 * @author: like
 * @since: 2021/5/19 19:37
 * @email: 980650920@qq.com
 * @desc:
 */
public class 基于GeoHash的你与商铺距离计算的程序 {

    /**
     * 添加位置
     *
     * @param name      的名字
     * @param longitude 经度
     * @param latitude  纬度
     */
    public void addLocation(String name, double longitude, double latitude) {
        sync().geoadd("location_date", longitude, latitude, name);
    }

    /**
     * 得到距离
     *
     * @param from 从
     * @param to   来
     * @return {@link Double}
     */
    public Double getDist(String from, String to) {
        return sync.geodist("location_date", from, to, GeoArgs.Unit.km);
    }

    public static void main(String[] args) {
        基于GeoHash的你与商铺距离计算的程序 test = new 基于GeoHash的你与商铺距离计算的程序();

        test.addLocation("天安门广场", 116.39777173016356, 39.90368886319734);
        test.addLocation("我", 116.46132935498045, 39.90599332133357);

        System.out.println(test.getDist("天安门广场", "我"));
    }
}
