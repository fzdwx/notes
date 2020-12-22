package 设计模式.结构型模式.桥接模式;

/**
 * @author like
 * @date 2020-12-21 15:22
 * @contactMe 980650920@qq.com
 * @description 具体实现化角色 rmvb格式的文件
 */
public class RmvbFile implements VideoFile {

    @Override
    public void deCode(String fileName) {
        System.out.println(fileName+"：解码为Rmvb格式");
    }
}
