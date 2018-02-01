package com.kingsoft.spider.business.common.spiderLastTime.dto;

/**
 * Created by wangyujie on 2018/1/29.
 */
public class SpiderLastTimeDto {
    private String spiderName;
    private Long lastTime;

    public String getSpiderName() {
        return spiderName;
    }

    public void setSpiderName(String spiderName) {
        this.spiderName = spiderName;
    }

    public Long getLastTime() {
        return lastTime;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }
}
