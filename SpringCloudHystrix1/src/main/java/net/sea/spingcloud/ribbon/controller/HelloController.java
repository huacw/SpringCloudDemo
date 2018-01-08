package net.sea.spingcloud.ribbon.controller;

import net.sea.spingcloud.ribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mtime on 2018/1/3.
 */
@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @RequestMapping("/ribbon/hi")
    public String hi(@RequestParam String name){
        return helloService.hiService(name);
    }
}
