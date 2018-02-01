package com.kingsoft.spider.business.generic.data.monitor.service.impl;

import com.kingsoft.spider.business.generic.data.monitor.service.DataMonitorService;
import com.kingsoft.spider.business.generic.data.monitor.mapper.DataMonitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangyujie on 2018/2/1.
 */
@Service
public class DataMonitorServiceImpl implements DataMonitorService {
    @Autowired
    private DataMonitorMapper dataMonitorMapper;

}
