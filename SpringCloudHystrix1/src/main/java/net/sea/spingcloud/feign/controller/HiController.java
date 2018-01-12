package net.sea.spingcloud.feign.controller;

import net.sea.spingcloud.feign.service.SchedualServiceHi;
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

    @RequestMapping(value = "/feign1/hi",method = RequestMethod.GET)
    public String sayHi(String name){
        return schedualServiceHi.sayHiFromClientOne(name);
    }
}
