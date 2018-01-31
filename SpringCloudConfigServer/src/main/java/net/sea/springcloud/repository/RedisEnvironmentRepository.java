package net.sea.springcloud.repository;

import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
//import org.springframework.jdbc.core.JdbcTemplate;

@ConfigurationProperties(prefix = "spring.cloud.config.server.redis")
@Profile("jdbc-redis")
public class RedisEnvironmentRepository implements EnvironmentRepository, Ordered {
    private Logger logger = Logger.getLogger(getClass());
    private String host;
//    private JdbcTemplate jdbcTemplate;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public Environment findOne(String application, String profile, String label) {
        logger.info("redis "+host);
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
