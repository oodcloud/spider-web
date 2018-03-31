package com.kingsoft.spider.business.generic.gather.configList.service;

import com.kingsoft.spider.business.generic.gather.configList.dto.SpiderConfigDatabaseDto;
import com.kingsoft.spider.business.generic.gather.configList.dto.SpiderConfigSaveDto;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import com.kingsoft.spider.business.generic.gather.configList.dto.SpiderConfigListDto;
import com.kingsoft.spider.business.spidercore.common.TestPipelineDto;

import java.util.List;

/**
 * Created by wangyujie on 2018/1/30.
 */
public interface SpiderConfigListService {
    /**
     * 获取爬虫配置列表
     * @return
     */
    List<SpiderConfigListDto> getList();

    SpiderConfigInfoDto getEditList(Long id);

    void delete(Long id);

    void update(SpiderConfigSaveDto spiderConfigEntity);

    List<TestPipelineDto> testRun(SpiderConfigInfoDto entity);

    boolean testDataBase(SpiderConfigDatabaseDto databaseDto);
}
