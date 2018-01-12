package net.sea.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.endpoint.BusEndpoint;
import org.springframework.cloud.bus.endpoint.RefreshBusEndpoint;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.kafka.support.LoggingProducerListener;

@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class SpringCloudKafkaBusServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudKafkaBusServerApplication.class, args);
	}
}
