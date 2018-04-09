package com.infoDiscover.infoAnalyse.service.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangychu on 4/27/17.
 */

@WebListener
public class ServiceResourceInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //DiscoverSpaceOperationUtil.refreshItemAliasNameCache();
        //refresh ItemAliasName Cache by each 30 minuter
        Timer refreshItemAliasNameCacheTimer=new java.util.Timer();
        TimerTask refreshItemAliasNameCacheTimerTask=new TimerTask(){
            @Override
            public void run() {
                DiscoverSpaceOperationUtil.refreshItemAliasNameCache();
            }
        };
        refreshItemAliasNameCacheTimer.schedule(refreshItemAliasNameCacheTimerTask, 0, 30*1000*60);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        DiscoverSpaceOperationUtil.clearItemAliasNameCache();
    }
}
