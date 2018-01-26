package com.kingsoft.spider.business.common.spider.bbs.mapper;

import com.kingsoft.spider.business.common.spider.bbs.dto.bbsDto;
import com.kingsoft.spider.core.common.mybatis.Mapper;

import java.util.List;

/**
 * Created by wangyujie on 2017/12/25.
 */
@Mapper
public interface BbsMapper {

    void add(List<bbsDto> list);

    void addSingle(bbsDto dto);

}
