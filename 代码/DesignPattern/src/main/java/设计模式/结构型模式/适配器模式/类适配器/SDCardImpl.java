package 设计模式.结构型模式.适配器模式.类适配器;

/**
 * @author like
 * @date 2020-12-18 19:13
 * @contactMe 980650920@qq.com
 * @description 目标
 */
public class SDCardImpl implements SDCard {

    private String data;

    @Override
    public String readSd() {
        return data;
    }

    @Override
    public void writeSd(String data) {
        this.data = data;
    }
}
