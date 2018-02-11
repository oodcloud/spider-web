package com.kingsoft.spider.business.spidercore.monitor;


import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;


import us.codecraft.webmagic.scheduler.MonitorableScheduler;

public class SpiderStatus implements SpiderStatusBean {
    protected final Spider spider;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final SpiderMonitorBootstrap.MonitorSpiderListener monitorSpiderListener;

    public SpiderStatus(Spider spider, SpiderMonitorBootstrap.MonitorSpiderListener monitorSpiderListener) {
        this.spider = spider;
        this.monitorSpiderListener = monitorSpiderListener;
    }

    public String getName() {
        return this.spider.getUUID();
    }

    public int getLeftPageCount() {
        if(this.spider.getScheduler() instanceof MonitorableScheduler) {
            return ((MonitorableScheduler)this.spider.getScheduler()).getLeftRequestsCount(this.spider);
        } else {
            this.logger.warn("Get leftPageCount fail, try to use a Scheduler implement MonitorableScheduler for monitor count!");
            return -1;
        }
    }

    public int getTotalPageCount() {
        if(this.spider.getScheduler() instanceof MonitorableScheduler) {
            return ((MonitorableScheduler)this.spider.getScheduler()).getTotalRequestsCount(this.spider);

        } else {
            this.logger.warn("Get totalPageCount fail, try to use a Scheduler implement MonitorableScheduler for monitor count!");
            return -1;
        }
    }

    public int getSuccessPageCount() {
        return this.monitorSpiderListener.getSuccessCount().get();
    }

    public int getErrorPageCount() {
        return this.monitorSpiderListener.getErrorCount().get();
    }

    public List<String> getErrorPages() {
        return this.monitorSpiderListener.getErrorUrls();
    }

    public String getStatus() {
        return this.spider.getStatus().name();
    }

    public int getThread() {
        return this.spider.getThreadAlive();
    }

    public void start() {
        this.spider.start();
    }

    public void stop() {
        this.spider.stop();
    }

    public Date getStartTime() {
        return this.spider.getStartTime();
    }

    public int getPagePerSecond() {
        int runSeconds = (int)(System.currentTimeMillis() - this.getStartTime().getTime()) / 1000;
        return this.getSuccessPageCount() / runSeconds;
    }

    @Override
    public List<String> getSucceePages() {
        return monitorSpiderListener.getSuccessUrls();
    }

    @Override
    public List<String> getAllResultList() {
        return monitorSpiderListener.getAllUrls();
    }
}
