package 设计模式.结构型模式.适配器模式.对象适配器;

/**
 * @author like
 * @date 2020-12-18 19:19
 * @contactMe 980650920@qq.com
 * @description sd的适配器
 */
public class SDAdapterTF implements SDCard {

    private  TFCard tfCard;

    public SDAdapterTF(TFCard tfCard) {
        this.tfCard = tfCard;
    }

    @Override
    public String readSd() {
        System.out.println("adapter read");
        return tfCard.readTf();
    }

    @Override
    public void writeSd(String data) {
        System.out.println("adapter write");
        tfCard.writeTf(data);
    }
}
