package com.kingsoft.spider.business.spidercore.run;

import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import com.kingsoft.spider.business.spidercore.common.SpiderInfoConvert;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by wangyujie on 2018/2/10.
 */
public class CommonPageProcessor implements PageProcessor {
    private List<SpiderConfigInfoDto.MatchField> matchFields;
    private SiteRequestHeaders targetSiteRequestParams;
    private Site site=Site.me();
    public CommonPageProcessor(List<SpiderConfigInfoDto.MatchField> matchFields,SiteRequestHeaders targetSiteRequestParams) {
        this.matchFields = matchFields;
        this.targetSiteRequestParams=targetSiteRequestParams;
    }

    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getCharset()))
        {
            site.setCharset(targetSiteRequestParams.getCharset());
        }
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getCookies()))
        {
            site.addHeader("Cookie",targetSiteRequestParams.getCookies());
        }
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getDomain()))
        {
            site.setDomain(targetSiteRequestParams.getDomain());
        }
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getHeaders()))
        {
            String headers=targetSiteRequestParams.getHeaders();
            String[] params= headers.split("\\|");
            for (String param:params)
            {
                String[] var= param.split("\\||");
                if (var.length>1)
                {
                    site.addHeader(var[0],var[1]);
                }
            }
        }
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getSleepTime()))
        {
            site.setSleepTime(Integer.parseInt(targetSiteRequestParams.getSleepTime()));
        }
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getTimeOut()))
        {
            site.setTimeOut(Integer.parseInt(targetSiteRequestParams.getTimeOut()));
        }
        if (StringUtils.isNotEmpty(targetSiteRequestParams.getUserAgent()))
        {
            site.setUserAgent(targetSiteRequestParams.getUserAgent());
        }
        site.setRetryTimes(3);
        return site;
    }
}
