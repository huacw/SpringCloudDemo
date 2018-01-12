package net.sea.springcloud.springcloudstreamkafka.input;

import org.apache.log4j.Logger;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(Sink.class)
public class KafkaReceiver {
    private final Logger logger = Logger.getLogger(getClass());

    @StreamListener(Sink.INPUT_1)
    private void receive(String message) {
        logger.info("receive message : " + message);
    }
}
