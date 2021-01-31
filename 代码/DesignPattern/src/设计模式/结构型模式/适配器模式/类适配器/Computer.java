package 设计模式.结构型模式.适配器模式.类适配器;

/**
 * @author like
 * @date 2020-12-18 19:15
 * @contactMe 980650920@qq.com
 * @description
 */
public class Computer {

    public String readSd(SDCard sdCard) {
        if (sdCard == null) {
            throw new NullPointerException("Sd card is null");
        }
        return sdCard.readSd();
    }
}
