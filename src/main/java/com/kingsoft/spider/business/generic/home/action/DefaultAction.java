package com.kingsoft.spider.business.generic.home.action;

import com.kingsoft.spider.business.generic.home.dto.DefaultDto;
import com.kingsoft.spider.business.generic.home.service.DefaultService;
import com.kingsoft.spider.core.common.support.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wangyujie on 2017/12/20.
 */
@RequestMapping("/generic")
@Controller
public class DefaultAction extends BaseController{
    private Logger logger= LoggerFactory.getLogger(DefaultAction.class);
    @Autowired
    private DefaultService defaultService;

    @RequestMapping("index.html")
    public String index(Model model){
        DefaultDto defaultDto=new DefaultDto();
        defaultDto.setbBSExtopiaCommentCount(defaultService.getBBSExtopiaCommentCount());
        defaultDto.setbSiteExtopiaCommentCount(defaultService.getBSiteExtopiaCommentCount());
        defaultDto.setbSiteMatCommentCount(defaultService.getBSiteMatCommentCount());
        defaultDto.setbSiteXmqzCommentCount(defaultService.getBSiteXmqzCommentCount());
        defaultDto.setTiebaExtopiaCommentCount(defaultService.getTiebaExtopiaCommentCount());
        defaultDto.setTiebaMatCommentCount(defaultService.getTiebaMatCommentCount());
        defaultDto.setTiebaXmqzCommentCount(defaultService.getTiebaXmqzCommentCount());
        defaultDto.setWechatExtopiaCommentCount(defaultService.getWechatExtopiaCommentCount());
        defaultDto.setWechatMatCommentCount(defaultService.getWechatMatCommentCount());
        defaultDto.setWechatXmqzCommentCount(defaultService.getWechatXmqzCommentCount());
        defaultDto.setWeiboExtopiaCommentCount(defaultService.getWeiboExtopiaCommentCount());
        defaultDto.setWeiboMatCommentCount(defaultService.getWeiboMatCommentCount());
        defaultDto.setWeiboProducerCommentCount(defaultService.getWeiboProducerCommentCount());
        defaultDto.setWeiboXmqzCommentCount(defaultService.getWeiboXmqzCommentCount());
        model.addAttribute("dataCount",defaultDto);
        return thymeleaf("generic/index");
    }
}
