package net.sea.springcloud.springcloudstreamkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class SpringCloudStreamKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamKafkaApplication.class, args);
	}
}
