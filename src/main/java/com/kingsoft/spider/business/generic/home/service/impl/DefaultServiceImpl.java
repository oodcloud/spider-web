package com.kingsoft.spider.business.generic.home.service.impl;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.datadb.ShowDataDbUseHandle;
import com.kingsoft.spider.business.datadb.entity.DataManageDbInfoDto;
import com.kingsoft.spider.business.generic.home.dto.DataBaseConfigDto;
import com.kingsoft.spider.business.generic.home.dto.ShowDataDto;
import com.kingsoft.spider.business.generic.home.dto.ShowDataFieldDto;
import com.kingsoft.spider.business.generic.home.mapper.DefaultMapper;
import com.kingsoft.spider.business.generic.home.service.DefaultService;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import com.kingsoft.spider.core.common.support.DtoConvator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wangyujie on 2018/1/9.
 */
@Service
public class DefaultServiceImpl implements DefaultService {
    @Autowired
    private DefaultMapper defaultMapper;

    @Override
    public List<ShowDataDto> getShowData() {
        List<DataBaseConfigDto> spiderConfigEntityList=defaultMapper.getAllSpiderConfig();
        List<SpiderConfigInfoDto.MatchField> queryFields=null;
        for (DataBaseConfigDto dto:spiderConfigEntityList)
        {
            queryFields=JSON.parseArray(dto.getMatchFields(),SpiderConfigInfoDto.MatchField.class);
         Iterator<SpiderConfigInfoDto.MatchField>  iterators=queryFields.iterator();
            while (iterators.hasNext())
            {
                SpiderConfigInfoDto.MatchField matchField=iterators.next();
                if ("".equals(matchField.getDefaultValue()))
                {
                    iterators.remove();
                }
            }
            dto.setFieldList(queryFields);
        }
        List<DataManageDbInfoDto> dataManageDbInfoDtos=new ArrayList<>();
        for (int i = 0; i < spiderConfigEntityList.size(); i++) {
            DataManageDbInfoDto dto=new DataManageDbInfoDto();
            dataManageDbInfoDtos.add(dto);
        }

        DtoConvator.convert(dataManageDbInfoDtos,spiderConfigEntityList);
        List<ShowDataFieldDto> dataFieldDtoList=new ArrayList<>();
        for (int i = 0; i < spiderConfigEntityList.size(); i++) {
            ShowDataFieldDto dataFieldDto=new ShowDataFieldDto();
            dataFieldDtoList.add(dataFieldDto);
        }
        DtoConvator.convert(dataFieldDtoList,spiderConfigEntityList);


        List<ShowDataDto> showDataDtos=new ArrayList<>();
        for (int i = 0; i < dataManageDbInfoDtos.size(); i++) {
            ShowDataDto showDataDto=new ShowDataDto();
            ShowDataDbUseHandle dataDbUseHandle=new ShowDataDbUseHandle(dataManageDbInfoDtos.get(i));
            Integer total=  dataDbUseHandle.getTotalCount(dataFieldDtoList.get(i));
            Integer nowCount=dataDbUseHandle.nowTotalCount(dataFieldDtoList.get(i));
            DataBaseConfigDto dataBaseConfigDto=  spiderConfigEntityList.get(i);
            showDataDto.setName(dataBaseConfigDto.getGroupName()+dataBaseConfigDto.getItemName()+dataBaseConfigDto.getCommonUrl().hashCode());
            showDataDto.setNowCount(nowCount);
            showDataDto.setTotal(total);
            showDataDtos.add(showDataDto);
        }
        return showDataDtos;
    }
}
