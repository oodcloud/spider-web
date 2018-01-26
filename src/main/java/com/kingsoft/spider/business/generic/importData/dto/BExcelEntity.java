package com.kingsoft.spider.business.generic.importData.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangyujie on 2017/12/26.
 */
public class BExcelEntity {

    private String date;
    private String text;
    private String title;
    private Integer type;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private Long time;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDate() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        if (time==null)
        {
            return "找不到时间";
        }
        return  simpleDateFormat.format(new Date(time*1000));
    }

    public void setDate(String date) {
        this.date = date;
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
