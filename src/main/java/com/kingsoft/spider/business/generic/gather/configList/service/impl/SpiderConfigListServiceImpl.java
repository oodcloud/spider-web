package com.kingsoft.spider.business.generic.gather.configList.service.impl;

import com.kingsoft.spider.business.generic.gather.configList.service.SpiderConfigListService;
import com.kingsoft.spider.business.generic.gather.configList.mapper.SpiderConfigListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangyujie on 2018/1/30.
 */
@Service
public class SpiderConfigListServiceImpl implements SpiderConfigListService {
    @Autowired
    private SpiderConfigListMapper spiderConfigListMapper;


}
