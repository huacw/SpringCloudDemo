package net.sea.springcloud.controller;

import feign.Feign;
import net.sea.springcloud.service.SchedualServiceHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mtime on 2018/1/3.
 */
@RestController
public class HiController {
    @Autowired
    private SchedualServiceHi schedualServiceHi;

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String sayHi(String name){
        System.out.println("feign响应");
        return schedualServiceHi.sayHiFromClientOne(name);
    }
}
