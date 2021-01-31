package 设计模式.行为模式.备忘录模式.white;

import lombok.Data;

/**
 * @author like
 * @date 2021-01-03 15:28
 * @contactMe 980650920@qq.com
 * @description 备忘录
 */
@Data
public class RoleStateMemento {
    private GameRole gameRole;

    public RoleStateMemento(GameRole gameRole) {
        this.gameRole = gameRole;
    }
}
