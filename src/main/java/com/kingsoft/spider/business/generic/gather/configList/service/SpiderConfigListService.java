package com.kingsoft.spider.business.generic.gather.configList.service;

import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import com.kingsoft.spider.business.generic.gather.configList.dto.SpiderConfigListDto;

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

    List<SpiderConfigInfoDto> getEditList(Long id);

    void delete(Long id);
}
