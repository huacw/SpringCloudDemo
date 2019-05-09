package net.sea.springcloud.router.refresh;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * Created by sea on 2019/5/9.
 */
public class AutoRefreshRouteJob {
    private final Logger logger = Logger.getLogger(AutoRefreshRouteJob.class);

    @Resource
    private RefreshRouteService refreshRouteService;

    @Scheduled(cron = "${spring.zuul.route.refreshCron:0/30 * * * * ?}")
    public void run() {
        logger.info("refresh zuul route config");
        refreshRouteService.refreshRoute();
    }

}
