package net.sea.springcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class SpringCloudconfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudconfigClientApplication.class, args);
    }

    @Value("${key}")
    String key;

    @RequestMapping(value = "/hi")
    public String hi() {
        return key;
    }

}
