package net.sea.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mtime on 2018/1/8.
 */
@RefreshScope
@RestController
@KafkaListener(topics = "springCloudBus")
public class ConfigController {
    @Value("${key}")
    private String key;

    @EventListener
    public void setKey(String key) {
        this.key = key;
    }

    @RequestMapping("/hi")
    public String sayHi(){
        return key;
    }
}
