package net.sea.springcloud.router.impl.redis;

import net.sea.springcloud.router.ZuulRouteEntity;
import net.sea.springcloud.router.ZuulRouteLocator;
import net.sea.springcloud.router.ZuulRouteRuleEntity;
import net.sea.springcloud.router.rule.IZuulRouteRule;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sea on 2019/5/9.
 */
public class ZuulRouteRedisLocator extends ZuulRouteLocator {
    public final static Logger logger = Logger.getLogger(ZuulRouteRedisLocator.class);

    @Autowired
    private RedisTemplate<String, ZuulRouteEntity> redisTemplate;

    @Autowired
    private ZuulRouteRedisProperties zuulRouteRedisProperties;

    private List<ZuulRouteEntity> locateRouteList;

    public ZuulRouteRedisLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
    }

    @Override
    public Map<String, ZuulProperties.ZuulRoute> loadLocateRoute() {
        List<Object> redisResult = new ArrayList<Object>();
        locateRouteList = new ArrayList<>();
        try {
            redisResult = redisTemplate.opsForHash().values(zuulRouteRedisProperties.getNamespace());
            if (!CollectionUtils.isEmpty(redisResult)) {
                for (Object item : redisResult) {
                    ZuulRouteEntity routeEntity = (ZuulRouteEntity) item;
                    if(!routeEntity.isEnable()){
                        continue;
                    }
                    List<Object> ruleResultList = redisTemplate.opsForHash().values(zuulRouteRedisProperties.getNamespace() + "_" + routeEntity.getId());
                    routeEntity.setRuleList(new ArrayList<IZuulRouteRule>());
                    if(!CollectionUtils.isEmpty(ruleResultList)){
                        for(Object ruleItem : ruleResultList){
                            ZuulRouteRuleEntity ruleEntity = (ZuulRouteRuleEntity) ruleItem;
                            if(!ruleEntity.isEnable()){
                                continue;
                            }
                            routeEntity.getRuleList().add(ruleEntity);
                        }
                    }
                    locateRouteList.add(routeEntity);
                }
            }
        } catch (Exception e) {
            logger.error("load zuul route from redis exception", e);
        }
        return handle(locateRouteList);
    }

    @Override
    public List<IZuulRouteRule> getRules(Route route) {
        if(CollectionUtils.isEmpty(locateRouteList)){
            return null;
        }
        for(ZuulRouteEntity item : locateRouteList){
            if(item.getId().equals(route.getId())){
                return item.getRuleList();
            }
        }
        return null;
    }

}
