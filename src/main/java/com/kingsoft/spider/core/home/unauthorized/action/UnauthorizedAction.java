package com.kingsoft.spider.core.home.unauthorized.action;

import com.kingsoft.spider.core.common.support.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangyujie on 2017/12/28.
 */
@RequestMapping("/")
@Controller
public class UnauthorizedAction extends BaseController{

    @RequestMapping("unauthorized.html")
    public String index(){
      return   thymeleaf("error");
    }

}
