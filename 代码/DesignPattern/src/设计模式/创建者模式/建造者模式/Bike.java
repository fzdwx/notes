package 设计模式.创建者模式.建造者模式;

/**
 * @author like
 * @date 2020-12-16 15:58
 * @contactMe 980650920@qq.com
 * @description 具体的产品对象
 */
public class Bike {

    private String frame; // 车架
    private String seat; // 车座

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}


