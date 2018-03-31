package com.kingsoft.spider.business.spidercore.monitor;


import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangyujie on 2018/2/9.
 */
public class SpiderMonitorBootstrap {
    private static volatile SpiderMonitorBootstrap INSTANCE = new SpiderMonitorBootstrap();
    private List<SpiderStatusBean> spiderStatuses =Collections.synchronizedList( new ArrayList());

    protected SpiderMonitorBootstrap() {
    }

    public synchronized SpiderMonitorBootstrap register(Spider... spiders) {
        Spider[] var2 = spiders;
        int var3 = spiders.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Spider spider = var2[var4];
            MonitorSpiderListener monitorSpiderListener = new MonitorSpiderListener();
            if (spider.getSpiderListeners() == null) {
                List<SpiderListener> spiderListeners = new ArrayList();
                spiderListeners.add(monitorSpiderListener);
                spider.setSpiderListeners(spiderListeners);
            } else {
                spider.getSpiderListeners().add(monitorSpiderListener);
            }

            SpiderStatusBean spiderStatusBean = this.getSpiderStatusMBean(spider, monitorSpiderListener);
            this.spiderStatuses.add(spiderStatusBean);
        }

        return this;
    }

    public List<SpiderStatusBean> getSpiderStatuses() {
        return spiderStatuses;
    }

    protected SpiderStatusBean getSpiderStatusMBean(Spider spider, MonitorSpiderListener monitorSpiderListener) {
        return new SpiderStatus(spider, monitorSpiderListener);
    }

    public static SpiderMonitorBootstrap instance() {
        return INSTANCE;
    }



    public class MonitorSpiderListener implements SpiderListener {
        private final AtomicInteger successCount = new AtomicInteger(0);
        private final AtomicInteger errorCount = new AtomicInteger(0);
        private List<String> errorUrls = Collections.synchronizedList(new ArrayList());
        private List<String> successUrls = Collections.synchronizedList(new ArrayList());
        private List<String> allUrls=Collections.synchronizedList(new ArrayList());



        public MonitorSpiderListener() {

        }

        public void onSuccess(Request request) {
            this.successUrls.add(request.getUrl());
            this.successCount.incrementAndGet();
            this.allUrls.add(request.getUrl()+"成功");
        }

        public void onError(Request request) {
            this.errorUrls.add(request.getUrl());
            this.errorCount.incrementAndGet();
            this.allUrls.add(request.getUrl()+"失败");
        }

        public AtomicInteger getSuccessCount() {
            return this.successCount;
        }

        public AtomicInteger getErrorCount() {
            return this.errorCount;
        }

        public List<String> getErrorUrls() {
            return this.errorUrls;
        }

        public List<String> getSuccessUrls() {
            return successUrls;
        }

        public List<String> getAllUrls() {
            return allUrls;
        }
    }
}

