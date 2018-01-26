package com.kingsoft.spider.business.generic.home.mapper;

import com.kingsoft.spider.core.common.mybatis.Mapper;

/**
 * Created by wangyujie on 2018/1/9.
 */
@Mapper
public interface DefaultMapper {
    Long getBSiteExtopiaCommentCount();
    Long getBSiteXmqzCommentCount();
    Long getBSiteMatCommentCount();

    Long getTiebaExtopiaCommentCount();
    Long getTiebaXmqzCommentCount();
    Long getTiebaMatCommentCount();

    Long getWeiboExtopiaCommentCount();
    Long getWeiboXmqzCommentCount();
    Long getWeiboMatCommentCount();
    Long getWeiboProducerCommentCount();

    Long getBBSExtopiaCommentCount();

    Long getWechatExtopiaCommentCount();
    Long getWechatXmqzCommentCount();
    Long getWechatMatCommentCount();

}
