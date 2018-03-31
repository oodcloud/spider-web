package com.kingsoft.spider.business.generic.home.mapper;

import com.kingsoft.spider.business.generic.home.dto.DataBaseConfigDto;
import com.kingsoft.spider.core.common.mybatis.Mapper;

import java.util.List;

/**
 * Created by wangyujie on 2018/1/9.
 */
@Mapper
public interface DefaultMapper {
    List<DataBaseConfigDto> getAllSpiderConfig();
}
