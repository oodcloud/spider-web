package com.kingsoft.spider.core.home.settings.dto;

import java.util.Optional;

/**
 * Created by wangyujie on 2018/3/21.
 */
public class UserInfoDto {
    private Long id;
    private String userName;
    private String generateTime="无";
    private String lastTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(String generateTime) {
        this.generateTime = generateTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
