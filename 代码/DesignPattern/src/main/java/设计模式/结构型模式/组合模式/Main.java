package 设计模式.结构型模式.组合模式;

/**
 * @author like
 * @date 2020-12-23 18:19
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        MenuComponent 系统管理 = new Menu("系统管理", 1);
        MenuComponent 菜单管理 = new Menu("菜单管理", 2);
        菜单管理.add(new MenuItem("页面访问",3));
        菜单管理.add(new MenuItem("展开菜单",3));
        菜单管理.add(new MenuItem("编辑菜单",3));
        菜单管理.add(new MenuItem("新增菜单",3));
        MenuComponent 权限配置 = new Menu("权限配置", 2);
        权限配置.add(new MenuItem("页面访问",3));
        权限配置.add(new MenuItem("提交保存",3));
        MenuComponent 角色管理 = new Menu("角色管理", 2);
        角色管理.add(new MenuItem("页面访问",3));
        角色管理.add(new MenuItem("新增角色",3));
        角色管理.add(new MenuItem("修改角色",3));

        系统管理.add(菜单管理);
        系统管理.add(权限配置);
        系统管理.add(角色管理);

        系统管理.printName();
    }
}
