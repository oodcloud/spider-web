package com.kingsoft.spider.business.generic.gather.onlineCollect.action;

import com.kingsoft.spider.business.generic.gather.onlineCollect.dto.SpiderOnlineCollectDto;
import com.kingsoft.spider.business.generic.gather.onlineCollect.dto.SpiderStatusDto;
import com.kingsoft.spider.business.generic.gather.onlineCollect.service.SpiderOnlineCollectService;
import com.kingsoft.spider.core.common.support.BaseController;
import com.kingsoft.spider.core.common.support.CommonResponse;
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
@RequestMapping("/generic/gather/onlinecollect")
public class SpiderOnlineCollectAction extends BaseController{
    @Autowired
    private SpiderOnlineCollectService spiderOnlineCollectService;
    @RequestMapping("index.html")
    public String index(Model model){
        List<SpiderOnlineCollectDto> onlineCollectDtos= spiderOnlineCollectService.getAll();
        model.addAttribute("list",onlineCollectDtos);
        return thymeleaf("index");
    }

    @ResponseBody
    @RequestMapping("start.html")
    public CommonResponse start( @RequestParam("id")Integer id)
    {
        CommonResponse commonResponse=CommonResponse.createCommonResponse();
        SpiderStatusDto spiderStatusDto=spiderOnlineCollectService.start(id);
        if (spiderStatusDto.getCode()==200)
        {
             commonResponse.success(spiderStatusDto.getMessage());
        }else {
            commonResponse.fail(spiderStatusDto.getMessage());
        }
        return commonResponse;
    }

    @ResponseBody
    @RequestMapping("stop.html")
    public CommonResponse stop( @RequestParam("uuid")String uuid)
    {
        CommonResponse commonResponse=CommonResponse.createCommonResponse();
        SpiderStatusDto spiderStatusDto=spiderOnlineCollectService.stop(uuid);
        if (spiderStatusDto.getCode()==200)
        {
            commonResponse.success(spiderStatusDto.getMessage());
        }else {
            commonResponse.fail(spiderStatusDto.getMessage());
        }
        return commonResponse;
    }
    @ResponseBody
    @RequestMapping("getSpiderStutas.html")
    public CommonResponse getSpiderStutas()
    {
        CommonResponse commonResponse=CommonResponse.createCommonResponse();
        commonResponse.setData(spiderOnlineCollectService.getSpiderStutas());
        return commonResponse;
    }
    @RequestMapping("log.html")
    public String logIndex(Model model,@RequestParam("uuid")String uuid){
        model.addAttribute("uuid",uuid);
        return thymeleaf("log");
    }


    @ResponseBody
    @RequestMapping("logQuery.html")
    public CommonResponse getSpiderLog(@RequestParam("uuid")String uuid,@RequestParam("index")Integer index){
        CommonResponse commonResponse=CommonResponse.createCommonResponse();

        commonResponse.setData(spiderOnlineCollectService.getSpiderLogInfo(uuid,index));
        return commonResponse;
    }


}
