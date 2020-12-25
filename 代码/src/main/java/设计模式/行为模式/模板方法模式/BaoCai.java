package 设计模式.行为模式.模板方法模式;

/**
 * @author like
 * @date 2020-12-25 17:10
 * @contactMe 980650920@qq.com
 * @description
 */
public class BaoCai extends AbstractClass {

    @Override
    protected void pourVeg() {
        System.out.println("放入包菜");
    }

    @Override
    protected void pourSauce() {
        System.out.println("放入辣椒");
    }
}
