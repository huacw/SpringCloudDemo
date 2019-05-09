package net.sea.springcloud.router.impl.zk;

import net.sea.springcloud.router.refresh.RefreshRouteService;
import net.sea.springcloud.router.rule.DefaultZuulRouteRuleMatcher;
import net.sea.springcloud.router.rule.IZuulRouteRuleMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by sea on 2019/5/9.
 */
//@Configuration
//@EnableConfigurationProperties(ZuulRouteZookeeperProperties.class)
public class ZuulRouteZookeeperAutoConfiguration {
    @Autowired
    private ZuulProperties zuulProperties;

    @Autowired
    private ServerProperties server;

    @Bean(initMethod = "init")
    public CuratorFrameworkClient curatorFrameworkClient(){
        return new CuratorFrameworkClient();
    }

    @Bean(initMethod = "init")
    public ZookeeperTreeCacheListener zookeeperTreeCacheListener(){
        return new ZookeeperTreeCacheListener();
    }

    @Bean
    @ConditionalOnMissingBean(ZuulRouteZookeeperLocator.class)
    public ZuulRouteZookeeperLocator zuulZookeeperRouteLocator() {
        return new ZuulRouteZookeeperLocator(this.server.getServletPrefix(), this.zuulProperties);
    }

    @Bean
    public RefreshRouteService refreshRouteService(){
        return new RefreshRouteService();
    }

//	@Bean
//	public AutoRefreshRouteJob autoRefreshRouteJob(){
//		return new AutoRefreshRouteJob();
//	}

    @Bean
    @ConditionalOnMissingBean(IZuulRouteRuleMatcher.class)
    public IZuulRouteRuleMatcher zuulRouteRuleMatcher(){
        return new DefaultZuulRouteRuleMatcher();
    }

}
