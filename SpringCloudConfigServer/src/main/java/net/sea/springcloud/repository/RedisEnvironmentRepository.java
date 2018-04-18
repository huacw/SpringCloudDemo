package net.sea.springcloud.repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;

import java.beans.PropertyVetoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
//import org.springframework.jdbc.core.JdbcTemplate;

@ConfigurationProperties(prefix = "spring.cloud.config.server.db.redis")
public class RedisEnvironmentRepository implements EnvironmentRepository, Ordered {
    private Logger logger = Logger.getLogger(getClass());
    private static final String DEFAULT_SQL = "SELECT KEY, VALUE from PROPERTIES where APPLICATION=? and PROFILE=? and LABEL=?";

    //redis配置
    private String host;
    private String masterName;
    private String password;

    //JDBC配置
    private String driverClass;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String sql;

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    private volatile static Object lock = new Object();
    //    private JdbcTemplate jdbcTemplate;
    private volatile static ShardedJedis sharding;
    private volatile static Jedis master;
    private volatile static ComboPooledDataSource dataSource;

    private void initShardedJedisPool() {
        if (sharding == null) {
            synchronized (lock) {
                if (sharding == null) {
                    logger.info("init redis");
                    List<JedisShardInfo> shards = new ArrayList<>();
                    if (StringUtils.isEmpty(host)) {
                        shards.add(new JedisShardInfo("localhost", 6379));
                    } else {
                        String[] hostStrs = host.split(",");
                        for (String hostStr : hostStrs) {
                            String[] hostCfgs = hostStr.split(":");
                            if (hostCfgs.length == 1) {
                                shards.add(new JedisShardInfo(hostCfgs[0]));
                            } else if (hostCfgs.length >= 2) {
                                shards.add(new JedisShardInfo(hostCfgs[0], Integer.parseInt(hostCfgs[1])));
                            }
                        }
                    }
                    sharding = new ShardedJedis(shards);
                    logger.info("sharding:" + sharding);
                }
            }
        }
    }

    private void initRedisSentinel() {
        if (master == null) {
            synchronized (lock) {
                if (master == null) {
                    //创建哨兵池
                    Set sentinels = new HashSet();
                    String[] hostStrs = host.split(",");
                    for (String hostStr : hostStrs) {
                        String[] hostCfgs = hostStr.split(":");
                        sentinels.add(new HostAndPort(hostCfgs[0], Integer.parseInt(hostCfgs[1])).toString());
                    }
                    JedisSentinelPool sentinelPool = new JedisSentinelPool(masterName,
                                                                           sentinels);
                    master = sentinelPool.getResource();
                }
            }
        }
    }

    private void initJDBCPool() throws PropertyVetoException {
        if (dataSource == null) {
            synchronized (lock) {
                if (dataSource == null) {
                    dataSource = new ComboPooledDataSource();
                    dataSource.setUser(dbUser);
                    dataSource.setPassword(dbPassword);
                    dataSource.setDriverClass(driverClass);
                    dataSource.setJdbcUrl(dbUrl);
                    dataSource.setMaxPoolSize(5);
                    logger.info("datasource:" + dataSource);
                }
            }
        }
    }

    private Map<String, String> queryConfig(String application, String profiles, String label) {
        Map<String, String> config = new HashMap<>();
        try {
            initJDBCPool();
            PreparedStatement pstmt = dataSource.getConnection().prepareStatement(StringUtils.isEmpty(sql) ? DEFAULT_SQL : sql);
            pstmt.setString(1, application);
            pstmt.setString(2, profiles);
            pstmt.setString(3, StringUtils.isEmpty(label) ? profiles : label);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
//                config.put("KEY", rs.getString("KEY"));
//                config.put("VALUE", rs.getString("VALUE"));
                config.put(rs.getString("KEY"),rs.getString("VALUE"));
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return config;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public Environment findOne(String application, String profiles, String label) {
        logger.info("findOne redis " + host);

        Environment environment = new Environment(application, profiles, label, (String) null, (String) null);
        logger.info("init environment,application:" + application + ",profiles:" + profiles + ",label:" + label);
        environment.add(new PropertySource(application + "_" + profiles, readConfig(application, profiles, label)));
        logger.info("query config");
        return environment;
    }

    private Map<String, String> readConfig(String application, String profiles, String label) {
        logger.info("readConfig");
//        initShardedJedisPool();
        initRedisSentinel();
        Map<String, String> cfgMap = readConfigFromRedis(application, profiles, label);
        if (cfgMap != null && (!cfgMap.isEmpty())) {
            return cfgMap;
        }
        return readConfigFromDB(application, profiles, label);
    }

    private Map<String, String> readConfigFromDB(String application, String profiles, String label) {
        logger.info("read config from db");
        Map<String, String> config = queryConfig(application, profiles, label);
        config.forEach((key, val) -> {
            String redisKey = application + "_" + profiles + "_" + label;
            master.hset(redisKey, key, val);
            master.expire(redisKey, 24 * 60 * 60);//配置一天有效
        });
        return config;
    }

    private Map<String, String> readConfigFromRedis(String application, String profiles, String label) {
        logger.info("read config from redis");
        Map<String, String> config = master.hgetAll(application + "_" + profiles + "_" + label);
        master.disconnect();
        return config;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
