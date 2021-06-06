package cn.like.code.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

/**
 * desc: redis 配置信息
 * details:
 *
 * @author: like
 * @since: 2021/6/6 11:18
 * @email: 980650920@qq.com
 */
@ConfigurationProperties(value = "redis")
public class LettuceConfigProperties {

    /**
     * fire:
     * FALSE - Stand-alone
     * TURE  - Cluster
     */
    private Boolean fire = Boolean.FALSE;

    /** Redis默认库，一共0～15. */
    private Integer database = 0;

    /** 在与CLIENT SETNAME的连接上设置的客户端名称 */
    private String clientName;

    /** 登录用的用户名. */
    private String username;

    /** Redis密码. */
    private String password;

    /** 是否需要SSL. */
    private Boolean ssl;

    /** 连接超时 时间 lettuce 60s */
    private Duration timeout;


    // ====================  stand-alone
    /** Redis服务器地址. */
    private String host = "localhost";

    /** Redis服务端口. */
    private Integer port = 6379;


    // ====================  cluster
    private Cluster Cluster = new Cluster();


    // =================== pool
    private Pool pool = new Pool();


    public static class Cluster {

        /** cluster 节点. */
        private List<String> nodes;

        /** cluster 最大重定向次数. */
        private Integer maxRedirects = 5;

        /** 自动重新连接 */
        private boolean autoReconnect = true;

        // =================== getter / setter

        public List<String> getNodes() {
            return nodes;
        }

        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }

        public Integer getMaxRedirects() {
            return maxRedirects;
        }

        public void setMaxRedirects(Integer maxRedirects) {
            this.maxRedirects = maxRedirects;
        }

        public boolean isAutoReconnect() {
            return autoReconnect;
        }

        public void setAutoReconnect(boolean autoReconnect) {
            this.autoReconnect = autoReconnect;
        }
    }

    public static class Pool {


        /** 设置可分配的最大Redis实例数量. */
        private Integer maxTotal = 8;

        /** 设置最多空闲的Redis实例数量. */
        private Integer maxIdle = 8;

        /** 设置最多空闲的Redis实例数量. */
        private Integer minIdle = 0;

        /** 当池耗尽时，在抛出异常之前连接分配应该阻塞的最长时间。使用负值无限期阻止。 */
        private Duration maxWait = Duration.ofMillis(-1);

        /** 空闲对象退出线程的运行之间的时间。当为正时，空闲对象逐出线程启动，否则不执行空闲对象逐出。 */
        private Duration timeBetweenEvictionRuns;

        /** 归还Redis实例时，检查有消息，如果失败，则销毁实例 */
        private Boolean testOnReturn = true;

        /** 当Redis实例处于空闲时检查有效性，默认false */
        private Boolean testWhileIdle;

        // =================== getter / setter

        public Integer getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(Integer maxTotal) {
            this.maxTotal = maxTotal;
        }

        public Integer getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(Integer maxIdle) {
            this.maxIdle = maxIdle;
        }

        public Integer getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(Integer minIdle) {
            this.minIdle = minIdle;
        }

        public Boolean getTestOnReturn() {
            return testOnReturn;
        }

        public void setTestOnReturn(Boolean testOnReturn) {
            this.testOnReturn = testOnReturn;
        }

        public Duration getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(Duration maxWait) {
            this.maxWait = maxWait;
        }

        public Duration getTimeBetweenEvictionRuns() {
            return timeBetweenEvictionRuns;
        }

        public void setTimeBetweenEvictionRuns(Duration timeBetweenEvictionRuns) {
            this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
        }

        public Boolean getTestWhileIdle() {
            return testWhileIdle;
        }

        public void setTestWhileIdle(Boolean testWhileIdle) {
            this.testWhileIdle = testWhileIdle;
        }
    }

    // =================== getter / setter

    public Boolean getFire() {
        return fire;
    }

    public void setFire(Boolean fire) {
        this.fire = fire;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public LettuceConfigProperties.Cluster getCluster() {
        return Cluster;
    }

    public void setCluster(LettuceConfigProperties.Cluster cluster) {
        Cluster = cluster;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }
}
