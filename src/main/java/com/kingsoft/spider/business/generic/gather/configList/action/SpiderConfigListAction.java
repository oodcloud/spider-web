package com.kingsoft.spider.business.generic.gather.configList.action;

import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import com.kingsoft.spider.business.generic.gather.configList.dto.SpiderConfigListDto;
import com.kingsoft.spider.business.generic.gather.configList.service.SpiderConfigListService;
import com.kingsoft.spider.core.common.support.BaseController;
import com.kingsoft.spider.core.common.support.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wangyujie on 2018/1/30.
 */
@Controller
@RequestMapping("/generic/gather/configlist")
public class SpiderConfigListAction extends BaseController{
    private final Logger logger= LoggerFactory.getLogger(SpiderConfigListAction.class);
    @Autowired
    private SpiderConfigListService spiderConfigListService;

    @RequestMapping("index.html")
    public String index(Model model){
        List<SpiderConfigListDto> listDtos= spiderConfigListService.getList();
        model.addAttribute("list",listDtos);
        return thymeleaf("index");
    }
    @RequestMapping("edit.html")
    public String edit(@RequestParam(name = "id") Long id,Model model)
    {
        List<SpiderConfigInfoDto> spiderConfigInfoDtos= spiderConfigListService.getEditList(id);
        model.addAttribute("list",spiderConfigInfoDtos);
        return thymeleaf("edit");
    }


    @RequestMapping("del.html")
    @ResponseBody
    public CommonResponse del(@RequestParam(name = "id") Long id)
    {
        CommonResponse commonResponse=new CommonResponse();
        try {
            spiderConfigListService.delete(id);
            commonResponse.success();
        }catch (Exception e)
        {
            commonResponse.fail("插入失败");
            logger.trace(e.fillInStackTrace().toString());
        }
        return commonResponse;
    }

}
