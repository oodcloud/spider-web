package com.kingsoft.spider.business.common.spider.wechat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingsoft.spider.business.common.spider.wechat.dto.CommentVo;
import com.kingsoft.spider.core.common.support.HttpClientUtil;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyujie on 2018/1/4.
 */

public class WechatProcessor implements PageProcessor {
    private Site site = Site.me();
    private String token = null;
    private String cookie = null;
    private int sleepTime = 1000;
    private int threadNum = 3;
    private final Logger logger = LoggerFactory.getLogger(WechatProcessor.class);

    public WechatProcessor(String token, String cookie, int sleepTime, int threadNum) {
        this.token = token;
        this.cookie = cookie;
        this.sleepTime = sleepTime;
        this.threadNum = threadNum;
    }

    @Override
    public void process(Page page) {
        String url = page.getUrl().get();
        if (url.contains("action=list_latest_comment")) {
            String content = page.getRawText();
            JSONObject jsonObject = JSONObject.parseObject(content);
            JSONObject app_msg_list = jsonObject.getJSONObject("app_msg_list");
            JSONArray app_msg = app_msg_list.getJSONArray("app_msg");
            if (app_msg != null && app_msg.size() > 0) {
                for (int i = 0; i < app_msg.size(); i++) {
                    JSONObject app_msg_item = app_msg.getJSONObject(i);
                    JSONObject item = app_msg_item.getJSONObject("item");
                    String comment_id = item.getString("comment_id");
                    page.addTargetRequest("https://mp.weixin.qq.com/misc/appmsgcomment?action=list_comment&begin=0&count=10&comment_id=" + comment_id + "&filtertype=0&day=0&type=2&token=" + token + "&lang=zh_CN&f=json&ajax=1");
                }
            }
        } else {
            String comment_content = page.getRawText();
            JSONObject jsonObject = JSONObject.parseObject(comment_content);
            if (jsonObject != null) {
                String title = jsonObject.getString("title");
                String comment_list = jsonObject.getString("comment_list");
                JSONObject commentObj = JSONObject.parseObject(comment_list);


                int total_count = commentObj.getInteger("total_count");
                int commentPageSize = total_count / 10 + 1;

                JSONArray commentListArray = commentObj.getJSONArray("comment");

                if (commentListArray != null && commentListArray.size() > 0) {
                    List<CommentVo> commentVoList = new ArrayList<>();
                    addCommentItem(title, commentListArray, commentVoList);
                    String targetCommentId = url.split("comment_id=")[1].split("&filtertype")[0];
                    List<String> nextUrlList = new ArrayList<>();
                    for (int i = 1; i < commentPageSize; i++) {
                        String nextUrl = "https://mp.weixin.qq.com/misc/appmsgcomment?action=list_comment&begin=" + (i * 10) + "&count=10&comment_id=" + targetCommentId + "&filtertype=0&day=0&type=2&token=" + token + "&lang=zh_CN&f=json&ajax=1";
                        nextUrlList.add(nextUrl);
                    }
                    handleInnerNextComment(commentVoList, nextUrlList);
                    logger.info("当前评论id：" + targetCommentId + "评论数量为：" + commentVoList.size());
                    page.putField("content", commentVoList);
                }

            }
        }
    }

    private void addCommentItem(String title, JSONArray commentListObj, List<CommentVo> commentVoList) {
        for (int i = 0; i < commentListObj.size(); i++) {
            CommentVo commentVo = new CommentVo();
            JSONObject commentItem = commentListObj.getJSONObject(i);
            String nick_name = commentItem.getString("nick_name");
            String content = commentItem.getString("content");
            Long post_time = commentItem.getLong("post_time");
            commentVo.setText(EmojiParser.removeAllEmojis(content));
            commentVo.setAuthor(EmojiParser.removeAllEmojis(nick_name));
            commentVo.setTime(post_time);
            commentVo.setTitle(EmojiParser.removeAllEmojis(title));
            commentVoList.add(commentVo);
        }
    }


    private void handleInnerNextComment(List<CommentVo> commentVoList, List<String> urls) {
        for (String url : urls) {
            String content = callbackContent(url);
            JSONObject jsonObject = JSONObject.parseObject(content);
            String title = jsonObject.getString("title");
            String comment_list = jsonObject.getString("comment_list");
            JSONObject commentListObj = JSONObject.parseObject(comment_list);
            JSONArray commentListArray = commentListObj.getJSONArray("comment");
            if (commentListArray != null && commentListArray.size() > 0) {
                addCommentItem(title, commentListArray, commentVoList);
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String callbackContent(String url) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8");
        headers.put("Cookie", cookie);
        headers.put("Host", "mp.weixin.qq.com");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        return HttpClientUtil.doGet(url, headers);
    }

    @Override
    public Site getSite() {
        site.setUserAgent(
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
                .setTimeOut(15000).setRetryTimes(3)
                .setSleepTime(sleepTime)
                .addHeader("Cookie", cookie);
        return site;
    }


    public void init(Pipeline pipeline) {
        WechatProcessor wechatProcessor = new WechatProcessor(this.token, this.cookie, this.sleepTime, this.threadNum);
        String startUrl = "https://mp.weixin.qq.com/misc/appmsgcomment?action=list_latest_comment&begin=0&count=10&token=" + token + "&lang=zh_CN&f=json&ajax=1";
        String oneContent = wechatProcessor.callbackContent(startUrl);
        if (oneContent == null) {
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(oneContent);
        String app_msg_list = jsonObject.getString("app_msg_list");
        JSONObject appMsgList = JSONObject.parseObject(app_msg_list);
        int total_count = appMsgList.getInteger("total_count");
        logger.info("WechatProcessor total_count:" + total_count);
        int pageSize = total_count / 10 + 1;
        List<String> targetUrls = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            targetUrls.add("https://mp.weixin.qq.com/misc/appmsgcomment?action=list_latest_comment&begin=" + (i * 10) + "&count=10&token=" + token + "&lang=zh_CN&f=json&ajax=1");
        }
        String[] sources = new String[targetUrls.size()];
        targetUrls.toArray(sources);
        Spider.create(wechatProcessor).addPipeline(pipeline).thread(threadNum).addUrl(sources).runAsync();
    }


}
