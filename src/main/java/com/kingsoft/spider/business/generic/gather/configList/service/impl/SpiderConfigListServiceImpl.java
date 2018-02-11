package com.kingsoft.spider.business.generic.gather.configList.service.impl;

import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoTransition;
import com.kingsoft.spider.business.generic.gather.configList.dto.SpiderConfigListDto;
import com.kingsoft.spider.business.generic.gather.configList.mapper.SpiderConfigListMapper;
import com.kingsoft.spider.business.generic.gather.configList.service.SpiderConfigListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangyujie on 2018/1/30.
 */
@Service
public class SpiderConfigListServiceImpl implements SpiderConfigListService {
    @Autowired
    private SpiderConfigListMapper spiderConfigListMapper;


    @Override
    public List<SpiderConfigListDto> getList() {
        return spiderConfigListMapper.getList();
    }

    @Override
    public List<SpiderConfigInfoDto> getEditList(Long id) {
        SpiderConfigInfoTransition transition=new SpiderConfigInfoTransition();
        return  transition.switchToDto(spiderConfigListMapper.getEditList(id));
    }

    @Override
    public void delete(Long id) {
        spiderConfigListMapper.delete(id);
    }
}
