package 设计模式.行为模式.命令模式;

/**
 * @author like
 * @date 2020-12-27 20:12
 * @contactMe 980650920@qq.com
 * @description 厨师类
 */
public class Chef {
    public  void makeFood(String name, int num) {
        System.out.printf("制作了：%s共%s份\r\n", name, num);
    }
}
