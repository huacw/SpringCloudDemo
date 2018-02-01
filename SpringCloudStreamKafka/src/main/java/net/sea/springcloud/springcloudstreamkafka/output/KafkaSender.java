package net.sea.springcloud.springcloudstreamkafka.output;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(Source.class)
public class KafkaSender {
    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private Source source;

    public void sendMessage(String message) {
        try {
            logger.info("发送的消息为：" + message);
            source.output1().send(MessageBuilder.withPayload(message).build());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("消息发送失败，原因：" + e);
        }
    }
}