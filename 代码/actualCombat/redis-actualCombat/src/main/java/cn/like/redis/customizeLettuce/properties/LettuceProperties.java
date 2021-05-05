package cn.like.redis.customizeLettuce.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.Duration;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "lettuce")
@EnableConfigurationProperties
public class LettuceProperties {

    /**
     * Database index used by the connection factory.
     */
    private int database = 0;

    /**
     * Connection URL. Overrides host, port, and password. User is ignored. Example:
     * redis://user:password@example.com:6379
     */
    private String url;

    /**
     * Redis server host.
     */
    private String host = "localhost";

    /**
     * Login username of the redis server.
     */
    private String username;

    /**
     * Login password of the redis server.
     */
    private String password;

    /**
     * Redis server port.
     */
    private int port = 6379;

    /**
     * Whether to enable SSL support.
     */
    private boolean ssl;

    /**
     * Read timeout.
     */
    private Duration timeout;

    /**
     * Connection timeout.
     */
    private Duration connectTimeout;

    /**
     * Client name to be set on connections with CLIENT SETNAME.
     */
    private String clientName;

    private Sentinel sentinel;

    private Cluster cluster;

    private final Lettuce lettuce = new Lettuce();

    /**
     * Pool properties.
     */
    @Data
    public static class Pool {

        /**
         * Maximum number of "idle" connections in the pool. Use a negative value to
         * indicate an unlimited number of idle connections.
         */
        private int maxIdle = 8;

        /**
         * Target for the minimum number of idle connections to maintain in the pool. This
         * setting only has an effect if both it and time between eviction runs are
         * positive.
         */
        private int minIdle = 0;

        /**
         * Maximum number of connections that can be allocated by the pool at a given
         * time. Use a negative value for no limit.
         */
        private int maxActive = 8;

        /**
         * Maximum amount of time a connection allocation should block before throwing an
         * exception when the pool is exhausted. Use a negative value to block
         * indefinitely.
         */
        private Duration maxWait = Duration.ofMillis(-1);

        /**
         * Time between runs of the idle object evictor thread. When positive, the idle
         * object evictor thread starts, otherwise no idle object eviction is performed.
         */
        private Duration timeBetweenEvictionRuns;
    }

    /**
     * Cluster properties.
     */
    @Data
    public static class Cluster {

        /**
         * Comma-separated list of "host:port" pairs to bootstrap from. This represents an
         * "initial" list of cluster nodes and is required to have at least one entry.
         */
        private List<String> nodes;

        /**
         * Maximum number of redirects to follow when executing commands across the
         * cluster.
         */
        private Integer maxRedirects;


    }

    /**
     * Redis sentinel properties.
     */
    @Data
    public static class Sentinel {

        /**
         * Name of the Redis server.
         */
        private String master;

        /**
         * Comma-separated list of "host:port" pairs.
         */
        private List<String> nodes;

        /**
         * Password for authenticating with sentinel(s).
         */
        private String password;

    }
    

    /**
     * Lettuce client properties.
     */
    @Data
    public static class Lettuce {

        /**
         * Shutdown timeout.
         */
        private Duration shutdownTimeout = Duration.ofMillis(100);

        /**
         * Lettuce pool configuration.
         */
        private Pool pool;

        private final Lettuce.Cluster cluster = new Lettuce.Cluster();

        public static class Cluster {

            private final Lettuce.Cluster.Refresh refresh = new Lettuce.Cluster.Refresh();

            public static class Refresh {

                /**
                 * Whether to discover and query all cluster nodes for obtaining the
                 * cluster topology. When set to false, only the initial seed nodes are
                 * used as sources for topology discovery.
                 */
                private boolean dynamicRefreshSources = true;

                /**
                 * Cluster topology refresh period.
                 */
                private Duration period;

                /**
                 * Whether adaptive topology refreshing using all available refresh
                 * triggers should be used.
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
    }
}
 
