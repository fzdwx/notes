package 设计模式.行为模式.备忘录模式.white;

import lombok.Data;

/**
 * @author like
 * @date 2021-01-03 15:20
 * @contactMe 980650920@qq.com
 * @description
 */
@Data
public class GameRole implements Cloneable {
    private int vit;
    private int atk;
    private int def;

    public GameRole() {
        vit = 100;
        atk = 100;
        def = 100;
    }

    public void fight() {
        vit = 20;
        atk = 80;
        def = 30;
    }

    public RoleStateMemento saveState() throws CloneNotSupportedException {
        return new RoleStateMemento((GameRole) this.clone());
    }

    public void recoverState(RoleStateMemento memento) {
        GameRole role = memento.getGameRole();
        this.def = role.def;
        this.vit = role.vit;
        this.atk = role.atk;
    }

    public void display() {
        System.out.println(this);
    }
}
