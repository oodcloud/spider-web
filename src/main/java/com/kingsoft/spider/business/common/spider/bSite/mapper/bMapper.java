package com.kingsoft.spider.business.common.spider.bSite.mapper;

import com.kingsoft.spider.business.common.spider.bSite.dto.CommentDto;
import com.kingsoft.spider.core.common.mybatis.Mapper;

import java.util.List;

/**
 * Created by wangyujie on 2017/12/25.
 */
@Mapper
public interface bMapper {
    void xmqzSingleAdd(CommentDto dto);
    void extopiaSingleAdd(CommentDto dto);
    void matSingleAdd(CommentDto dto);

    void xmqzAdd(List<CommentDto> list);
    void extopiaAdd(List<CommentDto> list);
    void matAdd(List<CommentDto> list);
}
