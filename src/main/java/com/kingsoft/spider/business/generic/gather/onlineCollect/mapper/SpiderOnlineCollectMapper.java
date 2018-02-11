package com.kingsoft.spider.business.generic.gather.onlineCollect.mapper;

import com.kingsoft.spider.business.generic.gather.onlineCollect.dto.SpiderOnlineCollectDto;
import com.kingsoft.spider.core.common.mybatis.Mapper;

import java.util.List;

/**
 * Created by wangyujie on 2018/1/30.
 */
@Mapper
public interface SpiderOnlineCollectMapper {
    List<SpiderOnlineCollectDto> getAll();

}
