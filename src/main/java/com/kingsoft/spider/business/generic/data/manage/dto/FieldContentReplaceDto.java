package com.kingsoft.spider.business.generic.data.manage.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by wangyujie on 2018/3/16.
 */
public class FieldContentReplaceDto {
    @NotNull(message = "当前id不能为空")
    private Long id;
    @NotNull(message = "当前field不能为空")
    private String field;
    @NotNull(message = "当前orginContent不能为空")
    private String orginContent;
    @NotNull(message = "当前orginContent不能为空")
    private String targetContent;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @NotNull(message = "当前startTime不能为空")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @NotNull(message = "当前endTime不能为空")
    private Date endTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrginContent() {
        return orginContent;
    }

    public void setOrginContent(String orginContent) {
        this.orginContent = orginContent;
    }

    public String getTargetContent() {
        return targetContent;
    }

    public void setTargetContent(String targetContent) {
        this.targetContent = targetContent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
