package com.kingsoft.spider.business.generic.gather.configList.mapper;

import com.kingsoft.spider.business.spidercore.common.SpiderConfigEntity;
import com.kingsoft.spider.business.generic.gather.configList.dto.SpiderConfigListDto;
import com.kingsoft.spider.core.common.mybatis.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangyujie on 2018/1/30.
 */
@Mapper
public interface SpiderConfigListMapper {
    List<SpiderConfigListDto> getList();

    List<SpiderConfigEntity> getEditList(@Param("id")Long id);

    void delete(@Param("id") Long id);
}
