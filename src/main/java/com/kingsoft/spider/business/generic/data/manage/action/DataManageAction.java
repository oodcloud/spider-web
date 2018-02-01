package com.kingsoft.spider.business.generic.data.manage.action;

import com.kingsoft.spider.business.generic.data.manage.service.DataManageService;
import com.kingsoft.spider.core.common.support.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangyujie on 2018/2/1.
 */
@Controller
@RequestMapping("/generic/data/manage")
public class DataManageAction extends BaseController{
    @Autowired
    private DataManageService dataManageService;

    @RequestMapping("index.html")
    public String index(){
        return thymeleaf("/generic/data/manage/index");
    }

}
