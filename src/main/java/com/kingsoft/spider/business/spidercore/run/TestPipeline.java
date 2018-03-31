package com.kingsoft.spider.business.spidercore.run;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.spidercore.common.TestPipelineDto;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * Created by wangyujie on 2018/3/19.
 */
public class TestPipeline implements Pipeline {
    private  PipelineCallBack pipelineCallBack;


    public void setPipelineCallBack(PipelineCallBack pipelineCallBack) {
        this.pipelineCallBack = pipelineCallBack;
    }

    @Override
    public void  process(ResultItems resultItems, Task task) {
        Map<String,Object> map=resultItems.getAll();
        for (String key:map.keySet())
        {
            TestPipelineDto dto=new TestPipelineDto();
            dto.setKey(key);
            dto.setValue(map.get(key).toString());
            pipelineCallBack.addResultItem(dto);
        }
    }
}
