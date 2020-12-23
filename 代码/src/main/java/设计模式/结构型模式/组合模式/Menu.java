package 设计模式.结构型模式.组合模式;


import java.util.ArrayList;
import java.util.List;

/**
 * @author like
 * @date 2020-12-23 18:05
 * @contactMe 980650920@qq.com
 * @description
 */
public class Menu extends MenuComponent {
    private List<MenuComponent> list = new ArrayList<>();

    public Menu(String name, int level) {
        super(name, level);
    }

    @Override
    public void add(MenuComponent menuComponent) {
        list.add(menuComponent);
    }

    @Override
    public void remove(MenuComponent menuComponent) {
        list.remove(menuComponent);
    }

    @Override
    public MenuComponent get(int index) {
        return list.get(index);
    }

    @Override
    public void printName() {
        for (int i = 0; i < level; i++) {
            System.out.print("-");
        }
        // 1.打印菜单名称
        System.out.println(getName());
        // 2.打印子菜单名称
        for (MenuComponent m : list) {
            m.printName();
        }
    }
}
