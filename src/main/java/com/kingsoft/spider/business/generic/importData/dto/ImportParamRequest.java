package com.kingsoft.spider.business.generic.importData.dto;

/**
 * Created by wangyujie on 2017/12/26.
 */
public class ImportParamRequest {
    private String start;
    private String end;
    private Integer type;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
