package com.kingsoft.spider.business.generic.gather.config.mapper;

import com.kingsoft.spider.business.spidercore.common.SpiderConfigEntity;
import com.kingsoft.spider.core.common.mybatis.Mapper;

/**
 * Created by wangyujie on 2018/1/23.
 */
@Mapper
public interface SpiderConfigMapper {
    void insert(SpiderConfigEntity spiderConfigEntity);
}
