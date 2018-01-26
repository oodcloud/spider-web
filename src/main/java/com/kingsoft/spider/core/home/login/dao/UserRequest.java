package com.kingsoft.spider.core.home.login.dao;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by wangyujie on 2017/12/20.
 */
public class UserRequest {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
