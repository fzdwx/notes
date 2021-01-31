package 设计模式.行为模式.备忘录模式.white;

/**
 * @author like
 * @date 2021-01-03 15:36
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        System.out.println("大战boss----------前");
        GameRole like = new GameRole();
        like.display();
        // 备份
        System.out.println("进行备份");
        RoleStateMemento memento = like.saveState();

        System.out.println("大战boss----------后");
        like.fight();
        like.display();
        System.out.println("恢复备份时的状态");
        like.recoverState(memento);
        like.display();
    }
}
