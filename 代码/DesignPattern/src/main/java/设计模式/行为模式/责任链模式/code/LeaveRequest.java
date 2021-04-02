package 设计模式.行为模式.责任链模式.code;

/**
 * 离开的请求
 *
 * @author like
 * @date 2020-12-28 15:02
 * @contactMe 980650920@qq.com
 * @description 请假条
 */
public class LeaveRequest {

    private String name;
    private int num ;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LeaveRequest(String name, int num, String content) {
        this.name = name;
        this.num = num;
        this.content = content;
    }
}
