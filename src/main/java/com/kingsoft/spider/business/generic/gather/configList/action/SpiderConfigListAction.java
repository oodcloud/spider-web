package com.kingsoft.spider.business.generic.gather.configList.action;

import com.kingsoft.spider.business.generic.gather.configList.service.SpiderConfigListService;
import com.kingsoft.spider.core.common.support.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangyujie on 2018/1/30.
 */
@Controller
@RequestMapping("/generic/gather/configlist")
public class SpiderConfigListAction extends BaseController{
    @Autowired
    private SpiderConfigListService spiderConfigListService;

    @RequestMapping("index.html")
    public String index(){
        return thymeleaf("/generic/gather/configlist/index");
    }
}
