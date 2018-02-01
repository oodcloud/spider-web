package com.kingsoft.spider.business.common.task;

import com.kingsoft.spider.business.common.spider.bSite.bPageProcessor;
import com.kingsoft.spider.business.common.spider.bSite.pipeline.BExtopiaPipeline;
import com.kingsoft.spider.business.common.spider.bSite.pipeline.BMatPipeline;
import com.kingsoft.spider.business.common.spider.bSite.pipeline.BXmqzPipeline;
import com.kingsoft.spider.business.common.spider.bbs.bbsPageProcessor;
import com.kingsoft.spider.business.common.spider.bbs.pipeline.bbsPipeline;
import com.kingsoft.spider.business.common.spider.tieba.TiebaSpider;
import com.kingsoft.spider.business.common.spider.tieba.pipeline.TiebaExtopiaPipeline;
import com.kingsoft.spider.business.common.spider.tieba.pipeline.TiebaMatPipeline;
import com.kingsoft.spider.business.common.spider.tieba.pipeline.TiebaXmqzPipeline;
import com.kingsoft.spider.business.common.spider.wechat.WechatProcessor;
import com.kingsoft.spider.business.common.spider.wechat.pipeline.WechatExtopiaPipeline;
import com.kingsoft.spider.business.common.spider.wechat.pipeline.WechatMatPipeline;
import com.kingsoft.spider.business.common.spider.wechat.pipeline.WechatXmqzPipeline;
import com.kingsoft.spider.business.common.spider.weibo.WeiboExtopiaProcessor;
import com.kingsoft.spider.business.common.spider.weibo.WeiboMatProcessor;
import com.kingsoft.spider.business.common.spider.weibo.WeiboProducerProcessor;
import com.kingsoft.spider.business.common.spider.weibo.WeiboXmqzProcessor;
import com.kingsoft.spider.business.common.spiderLastTime.service.SpiderLastTimeService;
import com.kingsoft.spider.core.common.support.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyujie on 2017/12/25.
 */
@Configuration
@EnableScheduling
@Scope("singleton")
public class SpiderTask {
    @Qualifier("BExtopiaPipeline")
    @Autowired
    private BExtopiaPipeline bExtopiaPipeline;
    @Qualifier("BMatPipeline")
    @Autowired
    private BMatPipeline bMatPipeline;
    @Qualifier("BXmqzPipeline")
    @Autowired
    private BXmqzPipeline bXmqzPipeline;
    @Qualifier("bbsPipeline")
    @Autowired
    private bbsPipeline bbsPipeline;
    @Qualifier("TiebaExtopiaPipeline")
    @Autowired
    private TiebaExtopiaPipeline tiebaExtopiaPipeline;
    @Qualifier("TiebaMatPipeline")
    @Autowired
    private TiebaMatPipeline tiebaMatPipeline;
    @Qualifier("TiebaXmqzPipeline")
    @Autowired
    private TiebaXmqzPipeline tiebaXmqzPipeline;
    @Qualifier("WeiboMatProcessor")
    @Autowired
    private WeiboMatProcessor weiboMatProcessor;
    @Qualifier("WeiboExtopiaProcessor")
    @Autowired
    private WeiboExtopiaProcessor weiboExtopiaProcessor;
    @Qualifier("WeiboXmqzProcessor")
    @Autowired
    private WeiboXmqzProcessor weiboXmqzProcessor;

    @Qualifier("WeiboProducerProcessor")
    @Autowired
    private WeiboProducerProcessor weiboProducerProcessor;
    @Autowired
    private WechatExtopiaPipeline wechatExtopiaPipeline;
    @Autowired
    private WechatMatPipeline wechatMatPipeline;
    @Autowired
    private WechatXmqzPipeline wechatXmqzPipeline;

    @Autowired
    private SpiderLastTimeService spiderLastTimeService;

    private boolean testFlag = false;

    private final Logger logger = LoggerFactory.getLogger(SpiderTask.class);
    @Scheduled(cron = "0 0 20 ? * MON,THU")
    public void initTask(){
        extopiaWeiboSpider();
        xmqzWeiboSpider();
        matWeiboSpider();
        producerWeiboSpider();
        bExtopiaSpider();
        bXmqzSpider();
        bMatSpider();
        bbsSpider();
    }



//    @Scheduled(cron = "10 5 13 ? * WED")
    public void timedTaskTest() {
        WechatProcessor wechatProcessor = new WechatProcessor("1111696229",
                "pgv_pvi=1517092864; RK=lTMHzV1vNg; tvfe_boss_uuid=db4901e1abbda0bc; eas_sid=21m510G78806A8g3R3J5S7x8O6; pgv_pvid=7692100374; o_cookie=506695073; ptcz=90becdf8a5337bf6e7395a222add9f4ea439a53e3a8dae9e78fbe19a89822815; pt2gguin=o0506695073; pgv_si=s4602012672; uuid=295e5860d80ea735d1d87c202ec4352c; ticket=db69ec453fe6f2d9615d6ac4b1d239bcd640db5f; ticket_id=gh_809a38b751c6; cert=OzNfKhSxlK8W8k0MS1kPNUNuIxCv_Nky; noticeLoginFlag=1; data_bizuin=3206629432; data_ticket=mGC2RF/pisvev8OlHBdhcGZJHjHvttdoku7dH+T1cw1TMsFqDcFQWQmmecDj6UD/; ua_id=8bsl8QwVYpz1SjZDAAAAAAeS_UwZB3jFPmzCz9II7nY=; xid=2a5ecd84cc242da32ba272bf49567f8d; openid2ticket_oOEghvyuJ4cVnatsqE8aOPzQ8INY=3V9GhWCQ13JXfD+qgPp18fc241Cv9ys4KjxhprIbNSA=; mm_lang=zh_CN; slave_user=gh_809a38b751c6; slave_sid=NmE0eDhBTkdpZGNuN21BRzZsY054Z2d0RnE4eENFRmtKR0JYbjg2YU9uREtQYlBQZ2ZSdFhmZ2FXRTJ3RF9VRU9jeXZ5SU13Rk1GYV9DcE9qY3hwNl83THpRSzAyY3VOT1djZ1dha3ZLUEI2U2dyWkgxOTRHWUJqRjBkZmJQSlVCSnhodnQ0T0k1YWlCcDN3; bizuin=3258505526",
                1000,
                3
        );
        wechatProcessor.init(wechatExtopiaPipeline);
    }

    public void bbsSpider() {
        logger.info("bbsSpider start  ====================================================================================");
        String startUrl = "http://ext.bbs.xoyo.com/forum.php?mod=forumdisplay&fid=50";
        HttpClientDownloader downloader = new HttpClientDownloader();
        Html html = downloader.download(startUrl);
        List<String> aa = html.xpath("//div[@id=\"pgt\"]//div[@class=\"pg\"]/a/@href").all();
        String ab = aa.get(aa.size() - 2);
        int pageSize = Integer.parseInt(ab.split("page=")[1]);
        List<String> sourceUrlList = new ArrayList<>();
        for (int i = 0; i <= pageSize; i++) {
            sourceUrlList.add("http://ext.bbs.xoyo.com/forum.php?mod=forumdisplay&fid=50&page=" + i);
        }
        String[] sources = new String[sourceUrlList.size()];
        sourceUrlList.toArray(sources);
        Spider  spider = Spider.create(new bbsPageProcessor()).addUrl(sources).addPipeline(bbsPipeline).setScheduler(new QueueScheduler()).thread(5);
        spider.runAsync();

        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int num = spider.getThreadAlive();
            if (num == 0 && spider.isExitWhenComplete()) {
                logger.info("插入完毕,更新自由禁区论坛当前时间:" + System.currentTimeMillis() + "获得页面数量：" + spider.getPageCount());
                spiderLastTimeService.updateLastTime("extopiaBBS",System.currentTimeMillis()/1000);
                logger.info("bbsSpider end    ====================================================================================");
                break;
            }
        }

    }

    //b站开始

    public void bExtopiaSpider() {
        logger.info("bExtopiaSpider start  ====================================================================================");

        String startUrl = "https://search.bilibili.com/video?keyword=%E8%87%AA%E7%94%B1%E7%A6%81%E5%8C%BA";
        HttpClientDownloader downloader = new HttpClientDownloader();
        Html html = downloader.download(startUrl);
        String size = html.xpath("//body/@data-num_pages").get();
        List<String> sourceUrlList = new ArrayList<>();
        int pageSize = Integer.valueOf(size);
        for (int i = 1; i <= pageSize; i++) {
            String url = startUrl + "&page=" + i + "&order=totalrank";
            sourceUrlList.add(url);
        }
        String[] sources = new String[sourceUrlList.size()];
        sourceUrlList.toArray(sources);
        Spider spider = Spider.create(new bPageProcessor()).addUrl(sources).addPipeline(bExtopiaPipeline).thread(2);
        spider.runAsync();
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int num = spider.getThreadAlive();
            if (num == 0 && spider.isExitWhenComplete()) {
                logger.info("插入完毕,更新B站自由禁区当前时间:" + System.currentTimeMillis() + "获得页面数量：" + spider.getPageCount());
                spiderLastTimeService.updateLastTime("extopiaB",System.currentTimeMillis()/1000);
                logger.info("bExtopiaSpider end    ====================================================================================");
                break;
            }
        }


    }

    public void bMatSpider() {
        logger.info("bMatSpider start  ====================================================================================");

        String startUrl = "https://search.bilibili.com/video?keyword=MAT反恐行动";
        HttpClientDownloader downloader = new HttpClientDownloader();
        Html html = downloader.download(startUrl);
        String size = html.xpath("//body/@data-num_pages").get();
        List<String> sourceUrlList = new ArrayList<>();
        int pageSize = Integer.valueOf(size);
        for (int i = 1; i <= pageSize; i++) {
            String url = startUrl + "&page=" + i + "&order=totalrank";
            sourceUrlList.add(url);
        }
        String[] sources = new String[sourceUrlList.size()];
        sourceUrlList.toArray(sources);
        Spider spider = Spider.create(new bPageProcessor()).addUrl(sources).addPipeline(bMatPipeline).thread(2);
        spider.runAsync();
        while (true)
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int num = spider.getThreadAlive();
            if (num == 0 && spider.isExitWhenComplete()) {
                logger.info("插入完毕,更新B站反恐行动当前时间:" + System.currentTimeMillis() + "获得页面数量：" + spider.getPageCount());
                spiderLastTimeService.updateLastTime("matB",System.currentTimeMillis()/1000);
                logger.info("bMatSpider end  ====================================================================================");
                break;
            }
        }

    }

    public void bXmqzSpider() {
        logger.info("bXmqzSpider start  ====================================================================================");
        String startUrl = "https://search.bilibili.com/video?keyword=%E5%B0%8F%E7%B1%B3%E6%9E%AA%E6%88%98";
        HttpClientDownloader downloader = new HttpClientDownloader();
        Html html = downloader.download(startUrl);
        String size = html.xpath("//body/@data-num_pages").get();
        List<String> sourceUrlList = new ArrayList<>();
        int pageSize = Integer.valueOf(size);
        for (int i = 1; i <= pageSize; i++) {
            String url = startUrl + "&page=" + i + "&order=totalrank";
            sourceUrlList.add(url);
        }
        String[] sources = new String[sourceUrlList.size()];
        sourceUrlList.toArray(sources);
        Spider spider = null;
        if (testFlag) {
            spider = Spider.create(new bPageProcessor()).addUrl("https://www.bilibili.com/video/av15641391/?from=search&seid=9218226015322658248").addPipeline(bXmqzPipeline).thread(2);
        } else {
            spider = Spider.create(new bPageProcessor()).addUrl(sources).addPipeline(bXmqzPipeline).thread(2);
        }
        spider.runAsync();
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int num = spider.getThreadAlive();
            if (num == 0 && spider.isExitWhenComplete()) {
                logger.info("插入完毕,更新B站小米枪战当前时间:" + System.currentTimeMillis() + "获得页面数量：" + spider.getPageCount());
                spiderLastTimeService.updateLastTime("xmqzB",System.currentTimeMillis()/1000);
                logger.info("bXmqzSpider end  ====================================================================================");
                break;
            }
        }

    }

    //b站结束
    //贴吧开始
    public void extopiaTiebaSpider() {
        logger.info("extopiaTiebaSpider start  ====================================================================================");
        String startUrl = "https://tieba.baidu.com/f?kw=%E8%87%AA%E7%94%B1%E7%A6%81%E5%8C%BA&ie=utf-8";
        TiebaSpider spider = new TiebaSpider();
        Spider spider1 = spider.execute(startUrl, tiebaExtopiaPipeline);
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int num = spider1.getThreadAlive();
            if (num == 0 && spider1.isExitWhenComplete()) {
                logger.info("插入完毕,更新贴吧自由禁区当前时间:" + System.currentTimeMillis() + "获得页面数量：" + spider1.getPageCount());
                spiderLastTimeService.updateLastTime("extopiaTieba",System.currentTimeMillis()/1000);
                logger.info("extopiaTiebaSpider end  ====================================================================================");

                break;
            }
        }
    }

    public void matTiebaSpider() {
        logger.info("matTiebaSpider start  ====================================================================================");
        String startUrl = "https://tieba.baidu.com/f?kw=%E5%8F%8D%E6%81%90%E8%A1%8C%E5%8A%A8&ie=utf-8";
        TiebaSpider spider = new TiebaSpider();
        Spider spider1 = spider.execute(startUrl, tiebaMatPipeline);
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int num = spider1.getThreadAlive();
            if (num == 0 && spider1.isExitWhenComplete()) {
                logger.info("插入完毕,更新贴吧反恐行动当前时间:" + System.currentTimeMillis() + "获得页面数量：" + spider1.getPageCount());
                spiderLastTimeService.updateLastTime("matTieba",System.currentTimeMillis()/1000);
                logger.info("matTiebaSpider end  ====================================================================================");
                break;
            }
        }
    }
   
    public void xmqzTiebaSpider() {
        logger.info("xmqzTiebaSpider start  ====================================================================================");
        String startUrl = "http://tieba.baidu.com/f?kw=%E5%B0%8F%E7%B1%B3%E6%9E%AA%E6%88%98&ie=utf-8";
        TiebaSpider spider = new TiebaSpider();
        Spider spider1 = spider.execute(startUrl, tiebaXmqzPipeline);
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int num = spider1.getThreadAlive();
            if (num == 0 && spider1.isExitWhenComplete()) {
                logger.info("插入完毕,更新贴吧小米枪战当前时间:" + System.currentTimeMillis() + "获得页面数量：" + spider1.getPageCount());
                spiderLastTimeService.updateLastTime("xmqzTieba",System.currentTimeMillis()/1000);
                logger.info("xmqzTiebaSpider end  ====================================================================================");
                break;
            }
        }
    }
    //贴吧结束
    //    微博开始
    public void extopiaWeiboSpider() {
        logger.info("extopiaWeiboSpider start  ====================================================================================");
        String url = "https://m.weibo.cn/api/container/getIndex?type=uid&value=6049543304&containerid=1076036049543304";
        weiboExtopiaProcessor.execute(url);
        spiderLastTimeService.updateLastTime("extopiaWeibo",System.currentTimeMillis()/1000);
        logger.info("extopiaWeiboSpider end  ====================================================================================");
    }

    public void xmqzWeiboSpider() {
        logger.info("xmqzWeiboSpider start  ====================================================================================");

        String url = "https://m.weibo.cn/api/container/getIndex?type=uid&value=5940839599&containerid=1076035940839599";
        weiboXmqzProcessor.execute(url);
        spiderLastTimeService.updateLastTime("xmqzWeibo",System.currentTimeMillis()/1000);
        logger.info("xmqzWeiboSpider end  ====================================================================================");

    }

    public void matWeiboSpider() {
        logger.info("matWeiboSpider start  ====================================================================================");
        String url = "https://m.weibo.cn/api/container/getIndex?type=uid&value=1692324120&containerid=1076031692324120";
        weiboMatProcessor.execute(url);
        spiderLastTimeService.updateLastTime("matWeibo",System.currentTimeMillis()/1000);
        logger.info("matWeiboSpider end  ====================================================================================");

    }

    public void producerWeiboSpider() {
        logger.info("producerWeiboSpider start  ====================================================================================");

        String url = "https://m.weibo.cn/api/container/getIndex?type=uid&value=6418341677&containerid=1076036418341677";
        weiboProducerProcessor.execute(url);
        spiderLastTimeService.updateLastTime("producerWeibo",System.currentTimeMillis()/1000);
        logger.info("producerWeiboSpider end  ====================================================================================");

    }

}
