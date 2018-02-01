package com.kingsoft.spider.business.common.spider.bSite.pipeline;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.common.spider.bSite.dto.CommentDto;
import com.kingsoft.spider.business.common.spider.bSite.mapper.bMapper;
import com.kingsoft.spider.business.common.spiderLastTime.service.SpiderLastTimeService;
import com.kingsoft.spider.core.common.support.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by wangyujie on 2017/12/25.
 */
@Component("BMatPipeline")
public class BMatPipeline implements Pipeline {
    @Autowired
    private bMapper bMapper;
    private Logger logger = LoggerFactory.getLogger(BMatPipeline.class);
    private final HandleData handleData = new HandleData();
    @Autowired
    private SpiderLastTimeService spiderLastTimeService;
    @Override
    public void process(ResultItems resultItems, Task task) {
        String commentVo = resultItems.get("comment");
        String discussVo = resultItems.get("discuss");
        String title = resultItems.get("title");
        if (commentVo != null) {
            Long time = spiderLastTimeService.selectLastTime("matB");
            List<CommentDto> commentVoDtos = JSON.parseArray(commentVo, CommentDto.class);
            List<CommentDto> discussVoDtos = JSON.parseArray(discussVo, CommentDto.class);


            commentVoDtos.addAll(discussVoDtos);
            for (CommentDto commentDto : commentVoDtos) {
                commentDto.setTitle(title);
            }
            List<CommentDto> commentDtos=new CopyOnWriteArrayList<>(commentVoDtos);
            //判断是否入库
            handleData.checkIsWriteDB(time, commentDtos);
            save(commentDtos);
        }
    }



    private void save(List<CommentDto> commentVoDtos) {
        int all = commentVoDtos.size();
        int limit = 500;
        if (all > 0) {
            if (all <= limit) {
                try {
                    bMapper.matAdd(commentVoDtos);
                } catch (Exception e) {
                    List<CommentDto> commentDtos = commentVoDtos;
                    for (CommentDto dto : commentDtos) {
                        try {
                            bMapper.matSingleAdd(dto);
                        } catch (Exception e1) {
                            logger.error("插入数据异常 BMatPipeline 异常信息：" + e1.fillInStackTrace().toString() + "异常数据：" + JSON.toJSONString(dto));
                        }
                    }
                    logger.error(e.fillInStackTrace().toString());
                }
            } else if (all > limit) {
                int currentSize = 0;
                int allPage = all / limit + 1;
                int cunrrentPage = 0;
                while (currentSize < all) {
                    if (allPage == (cunrrentPage + 1)) {

                        try {
                            bMapper.matAdd(commentVoDtos.subList(cunrrentPage * limit, all));
                        } catch (Exception e) {
                            List<CommentDto> commentDtos = commentVoDtos.subList(cunrrentPage * limit, all);
                            for (CommentDto dto : commentDtos) {
                                try {
                                    bMapper.matSingleAdd(dto);
                                } catch (Exception e1) {
                                    logger.error("插入数据异常 BMatPipeline 异常信息：" + e1.fillInStackTrace().toString() + "异常数据：" + JSON.toJSONString(dto));
                                }
                            }
                            logger.error(e.fillInStackTrace().toString());
                        }


                    } else {

                        try {
                            bMapper.matAdd(commentVoDtos.subList(cunrrentPage * limit, (cunrrentPage + 1) * limit));
                        } catch (Exception e) {
                            List<CommentDto> commentDtos = commentVoDtos.subList(cunrrentPage * limit, (cunrrentPage + 1) * limit);
                            for (CommentDto dto : commentDtos) {
                                try {
                                    bMapper.matSingleAdd(dto);
                                } catch (Exception e1) {
                                    logger.error("插入数据异常 BMatPipeline 异常信息：" + e1.fillInStackTrace().toString() + "异常数据：" + JSON.toJSONString(dto));
                                }
                            }
                            logger.error(e.fillInStackTrace().toString());
                        }

                    }
                    currentSize += limit;
                    cunrrentPage += 1;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
