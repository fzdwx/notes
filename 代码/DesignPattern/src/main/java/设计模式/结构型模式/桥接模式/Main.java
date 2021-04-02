package 设计模式.结构型模式.桥接模式;

/**
 * @author like
 * @date 2020-12-21 15:28
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {

    public static void main(String[] args) {
        VideoFile avi = new AviFile();
        VideoFile rmvb = new RmvbFile();


        OperatingSystem mac = new Mac(avi);
        mac.playVideo("战狼3");
    }
}
