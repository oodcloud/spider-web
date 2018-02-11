package com.kingsoft.spider.business.spidercore.common;

import com.kingsoft.spider.business.generic.gather.config.dto.CommonUrlTestRequest;
import com.kingsoft.spider.business.spidercore.db.DataBaseBoot;
import com.kingsoft.spider.business.spidercore.run.SiteRequestHeaders;
import com.kingsoft.spider.business.spidercore.run.SpiderWorkInfo;
import com.kingsoft.spider.core.common.config.SpiderUrlRole;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangyujie on 2018/2/10.
 */
public class SpiderInfoConvert {


    public List<String> getTargetUrlList(CommonUrlTestRequest request) {
        String commonUrl = request.getCommonUrl();
        int startNum = request.getStartNum();
        int endNum = request.getEndNum();
        int growthPattern = request.getGrowthPattern();
        int urlRule = request.getUrlRule();
        if (!commonUrl.contains("http://") && !commonUrl.contains("https://")) {
            commonUrl = "http://" + commonUrl;
        }
        String[] commonUrlArray = commonUrl.split("\\*");
        String commonUrlFrom = commonUrlArray[0];
        List<String> urls = new ArrayList<>();
        switchRule(startNum, endNum, growthPattern, urlRule, commonUrlArray, commonUrlFrom, urls);

        return urls;
    }

    /**
     * 该爬虫任务的源链接列表
     *
     * @param dto
     */
    public List<String> getTargetUrlList(SpiderConfigInfoDto dto) {
        int startNum = Integer.parseInt(dto.getStartNum());
        int endNum = Integer.parseInt(dto.getEndNum());
        int growthPattern = Integer.parseInt(dto.getGrowthPattern());
        int urlRule = Integer.parseInt(dto.getUrlRule());
        String commonUrl = dto.getCommonUrl();
        if (!commonUrl.contains("http://") && !commonUrl.contains("https://")) {
            commonUrl = "http://" + commonUrl;
        }
        String[] commonUrlArray = commonUrl.split("\\*");
        String commonUrlFrom = commonUrlArray[0];
        List<String> urls = new ArrayList<>();
        switchRule(startNum, endNum, growthPattern, urlRule, commonUrlArray, commonUrlFrom, urls);
        return urls;
    }
    public String[]  getTargetUrlStrings(SpiderConfigInfoDto dto) {
        List<String> urls= getTargetUrlList(dto);
        String[] v=new String[urls.size()];
        v= urls.toArray(v);
        return v;
    }

    /**
     *该爬虫任务的网络请求的请求头
     *
     * @param dto
     */
    public SiteRequestHeaders getTargetSiteRequestParams(SpiderConfigInfoDto dto) {
        SiteRequestHeaders headers=new SiteRequestHeaders();
        headers.setCharset(dto.getCharset());
        headers.setCookies(dto.getCookies());
        headers.setDomain(dto.getDomain());
        headers.setHeaders(dto.getHeaders());
        headers.setUserAgent(dto.getUserAgent());
        headers.setTimeOut(dto.getTimeOut());
        headers.setSleepTime(dto.getSleepTime());
       return headers;
    }

    /**
     * 该爬虫任务的爬取设置信息（开几个线程，睡眠多少等）
     *
     * @param dto
     */
    public SpiderWorkInfo getSpiderWorkInfo(SpiderConfigInfoDto dto) {
        SpiderWorkInfo workInfo=new SpiderWorkInfo();
        workInfo.setThread(dto.getThread());

        workInfo.setUuid(dto.getGroupName()+dto.getItemName()+dto.getCommonUrl().hashCode());
        return workInfo;
    }

    /**
     * 设置该爬虫任务的爬取规则
     *
     * @param dto
     */
    public  List<SpiderConfigInfoDto.MatchField> getSpiderRuleFieldList(SpiderConfigInfoDto dto) {
        List<SpiderConfigInfoDto.MatchField> matchFields=dto.getMatchFields();
        return matchFields;
    }

    /**
     * 配置入库
     *
     * @param dto
     */
    public DataBaseBoot getDataBase(SpiderConfigInfoDto dto) {
        DataBaseBoot dataBaseBoot=null;
        //TODO dothing v
        return dataBaseBoot;
    }


    private void switchRule(int startNum, int endNum, int growthPattern, int urlRule, String[] commonUrlArray, String commonUrlFrom, List<String> urls) {
        switch (urlRule) {
            case 1://等比
                if (growthPattern == 1) {
                    break;
                }
                for (int i = startNum; i <= endNum; i = i * growthPattern) {
                    if (commonUrlArray.length > 1) {
                        String commonUrlTo = commonUrlArray[1];
                        urls.add(commonUrlFrom + i + commonUrlTo);
                    } else {
                        urls.add(commonUrlFrom + i);
                    }
                }
                break;
            case 2://单调递增
                for (int i = startNum; i <= endNum; i = i + growthPattern) {
                    if (commonUrlArray.length > 1) {
                        String commonUrlTo = commonUrlArray[1];
                        urls.add(commonUrlFrom + i + commonUrlTo);
                    } else {
                        urls.add(commonUrlFrom + i);
                    }
                }
                break;
            case 3://单调递减
                for (int i = startNum; i <= endNum; i = i - growthPattern) {
                    if (commonUrlArray.length > 1) {
                        String commonUrlTo = commonUrlArray[1];
                        urls.add(commonUrlFrom + i + commonUrlTo);
                    } else {
                        urls.add(commonUrlFrom + i);
                    }
                }
                break;
        }
    }
}
