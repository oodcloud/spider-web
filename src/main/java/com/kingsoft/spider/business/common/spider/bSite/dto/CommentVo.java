package com.kingsoft.spider.business.common.spider.bSite.dto;

import java.util.List;

/**
 * Created by wangyujie on 2017/12/25.
 */
public class CommentVo {
    private Integer type;
    private List<CommentDto> commentDtos;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<CommentDto> getCommentDtos() {
        return commentDtos;
    }

    public void setCommentDtos(List<CommentDto> commentDtos) {
        this.commentDtos = commentDtos;
    }
}
