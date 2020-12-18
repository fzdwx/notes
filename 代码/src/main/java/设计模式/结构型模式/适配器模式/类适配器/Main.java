package 设计模式.结构型模式.适配器模式.类适配器;

/**
 * @author like
 * @date 2020-12-18 19:17
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        Computer computer = new Computer();
        SDCardImpl sdCard = new SDCardImpl();
        sdCard.writeSd("电脑中的sd卡数据：hello world sdcard");
        System.out.println(computer.readSd(sdCard));
        System.out.println("========使用tf对象=========");
        SDAdapterTF tf = new SDAdapterTF();
        tf.writeSd("sd card data");
        System.out.println(computer.readSd(tf));
    }
}
