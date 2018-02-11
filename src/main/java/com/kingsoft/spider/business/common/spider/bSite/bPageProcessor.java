package com.kingsoft.spider.business.common.spider.bSite;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.common.spider.bSite.dto.CommentDto;
import com.kingsoft.spider.business.common.spider.bSite.dto.CommentParseXml;
import com.kingsoft.spider.business.common.spider.bSite.dto.DiscussCommentParseJson;
import com.kingsoft.spider.business.spidercore.monitor.SpiderMonitorBootstrap;
import com.kingsoft.spider.business.spidercore.monitor.SpiderStatusBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by wangyujie on 2017/12/24.
 */
public class bPageProcessor implements PageProcessor {
    private Site site = Site.me();
    private final Logger logger = LoggerFactory.getLogger(bPageProcessor.class);

    @Override
    public void process(Page page) {
        String url = page.getUrl().get();
        if (url.contains("?keyword=")) {
            List<String> targetUrlList = page.getHtml().xpath("//div[@class='so-wrap']//li[@class='video matrix']/a/@href").replace("//", "https://").all();
            page.addTargetRequests(targetUrlList);
        } else {
            String content = page.getHtml().xpath("//div[@class='scontent']/script").get().split(",")[2];
            String title = page.getHtml().xpath("//div[@class='v-title']/h1/text()").get();
            String cid = content.split("&aid=")[0].substring(6);
            String aid = content.split("&aid=")[1].substring(0, 8);//url上
            String commentUrl = "https://comment.bilibili.com/" + cid + ".xml";
            String discussUrl = "https://api.bilibili.com/x/v2/reply?jsonp=jsonp&pn=2&type=1&oid=" + aid;
            CommentParseXml commentParseXmlXml = new CommentParseXml();
            DiscussCommentParseJson discussCommentParseJson = new DiscussCommentParseJson();
            List<CommentDto> commentDtos = commentParseXmlXml.convert(commentUrl);
            List<CommentDto> discussDtos = discussCommentParseJson.convert(discussUrl);
            logger.info("当前url:" + page.getUrl().get());
            logger.info("b站评论数量为：" + discussDtos.size());
            logger.info("b站弹幕数量为：" + commentDtos.size());
            page.putField("comment", JSON.toJSONString(commentDtos));
            page.putField("discuss", JSON.toJSONString(discussDtos));
            page.putField("title", title);
        }
    }

    @Override
    public Site getSite() {
        site.setUserAgent(
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
                .setTimeOut(15000).setRetryTimes(3)
                .setSleepTime(1000);
        return site;
    }

    public static void main(String[] args) {
        String startUrl = "https://search.bilibili.com/video?keyword=%E8%87%AA%E7%94%B1%E7%A6%81%E5%8C%BA";
//        HttpClientDownloader downloader = new HttpClientDownloader();
//        Request request=new Request(startUrl);
//        request.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
//                .addHeader("Cookie", "sid=cfwin8lm; LIVE_BUVID=fdd3b6bd2dea8bdd34b7f6d82645c935; LIVE_BUVID__ckMd5=ec70286b12f5bf7b; fts=1507523479; UM_distinctid=15f13f03be618-0de6bcce289b69-3b3e5906-13c680-15f13f03be71cb; pgv_pvi=2600289280; buvid3=F843344E-079B-4A47-BCBD-8B6B42E98BB334442infoc; rpdid=kxslwpwlmldoswmqxmqxw; LIVE_PLAYER_TYPE=2; finger=edc6ecda")
//                .addHeader("Host","search.bilibili.com");
//        Task task =new Task() {
//            @Override
//            public String getUUID() {
//                return null;
//            }
//
//            @Override
//            public Site getSite() {
//                Site site=Site.me();
//                    site.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
//                        .setUserAgent(
//                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
//                        .addHeader("Cookie", "sid=cfwin8lm; LIVE_BUVID=fdd3b6bd2dea8bdd34b7f6d82645c935; LIVE_BUVID__ckMd5=ec70286b12f5bf7b; fts=1507523479; UM_distinctid=15f13f03be618-0de6bcce289b69-3b3e5906-13c680-15f13f03be71cb; pgv_pvi=2600289280; buvid3=F843344E-079B-4A47-BCBD-8B6B42E98BB334442infoc; rpdid=kxslwpwlmldoswmqxmqxw; LIVE_PLAYER_TYPE=2; finger=edc6ecda")
//                        .addHeader("Host","search.bilibili.com")
//                        .setTimeOut(15000).setRetryTimes(3)
//                        .setSleepTime(300);
//                return site;
//            }
//        };
//
//        Html html =  downloader.download(request,task).getHtml();
//        System.out.println(html.get());
//        String size = html.xpath("//li[@class='page-item last']/button/text()").get();
//        List<String> sourceUrlList = new ArrayList<>();
////        int pageSize = Integer.valueOf(size);
//        for (int i = 1; i <= 1000; i++) {
//            startUrl = startUrl + "&page=" + i + "&order=totalrank";
//            sourceUrlList.add(startUrl);
//        }
//        String[] sources = new String[sourceUrlList.size()];
//        sourceUrlList.toArray(sources);
        Spider spider = Spider.create(new bPageProcessor());
        spider.addUrl("https://www.bilibili.com/video/av17510929/?from=search&seid=1138118360527282788").addPipeline(new FilePipeline("b站")).thread(1).start();
        SpiderMonitorBootstrap bootstrap = SpiderMonitorBootstrap.instance().register(new Spider[]{spider});

        List<SpiderStatusBean> spiderStatusMXBeans = bootstrap.getSpiderStatuses();
        List<String> strings = spiderStatusMXBeans.get(0).getAllResultList();
        while (true) {
            System.out.println("当前大小：" + strings.size());
            if (strings.size() != 0) {
                    System.out.println("数据结果：" + JSON.toJSONString(strings));
                break;
            } else {
                System.out.println("接不到数据，等待中。。。。");

            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
