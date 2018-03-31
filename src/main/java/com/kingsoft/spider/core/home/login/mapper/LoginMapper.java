package com.kingsoft.spider.core.home.login.mapper;

import com.kingsoft.spider.core.common.mybatis.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

/**
 * Created by wangyujie on 2018/3/21.
 */
@Mapper
public interface LoginMapper {
    Integer updatePassword(@Param("name") String userName,@Param("pwd") String newPassword);

    void updateGenerateTime(@Param("name")String username,@Param("generateTime") Timestamp generateTime);
}
