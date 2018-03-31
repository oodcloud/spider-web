package com.kingsoft.spider.business.generic.gather.onlineCollect.service;

import com.kingsoft.spider.business.generic.gather.onlineCollect.dto.SpiderLogInfoDto;
import com.kingsoft.spider.business.generic.gather.onlineCollect.dto.SpiderOnlineCollectDto;
import com.kingsoft.spider.business.generic.gather.onlineCollect.dto.SpiderStatusDto;
import com.kingsoft.spider.business.generic.gather.onlineCollect.dto.SpiderStatusTimerDto;

import java.util.List;

/**
 * Created by wangyujie on 2018/1/30.
 */
public interface SpiderOnlineCollectService {
    List<SpiderOnlineCollectDto> getAll();
    SpiderStatusDto start(Integer id);
    SpiderStatusDto stop(String uuid);
    List<SpiderStatusTimerDto> getSpiderStutas();
    SpiderLogInfoDto getSpiderLogInfo(String uuid, Integer index);
}
