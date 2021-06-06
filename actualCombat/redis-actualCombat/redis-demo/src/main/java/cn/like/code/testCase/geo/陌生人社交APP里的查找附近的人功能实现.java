package cn.like.code.testCase.geo;

import io.lettuce.core.GeoArgs;

import java.util.Set;

import static cn.like.code.testCase.Redis.sync;

/**
 * @author: like
 * @since: 2021/5/19 19:50
 * @email: 980650920@qq.com
 * @desc:
 */
public class 陌生人社交APP里的查找附近的人功能实现 {

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

    public Set<String> nearPeople(String user) {
        return sync().georadiusbymember("location_date", user, 6.0, GeoArgs.Unit.km);
    }

    public static void main(String[] args) {
        陌生人社交APP里的查找附近的人功能实现 test = new 陌生人社交APP里的查找附近的人功能实现();
        test.addLocation("我", 116.46132935498045, 39.90599332133357);
        test.addLocation("小美", 118.39777173016356, 39.90368886319734);
        test.addLocation("小红", 116.3977717301646, 39.90368886319737);
        test.addLocation("小李", 116.39778717301646, 39.90368886349737);

        Set<String> nearPeople = test.nearPeople("我");
        System.out.println(nearPeople);
    }
}
