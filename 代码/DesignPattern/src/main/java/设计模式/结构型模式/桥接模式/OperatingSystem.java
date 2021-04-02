package 设计模式.结构型模式.桥接模式;

/**
 * @author like
 * @date 2020-12-21 15:23
 * @contactMe 980650920@qq.com
 * @description 抽象化角色  操作系统
 */
public abstract class OperatingSystem {
    protected VideoFile videoFile;

    public OperatingSystem(VideoFile videoFile) {
        this.videoFile = videoFile;
    }

    /**
     * 播放视频
     *
     * @param filename 文件名
     */
    public abstract void playVideo(String filename);
}
