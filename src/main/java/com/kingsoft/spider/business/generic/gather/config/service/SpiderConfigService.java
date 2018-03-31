package com.kingsoft.spider.business.generic.gather.config.service;

import com.kingsoft.spider.business.generic.gather.configList.dto.SpiderConfigDatabaseDto;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigEntity;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import com.kingsoft.spider.business.spidercore.common.TestPipelineDto;

import java.util.List;

/**
 * Created by wangyujie on 2018/1/23.
 */
public interface SpiderConfigService {
    void save(SpiderConfigEntity spiderConfigEntity);

    List<TestPipelineDto> testRun(SpiderConfigInfoDto entity);

    boolean testDataBase(SpiderConfigDatabaseDto databaseDto);
}
