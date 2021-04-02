package 设计模式.行为模式.责任链模式.code;

/**
 * @author like
 * @date 2020-12-28 15:03
 * @contactMe 980650920@qq.com
 * @description 抽象处理器
 */
public abstract class Handler {
    protected static final int one = 1;
    protected static final int three = 3;
    protected static final int seven = 7;
    private Handler nextHandler;
    private int numStart;
    private int numEnd;

    protected Handler(int numStart) {
        this.numStart = numStart;
    }

    protected Handler(int numStart, int numEnd) {
        this.numStart = numStart;
        this.numEnd = numEnd;
    }

    protected void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract void handle(LeaveRequest request);

    public final void submit(LeaveRequest request) {
        if (nextHandler != null && request.getNum() > numEnd) {
            // 处理不了,向上提交
            nextHandler.submit(request);
        } else if (request.getNum() < numEnd) {
            handle(request);
        } else {
            System.out.println("您的请假条件不符合规范，请重新填写");
        }
    }
}
