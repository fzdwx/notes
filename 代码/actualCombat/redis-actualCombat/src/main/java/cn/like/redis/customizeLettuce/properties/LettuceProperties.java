package cn.like.redis.customizeLettuce.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.Duration;
import java.util.List;

/**
 * lettuce 配置项
 *
 * @author like
 */
@Data
@ConfigurationProperties(prefix = "lettuce")
@EnableConfigurationProperties
public class LettuceProperties {

    /**
     * 连接工厂使用的数据库索引。
     */
    private int database = 0;

    /**
     * 连接URL,覆盖主机,端口和密码,用户被忽略。
     * 例子：
     * redis：//user:password@example.com:6379
     */
    private String url;

    /**
     * Redis服务器主机。
     */
    private String host = "localhost";

    /**
     * Redis服务器的登录用户名。
     */
    private String username;

    /**
     * Redis服务器的登录密码。
     */
    private String password;

    /**
     * Redis服务器端口。
     */
    private int port = 6379;

    /**
     * 是否启用SSL支持。
     */
    private boolean ssl;

    /**
     * 读取超时。
     */
    private Duration timeout;

    /**
     * 连接超时。
     */
    private Duration connectTimeout;

    /** 自动重新连接 */
    private boolean autoReconnect;

    /**
     * 在与CLIENT SETNAME的连接上设置的客户端名称
     */
    private String clientName;

    private Sentinel sentinel;

    private Cluster cluster;

    private Pool pool;

    /*private final Lettuce lettuce = new Lettuce();*/

    /**
     * Pool properties.
     */
    @Data
    public static class Pool {

        /**
         * 池中“空闲”连接的最大数量。使用负值表示无限数量的空闲连接。
         */
        private int maxIdle = 8;

        /**
         * 目标是要在池中维护的最小空闲连接数。此设置仅在 timeBetweenEvictionRuns 间隔为正时才有效。
         */
        private int minIdle = 0;

        /**
         * 在给定时间池可以分配的最大连接数。使用负值表示没有限制。
         */
        private int maxActive = 8;

        /**
         * 当池耗尽时，在引发异常之前，连接分配应阻塞的最长时间。使用负值无限期阻止。
         */
        private int maxWait = -1;

        /**
         * 空闲对象退出线程的运行之间的时间。当为正时，空闲对象逐出线程启动，否则不执行空闲对象逐出。
         */
        private Duration timeBetweenEvictionRuns;
    }

    /**
     * Cluster properties.
     */
    @Data
    public static class Cluster {

        /**
         * 以逗号分隔的“ host：port”对列表，从中进行引导。这表示群集节点的“初始”列表，并且要求至少具有一个条目。
         */
        private List<String> nodes;

        /**
         * 在整个集群中执行命令时要遵循的最大重定向数。
         */
        private Integer maxRedirects;

        /**
         * 关机超时。
         */
        private Duration shutdownTimeout = Duration.ofMillis(100);

        private final Cluster.Refresh refresh = new Cluster.Refresh();

        @Data
        public static class Refresh {

            /**
             * 是否发现和查询所有集群节点以获取集群拓扑。设置为false时，仅将初始种子节点用作拓扑发现的源。
             */
            private boolean dynamicRefreshSources = true;

            /**
             * 集群拓扑刷新周期。
             */
            private Duration period;

            /**
             * 是否应该使用使用所有可用刷新触发器的自适应拓扑刷新。
             */
            private boolean adaptive;

            public boolean isDynamicRefreshSources() {
                return this.dynamicRefreshSources;
            }

            public boolean isAdaptive() {
                return this.adaptive;
            }

        }

    }

    /**
     * Redis哨兵属性
     */
    @Data
    public static class Sentinel {

        /**
         * Redis服务器名称
         */
        private String master;

        /**
         * 以逗号分隔的“ host：port”对列表。
         */
        private List<String> nodes;

        /**
         * 用于使用哨兵进行身份验证的密码。
         */
        private String password;

    }
}
 
