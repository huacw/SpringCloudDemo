package net.sea.springcloud.router.impl.zk;

import net.sea.springcloud.router.refresh.RefreshRouteService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * Created by sea on 2019/5/9.
 */
public class ZookeeperTreeCacheListener {
    private final Logger logger = Logger.getLogger(ZookeeperTreeCacheListener.class);

    @Autowired
    private CuratorFrameworkClient curatorFrameworkClient;

    @Resource
    private RefreshRouteService refreshRouteService;

    public void init(){
        TreeCache treeCache = curatorFrameworkClient.getTreeCache();
        treeCache.getListenable().addListener(new TreeCacheListener() {

            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                logger.info("tree cache listener");
                refreshRouteService.refreshRoute();
            }
        });
    }

}
