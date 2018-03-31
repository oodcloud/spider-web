package com.kingsoft.spider.core.home.login.service.impl;

import com.kingsoft.spider.core.home.login.service.LoginService;
import com.kingsoft.spider.core.home.login.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by wangyujie on 2018/3/21.
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public Integer updatePassword(String userName, String newPassword) {
        return loginMapper.updatePassword(userName,newPassword);
    }

    @Override
    public void updateGenerateTime(String username) {
        Timestamp generateTime=new Timestamp(new Date().getTime());
        loginMapper.updateGenerateTime(username,generateTime);
    }
}
