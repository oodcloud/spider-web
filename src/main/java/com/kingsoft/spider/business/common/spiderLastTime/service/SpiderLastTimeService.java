package com.kingsoft.spider.business.common.spiderLastTime.service;

/**
 * Created by wangyujie on 2018/1/29.
 */
public interface SpiderLastTimeService {
    Long selectLastTime(String name);
    void updateLastTime(String name,Long time);
}
