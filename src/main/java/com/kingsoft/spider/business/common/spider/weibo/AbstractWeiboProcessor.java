package com.kingsoft.spider.business.common.spider.weibo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingsoft.spider.business.common.spider.weibo.dao.CreateAtSwitchTime;
import com.kingsoft.spider.business.common.spider.weibo.dao.WeiboDto;
import com.kingsoft.spider.core.common.support.HttpClientUtil;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangyujie on 2018/1/4.
 */
public abstract class AbstractWeiboProcessor {
    private Pattern htmlPattern = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
    private final Logger logger= LoggerFactory.getLogger(AbstractWeiboProcessor.class);
//     private  Pattern emojPattern = Pattern.compile("([D83cDc00-D83cDfff]|[D83dDc00-D83dDfff]|[2600-27ff]){0,}", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
    public void execute(String url) {
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        header.put("Host", "m.weibo.cn");
        header.put("Upgrade-Insecure-Requests", "1");
        header.put("Cookie", "_T_WM=df3e50108e286ed5e464fbfbe436ab7c; browser=d2VpYm9mYXhpYW4%3D; h5_deviceID=2fd5f3f8850ca5494c7543252f2da5bc; ALF=1517721047; SCF=AgKo7z10oJ3Iq087qSG8-5YTjh2p0C8Sky5mmNRtYHnWin9P_S8R79d8lJUbgO-2zQWiN2TcRSydkuPIXMKqmfs.; SUB=_2A253S3iHDeThGeNN6FIY8CfEyj6IHXVUtBjPrDV6PUNbktAKLRP8kW1NSbgHdJMe_yCP-ygLLDjYFOW9U7g7ETbl; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWYjviOi160haB.EAo1BcNY5JpX5KMhUgL.Fo-0e054eh.ReKz2dJLoI7DoIgSDM0M41h5c; SUHB=0IIF0pUDcgB2YR; SSOLoginState=1515129047; WEIBOCN_FROM=1110006030; M_WEIBOCN_PARAMS=luicode%3D10000011%26lfid%3D1076033237713974%26fid%3D1076033237713974%26uicode%3D10000011");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        String homeList = HttpClientUtil.doGet(url);
        JSONObject jsonObject = JSON.parseObject(homeList);
        JSONObject jsonObject1=jsonObject.getJSONObject("data");
        int pageSize=0;
        if(jsonObject1!=null)
        {
            JSONObject jsonObject2=jsonObject1.getJSONObject("cardlistInfo");
            if (jsonObject2!=null)
            {
                int total = jsonObject2.getIntValue("total");
                 pageSize = total / 10 + 1;
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int m = 1; m < pageSize + 1; m++) {
            String targetUrl = null;
            if (m != 1) {
                targetUrl = url.concat("&page=" + m);
            } else {
                targetUrl = url;
            }
            String finalTargetUrl = targetUrl;
            Future<List<WeiboDto>> listFuture = getListFuture(executorService, finalTargetUrl);
            //报存数据库
            save(listFuture);
        }
        executorService.shutdown();
    }

    public abstract void save(Future<List<WeiboDto>> listFuture);

    private Future<List<WeiboDto>> getListFuture(ExecutorService executorService, String finalTargetUrl) {
        return executorService.submit(()-> {
            {
                logger.info("Weibo Task is runing in:" + finalTargetUrl);
                String homeList = HttpClientUtil.doGet(finalTargetUrl);
                JSONObject jsonObject = JSON.parseObject(homeList);
                JSONArray cards = jsonObject.getJSONObject("data").getJSONArray("cards");
                List<WeiboDto> weiboDtoPageContentAll = new ArrayList<>();
                for (int i = 0; i < cards.size(); i++) {
                    JSONObject cardsItem = cards.getJSONObject(i);
                    JSONObject mblog = cardsItem.getJSONObject("mblog");
                    if (mblog == null) {
                        continue;
                    }
                    String key = mblog.getString("mid");
                    if (key == null) {
                        key = mblog.getString("idstr");
                    }
                    if (key == null) {
                        key = mblog.getString("id");
                    }
                    //                        vo.setCreatedAt(mblog.getString("created_at"));
                    //                        vo.setTitle(mblog.getString("text"));
                    String title = mblog.getString("text");
                    String commentUrl = "https://m.weibo.cn/api/comments/show?id=" + key + "&page=1";
                    String commentInfo = HttpClientUtil.doGet(commentUrl);
                    JSONObject jsonObject1 = JSON.parseObject(commentInfo);
                    if (jsonObject1 == null)
                        continue;
                    int ok = jsonObject1.getInteger("ok");
                    if (ok == 0) {
                        continue;
                    }
                    int max = jsonObject1.getJSONObject("data").getInteger("max");

                    List<WeiboDto> weiboDtoPageContent = new ArrayList<>();
                    for (int j = 1; j < max + 1; j++) {
                        commentUrl = "https://m.weibo.cn/api/comments/show?id=" + key + "&page=" + j;
                        commentInfo = HttpClientUtil.doGet(commentUrl);
                        jsonObject1 = JSON.parseObject(commentInfo);
                        ok = jsonObject1.getInteger("ok");
                        if (ok == 0) {
                            break;
                        }
                        JSONArray commentList = jsonObject1.getJSONObject("data").getJSONArray("data");

                        List<WeiboDto> weiboDtos = new ArrayList<>();
                        for (int k = 0; k < commentList.size(); k++) {

                            WeiboDto weiboDto = new WeiboDto();
                            JSONObject commentItem = commentList.getJSONObject(k);
                            String currentCommentTime = commentItem.getString("created_at");

                            String replayAuthor = commentItem.getJSONObject("user").getString("screen_name");
                            Matcher replayAuthorMatcher = htmlPattern.matcher(replayAuthor);
                            replayAuthor = replayAuthorMatcher.replaceAll("");
                            weiboDto.setAuthor(EmojiParser.removeAllEmojis(replayAuthor));


                            String commentText = commentItem.getString("text");
                            Matcher htmlmatcher = htmlPattern.matcher(commentText);
                            commentText = htmlmatcher.replaceAll("");
                            weiboDto.setText(EmojiParser.removeAllEmojis(commentText));

                            weiboDto.setTime(CreateAtSwitchTime.convert(currentCommentTime).getTime() / 1000);
                            htmlmatcher = htmlPattern.matcher(title);
                            title = htmlmatcher.replaceAll("");
                            weiboDto.setTitle(EmojiParser.removeAllEmojis(title));
                            if (commentItem.getString("reply_text") != null) {
                                htmlmatcher = htmlPattern.matcher(commentText + commentItem.getString("reply_text"));
                                String unionContent = htmlmatcher.replaceAll("");
                                weiboDto.setText(EmojiParser.removeAllEmojis(unionContent));
                            }

                            weiboDtos.add(weiboDto);
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        weiboDtoPageContent.addAll(weiboDtos);
                    }
                    weiboDtoPageContentAll.addAll(weiboDtoPageContent);
                }
                return weiboDtoPageContentAll;
            }
        });
    }

}
