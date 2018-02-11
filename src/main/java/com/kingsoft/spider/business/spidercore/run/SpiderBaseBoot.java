package com.kingsoft.spider.business.spidercore.run;

import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import com.kingsoft.spider.business.spidercore.db.DataBaseBoot;

import java.util.List;

/**
 * 
 */
public interface SpiderBaseBoot {


    /**
     * 设置该爬虫任务的源链接列表
     * @param targetUrls
     */
     String[] setTargetUrlList(List<String> targetUrls);

    /**
     * 设置该爬虫任务的网络请求的请求头
     * @param headers
     */
    void setTargetSiteRequestParams(SiteRequestHeaders headers);

    /**
     * 配置该爬虫任务的爬取设置信息（开几个线程，睡眠多少等）
     * @param workInfo
     */
    void setSpiderWorkInfo(SpiderWorkInfo workInfo);

    /**
     * 设置该爬虫任务的爬取规则
     * @param matchFields
     */
    void setSpiderRuleFieldList(List<SpiderConfigInfoDto.MatchField> matchFields);

    /**
     * 配置入库
     * @param dataBaseBoot
     */
    void setDataBase(DataBaseBoot dataBaseBoot) ;

}