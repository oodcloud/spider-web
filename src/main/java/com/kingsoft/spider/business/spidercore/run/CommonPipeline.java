package com.kingsoft.spider.business.spidercore.run;


import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import com.kingsoft.spider.business.spidercore.db.DataBaseBoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Created by wangyujie on 2018/2/10.
 */
public class CommonPipeline implements Pipeline {
    private final Logger logger= LoggerFactory.getLogger(CommonPipeline.class);
    private DataBaseBoot baseBoot;
    private List<SpiderConfigInfoDto.MatchField> matchFields;
    private String tableName;

    public CommonPipeline(DataBaseBoot baseBoot, List<SpiderConfigInfoDto.MatchField> matchFields, String tableName) {
        this.baseBoot = baseBoot;
        this.matchFields = matchFields;
        this.tableName = tableName;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        int resultCount = resultItems.getAll().entrySet().size();
        if (resultCount != 0) {
            if (baseBoot.getDataSource()!=null)
            {
                baseBoot.template(matchFields, resultItems, tableName);
            }else {
                logger.info("DataSource is null,because database not setting");
            }
        }
    }

}
