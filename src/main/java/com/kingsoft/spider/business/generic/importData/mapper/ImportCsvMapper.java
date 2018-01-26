package com.kingsoft.spider.business.generic.importData.mapper;

import com.kingsoft.spider.business.generic.importData.dto.BExcelEntity;
import com.kingsoft.spider.business.generic.importData.dto.ExcelEntity;
import com.kingsoft.spider.business.generic.importData.dto.ImportParamDto;
import com.kingsoft.spider.core.common.mybatis.Mapper;

import java.util.List;

/**
 * Created by wangyujie on 2017/12/28.
 */
@Mapper
public interface ImportCsvMapper {
    List<BExcelEntity> selectExcelInBSiteExtopia(ImportParamDto dto);

    List<BExcelEntity> selectExcelInBSiteXmqz(ImportParamDto dto);

    List<BExcelEntity> selectExcelInBSiteMat(ImportParamDto dto);


    List<ExcelEntity> selectExcelInBBSSite(ImportParamDto dto);

    List<ExcelEntity> selectExcelInTiebaSiteExtopia(ImportParamDto dto);
    List<ExcelEntity> selectExcelInTiebaSiteXmqz(ImportParamDto dto);
    List<ExcelEntity> selectExcelInTiebaSiteMat(ImportParamDto dto);

    List<ExcelEntity> selectExcelInWeiboSiteExtopia(ImportParamDto dto);
    List<ExcelEntity> selectExcelInWeiboSiteXmqz(ImportParamDto dto);
    List<ExcelEntity> selectExcelInWeiboSiteMat(ImportParamDto dto);
    List<ExcelEntity> selectExcelInWeiboSiteProducer(ImportParamDto dto);


    List<ExcelEntity> selectExcelInWechatExtopia(ImportParamDto dto);
    List<ExcelEntity> selectExcelInWechatXmqz(ImportParamDto dto);
    List<ExcelEntity> selectExcelInWechatMat(ImportParamDto dto);

}
