package com.kingsoft.spider.business.common.spider.wechat.pipeline;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.common.spider.wechat.dto.CommentVo;
import com.kingsoft.spider.business.common.spider.wechat.mapper.WechatExtopiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Created by wangyujie on 2018/1/8.
 */
@Component
public class WechatXmqzPipeline implements Pipeline {
    @Autowired
    private WechatExtopiaMapper wechatExtopiaMapper;
    private final Logger logger= LoggerFactory.getLogger(WechatExtopiaPipeline.class);

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<CommentVo> commentVoList =  resultItems.get("content");
        if (commentVoList!=null&&commentVoList.size()>0)
        {
            try {
                wechatExtopiaMapper.xmqzAdd(commentVoList);
            } catch (Exception e) {
                for (CommentVo commentVo:commentVoList)
                {
                    try {
                        wechatExtopiaMapper.xmqzSingleAdd(commentVo);
                    }catch (Exception e1)
                    {
                        logger.error("插入数据库异常 WechatXmqzPipeline："+ JSON.toJSONString(commentVo)+"异常信息："+e1.fillInStackTrace());
                    }

                }
            }
        }
    }
}
