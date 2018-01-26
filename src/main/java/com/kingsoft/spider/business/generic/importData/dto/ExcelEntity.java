package com.kingsoft.spider.business.generic.importData.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangyujie on 2017/12/26.
 */
public class ExcelEntity {

    private String dateTime;
    private String text;
    private String title;
    private Long time;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
}
