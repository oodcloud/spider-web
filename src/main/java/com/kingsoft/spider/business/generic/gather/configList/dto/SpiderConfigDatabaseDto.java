package com.kingsoft.spider.business.generic.gather.configList.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by wangyujie on 2018/3/19.
 */
public class SpiderConfigDatabaseDto {
    @NotNull(message = "该字段dbAddress不能为空")
    private String dbAddress;
    @NotNull(message = "该字段name不能为空")
    private String name;
    @NotNull(message = "该字段user不能为空")
    private String user;
    @NotNull(message = "该字段password不能为空")
    private String password;

    public String getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(String dbAddress) {
        this.dbAddress = dbAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
