package com.kingsoft.spider.business.spidercore;

import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import com.kingsoft.spider.business.spidercore.common.SpiderInfoConvert;
import com.kingsoft.spider.business.spidercore.run.CommonPageProcessor;
import com.kingsoft.spider.business.spidercore.run.CommonPipeline;
import com.kingsoft.spider.business.spidercore.run.SpiderBaseBoot;
import com.kingsoft.spider.business.spidercore.run.SpiderWorkInfo;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * Created by wangyujie on 2018/2/10.
 */
public class SpiderBoot {

    private CommonPageProcessor processor;
    private CommonPipeline pipeline;
    private SpiderInfoConvert convert;
    private String[] urls;
    private SpiderWorkInfo workInfo;

    private void init(SpiderConfigInfoDto dto) {
        convert = new SpiderInfoConvert();
        processor = new CommonPageProcessor(convert.getSpiderRuleFieldList(dto), convert.getTargetSiteRequestParams(dto));
        pipeline = new CommonPipeline(convert.getDataBase(dto));
        urls = convert.getTargetUrlStrings(dto);
        workInfo = convert.getSpiderWorkInfo(dto);
    }

    public Spider react(SpiderConfigInfoDto dto) {
        init(dto);
        Spider spider = Spider.create(processor).addUrl(urls).setScheduler(new QueueScheduler()).addPipeline(pipeline);
        String uuid = workInfo.getUuid();
        String threadNum = workInfo.getThread();
        if (threadNum != null) {
            spider.thread(Integer.parseInt(threadNum));
        }
        if (uuid!=null)
        {
            spider.setUUID(uuid);
        }
        return spider;
    }

    public static void main(String[] args) {
        SpiderBoot boot = new SpiderBoot();

    }


}
