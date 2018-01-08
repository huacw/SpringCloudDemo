package net.sea.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
//@EnableEurekaClient
public class SpringCloudKafkaBusClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudKafkaBusClientApplication.class, args);
	}
}
