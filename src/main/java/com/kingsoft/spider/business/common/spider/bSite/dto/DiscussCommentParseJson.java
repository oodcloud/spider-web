package com.kingsoft.spider.business.common.spider.bSite.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingsoft.spider.core.common.support.HttpClientUtil;
import com.vdurmont.emoji.EmojiParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangyujie on 2017/12/25.
 */
public class DiscussCommentParseJson extends AbstractParse<CommentDto> {
    private List<CommentDto> commentDtos = new ArrayList<>();
    @Override
    public List<CommentDto> convert(String url) {
        String txt = HttpClientUtil.doGet(url);
        JSONObject jsonObject = JSONObject.parseObject(txt).getJSONObject("data");
        JSONObject page = jsonObject.getJSONObject("page");
        int count = page.getInteger("count");
        int size = page.getInteger("size");
        int index = count / size + 1;
        Matcher matcher = Pattern.compile("pn=([0-9]+)").matcher(url);
        int currentIndex = 0;
        while (matcher.find()) {
            currentIndex = Integer.parseInt(matcher.group().split("=")[1]);
        }
        if (url.contains("&pn=1"))//避免hots重复生成
        {
            JSONArray hots = jsonObject.getJSONArray("hots");
            if (hots!=null)
            {
                for (int i = 0; i < hots.size(); i++) {
                    CommentDto dto = new CommentDto();
                    dto.setTime(hots.getJSONObject(i).getLong("ctime"));
                    dto.setText(EmojiParser.removeAllEmojis(hots.getJSONObject(i).getJSONObject("content").getString("message")));
                    dto.setAuthor(EmojiParser.removeAllEmojis(hots.getJSONObject(i).getJSONObject("member").getString("uname")));
                    dto.setType(2);
                    commentDtos.add(dto);
                }
            }
            JSONArray replies = jsonObject.getJSONArray("replies");
            parseReplies(commentDtos, replies);

        } else {
            JSONArray replies = jsonObject.getJSONArray("replies");
            parseReplies(commentDtos, replies);
        }
        if (index > currentIndex) {
            currentIndex++;
            convert("https://api.bilibili.com/x/v2/reply?jsonp=jsonp&pn=" + currentIndex + "&type=1&oid=" + url.split("oid=")[1]);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return commentDtos;
    }
    private void parseReplies(List<CommentDto> commentDtos, JSONArray replies) {
        for (int i = 0; i < replies.size(); i++) {
            CommentDto dto = new CommentDto();
            dto.setTime(replies.getJSONObject(i).getLong("ctime"));
            dto.setText(EmojiParser.removeAllEmojis(replies.getJSONObject(i).getJSONObject("content").getString("message")));
            dto.setType(2);
            dto.setAuthor(EmojiParser.removeAllEmojis(replies.getJSONObject(i).getJSONObject("member").getString("uname")));
            commentDtos.add(dto);
            JSONArray repliesInner = replies.getJSONObject(i).getJSONArray("replies");
            if (repliesInner != null) {
                for (int j = 0; j < repliesInner.size(); j++) {
                    dto = new CommentDto();
                    dto.setTime(repliesInner.getJSONObject(j).getLong("ctime"));
                    dto.setText(EmojiParser.removeAllEmojis(repliesInner.getJSONObject(j).getJSONObject("content").getString("message")));
                    dto.setAuthor(EmojiParser.removeAllEmojis(repliesInner.getJSONObject(j).getJSONObject("member").getString("uname")));
                    dto.setType(2);
                    commentDtos.add(dto);
                }
            }
        }
    }
}
