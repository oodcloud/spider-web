package com.kingsoft.spider.business.common.spider.wechat.mapper;

import com.kingsoft.spider.business.common.spider.wechat.dto.CommentVo;
import com.kingsoft.spider.core.common.mybatis.Mapper;

import java.util.List;

/**
 * Created by wangyujie on 2018/1/8.
 */
@Mapper
public interface WechatExtopiaMapper {
    void xmqzSingleAdd(CommentVo commentVo);
    void extopiaSingleAdd(CommentVo commentVo);
    void matSingleAdd(CommentVo commentVo);

    void xmqzAdd(List<CommentVo> list);
    void extopiaAdd(List<CommentVo> list);
    void matAdd(List<CommentVo> list);
}
