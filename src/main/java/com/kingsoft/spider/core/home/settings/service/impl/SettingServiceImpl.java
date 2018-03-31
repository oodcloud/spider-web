package com.kingsoft.spider.core.home.settings.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingsoft.spider.core.home.settings.dto.UserDto;
import com.kingsoft.spider.core.home.settings.dto.UserInfoDto;
import com.kingsoft.spider.core.home.settings.mapper.SettingMapper;
import com.kingsoft.spider.core.home.settings.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by wangyujie on 2018/3/21.
 */
@Service
public class SettingServiceImpl implements SettingService {
    @Autowired
    private SettingMapper settingMapper;

    @Override
    public PageInfo getUserInfoList(Integer pageIndex, Integer pageSize) {

        PageHelper.startPage(pageIndex, pageSize);
        List<UserInfoDto> list = settingMapper.getUserInfoList();
        PageInfo page = new PageInfo(list);
        return page;
    }

    @Override
    public void saveUser(UserDto dto) {
        Date date=new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        dto.setGenerateTime(timestamp);
        settingMapper.addUser(dto);
    }

    @Override
    public void updateUser(UserDto dto) {
        settingMapper.updateUser(dto);
    }

    @Override
    public void delUser(Long id) {
        settingMapper.delUser(id);
    }

    @Override
    public UserDto getUserById(Long id) {
        return settingMapper.getUserById(id);
    }

    @Override
    public Integer getRoleByUserName(String userName) {
        return settingMapper.getRoleByUserName(userName);
    }
}
