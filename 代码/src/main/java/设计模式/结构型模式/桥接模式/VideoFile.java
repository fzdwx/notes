package 设计模式.结构型模式.桥接模式;

/**
 * @author like
 * @date 2020-12-21 15:20
 * @contactMe 980650920@qq.com
 * @description 实现化角色 视频文件
 */
public interface VideoFile {

    /**
     * 解码
     *
     * @param fileName 文件名称
     */
    void deCode(String fileName);
}
