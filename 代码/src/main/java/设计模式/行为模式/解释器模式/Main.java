package 设计模式.行为模式.解释器模式;

/**
 * @author like
 * @date 2021-01-04 15:50
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        Context ioc = new Context();
        Var a = new Var("a");
        Var b = new Var("b");
        Var c = new Var("c");
        Var d = new Var("d");
        ioc.assign(a,1);
        ioc.assign(b,2);
        ioc.assign(c,3);
        ioc.assign(d,4);

        AbstractExp exp =  new Sub(new Add(new Add(a, b),c),d);
        System.out.println(exp+"="+ exp.interpret(ioc));
    }
}
