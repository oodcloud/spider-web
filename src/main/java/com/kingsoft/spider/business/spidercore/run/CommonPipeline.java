package com.kingsoft.spider.business.spidercore.run;


import com.kingsoft.spider.business.spidercore.db.DataBaseBoot;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by wangyujie on 2018/2/10.
 */
public class CommonPipeline implements Pipeline {
    DataBaseBoot baseBoot;

    public CommonPipeline(DataBaseBoot baseBoot) {
        this.baseBoot = baseBoot;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {

    }
}
