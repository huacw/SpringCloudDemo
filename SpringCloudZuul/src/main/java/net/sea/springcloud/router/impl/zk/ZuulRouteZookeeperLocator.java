package net.sea.springcloud.router.impl.zk;

import com.alibaba.fastjson.JSON;
import net.sea.springcloud.router.ZuulRouteEntity;
import net.sea.springcloud.router.ZuulRouteLocator;
import net.sea.springcloud.router.ZuulRouteRuleEntity;
import net.sea.springcloud.router.rule.IZuulRouteRule;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sea on 2019/5/9.
 */
public class ZuulRouteZookeeperLocator extends ZuulRouteLocator {
    public final static Logger logger = Logger.getLogger(ZuulRouteZookeeperLocator.class);

    @Autowired
    private CuratorFrameworkClient curatorFrameworkClient;

    private List<ZuulRouteEntity> locateRouteList;

    public ZuulRouteZookeeperLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
    }

    @Override
    public Map<String, ZuulProperties.ZuulRoute> loadLocateRoute() {
        locateRouteList = new ArrayList<ZuulRouteEntity>();
        try {
            locateRouteList = new ArrayList<ZuulRouteEntity>();
            //获取所有路由配置的id
            List<String> keys = curatorFrameworkClient.getChildrenKeys("/");
            //遍历获取所有路由配置
            for(String item : keys){
                String value = curatorFrameworkClient.get("/" + item);
                if(!StringUtils.isEmpty(value)){
                    ZuulRouteEntity route = JSON.parseObject(value, ZuulRouteEntity.class);
                    //只需要启用的路由配置
                    if(!route.isEnable()){
                        continue;
                    }
                    route.setRuleList(new ArrayList<IZuulRouteRule>());
                    //获取路由配置对应的所有路由规则的ID
                    List<String> ruleKeys = curatorFrameworkClient.getChildrenKeys("/" + item);
                    //遍历获取所有的路由规则
                    for(String ruleKey : ruleKeys){
                        String ruleStr = curatorFrameworkClient.get("/" + item + "/" + ruleKey);
                        if(!StringUtils.isEmpty(ruleStr)){
                            ZuulRouteRuleEntity rule = JSON.parseObject(ruleStr, ZuulRouteRuleEntity.class);
                            //只保留可用的路由规则
                            if(!rule.isEnable()){
                                continue;
                            }
                            route.getRuleList().add(rule);
                        }
                    }
                    locateRouteList.add(route);
                }
            }
        } catch (Exception e) {
            logger.error("load zuul route from zk exception", e);
        }
        logger.info("load zuul route from zk : " + JSON.toJSONString(locateRouteList));
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
