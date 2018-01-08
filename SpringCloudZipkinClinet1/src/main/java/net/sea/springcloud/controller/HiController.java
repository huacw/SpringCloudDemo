package net.sea.springcloud.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Mtime on 2018/1/8.
 */
@RestController
public class HiController {
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @RequestMapping("/hi")
    public String callHome(){
        logger.info("calling trace service-hi  ");
        return restTemplate.getForObject("http://localhost:9413/miya", String.class);
    }

    @RequestMapping("/info")
    public String info(){
        logger.info("calling trace service-hi ");
        return "i'm service-hi";
    }

    /**
     * 采集器
     *
     * @return
     */
    @Bean
    public AlwaysSampler defaultSampler(){
        return new AlwaysSampler();
    }

}
