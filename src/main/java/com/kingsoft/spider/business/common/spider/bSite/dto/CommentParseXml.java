package com.kingsoft.spider.business.common.spider.bSite.dto;

import com.vdurmont.emoji.EmojiParser;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyujie on 2017/12/25.
 */
public class CommentParseXml extends AbstractParse<CommentDto> {
    private HttpClientDownloader clientDownloader = new HttpClientDownloader();
    public List<CommentDto> convert(String url) {
        Html html = clientDownloader.download(url);
        List<String> commentTimeInfoList = html.xpath("//d/@p").all();
        List<CommentDto> commentList = new ArrayList<>();
        List<String> commentTextList = html.xpath("//d/text()").all();
        for (int i = 0; i < commentTimeInfoList.size(); i++) {
            CommentDto commentDto = new CommentDto();
            Long time = Long.valueOf(commentTimeInfoList.get(i).split(",")[4]);
            commentDto.setTime(time);
            commentDto.setText(EmojiParser.removeAllEmojis(commentTextList.get(i)));
            commentDto.setType(1);
            commentList.add(commentDto);
        }
        return commentList;
    }
}
