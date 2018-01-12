package net.sea.springcloud.springcloudstreamkafka.input;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Sink {
    String INPUT_1 = "testa";

    @Input(INPUT_1)
    SubscribableChannel input1();
}
