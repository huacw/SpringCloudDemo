package net.sea.springcloud.repository;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jdbc-redis")
@Import(RedisEnvironmentRepository.class)
public class RedisConfig {
}
