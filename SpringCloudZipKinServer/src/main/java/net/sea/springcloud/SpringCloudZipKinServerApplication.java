package net.sea.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class SpringCloudZipKinServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudZipKinServerApplication.class, args);
	}
}
