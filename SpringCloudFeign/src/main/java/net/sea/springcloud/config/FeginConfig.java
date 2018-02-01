package net.sea.springcloud.config;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeginConfig {
    private Retryer retryer;

    @Bean
    public Retryer feginRetryer(){
        return new Retryer.Default(100, 10000, 3);
    }

    @Bean
    public Request.Options feginOption(){
        return new Request.Options(7000,7000);
    }
}
