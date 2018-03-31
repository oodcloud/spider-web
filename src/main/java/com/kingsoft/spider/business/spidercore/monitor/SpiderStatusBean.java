package com.kingsoft.spider.business.spidercore.monitor;

import us.codecraft.webmagic.monitor.SpiderStatusMXBean;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by wangyujie on 2018/2/10.
 */
public interface SpiderStatusBean extends SpiderStatusMXBean {
    List<String> getSucceePages();
    List<String> getAllResultList();
    String getUUid();
}
