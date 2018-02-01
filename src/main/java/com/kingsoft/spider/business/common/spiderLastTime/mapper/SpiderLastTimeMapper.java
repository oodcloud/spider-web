package com.kingsoft.spider.business.common.spiderLastTime.mapper;

import com.kingsoft.spider.core.common.mybatis.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by wangyujie on 2018/1/29.
 */
@Mapper
public interface SpiderLastTimeMapper {
    Long selectLastTime(@Param("name") String name);
    void updateLastTime(@Param("name") String name, @Param("time") Long time, @Param("date") Date date);
}
