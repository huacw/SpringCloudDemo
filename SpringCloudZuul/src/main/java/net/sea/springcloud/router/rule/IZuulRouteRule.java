package net.sea.springcloud.router.rule;

import org.springframework.core.Ordered;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by sea on 2019/5/9.
 */
public interface IZuulRouteRule extends Ordered, Serializable {
    boolean match(Map<String, Object> params);

    String getLocation();
}
