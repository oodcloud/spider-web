package com.kingsoft.spider.business.common.spider.bbs.pipeline;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.common.spider.bbs.dto.bbsDto;
import com.kingsoft.spider.business.common.spider.bbs.mapper.BbsMapper;
import com.kingsoft.spider.core.common.support.PropertiesUtils;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyujie on 2017/12/25.
 */
@Component("bbsPipeline")
public class bbsPipeline implements Pipeline {

    @Resource
    private BbsMapper bbsMapper;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private Logger logger= LoggerFactory.getLogger(bbsPipeline.class);

    @Override
    public void process(ResultItems resultItems, Task task) {
        String title = resultItems.get("title");
        List<String> content = resultItems.get("content");
        List<String> times = resultItems.get("times");
        List<String> authors = resultItems.get("author");
        if (content != null && !content.isEmpty()) {
            List<bbsDto> bbsDtos = new ArrayList<>();
            String time = PropertiesUtils.readData("spiderLastTime.properties", "extopiaBBS");
            for (int i = 0; i < content.size(); i++) {
                if (!"".equals(content.get(i))) {
                    bbsDto dto = new bbsDto();
                    dto.setText(EmojiParser.removeAllEmojis(content.get(i)));
                    try {
                        dto.setTime(dateFormat.parse(times.get(i)).getTime() / 1000);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dto.setTitle(EmojiParser.removeAllEmojis(title));
                    if (authors.size()>i)
                    {
                        dto.setAuthor(EmojiParser.removeAllEmojis(authors.get(i)));
                    }
                    //判断是否入库
                    checkIsWriteDB(bbsDtos, time, dto);


                }
            }
            if (bbsDtos.size()!=0)
            {
                try {
                    bbsMapper.add(bbsDtos);
                }catch (Exception e)
                {
                    for (bbsDto dto:bbsDtos)
                    {
                    try {
                            bbsMapper.addSingle(dto);
                    }catch (Exception e1)
                    {
                        logger.error("数据插入异常：bbsPipeline：异常信息"+e1.fillInStackTrace()+"：：异常数据："+JSON.toJSONString(dto));
                    }
                    }
                }

            }
        }
    }

    private void checkIsWriteDB(List<bbsDto> bbsDtos, String time, bbsDto dto) {
        if (time == null||"".equals(time))  {
            bbsDtos.add(dto);
        } else {
            Long date = Long.valueOf(time);
            if (date <dto.getTime())
            {
                bbsDtos.add(dto);
            }
        }
    }
}
