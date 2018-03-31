package com.kingsoft.spider.core.home.settings.mapper;

import com.kingsoft.spider.core.common.mybatis.Mapper;
import com.kingsoft.spider.core.home.settings.dto.UserDto;
import com.kingsoft.spider.core.home.settings.dto.UserInfoDto;

import java.util.List;

/**
 * Created by wangyujie on 2018/3/21.
 */
@Mapper
public interface SettingMapper {
    List<UserInfoDto> getUserInfoList();

    void addUser(UserDto dto);

    void updateUser(UserDto dto);

    void delUser(Long id);

    UserDto getUserById(Long id);

    Integer getRoleByUserName(String userName);
}
