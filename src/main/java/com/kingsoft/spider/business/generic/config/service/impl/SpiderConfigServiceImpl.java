package com.kingsoft.spider.business.generic.config.service.impl;

import com.kingsoft.spider.business.generic.config.dto.SpiderConfigEntity;
import com.kingsoft.spider.business.generic.config.mapper.SpiderConfigMapper;
import com.kingsoft.spider.business.generic.config.service.SpiderConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangyujie on 2018/1/23.
 */
@Service
public class SpiderConfigServiceImpl implements SpiderConfigService {
    @Autowired
    private SpiderConfigMapper spiderConfigMapper;
    @Override
    public void save(SpiderConfigEntity spiderConfigEntity) {
        spiderConfigMapper.insert(spiderConfigEntity);
    }
}
