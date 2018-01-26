package com.kingsoft.spider.business.common.spider.wechat.dto;

/**
 * Created by wangyujie on 2018/1/8.
 */
public class CommentVo {
    private Long time;
    private String text;
    private String title;
    private String author;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
