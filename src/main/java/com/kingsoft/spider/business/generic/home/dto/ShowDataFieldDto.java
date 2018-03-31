package com.kingsoft.spider.business.generic.home.dto;

import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;

import java.util.List;

/**
 * Created by wangyujie on 2018/3/22.
 */
public class ShowDataFieldDto {
    private String dbTable;
    private List<SpiderConfigInfoDto.MatchField> fieldList;

    public String getDbTable() {
        return dbTable;
    }

    public void setDbTable(String dbTable) {
        this.dbTable = dbTable;
    }

    public List<SpiderConfigInfoDto.MatchField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<SpiderConfigInfoDto.MatchField> fieldList) {
        this.fieldList = fieldList;
    }
}
