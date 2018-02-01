package com.kingsoft.spider.business.generic.gather.taskCollect.service.impl;

import com.kingsoft.spider.business.generic.gather.taskCollect.service.SpiderTaskCollectService;
import com.kingsoft.spider.business.generic.gather.taskCollect.mapper.SpiderTaskCollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangyujie on 2018/1/30.
 */
@Service
public class SpiderTaskCollectServiceImpl implements SpiderTaskCollectService {
    @Autowired
    private SpiderTaskCollectMapper spiderTaskCollectMapper;

}
