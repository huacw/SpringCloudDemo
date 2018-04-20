package net.sea.springcloud.service;

import net.sea.springcloud.config.FeginConfig;
import net.sea.springcloud.model.Order;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Mtime on 2018/1/3.
 */
@FeignClient(value = "service-hi",configuration = FeginConfig.class)
public interface SchedualServiceHi {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);

    @RequestMapping(value = "/getOrder")
    Order getOrder();

    @RequestMapping(value = "/getOrders")
    List<Order> getOrders();
}
