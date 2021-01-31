package 设计模式.行为模式.模板方法模式;

/**
 * @author like
 * @date 2020-12-25 17:05
 * @contactMe 980650920@qq.com
 * @description 抽象模板类
 */
public abstract class AbstractClass {

    // 模板方法->不能个修改
    public final void cookProcessing() {
        pourOil();
        heatOil();;
        pourVeg();
        pourSauce();
        fry();
    }

    private void pourOil() {
        System.out.println("倒油");
    }

    private void heatOil() {
        System.out.println("热油");
    }

    private void fry() {
        System.out.println("翻炒");
    }
    /**
     * 倒入蔬菜
     */
    protected   abstract void pourVeg();

    /**
     * 倒入调料
     */
    protected abstract void pourSauce();
}
