package com.kingsoft.spider.business.generic.importData.dto.Event;

import com.kingsoft.spider.business.generic.importData.dto.Event.AbstractExcelList;
import com.kingsoft.spider.business.generic.importData.dto.ExcelEntity;
import com.kingsoft.spider.business.generic.importData.dto.ImportParamDto;
import com.kingsoft.spider.business.generic.importData.mapper.ImportCsvMapper;

import java.util.List;

/**
 * Created by wangyujie on 2017/12/28.
 */
public class WeiboExtopiaExcelList extends AbstractExcelList {
    @Override
    public List<ExcelEntity> handle(ImportCsvMapper mapper, ImportParamDto importParamDto) {
        return mapper.selectExcelInWeiboSiteExtopia(importParamDto);
    }
}