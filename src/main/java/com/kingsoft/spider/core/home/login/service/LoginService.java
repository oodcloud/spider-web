package com.kingsoft.spider.core.home.login.service;

/**
 * Created by wangyujie on 2018/3/21.
 */
public interface LoginService {
    Integer updatePassword(String userName, String newPassword);

    void updateGenerateTime(String username);
}
