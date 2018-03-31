package com.kingsoft.spider.business.generic.data.manage.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by wangyujie on 2018/3/16.
 */
public class FieldContentReplaceByIdDto {
    @NotNull(message = "该id字段不能为空")
    private Long id;
    @NotNull(message = "该field字段不能为空")
    private String field;
    @NotNull(message = "该orginContent字段不能为空")
    private String orginContent;
    @NotNull(message = "该targetContent字段不能为空")
    private String targetContent;
    @NotNull(message = "该ids字段不能为空")
    private Long[] ids;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

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

}
