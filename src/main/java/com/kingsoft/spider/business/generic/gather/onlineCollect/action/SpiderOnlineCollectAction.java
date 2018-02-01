package com.kingsoft.spider.business.generic.gather.onlineCollect.action;

import com.kingsoft.spider.business.generic.gather.onlineCollect.service.SpiderOnlineCollectService;
import com.kingsoft.spider.core.common.support.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangyujie on 2018/1/30.
 */
@Controller
@RequestMapping("/generic/gather/onlinecollect")
public class SpiderOnlineCollectAction extends BaseController{
    @Autowired
    private SpiderOnlineCollectService spiderOnlineCollectService;
    @RequestMapping("index.html")
    public String index(){
        return thymeleaf("/generic/gather/onlinecollect/index");
    }

}
