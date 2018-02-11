package com.kingsoft.spider.business.spidercore.common;

/**
 * Created by wangyujie on 2018/1/23.
 */
public class SpiderConfigEntity {
    private String commonUrl;
    private Integer urlRule;
    private Integer growthPattern;
    private Integer startNum;
    private Integer endNum;
    private String groupName;
    private String itemName;
    private String siteName;
    private String domain;
    private String charset;
    private String userAgent;
    private String cookies;
    private Integer sleepTime;
    private Integer timeOut;
    private Integer thread;
    private String matchFields;
    private String headers;
    private Long generatedTime;

    public Long getGeneratedTime() {
        return generatedTime;
    }

    public void setGeneratedTime(Long generatedTime) {
        this.generatedTime = generatedTime;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getCommonUrl() {
        return commonUrl;
    }

    public void setCommonUrl(String commonUrl) {
        this.commonUrl = commonUrl;
    }

    public Integer getUrlRule() {
        return urlRule;
    }

    public void setUrlRule(Integer urlRule) {
        this.urlRule = urlRule;
    }

    public Integer getGrowthPattern() {
        return growthPattern;
    }

    public void setGrowthPattern(Integer growthPattern) {
        this.growthPattern = growthPattern;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Integer sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public Integer getThread() {
        return thread;
    }

    public void setThread(Integer thread) {
        this.thread = thread;
    }

    public String getMatchFields() {
        return matchFields;
    }

    public void setMatchFields(String matchFields) {
        this.matchFields = matchFields;
    }

    @Override
    public String toString() {
        return "SpiderConfigEntity{" +
                "commonUrl='" + commonUrl + '\'' +
                ", urlRule=" + urlRule +
                ", growthPattern=" + growthPattern +
                ", startNum=" + startNum +
                ", endNum=" + endNum +
                ", groupName='" + groupName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", siteName='" + siteName + '\'' +
                ", domain='" + domain + '\'' +
                ", charset='" + charset + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", cookies='" + cookies + '\'' +
                ", sleepTime=" + sleepTime +
                ", timeOut=" + timeOut +
                ", thread=" + thread +
                ", matchFields='" + matchFields + '\'' +
                '}';
    }
}
