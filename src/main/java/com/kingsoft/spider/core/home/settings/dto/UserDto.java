package com.kingsoft.spider.core.home.settings.dto;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created by wangyujie on 2018/3/22.
 */
public class UserDto {
    private Long id;
    @NotNull(message = "用户名不能为空")
    private String userName;
    @NotNull(message = "密码不能为空")
    private String passWord;
    private Timestamp generateTime;
    @NotNull(message = "email不能为空")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Timestamp getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(Timestamp generateTime) {
        this.generateTime = generateTime;
    }
}
