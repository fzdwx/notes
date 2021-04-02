package 设计模式.行为模式.备忘录模式.white;

import lombok.Data;

/**
 * @author like
 * @date 2021-01-03 15:34
 * @contactMe 980650920@qq.com
 * @description 备忘录管理角色
 */
@Data
public class RoleStateCaretaker {
    private RoleStateMemento memento;

    public RoleStateCaretaker(RoleStateMemento memento) {
        this.memento = memento;
    }
}
