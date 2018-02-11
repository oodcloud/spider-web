package com.kingsoft.spider.business.generic.gather.onlineCollect.service.impl;

import com.kingsoft.spider.business.generic.gather.onlineCollect.dto.SpiderOnlineCollectDto;
import com.kingsoft.spider.business.generic.gather.onlineCollect.service.SpiderOnlineCollectService;
import com.kingsoft.spider.business.generic.gather.onlineCollect.mapper.SpiderOnlineCollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangyujie on 2018/1/30.
 */
@Service
public class SpiderOnlineCollectServiceImpl implements SpiderOnlineCollectService {
    @Autowired
    private SpiderOnlineCollectMapper spiderOnlineCollectMapper;

    @Override
    public List<SpiderOnlineCollectDto> getAll() {
        return spiderOnlineCollectMapper.getAll();
    }
}
