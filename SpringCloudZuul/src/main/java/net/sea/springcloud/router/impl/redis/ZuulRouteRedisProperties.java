package net.sea.springcloud.router.impl.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by sea on 2019/5/9.
 */
@ConfigurationProperties(prefix="spring.zuul.route.redis")
public class ZuulRouteRedisProperties {
    private String namespace = "zuul_route_config";

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

}
