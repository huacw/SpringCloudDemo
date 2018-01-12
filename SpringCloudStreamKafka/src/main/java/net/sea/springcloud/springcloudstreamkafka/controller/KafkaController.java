package net.sea.springcloud.springcloudstreamkafka.controller;

import net.sea.springcloud.springcloudstreamkafka.output.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaSender kafkaSender;

    @RequestMapping("/hi")
    public void hi(String msg) {
        kafkaSender.sendMessage(msg);
    }
}
