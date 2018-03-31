package com.kingsoft.spider.business.generic.home.dto;

import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;

import java.util.List;

/**
 * Created by wangyujie on 2018/3/22.
 */
public class DataBaseConfigDto {
    private String groupName;
    private String itemName;
    private String commonUrl;
    private String dbType;
    private String dbAddress;
    private String dbName;
    private String dbUserName;
    private String dbPassWord;
    private String dbTable;
    private String matchFields;
    private List<SpiderConfigInfoDto.MatchField> fieldList;

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

    public String getCommonUrl() {
        return commonUrl;
    }

    public void setCommonUrl(String commonUrl) {
        this.commonUrl = commonUrl;
    }

    public List<SpiderConfigInfoDto.MatchField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<SpiderConfigInfoDto.MatchField> fieldList) {
        this.fieldList = fieldList;
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

    public String getDbPassWord() {
        return dbPassWord;
    }

    public void setDbPassWord(String dbPassWord) {
        this.dbPassWord = dbPassWord;
    }

    public String getDbTable() {
        return dbTable;
    }

    public void setDbTable(String dbTable) {
        this.dbTable = dbTable;
    }

    public String getMatchFields() {
        return matchFields;
    }

    public void setMatchFields(String matchFields) {
        this.matchFields = matchFields;
    }

}
