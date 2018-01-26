package com.kingsoft.spider.core.home.settings.action;

import com.kingsoft.spider.core.common.support.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangyujie on 2017/12/29.
 */
@Controller
@RequestMapping("/settings")
public class SettingsAction extends BaseController {
    @RequestMapping("index.html")
    public String index() {
        return thymeleaf("settings");
    }

    @RequestMapping("druid.html")
    public String druid(){
        return thymeleaf("redirect:/druid/index");
    }
}
