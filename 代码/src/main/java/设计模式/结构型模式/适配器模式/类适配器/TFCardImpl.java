package 设计模式.结构型模式.适配器模式.类适配器;

/**
 * @author like
 * @date 2020-12-18 19:10
 * @contactMe 980650920@qq.com
 * @description 适配者
 */
public class TFCardImpl implements TFCard {
    String data;

    @Override
    public String readTf() {
        return data;
    }

    @Override
    public void writeTf(String data) {
        this.data = data;
    }
}
