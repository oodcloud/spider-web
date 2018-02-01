package com.kingsoft.spider.business.common.spiderLastTime.service.impl;

import com.kingsoft.spider.business.common.spiderLastTime.service.SpiderLastTimeService;
import com.kingsoft.spider.business.common.spiderLastTime.mapper.SpiderLastTimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wangyujie on 2018/1/29.
 */
@Service
public class SpiderLastTimeServiceImpl implements SpiderLastTimeService {
    @Autowired
    private SpiderLastTimeMapper spiderLastTimeMapper;

    @Override
    public Long selectLastTime(String name) {
        return spiderLastTimeMapper.selectLastTime(name);
    }

    @Override
    public void updateLastTime(String name, Long time) {
        Date date=new Date();
        spiderLastTimeMapper.updateLastTime(name,time,date);
    }
}
