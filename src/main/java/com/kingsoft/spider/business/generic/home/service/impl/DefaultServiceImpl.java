package com.kingsoft.spider.business.generic.home.service.impl;

import com.kingsoft.spider.business.generic.home.mapper.DefaultMapper;
import com.kingsoft.spider.business.generic.home.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangyujie on 2018/1/9.
 */
@Service
public class DefaultServiceImpl implements DefaultService {
    @Autowired
    private DefaultMapper defaultMapper;
    @Override
    public Long getBSiteExtopiaCommentCount() {
        return defaultMapper.getBSiteExtopiaCommentCount();
    }

    @Override
    public Long getBSiteXmqzCommentCount() {
        return defaultMapper.getBSiteXmqzCommentCount();
    }

    @Override
    public Long getBSiteMatCommentCount() {
        return defaultMapper.getBSiteMatCommentCount();
    }

    @Override
    public Long getTiebaExtopiaCommentCount() {
        return defaultMapper.getTiebaExtopiaCommentCount();
    }

    @Override
    public Long getTiebaXmqzCommentCount() {
        return defaultMapper.getTiebaXmqzCommentCount();
    }

    @Override
    public Long getTiebaMatCommentCount() {
        return defaultMapper.getTiebaMatCommentCount();
    }

    @Override
    public Long getWeiboExtopiaCommentCount() {
        return defaultMapper.getWeiboExtopiaCommentCount();
    }

    @Override
    public Long getWeiboXmqzCommentCount() {
        return defaultMapper.getWeiboXmqzCommentCount();
    }

    @Override
    public Long getWeiboMatCommentCount() {
        return defaultMapper.getWeiboMatCommentCount();
    }

    @Override
    public Long getWeiboProducerCommentCount() {
        return defaultMapper.getWeiboProducerCommentCount();
    }

    @Override
    public Long getBBSExtopiaCommentCount() {
        return defaultMapper.getBBSExtopiaCommentCount();
    }

    @Override
    public Long getWechatExtopiaCommentCount() {
        return defaultMapper.getWechatExtopiaCommentCount();
    }

    @Override
    public Long getWechatXmqzCommentCount() {
        return defaultMapper.getWechatXmqzCommentCount();
    }

    @Override
    public Long getWechatMatCommentCount() {
        return defaultMapper.getWechatMatCommentCount();
    }
}
