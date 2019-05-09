package net.sea.springcloud.router.impl.db;

import net.sea.springcloud.router.ZuulRouteEntity;
import net.sea.springcloud.router.ZuulRouteLocator;
import net.sea.springcloud.router.ZuulRouteRuleEntity;
import net.sea.springcloud.router.rule.IZuulRouteRule;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sea on 2019/5/9.
 */
public class ZuulRouteDatabaseLocator extends ZuulRouteLocator {
    public final static Logger logger = Logger.getLogger(ZuulRouteDatabaseLocator.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ZuulRouteDatabaseProperties zuulRouteDatabaseProperties;

    private List<ZuulRouteEntity> locateRouteList;

    public ZuulRouteDatabaseLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
    }

    @Override
    public Map<String, ZuulProperties.ZuulRoute> loadLocateRoute() {
        locateRouteList = new ArrayList<>();
        try {
            String sql = "select * from " + zuulRouteDatabaseProperties.getTableName() + " where enable = true";
            locateRouteList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ZuulRouteEntity.class));
            if(!CollectionUtils.isEmpty(locateRouteList)){
                for(ZuulRouteEntity zuulRoute : locateRouteList){
                    sql = "select * from " + zuulRouteDatabaseProperties.getRuleTableName() + " where enable = true and route_id = ?";
                    List<ZuulRouteRuleEntity> ruleList = jdbcTemplate.query(sql, new String[]{zuulRoute.getId()}, new BeanPropertyRowMapper<>(ZuulRouteRuleEntity.class));
                    zuulRoute.setRuleList(new ArrayList<IZuulRouteRule>());
                    if(!CollectionUtils.isEmpty(ruleList)){
                        for(ZuulRouteRuleEntity rule : ruleList){
                            zuulRoute.getRuleList().add(rule);
                        }
                    }
                }
            }
        } catch (DataAccessException e) {
            logger.error("load zuul route from db exception", e);
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
