package com.kingsoft.spider.business.generic.home.action;

import com.kingsoft.spider.business.generic.home.dto.ShowDataDto;
import com.kingsoft.spider.business.generic.home.service.DefaultService;
import com.kingsoft.spider.core.common.support.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by wangyujie on 2017/12/20.
 */
@RequestMapping("/generic")
@Controller
public class DefaultAction extends BaseController {
    private Logger logger = LoggerFactory.getLogger(DefaultAction.class);
    @Autowired
    private DefaultService defaultService;

    @RequestMapping("index.html")
    public String index(Model model) {
        List<ShowDataDto> showDataList = defaultService.getShowData();
        model.addAttribute("data", showDataList);
        return thymeleaf("index");
    }
}
