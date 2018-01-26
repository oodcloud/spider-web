package com.kingsoft.spider.business.generic.importData.service;

import com.kingsoft.spider.business.generic.importData.dto.ExcelEntity;
import com.kingsoft.spider.business.generic.importData.dto.ImportParamRequest;

import java.util.List;

/**
 * Created by wangyujie on 2017/12/28.
 */
public interface ImportCsvService {
    List<ExcelEntity> selectByTime(ImportParamRequest importParamRequest);
}
