package net.sea.springcloud.router.impl.db;

import net.sea.springcloud.router.refresh.AutoRefreshRouteJob;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;


/**
 * Created by sea on 2019/5/9.
 */
@Configuration
@EnableScheduling
@ConditionalOnBean(DataSource.class)
@EnableConfigurationProperties(ZuulRouteDatabaseProperties.class)
public class ZuulRouteDatabaseAutoConfiguration {
    @Autowired
    private ZuulProperties zuulProperties;

    @Autowired
    private ServerProperties server;

    @Autowired
    private DataSource dataSource;

    @Bean
    @ConditionalOnMissingBean(JdbcTemplate.class)
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @ConditionalOnBean(JdbcTemplate.class)
    @ConditionalOnMissingBean(ZuulRouteDatabaseLocator.class)
    public ZuulRouteDatabaseLocator zuulRouteDatabaseLocator() {
        return new ZuulRouteDatabaseLocator(this.server.getServletPrefix(), this.zuulProperties);
    }

    @Bean
    public RefreshRouteService refreshRouteService() {
        return new RefreshRouteService();
    }

    @Bean
    public AutoRefreshRouteJob autoRefreshRouteJob() {
        return new AutoRefreshRouteJob();
    }

    @Bean
    @ConditionalOnMissingBean(IZuulRouteRuleMatcher.class)
    public IZuulRouteRuleMatcher zuulRouteRuleMatcher() {
        return new DefaultZuulRouteRuleMatcher();
    }

}
