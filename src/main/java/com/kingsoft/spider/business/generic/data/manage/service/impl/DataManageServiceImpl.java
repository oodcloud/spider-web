package com.kingsoft.spider.business.generic.data.manage.service.impl;

import com.kingsoft.spider.business.generic.data.manage.service.DataManageService;
import com.kingsoft.spider.business.generic.data.manage.mapper.DataManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangyujie on 2018/2/1.
 */
@Service
public class DataManageServiceImpl implements DataManageService {
    @Autowired
    private DataManageMapper dataManageMapper;

}
