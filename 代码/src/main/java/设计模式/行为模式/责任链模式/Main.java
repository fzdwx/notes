package 设计模式.行为模式.责任链模式;

import 设计模式.行为模式.责任链模式.code.GroupLeader;
import 设计模式.行为模式.责任链模式.code.LeaveRequest;

/**
 * @author like
 * @date 2020-12-28 15:17
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        new GroupLeader().submit(new LeaveRequest("李可",8, "身体很健康"));
    }
}
