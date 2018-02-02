package com.kingsoft.spider.business.common.spider.weibo;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.common.spider.bSite.dto.CommentDto;
import com.kingsoft.spider.business.common.spider.weibo.dao.WeiboDto;
import com.kingsoft.spider.business.common.spider.weibo.mapper.WeiboMapper;
import com.kingsoft.spider.business.common.spiderLastTime.service.SpiderLastTimeService;
import com.kingsoft.spider.core.common.support.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by wangyujie on 2017/12/26.
 */
@Component("WeiboExtopiaProcessor")
public class WeiboExtopiaProcessor extends AbstractWeiboProcessor {
    private final HandleData handleData = new HandleData();
    @Autowired
    private WeiboMapper weiboMapper;
    private Logger logger = LoggerFactory.getLogger(WeiboExtopiaProcessor.class);
    @Autowired
    private SpiderLastTimeService spiderLastTimeService;
    public void save(Future<List<WeiboDto>> listFuture) {

        try {
            List<WeiboDto> weiboDtos = listFuture.get();
            if (listFuture.isDone()) {
                logger.info("WeiboExtopiaProcessor 当前执行完毕：");
                logger.info("WeiboExtopiaProcessor 生成内容数量：" + weiboDtos.size());
                if (weiboDtos != null && weiboDtos.size() != 0) {
                    List<WeiboDto> weiboDtoList=new CopyOnWriteArrayList<>(weiboDtos);
                    //判断是否入库
                    Long time = spiderLastTimeService.selectLastTime("extopiaWeibo");
                    handleData.checkIsWriteDB(time, weiboDtoList);
                    try {
                        weiboMapper.extopiaAdd(weiboDtoList);
                    } catch (Exception e) {
                        for (WeiboDto dto : weiboDtoList) {
                            try {
                                weiboMapper.extopiaSingleAdd(dto);
                            } catch (Exception e1) {
                                logger.error("WeiboExtopiaProcessor 插入数据库异常 WeiboExtopiaProcessor：" + JSON.toJSONString(dto) + "异常信息：" + e1.fillInStackTrace());
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
    }
}
