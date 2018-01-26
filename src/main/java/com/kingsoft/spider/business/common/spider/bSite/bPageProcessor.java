package com.kingsoft.spider.business.common.spider.bSite;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.common.spider.bSite.dto.CommentDto;
import com.kingsoft.spider.business.common.spider.bSite.dto.CommentParseXml;
import com.kingsoft.spider.business.common.spider.bSite.dto.DiscussCommentParseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyujie on 2017/12/24.
 */
public class bPageProcessor implements PageProcessor {
    private Site site = Site.me();
    private final Logger logger= LoggerFactory.getLogger(bPageProcessor.class);
    @Override
    public void process(Page page) {
        String url = page.getUrl().get();
        if (url.contains("?keyword=")) {
            List<String> targetUrlList = page.getHtml().xpath("//div[@class='so-wrap']//li[@class='video matrix']/a/@href").replace("//", "https://").all();
            page.addTargetRequests(targetUrlList);
        } else {
            String content = page.getHtml().xpath("//div[@class='scontent']/script").get().split(",")[2];
            String title=page.getHtml().xpath("//div[@class='v-title']/h1/text()").get();
            String cid = content.split("&aid=")[0].substring(6);
            String aid = content.split("&aid=")[1].substring(0, 8);//url上
            String commentUrl = "https://comment.bilibili.com/" + cid + ".xml";
            String discussUrl = "https://api.bilibili.com/x/v2/reply?jsonp=jsonp&pn=2&type=1&oid=" + aid;
            CommentParseXml commentParseXmlXml = new CommentParseXml();
            DiscussCommentParseJson discussCommentParseJson = new DiscussCommentParseJson();
            List<CommentDto> commentDtos = commentParseXmlXml.convert(commentUrl);
            List<CommentDto> discussDtos = discussCommentParseJson.convert(discussUrl);
            logger.info("当前url:"+page.getUrl().get());
            logger.info("b站评论数量为："+discussDtos.size());
            logger.info("b站弹幕数量为："+commentDtos.size());
            page.putField("comment", JSON.toJSONString(commentDtos));
            page.putField("discuss", JSON.toJSONString(discussDtos));
            page.putField("title",title);
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

//    public static void main(String[] args) {
//        String startUrl = "https://search.bilibili.com/video?keyword=%E8%87%AA%E7%94%B1%E7%A6%81%E5%8C%BA";
//        HttpClientDownloader downloader = new HttpClientDownloader();
//        Html html = downloader.download(startUrl);
//        String size = html.xpath("//body/@data-num_pages").get();
//        List<String> sourceUrlList = new ArrayList<>();
//        int pageSize = Integer.valueOf(size);
//        for (int i = 1; i <= pageSize; i++) {
//            startUrl = startUrl + "&page=" + i + "&order=totalrank";
//            sourceUrlList.add(startUrl);
//        }
//        String[] sources = new String[sourceUrlList.size()];
//        sourceUrlList.toArray(sources);
//        Spider.create(new bPageProcessor()).addUrl("https://www.bilibili.com/video/av17510929/?from=search&seid=1138118360527282788").addPipeline(new FilePipeline("b站")).thread(1).start();
//    }

}
