package com.kingsoft.spider.business.generic.config.dto;

/**
 * Created by wangyujie on 2018/1/16.
 */
public class CommonUrlTestRequest {
    private String commonUrl;
    private Integer growthPattern;
    private Integer urlRule;
    private Integer startNum;
    private Integer endNum;

    public String getCommonUrl() {
        return commonUrl;
    }

    public void setCommonUrl(String commonUrl) {
        this.commonUrl = commonUrl;
    }

    public Integer getGrowthPattern() {
        return growthPattern;
    }

    public void setGrowthPattern(Integer growthPattern) {
        this.growthPattern = growthPattern;
    }

    public Integer getUrlRule() {
        return urlRule;
    }

    public void setUrlRule(Integer urlRule) {
        this.urlRule = urlRule;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getEndNum() {
        return endNum;
    }

    public void setEndNum(Integer endNum) {
        this.endNum = endNum;
    }
}
