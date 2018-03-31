package com.kingsoft.spider.business.spidercore.common;

import java.util.List;

/**
 * Created by wangyujie on 2018/1/23.
 */
public class SpiderConfigInfoDto {
    private String commonUrl;
    private String targetUrl;
    private String urlRule;
    private String growthPattern;
    private String startNum;
    private String endNum;
    private String groupName;
    private String itemName;
    private String retryTimes;
    private String siteName;
    private String domain;
    private String charset;
    private String userAgent;
    private String cookies;
    private String sleepTime;
    private String timeOut;
    private String thread;
    private String headers;
    private List<MatchField> matchFields;
    private Long generatedTime;
    private String dbType;
    private String dbAddress;
    private String dbName;
    private String dbUserName;
    private String dbPassWord;
    private String dbTable;

    public String getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(String retryTimes) {
        this.retryTimes = retryTimes;
    }

    public String getDbTable() {
        return dbTable;
    }

    public void setDbTable(String dbTable) {
        this.dbTable = dbTable;
    }

    public String getDbPassWord() {
        return dbPassWord;
    }

    public void setDbPassWord(String dbPassWord) {
        this.dbPassWord = dbPassWord;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(String dbAddress) {
        this.dbAddress = dbAddress;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Long getGeneratedTime() {
        return generatedTime;
    }

    public void setGeneratedTime(Long generatedTime) {
        this.generatedTime = generatedTime;
    }

    public List<MatchField> getMatchFields() {
        return matchFields;
    }

    public void setMatchFields(List<MatchField> matchFields) {
        this.matchFields = matchFields;
    }

    public static class MatchField {
        private String fieldName;
        private String fieldEnglishName;
        private String regex;
        private String xpath;
        private String defaultValue;

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldEnglishName() {
            return fieldEnglishName;
        }

        public void setFieldEnglishName(String fieldEnglishName) {
            this.fieldEnglishName = fieldEnglishName;
        }

        public String getRegex() {
            return regex;
        }

        public void setRegex(String regex) {
            this.regex = regex;
        }

        public String getXpath() {
            return xpath;
        }

        public void setXpath(String xpath) {
            this.xpath = xpath;
        }

        @Override
        public String toString() {
            return "MatchField{" +
                    "fieldName='" + fieldName + '\'' +
                    ", fieldEnglishName='" + fieldEnglishName + '\'' +
                    ", regex='" + regex + '\'' +
                    ", xpath='" + xpath + '\'' +
                    '}';
        }
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

    public String getUrlRule() {
        return urlRule;
    }

    public void setUrlRule(String urlRule) {
        this.urlRule = urlRule;
    }

    public String getGrowthPattern() {
        return growthPattern;
    }

    public void setGrowthPattern(String growthPattern) {
        this.growthPattern = growthPattern;
    }

    public String getStartNum() {
        return startNum;
    }

    public void setStartNum(String startNum) {
        this.startNum = startNum;
    }

    public String getEndNum() {
        return endNum;
    }

    public void setEndNum(String endNum) {
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

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    @Override
    public String toString() {
        return "SpiderConfigInfoDto{" +
                "commonUrl='" + commonUrl + '\'' +
                ", urlRule='" + urlRule + '\'' +
                ", growthPattern='" + growthPattern + '\'' +
                ", startNum='" + startNum + '\'' +
                ", endNum='" + endNum + '\'' +
                ", groupName='" + groupName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", siteName='" + siteName + '\'' +
                ", domain='" + domain + '\'' +
                ", charset='" + charset + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", cookies='" + cookies + '\'' +
                ", sleepTime='" + sleepTime + '\'' +
                ", timeOut='" + timeOut + '\'' +
                ", thread='" + thread + '\'' +
                ", matchFields=" + matchFields +
                '}';
    }
}
