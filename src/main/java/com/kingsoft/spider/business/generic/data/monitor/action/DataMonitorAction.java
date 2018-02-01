package com.kingsoft.spider.business.generic.data.monitor.action;

import com.kingsoft.spider.business.generic.data.monitor.service.DataMonitorService;
import com.kingsoft.spider.core.common.support.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangyujie on 2018/2/1.
 */
@Controller
@RequestMapping("/generic/data/monitor")
public class DataMonitorAction extends BaseController{
    @Autowired
    private DataMonitorService dataMonitorService;

    @RequestMapping("index.html")
    public String index(){

        return thymeleaf("/generic/data/monitor/index");
    }

}
