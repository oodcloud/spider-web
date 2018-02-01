package com.kingsoft.spider.business.common.spider.bSite.pipeline;

import com.kingsoft.spider.business.common.spider.bSite.dto.CommentDto;

import java.util.Iterator;
import java.util.List;

public class HandleData {
    public HandleData() {
    }
    void checkIsWriteDB(Long time, List<CommentDto> commentVoDtos) {
        Iterator<CommentDto> iterator = commentVoDtos.iterator();
        while (iterator.hasNext()) {
            CommentDto commentDto=iterator.next();
            if (time != null && !"".equals(time)) {
                if (time > commentDto.getTime()) {
                    commentVoDtos.remove(commentDto);
                }
            }
        }
    }
}