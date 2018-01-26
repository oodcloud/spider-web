package com.kingsoft.spider.business.common.spider.xmqz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingsoft.spider.business.common.spider.xmqz.pipeline.ImportPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyujie on 2017/12/27.
 */
public class XmqzPageProcessor implements PageProcessor {
    private Site site = Site.me();
    private final Logger logger= LoggerFactory.getLogger(XmqzPageProcessor.class);

    @Override
    public void process(Page page) {
        String url = page.getUrl().get();

        if (url.contains("newmasssendpage")) {
            String content = page.getRawText();
            if (content != null) {
                JSONObject jsonObject = JSONObject.parseObject(content);
                if (page.getUrl().get().contains("&begin=0")) {
                    Integer total_count = jsonObject.getInteger("total_count");
                    int size = total_count / 7 + 1;
                    List<String> list = new ArrayList<>();
                    for (int i = 1; i <= size; i++) {
                        int num = 7 * i;
                        list.add("https://mp.weixin.qq.com/cgi-bin/newmasssendpage?count=7&begin=" + num + "&token=1017499496&lang=zh_CN&token=1017499496&lang=zh_CN&f=json&ajax=1");
                    }
                   logger.info("XmqzPageProcessor source size:"+list.size());
                    page.addTargetRequests(list);
                }
                JSONArray sent_list = jsonObject.getJSONArray("sent_list");
                List<String> urlAll = new ArrayList<>();
                for (int i = 0; i < sent_list.size(); i++) {
                    List<String> urls = new ArrayList<>();
                    JSONObject sentListItem = sent_list.getJSONObject(i);
                    JSONArray appmsg_info = sentListItem.getJSONArray("appmsg_info");
                    for (int j = 0; j < appmsg_info.size(); j++) {
                        JSONObject appmsgInfoItem = appmsg_info.getJSONObject(j);
                        String content_url = appmsgInfoItem.getString("content_url");
                        urls.add(content_url);
                    }
                    urlAll.addAll(urls);
                }

                logger.info(" XmqzPageProcessor content_url size:"+urlAll.size());
                page.addTargetRequests(urlAll);
            }
        } else if (url.contains("s?__biz")) {
            Html html = page.getHtml();
            String title = html.xpath("//h2[@class='rich_media_title']/text()").get();
            String time=html.xpath("//em[@id='post-date']/text()").get();
            String contents=html.xpath("//div[@class='rich_media_content']").replace("<[^>]*>","").get();
            List<String> imgs=html.xpath("//div[@class='rich_media_content ']//img/@data-src").all();
            page.putField("title",title);
            page.putField("time",time);
            page.putField("contents",contents);
            page.putField("imgs",imgs);
        }

    }

    @Override
    public Site getSite() {
        site.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
//                .addHeader("Referer", "https://mp.weixin.qq.com/cgi-bin/appmsg?begin=0&count=10&t=media/appmsg_list&type=10&action=list_card&lang=zh_CN&token=1017499496")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .setTimeOut(15000)
                .setRetryTimes(5)
                .addCookie("pgv_pvi", "1517092864")
                .addCookie("RK", "lTMHzV1vNg")
                .addCookie("tvfe_boss_uuid", "db4901e1abbda0bc")
                .addCookie("eas_sid", "21m510G78806A8g3R3J5S7x8O6")
                .addCookie("luin", "o0506695073")
                .addCookie("lskey", "000100009d430a77634d5f6e9757be42304c3bfba6043f85c19a80bd188638fe27ad489bf2d28137c31a2ed9")
                .addCookie("o_cookie", "506695073")
                .addCookie("ptcz", "90becdf8a5337bf6e7395a222add9f4ea439a53e3a8dae9e78fbe19a89822815")
                .addCookie("pt2gguin", "o0506695073")
                .addCookie("pgv_info", "ssid=s7920525870")
                .addCookie("pgv_pvid", "7692100374")
                .addCookie("uuid", "cb597a8f371a06764bf69bea62cb294a")
                .addCookie("ticket", "225d131d4d758ca8e1fb21b7393ca52804ede910")
                .addCookie("ticket_id", "gh_26e62870d612")
                .addCookie("cert", "Qc7Fb7n4MCXwk_K3fhkEKaR1MF5juR5u")
                .addCookie("noticeLoginFlag", "1")
                .addCookie("data_bizuin", "3257531128")
                .addCookie("data_ticket", "SIqh95gyq2QCpFwyiF619x6TymQbUnz29QN2woY5ezGkNpZ40HqHmunksiLmBx/U")
                .addCookie("ua_id", "8bsl8QwVYpz1SjZDAAAAAAeS_UwZB3jFPmzCz9II7nY=")
                .addCookie("xid", "30de7ae67fb47c50e8f16f38603231a1")
                .addCookie("openid2ticket_o-PopwpUMAJXD7LVvwg1IMamojDY", "q9tod61DjQt0nZH6ii7Kbk/jy48/mDUzzI/6VX7IaCU=")
                .addCookie("mm_lang", "zh_CN")
                .addCookie("slave_user", "gh_26e62870d612")
                .addCookie("slave_sid", "YmVTRVRnTmJuSDh4UnZReXRlbHpWYlcxYlZudDA5Mm03WTBoc1M3TmFzVk1tUEdoSmszclJlckh1ZE9lQ1FzcTdmb2dsajJKXzBMZ3Q3VHV1NTVua0pWcFVqWVB5TmN0bGdGVnlSVWl0RlNqaEJUQzcza0ljVEkzYlhSSFlOSTRXWmY1azM0R0k5aVJNU3RE")
                .addCookie("bizuin", "3299533296")
                .setSleepTime(1500)
                .setDomain("mp.weixin.qq.com");
        return site;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new XmqzPageProcessor()).addUrl("https://mp.weixin.qq.com/cgi-bin/newmasssendpage?count=7&begin=0&token=1017499496&lang=zh_CN&token=1017499496&lang=zh_CN&f=json&ajax=1").setScheduler(new QueueScheduler())
                .addPipeline(new ImportPipeline("微信")).thread(1);
        spider.start();
    }
}
