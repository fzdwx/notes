package 设计模式.结构型模式.适配器模式.类适配器;

/**
 * @author like
 * @date 2020-12-18 19:19
 * @contactMe 980650920@qq.com
 * @description
 */
public class SDAdapterTF extends TFCardImpl implements SDCard{

    @Override
    public String readSd() {
        System.out.println("adapter read");
        return readTf();
    }

    @Override
    public void writeSd(String data) {
        System.out.println("adapter write");
        writeTf(data);
    }
}
