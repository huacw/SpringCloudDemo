package net.sea.springcloud.client;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sea.springcloud.model.Goods;
import net.sea.springcloud.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
@EnableEurekaClient
@RestController
public class SpringCloudClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudClientApplication.class, args);
    }

    @Value("${server.port}")
    String port;

    @ApiOperation(value = "springcloud测试接口", notes = "springcloud测试接口")
    //paramType表示参数的类型，可选的值为"path","body","query","header","form"
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String", paramType = "query")})
    @RequestMapping(value = "/hi", method = {RequestMethod.POST, RequestMethod.GET})
    public String home(@RequestParam String name) {
        return "hi " + name + ",i am from port:" + port;
    }

    @RequestMapping(value = "/getOrder", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Order getOrder(){
        Order order = new Order();
        order.setOrderId(1);
        order.setProductName("商品1");
        order.setCreateTime(LocalDateTime.now());
        Goods goods = new Goods();
        goods.setName("卖品1");
        goods.setPrice(BigDecimal.valueOf(50));
        goods.setExpireDate(LocalDateTime.now().plusDays(180));
        order.setGoods(Arrays.asList(goods));
        return order;
    }

    @RequestMapping(value = "/getOrders", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public List<Order> getOrders(){
        return Arrays.asList(getOrder());
    }
}
