package net.sea.springcloud.router;

import net.sea.springcloud.router.rule.IZuulRouteRule;

import java.util.Map;

/**
 * Created by sea on 2019/5/9.
 */
public class ZuulRouteRuleEntity implements IZuulRouteRule {
    private String id;
    private String routeId;
    private String rule;
    private String expectedResult;
    private String location;
    private boolean enable;
    private int sortNum;
    @Override
    public boolean match(Map<String, Object> params) {
        return false;
    }

//    @Override
//    public String getLocation() {
//        return null;
//    }
//
    @Override
    public int getOrder() {
        return 0;
    }
//
//    public boolean isEnable(){
//        return true;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }
}
