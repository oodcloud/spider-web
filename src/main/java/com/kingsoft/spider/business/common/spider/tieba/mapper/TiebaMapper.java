package com.kingsoft.spider.business.common.spider.tieba.mapper;

import com.kingsoft.spider.business.common.spider.tieba.dto.tiebaDto;
import com.kingsoft.spider.core.common.mybatis.Mapper;

import java.util.List;

/**
 * Created by wangyujie on 2017/12/26.
 */
@Mapper
public interface TiebaMapper {
    void xmqzAdd(List<tiebaDto> list);
    void extopiaAdd(List<tiebaDto> list);
    void matAdd(List<tiebaDto> list);


    void xmqzSingleAdd(tiebaDto dto);
    void extopiaSingleAdd(tiebaDto dto);
    void matSingleAdd(tiebaDto dto);
}
