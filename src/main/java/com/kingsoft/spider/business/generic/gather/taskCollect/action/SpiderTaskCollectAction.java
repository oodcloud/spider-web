package com.kingsoft.spider.business.generic.gather.taskCollect.action;

import com.kingsoft.spider.business.generic.gather.taskCollect.service.SpiderTaskCollectService;
import com.kingsoft.spider.core.common.support.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangyujie on 2018/1/30.
 */
@Controller
@RequestMapping("/generic/gather/taskcollect")
public class SpiderTaskCollectAction extends BaseController{
    @Autowired
    private SpiderTaskCollectService spiderTaskCollectService;

    @RequestMapping("index.html")
    public String index(){
        return thymeleaf("/generic/gather/taskcollect/index");
    }
}
