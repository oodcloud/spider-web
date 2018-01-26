package com.kingsoft.spider.business.common.spider.bbs;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * Created by wangyujie on 2017/12/23.
 */

public class bbsPageProcessor implements PageProcessor {
    private Site site = Site.me();


    @Override
    public void process(Page page) {
        String url = page.getUrl().get();
        if (url.contains("forumdisplay")) {
            List<String> targetUrlList = page.getHtml().xpath("//a[@class=\"xst\"]/@href").replace("forum.php", "http://ext.bbs.xoyo.com/forum.php").all();
            page.addTargetRequests(targetUrlList);
        } else {
            List<String> strings = page.getHtml().xpath("//td[@class=\"t_f\"]/text()").all();
            List<String> authors= page.getHtml().xpath("//div[@class='pi']/div[@class=\"authi\"]/a/text()").all();
            List<String> times = page.getHtml().xpath("//div[@class='authi']/em/text()").replace("发表于 ", "").all();
            List<String> times1 = page.getHtml().xpath("//div[@class='authi']/em/span/@title").all();
            String title = page.getHtml().xpath("//span[@id='thread_subject']/text()").get();
            int j = 0;
            for (int i = 0; i < times.size(); i++) {
                if (times.get(i).equals("") && j < times1.size()) {
                    times.set(i, times1.get(j));
                    j++;
                }
            }
            int times1a = times.size();
            List<String> sub = times.subList(strings.size(), times1a);
            times.removeAll(sub);
            List<String> allulr = page.getHtml().xpath("//div[@id=\"pgt\"]//div[@class=\"pg\"]/a/@href").all();
            if (allulr.size() > 2) {
                String aa = allulr.get(allulr.size() - 2);
                if (aa != null) {
                    int currentPageSize = 2;
                    int pageSize = Integer.parseInt(aa.split("page=")[1]);
                    HttpClientDownloader downloader = new HttpClientDownloader();
                    while (currentPageSize < pageSize) {
                        String innerUrl = page.getUrl().get().replaceAll("&page=", "") + "&page=" + "" + currentPageSize;
                        Html html = downloader.download(innerUrl);
                        List<String> innerContent = html.xpath("//td[@class=\"t_f\"]/text()").all();
                        List<String> innerAuthor= html.xpath("//div[@class='pi']/div[@class=\"authi\"]/a/text()").all();
                        List<String> innertimes = html.xpath("//div[@class='authi']/em/text()").replace("发表于 ", "").all();
                        List<String> innertimes1 = html.xpath("//div[@class='authi']/em/span/@title").all();
                        j = 0;
                        for (int i = 0; i < innertimes.size(); i++) {
                            if (innertimes.get(i).equals("") && j < innertimes1.size()) {
                                innertimes.set(i, innertimes1.get(j));
                                j++;
                            }
                        }
                        times1a = innertimes.size();
                        sub = innertimes.subList(innerContent.size(), times1a);
                        innertimes.removeAll(sub);
                        times.addAll(innertimes);
                        strings.addAll(innerContent);
                        authors.addAll(innerAuthor);
                        currentPageSize++;
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            page.putField("title", title);
            page.putField("content", strings);
            page.putField("times", times);
            page.putField("author", authors);
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
//        String startUrl = "http://ext.bbs.xoyo.com/forum.php?mod=forumdisplay&fid=50";
//        HttpClientDownloader downloader = new HttpClientDownloader();
//        Html html = downloader.download(startUrl);
//        List<String> aa = html.xpath("//div[@id=\"pgt\"]//div[@class=\"pg\"]/a/@href").all();
//        String ab = aa.get(aa.size() - 2);
//        int pageSize = Integer.parseInt(ab.split("page=")[1]);
//        System.out.println(pageSize);
//        List<String> sourceUrlList = new ArrayList<>();
//        for (int i = 0; i <= pageSize; i++) {
//            sourceUrlList.add("http://ext.bbs.xoyo.com/forum.php?mod=forumdisplay&fid=50&page=" + i);
//        }
//        String[] sources = new String[sourceUrlList.size()];
//        sourceUrlList.toArray(sources);
//        Spider.create(new bbsPageProcessor()).addUrl("http://ext.bbs.xoyo.com/forum.php?mod=viewthread&tid=10398&extra=page%3D1&page=2").addPipeline(new FilePipeline("bbs")).thread(1).start();
//
//
//    }
}
