package com.kingsoft.spider.business.common.spider.weibo;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.common.spider.weibo.dao.WeiboDto;
import com.kingsoft.spider.business.common.spider.weibo.mapper.WeiboMapper;
import com.kingsoft.spider.core.common.support.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by wangyujie on 2017/12/26.
 */
@Component("WeiboProducerProcessor")
public class WeiboProducerProcessor extends AbstractWeiboProcessor {
    @Autowired
    private WeiboMapper weiboMapper;
    private final HandleData handleData = new HandleData();
    private final Logger logger = LoggerFactory.getLogger(WeiboProducerProcessor.class);

    public void save(Future<List<WeiboDto>> listFuture) {
        String time = PropertiesUtils.readData("spiderLastTime.properties", "producerWeibo");
        try {
            List<WeiboDto> weiboDtos = listFuture.get();
            if (listFuture.isDone()) {
                logger.info("WeiboProducerProcessor 当前执行完毕：");
                logger.info("WeiboProducerProcessor 生成内容数量：" + weiboDtos.size());
                List<WeiboDto> weiboDtoList=new CopyOnWriteArrayList<>(weiboDtos);
                //判断是否入库
                handleData.checkIsWriteDB(time, weiboDtoList);
                logger.info("WeiboProducerProcessor 入库检查完成 马上进行入库");
                if (listFuture.get().size() != 0) {
                    try {
                        weiboMapper.producerAdd(weiboDtoList);
                    } catch (Exception e) {
                        for (WeiboDto dto : weiboDtoList) {
                            try {
                                weiboMapper.producerSingleAdd(dto);
                            } catch (Exception e1) {
                                logger.error("插入数据库异常 WeiboProducerProcessor：" + JSON.toJSONString(dto) + "异常信息：" + e1.fillInStackTrace());
                            }
                        }
                    }
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
