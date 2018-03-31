package com.kingsoft.spider.core.home.settings.service;

import com.github.pagehelper.PageInfo;
import com.kingsoft.spider.core.home.settings.dto.UserDto;

/**
 * Created by wangyujie on 2018/3/21.
 */
public interface SettingService {
    PageInfo getUserInfoList(Integer pageIndex, Integer pageSize);

    void saveUser(UserDto dto);

    void updateUser(UserDto dto);

    void delUser(Long id);

    UserDto getUserById(Long id);

    Integer getRoleByUserName(String userName);
}
