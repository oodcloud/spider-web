package com.kingsoft.spider.business.generic.importData.dto.Event;

import com.kingsoft.spider.business.generic.importData.dto.BExcelEntity;
import com.kingsoft.spider.business.generic.importData.dto.ExcelEntity;
import com.kingsoft.spider.business.generic.importData.dto.ImportParamDto;
import com.kingsoft.spider.business.generic.importData.mapper.ImportCsvMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyujie on 2017/12/28.
 */
public class BMatExcelList extends AbstractExcelList {
    @Override
    public List<ExcelEntity> handle(ImportCsvMapper mapper, ImportParamDto importParamDto) {
        List<ExcelEntity> entities=new ArrayList<>();
        List<BExcelEntity> bExcelEntities= mapper.selectExcelInBSiteMat(importParamDto);
        for (BExcelEntity bExcelEntity:bExcelEntities)
        {
            ExcelEntity excelEntity=new ExcelEntity();
            excelEntity.setTime(bExcelEntity.getTime());
            excelEntity.setTitle(bExcelEntity.getTitle());
            excelEntity.setAuthor(bExcelEntity.getAuthor());
            if (bExcelEntity.getType()==1)
            {
                excelEntity.setText("弹幕:"+bExcelEntity.getText());
            }else if (bExcelEntity.getType()==2)
            {
                excelEntity.setText("评论:"+bExcelEntity.getText());
            }
            entities.add(excelEntity);
        }

        return entities;
    }
}
