package net.sea.springcloud.router.rule;

import org.springframework.cloud.netflix.zuul.filters.Route;

import java.util.List;

/**
 * Created by sea on 2019/5/9.
 */
public interface IZuulRouteRuleMatcher {
    public Route matchingRule(Route route, List<IZuulRouteRule> rules);
}
