package 设计模式.行为模式.解释器模式;

/**
 * @author like
 * @date 2021-01-04 15:47
 * @contactMe 980650920@qq.com
 * @description
 */
public class Sub extends AbstractExp {

    private AbstractExp left;
    private AbstractExp right;

    public Sub(AbstractExp left, AbstractExp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret(Context context) {
        return left.interpret(context) - right.interpret(context);
    }

    @Override
    public String toString() {
        return "(" + left + "-" + right + ")";
    }
}
