package 设计模式.结构型模式.适配器模式.对象适配器;

/**
 * @author like
 * @date 2020-12-18 19:13
 * @contactMe 980650920@qq.com
 * @description 目标
 */
public interface SDCard {

    String readSd();

    void writeSd(String data);
}
