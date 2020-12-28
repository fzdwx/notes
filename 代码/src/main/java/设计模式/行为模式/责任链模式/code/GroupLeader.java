package 设计模式.行为模式.责任链模式.code;

/**
 * @author like
 * @date 2020-12-28 15:09
 * @contactMe 980650920@qq.com
 * @description 小组长
 */
public class GroupLeader extends Handler {

    public GroupLeader() {
        super(0,one);
        setNextHandler(new Manger());
    }

    @Override
    protected void handle(LeaveRequest request) {
        System.out.println(request.getName() + "请假:" + request.getNum() + "天，理由：" + request.getContent());
        System.out.println("小组长：同意！");
    }
}
