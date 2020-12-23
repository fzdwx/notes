package 设计模式.结构型模式.组合模式;

/**
 * @author like
 * @date 2020-12-23 18:05
 * @contactMe 980650920@qq.com
 * @description
 */
public abstract class MenuComponent {
    /**
     * 菜单的名字
     */
    protected String name;
    /**
     * 菜单的层级
     */
    protected int level;
    public MenuComponent(String name, int level) {
        this.name = name;
        this.level = level;
    }

    /**
     * 添加
     *
     * @param menuComponent 菜单组件
     */
    public void add(MenuComponent menuComponent) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除
     *
     * @param menuComponent 菜单组件
     */
    public void remove(MenuComponent menuComponent) {
        throw new UnsupportedOperationException();
    }

    /**
     * 得到
     *
     * @param index 索引
     * @return {@link MenuComponent}
     */
    public MenuComponent get(int index) {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        return name;
    }

    /**
     * 打印名字
     */
    public abstract void printName();
}
