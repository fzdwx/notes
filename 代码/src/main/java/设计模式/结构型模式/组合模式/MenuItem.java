package 设计模式.结构型模式.组合模式;

/**
 * @author like
 * @date 2020-12-23 18:05
 * @contactMe 980650920@qq.com
 * @description
 */
public class MenuItem  extends  MenuComponent{

    public MenuItem(String name, int level) {
        super(name, level);
    }

    @Override
    public void printName() {
        for (int i = 0; i < level; i++) {
            System.out.print("-");
        }
        System.out.println(getName());
    }
}
