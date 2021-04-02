package 设计模式.创建者模式.工厂模式.实际使用;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * @author like
 * @date 2020-12-14 15:18
 * @contactMe 980650920@qq.com
 * @description 咖啡店
 */
public class SimpleCoffeeFactory {

    public static void main(String[] args) {
        Coffee usa = createCoffee("usa");
        Coffee latte = createCoffee("latte");
        System.out.println(usa.getName());
        System.out.println(latte.getName());
    }

    // 定义容器对象存储咖啡对象
    private static HashMap<String, Coffee> map = new HashMap<>();

    // 加载配置文件
    static {
        try {
            Properties properties = new Properties();
            properties.load(new InputStreamReader(new FileInputStream("src\\main\\resources\\bean.properties")));
            Set<Object> keys = properties.keySet();
            for (Object key: keys) {
                String className = properties.getProperty((String) key);
                Class<Coffee> clazz = (Class<Coffee>) Class.forName(className);
                Coffee coffee = clazz.newInstance();
                map.put((String) key, coffee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Coffee createCoffee(String type) {
        return map.get(type);
    }
}
