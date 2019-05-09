package net.sea.springcloud.router.rule;

import org.apache.log4j.Logger;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by sea on 2019/5/9.
 */
public class DefaultZuulRouteRuleMatcher implements IZuulRouteRuleMatcher {
    private final Logger logger = Logger.getLogger(DefaultZuulRouteRuleMatcher.class);

    @Resource
    private HttpServletRequest request;

    @Override
    public Route matchingRule(Route route, List<IZuulRouteRule> rules) {
        if(route == null || CollectionUtils.isEmpty(rules)){
            return route;
        }

        rules.sort(new Comparator<IZuulRouteRule>() {
            @Override
            public int compare(IZuulRouteRule rule1, IZuulRouteRule rule2) {
                return rule1.getOrder() - rule2.getOrder();
            }
        });

        Map<String, String[]> requestParams = request.getParameterMap();

        Map<String, Object> params = new HashMap<String, Object>();
        for(String key : requestParams.keySet()){
            params.put(key, requestParams.get(key)[0]);
        }
        for(IZuulRouteRule rule : rules){
            if(!rule.match(params)){
                logger.info("路由规则不匹配");
                continue;
            }
            if(!StringUtils.isEmpty(rule.getLocation())){
                route.setLocation(rule.getLocation());
            }
            return route;
        }
        return null;
    }


}
