package com.kingsoft.spider.business.spidercore.run;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import jxl.write.DateTime;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wangyujie on 2018/2/10.
 */
public class CommonPageProcessor implements PageProcessor {
    private final Logger logger = LoggerFactory.getLogger(CommonPageProcessor.class);
    private List<SpiderConfigInfoDto.MatchField> matchFields;
    private SiteRequestHeaders targetSiteRequestParams;
    private Site site = Site.me();
    private String targetUrl;

    public CommonPageProcessor(List<SpiderConfigInfoDto.MatchField> matchFields, String targetUrl, SiteRequestHeaders targetSiteRequestParams) {
        this.matchFields = matchFields;
        this.targetSiteRequestParams = targetSiteRequestParams;
        this.targetUrl = targetUrl;
    }

    @Override
    public void process(Page page) {
        if (!page.getUrl().regex(targetUrl).match()) {
            List<String> urls = page.getHtml().links().regex(targetUrl).all();
            logger.info("=======================================");
            logger.info("targetUrl:"+targetUrl);
            logger.info("links:"+JSON.toJSONString(page.getHtml().links()));
            logger.info("urls:"+JSON.toJSONString(urls));
            logger.info("=======================================");

            List<String> sites=new ArrayList<>(urls.size());
            for (int i = 0; i < urls.size(); i++) {
                if (!urls.get(i).contains("http://")&&!urls.get(i).contains("https://"))
                {
                    sites.add("http://"+urls.get(i));
                }else {
                    sites.add(urls.get(i));
                }
            }
            page.addTargetRequests(sites);

        } else {
            for (SpiderConfigInfoDto.MatchField field : matchFields) {

                if (field.getRegex() != null && !"".equals(field.getRegex()) && field.getXpath() != null && !"".equals(field.getXpath())) {
                    page.putField(field.getFieldEnglishName(), page.getHtml().regex(field.getRegex()).xpath(field.getXpath()).get());
                } else if ((field.getRegex() == null || "".equals(field.getRegex())) && field.getXpath() != null && !"".equals(field.getXpath())) {
                    page.putField(field.getFieldEnglishName(), page.getHtml().xpath(field.getXpath()).get());
                } else if (field.getRegex() != null && !"".equals(field.getRegex()) && (field.getXpath() == null || "".equals(field.getXpath()))) {
                    page.putField(field.getFieldEnglishName(), page.getHtml().regex(field.getRegex()).get());
                } else if (field.getDefaultValue() != null && !"".equals(field.getDefaultValue())) {
                    page.putField(field.getFieldEnglishName(), field.getDefaultValue());
                }
            }
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            page.putField("creat_time", timestamp);
            page.putField("site_url", page.getUrl().toString());
            int flag = 0;
            for (SpiderConfigInfoDto.MatchField field : matchFields) {
                if (page.getResultItems().get(field.getFieldEnglishName()) == null || "".equals(page.getResultItems().get(field.getFieldEnglishName()))) {
                    flag++;
                }
            }
            if (flag > 2) {
                page.setSkip(true);
            }
        }
    }

    @Override
    public Site getSite() {
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getCharset())) {
            site.setCharset(targetSiteRequestParams.getCharset());
        } else {
            site.setCharset("UTF-8");
        }
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getCookies())) {
            site.addHeader("Cookie", targetSiteRequestParams.getCookies());
        }
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getDomain())) {
            site.setDomain(targetSiteRequestParams.getDomain());
        }
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getHeaders())) {
            String headers = targetSiteRequestParams.getHeaders();
            String[] params = headers.split("\\|");
            for (String param : params) {
                String[] var = param.split("\\||");
                if (var.length > 1) {
                    site.addHeader(var[0], var[1]);
                }
            }
        }
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getSleepTime())) {
            site.setSleepTime(Integer.parseInt(targetSiteRequestParams.getSleepTime()));
        } else {
            site.setSleepTime(500);
        }
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getTimeOut())) {
            site.setTimeOut(Integer.parseInt(targetSiteRequestParams.getTimeOut()));
        } else {
            site.setTimeOut(3000);
        }
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getUserAgent())) {
            site.setUserAgent(targetSiteRequestParams.getUserAgent());
        }
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getRetryTimes())) {
            site.setRetryTimes(Integer.parseInt(targetSiteRequestParams.getRetryTimes()));
        } else {
            site.setRetryTimes(3);
        }
        return site;
    }
}
