package 设计模式.行为模式.责任链模式.code;

/**
 * @author like
 * @date 2020-12-28 15:14
 * @contactMe 980650920@qq.com
 * @description
 */
public class Manger extends Handler {

    public Manger() {
        super(0,three);
        setNextHandler(new GeneralManager());
    }

    @Override
    protected void handle(LeaveRequest request) {
        System.out.println(request.getName() + "请假:" + request.getNum() + "天，理由：" + request.getContent());
        System.out.println("经理：同意！");
    }
}
