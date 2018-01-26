package com.kingsoft.spider.business.generic.config.mapper;

import com.kingsoft.spider.business.generic.config.dto.SpiderConfigEntity;
import com.kingsoft.spider.core.common.mybatis.Mapper;

/**
 * Created by wangyujie on 2018/1/23.
 */
@Mapper
public interface SpiderConfigMapper {
    void insert(SpiderConfigEntity spiderConfigEntity);
}
