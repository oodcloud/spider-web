package com.kingsoft.spider.business.common.spider.bSite.dto;

/**
 * Created by wangyujie on 2017/12/25.
 */
public class CommentDto {
    private Long time;
    private String text;
    private Integer type;
    private String title;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentDto that = (CommentDto) o;

        if (!getTime().equals(that.getTime())) return false;
        if (!getText().equals(that.getText())) return false;
        if (!getType().equals(that.getType())) return false;
        if (!getTitle().equals(that.getTitle())) return false;
        return getAuthor().equals(that.getAuthor());
    }

    @Override
    public int hashCode() {
        int result = getTime().hashCode();
        result = 31 * result + getText().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getAuthor().hashCode();
        return result;
    }
}
