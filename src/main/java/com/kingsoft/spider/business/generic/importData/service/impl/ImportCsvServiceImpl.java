package com.kingsoft.spider.business.generic.importData.service.impl;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.generic.importData.dto.*;
import com.kingsoft.spider.business.generic.importData.dto.Event.AbstractExcelList;
import com.kingsoft.spider.business.generic.importData.dto.Event.ExcelFactory;
import com.kingsoft.spider.business.generic.importData.mapper.ImportCsvMapper;
import com.kingsoft.spider.business.generic.importData.service.ImportCsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wangyujie on 2017/12/28.
 */
@Service
public class ImportCsvServiceImpl implements ImportCsvService {
    @Autowired
    private ImportCsvMapper importCsvMapper;

    @Override
    public List<ExcelEntity> selectByTime(ImportParamRequest importParamRequest) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ImportParamDto importParamDto = new ImportParamDto();
        if (importParamRequest.getEnd() != null && !"".equals(importParamRequest.getEnd())) {
            try {
                Long endTime = simpleDateFormat.parse(importParamRequest.getEnd()).getTime() / 1000;
                importParamDto.setEnd(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (importParamRequest.getStart() != null && !"".equals(importParamRequest.getStart())) {
            try {
                Long startTime = simpleDateFormat.parse(importParamRequest.getStart()).getTime() / 1000;
                importParamDto.setStart(startTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        AbstractExcelList excelList = ExcelFactory.create(importParamRequest.getType());
        List<ExcelEntity> excelEntityList = excelList.handle(importCsvMapper, importParamDto);
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        for (ExcelEntity entity : excelEntityList) {
            entity.setDateTime(formatTime.format(new Date(entity.getTime() * 1000)));
        }
        return excelEntityList;
    }
}
