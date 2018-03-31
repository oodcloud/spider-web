package com.kingsoft.spider.business.generic.data.manage.mapper;

import com.kingsoft.spider.business.datadb.entity.DataManageDbInfoDto;
import com.kingsoft.spider.business.generic.data.manage.dto.DataManageDBDto;
import com.kingsoft.spider.core.common.mybatis.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangyujie on 2018/2/1.
 */
@Mapper
public interface DataManageMapper {
    List<DataManageDBDto> queryAllDBList();

    DataManageDbInfoDto queryDbInfoById(@Param("id") Long id);


}
