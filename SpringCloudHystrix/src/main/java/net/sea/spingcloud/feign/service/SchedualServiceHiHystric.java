package net.sea.spingcloud.feign.service;

import org.springframework.stereotype.Component;

/**
 * Created by Mtime on 2018/1/3.
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi{
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }

}
