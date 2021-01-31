package 设计模式.行为模式.解释器模式;

/**
 * @author like
 * @date 2021-01-04 15:42
 * @contactMe 980650920@qq.com
 * @description
 */
public class Var extends AbstractExp {

    private String name;

    public Var(String name) {
        this.name = name;
    }

    @Override
    public int interpret(Context context) {
        return context.getValue(this);
    }

    @Override
    public String toString() {
        return name;
    }
}
