package com.kingsoft.spider.core.home.logout.action;

import com.kingsoft.spider.core.common.support.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangyujie on 2017/12/28.
 */
@RequestMapping("/")
@Controller
public class LogoutAction extends BaseController{
    @RequestMapping("logout.html")
    public String index(){
        SecurityUtils.getSubject().getSession().removeAttribute("user");
        SecurityUtils.getSubject().logout();
        return thymeleaf("redirect:/login");
    }
}
