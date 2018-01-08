package net.sea.springcloud.kafka;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;

/**
 * Created by Mtime on 2018/1/4.
 */
@EnableBinding(Source.class)
public class KafkaSender {
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private Source source;

    public void sendMessage(String message) {
        try {
            logger.info("message:" + message);
            source.output1().send(MessageBuilder.withPayload("message:" + message).build());
        } catch (Exception e) {
            logger.info("消息发送失败，原因：" + e);
            e.printStackTrace();
        }
    }
}
