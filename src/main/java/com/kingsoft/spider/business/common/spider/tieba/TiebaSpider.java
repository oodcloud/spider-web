package com.kingsoft.spider.business.common.spider.tieba;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangyujie on 2018/1/3.
 */
public class TiebaSpider {
    public Spider execute(String targetUlr, Pipeline pipeline){
        String startUrl = targetUlr;
        HttpClientDownloader downloader = new HttpClientDownloader();
        Html html = downloader.download(startUrl);
        String urls = html.get();
        Pattern pattern = Pattern.compile("pn=\\d+\" class=\"last");
        Pattern numberPattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(urls);
        Long pageSize = 0L;
        while (matcher.find()) {
            String group1 = matcher.group();
            Matcher matcher1 = numberPattern.matcher(group1);
            if (matcher1.find()) {
                pageSize = Long.valueOf(matcher1.group());
            }
        }
        List<String> urlContainer = new ArrayList<String>();
        for (int i = 0; i <= pageSize; i += 50) {
            String url = startUrl + "&pn=" + i;
            urlContainer.add(url);
        }
        String[] sources = new String[urlContainer.size()];
        sources = urlContainer.toArray(sources);
        int siteId = 2;
        int sleepTime = 3000;
        int thread = 3;
        String domain = UrlUtils.getDomain(startUrl);
        TiebaPageProcessor pageProcessor = new TiebaPageProcessor(domain, siteId, sleepTime, "");
        Spider spider = Spider.create(pageProcessor).addUrl(sources)
                .addPipeline(pipeline).thread(thread);
        spider.runAsync();
        return spider;
    }
}
