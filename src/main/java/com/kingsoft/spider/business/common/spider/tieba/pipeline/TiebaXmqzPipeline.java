package com.kingsoft.spider.business.common.spider.tieba.pipeline;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.common.spider.tieba.dto.tiebaDto;
import com.kingsoft.spider.business.common.spider.tieba.mapper.TiebaMapper;
import com.kingsoft.spider.core.common.support.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangyujie on 2017/12/26.
 */
@Component("TiebaXmqzPipeline")
public class TiebaXmqzPipeline implements Pipeline {
    @Autowired
    private TiebaMapper tiebaMapper;
    private final HandleData handleData = new HandleData();
    private Logger logger= LoggerFactory.getLogger(TiebaXmqzPipeline.class);
    @Override
    public void process(ResultItems resultItems, Task task) {
        String title = resultItems.get("title");
        List<String> allContent = resultItems.get("allContent");
        List<String> allRepeatTime = resultItems.get("allRepeatTime");
        List<String> allAuthor = resultItems.get("authors");

        if (allContent != null) {
            logger.info("TiebaMatPipeline download one page Extract allContent:" + allContent.size() + "allRepeatTime:" + allRepeatTime.size());
        }


        if (allContent != null && !allContent.isEmpty()) {
            String time = PropertiesUtils.readData("spiderLastTime.properties", "xmqzTieba");
            List<tiebaDto> dtos = new ArrayList<>();
            handleData.execute(title, allContent, allRepeatTime, allAuthor, time, dtos);
            save(dtos);

        }
    }


    private void save(List<tiebaDto> dtos) {
        if (dtos.size() != 0) {
            tiebaMapper.xmqzAdd(dtos);

            try {
                tiebaMapper.xmqzAdd(dtos);
            }catch (Exception e)
            {
                for (tiebaDto dto:dtos)
                {
                    try {
                        tiebaMapper.xmqzSingleAdd(dto);
                    }catch (Exception e1)
                    {
                        logger.error("插入数据异常 TiebaXmqzPipeline 异常信息：" + e1.fillInStackTrace().toString() + "异常数据：" + JSON.toJSONString(dto));
                    }
                }
            }

        }
    }
}
