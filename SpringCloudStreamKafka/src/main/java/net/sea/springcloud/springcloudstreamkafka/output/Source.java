package net.sea.springcloud.springcloudstreamkafka.output;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Source {
    String OUTPUT_1 = "sourceA";

    @Output(OUTPUT_1)
    MessageChannel output1();
}
