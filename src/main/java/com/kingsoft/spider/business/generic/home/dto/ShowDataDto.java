package com.kingsoft.spider.business.generic.home.dto;

/**
 * Created by wangyujie on 2018/3/22.
 */
public class ShowDataDto {
    private Integer total;
    private Integer nowCount;
    private String name;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getNowCount() {
        return nowCount;
    }

    public void setNowCount(Integer nowCount) {
        this.nowCount = nowCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
