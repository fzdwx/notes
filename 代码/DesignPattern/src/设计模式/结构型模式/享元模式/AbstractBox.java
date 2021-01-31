package 设计模式.结构型模式.享元模式;

/**
 * @author like
 * @date 2020-12-24 15:48
 * @contactMe 980650920@qq.com
 * @description 抽象享元角色
 */
public abstract class AbstractBox {
    /**
     * 获取图像
     *
     * @return {@link String}
     */
    public abstract String getShape();

    public void display(String color) {
        System.out.println("方块的颜色："+ color);
    }
}
