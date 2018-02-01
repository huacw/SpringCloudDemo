package net.sea.springcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Mtime on 2018/1/3.
 */
@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    public String hiService(String name) {
//        Map<String,Object> params = new HashMap<>();
//        params.put("name",name);
//        String response = restTemplate.getForObject("http://SERVICE-HI/hi?name={name}", String.class, params);
//        System.out.println(response);
//        System.out.println(restTemplate.postForObject("http://SERVICE-HI/hi?name={name}",null,String.class,name));
        return restTemplate.getForEntity("http://SERVICE-HI/hi?name={name}", String.class, name).getBody();
//        return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }
}
