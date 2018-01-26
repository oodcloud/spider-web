package com.kingsoft.spider.business.common.spider.tieba;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangyujie on 2017/12/22.
 */
public class TiebaPageProcessor implements PageProcessor {
    private String domain;
    private int siteId;
    private int sleepTime;
    private String jobInfo;
    private Site site = Site.me();

    public TiebaPageProcessor(String domain, int siteId, int sleepTime, String jobInfo) {
        this.domain = domain;
        this.siteId = siteId;
        this.sleepTime = sleepTime;
        this.jobInfo = jobInfo;
    }

    @Override
    public Site getSite() {
        site.setUserAgent(
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
                .addHeader("Referer", "http://www.baidu.com").setTimeOut(15000).setRetryTimes(3)
                .setSleepTime(sleepTime);
        site.setDomain(this.domain);
        return site;
    }

    /**
     * 处理流程
     */
    @Override
    public void process(Page page) {
        String sourceUrl = page.getUrl().get();
        if (!sourceUrl.contains("/p/")) { //详细列表
            String initSourceUrl = page.getHtml().replace("<!--", "").replace("-->", "").replace("<style style=\"text/css\"></style>", "").replace("id=\"\"", "").get();
            Html html = new Html(initSourceUrl);
            List<String> targetUrlList = html.xpath("//div[@class=\"threadlist_title\"]/a/@href").replace("/p", "http://tieba.baidu.com/p").all();
            page.addTargetRequests(targetUrlList);
        } else {//原始列表
            // 判断内容页或者详情页
            String handleString = page.getHtml().replace("<!--", "").replace("-->", "").get();
            Html html = new Html(handleString);
            String title = html.xpath("//div[@class='core_title_wrap_bright clearfix']/h3/@title").get();
            if (title == null) {
                title = html.xpath("//div[@class='core_title core_title_theme_bright']/h1/@title").get();
            }
            String oneContent = html.$(".d_post_content_firstfloor cc div", "text").get();
            // class="l_post l_post_bright j_l_post clearfix "
            List<String> getTime = html.$(".l_post.j_l_post.l_post_bright")
                    .regex("\\\"tail-info\\\">(\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2})<\\/span>").all();
            String source = html.$(".card_title_fname", "text").get();
            List<String> allRepeatTime;
            if (getTime.size() > 0) {
                allRepeatTime = getTime;
            } else {
                allRepeatTime = html.$(".l_post_bright", "data-field").regex("date\":\"([\\w\\- :]{16})").all();
            }
            String publicTime;
            if (allRepeatTime.size() > 0) {
                publicTime = allRepeatTime.get(0);
            } else {
                page.setSkip(true);
                return;
            }
            String pageTotal = html.$(".l_posts_num:eq(0) .l_reply_num span.red:eq(1)", "text").get();
            String author = html.$(".louzhubiaoshi.j_louzhubiaoshi", "author").get();
            int totalFloors = 0;//contains(@class, 'entry-title') //d_post_content j_d_post_content
            List<String> floorList = html.xpath("//div[contains(@class, 'd_post_content') and contains(@class, 'j_d_post_content')]/text()").all();
            List<String> authors = html.xpath("//ul[@class='p_author']/li[@class='d_name']/a/text()").all();
            String thisUrl = page.getUrl().get();
            totalFloors += floorList.size();

            int currentPage = 1;
            if (allRepeatTime.size() > 1) {
                int pageTotalInt = Integer.valueOf(pageTotal);
                if (pageTotalInt > 1) {
                    while (currentPage <= pageTotalInt) {
                        String pageUrl = thisUrl + "?pn=" + String.valueOf(currentPage + 1);
                        HttpClientDownloader downloader = new HttpClientDownloader();
                        Page pagePage = downloader.download(new Request(pageUrl), this.getSite().toTask());
                        try {
                            Html pageHtml = pagePage.getHtml();
                            // 分页里面的楼层内容
                            List<String> pageFloor = pageHtml.xpath("//div[contains(@class, 'd_post_content') and contains(@class, 'j_d_post_content')]/text()").all();
                            // 分页里面的楼层作者
                            List<String> pageAuthors = pageHtml.xpath("//ul[@class='p_author']/li[@class='d_name']/a/text()").all();
                            floorList.addAll(pageFloor);
                            authors.addAll(pageAuthors);
                            // 分页里面的page
                            List<String> pageRepeatTime;
                            List<String> pageGetTime = pageHtml.$(".l_post.j_l_post.l_post_bright")
                                    .regex("\\\"tail-info\\\">(\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2})<\\/span>")
                                    .all();
                            if (pageGetTime.size() > 0) {
                                pageRepeatTime = pageGetTime;
                            } else {
                                pageRepeatTime = pageHtml.$(".l_post_bright", "data-field")
                                        .regex("date\":\"([\\w\\- :]{16})").all();
                            }
                            allRepeatTime.addAll(pageRepeatTime);
                            totalFloors = totalFloors + pageFloor.size();
                            pageTotalInt = Integer
                                    .valueOf(html.$(".l_posts_num:eq(0) .l_reply_num span.red:eq(1)", "text").get());
                        } catch (Exception e) {
                        }
                        currentPage++;

                    }
                }
            }
//            page.putField("siteId", siteId);
            page.putField("title", title);
//            page.putField("source", source);
//            page.putField("oneContent", oneContent);
//            page.putField("publishTime", publicTime);
//            page.putField("totalFloor", totalFloors);
            page.putField("authors", authors);
            page.putField("allContent", floorList);
            page.putField("allRepeatTime", allRepeatTime);
            String pageUrl = page.getUrl().get();
            page.putField("url", pageUrl);
        }
    }

//    public static void main(String[] args) {
//        String startUrl = "https://tieba.baidu.com/f?kw=%E8%87%AA%E7%94%B1%E7%A6%81%E5%8C%BA&ie=utf-8";
//        HttpClientDownloader downloader = new HttpClientDownloader();
//        Html html = downloader.download(startUrl);
//        String urls = html.get();
//        Pattern pattern = Pattern.compile("pn=\\d+\" class=\"last");
//        Pattern numberPattern = Pattern.compile("\\d+");
//        Matcher matcher = pattern.matcher(urls);
//        Long pageSize = 0L;
//        while (matcher.find()) {
//            String group1 = matcher.group();
//            Matcher matcher1 = numberPattern.matcher(group1);
//            if (matcher1.find()) {
//                pageSize = Long.valueOf(matcher1.group());
//            }
//        }
//        List<String> urlContainer = new ArrayList<String>();
//        for (int i = 0; i <= pageSize; i += 50) {
//            String url = startUrl + "&pn=" + i;
//            urlContainer.add(url);
//        }
//        String[] strings = new String[urlContainer.size()];
//        strings = urlContainer.toArray(strings);
//        int siteId = 2;
//        int sleepTime = 1500;
//        int thread = 1;
//        String domain = UrlUtils.getDomain(startUrl);
//        TiebaPageProcessor pageProcessor = new TiebaPageProcessor(domain, siteId, sleepTime, "");
//        Spider spider = Spider.create(pageProcessor).addUrl("http://tieba.baidu.com/p/2213791194").setScheduler(new QueueScheduler())
//                .addPipeline(new FilePipeline("tieba")).thread(thread);
//        spider.start();
//    }
}
