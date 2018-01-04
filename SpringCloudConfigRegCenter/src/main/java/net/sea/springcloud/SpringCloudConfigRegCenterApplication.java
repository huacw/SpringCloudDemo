package net.sea.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringCloudConfigRegCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigRegCenterApplication.class, args);
	}
}
