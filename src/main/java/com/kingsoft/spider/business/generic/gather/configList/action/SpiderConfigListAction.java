package com.kingsoft.spider.business.generic.gather.configList.action;

import com.kingsoft.spider.business.generic.gather.config.dto.CommonUrlTestRequest;
import com.kingsoft.spider.business.generic.gather.configList.dto.SpiderConfigDatabaseDto;
import com.kingsoft.spider.business.generic.gather.configList.dto.SpiderConfigSaveDto;
import com.kingsoft.spider.business.spidercore.common.*;
import com.kingsoft.spider.business.generic.gather.configList.dto.SpiderConfigListDto;
import com.kingsoft.spider.business.generic.gather.configList.service.SpiderConfigListService;
import com.kingsoft.spider.core.common.support.BaseController;
import com.kingsoft.spider.core.common.support.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
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
        SpiderConfigInfoDto dto= spiderConfigListService.getEditList(id);
        model.addAttribute("list",dto);
        model.addAttribute("id",id);
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

    /**
     * 保存爬虫配置
     *
     * @param content
     * @return
     */
    @PostMapping("save-config.html")
    @ResponseBody
    public CommonResponse saveConfig(String content) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        SpiderConfigInfoTransition transition = new SpiderConfigInfoTransition();
        SpiderConfigSaveDto entity = transition.getSpiderConfigSaveDto(content);
        try {
            spiderConfigListService.update(entity);
            commonResponse.success();
        }catch (Exception e)
        {
            logger.error(e.fillInStackTrace().toString());
            commonResponse.fail("保存失败");
        }
        return commonResponse;
    }

    /**
     * 爬虫u公共rl测试
     *
     * @param request
     * @return
     */
    @PostMapping("test-commoon-url.html")
    @ResponseBody
    public CommonResponse getTestCommonUrl(CommonUrlTestRequest request) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        SpiderHelper convertDo=new SpiderHelper();
        commonResponse.setData(convertDo.getCommonUrlList(request));
        return commonResponse;
    }

    @PostMapping("testRun.html")
    @ResponseBody
    public CommonResponse testRun(String content) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        SpiderConfigInfoTransition transition = new SpiderConfigInfoTransition();
        SpiderConfigInfoDto entity = transition.getSpiderConfigInfoDto(content);
        try {
            List<TestPipelineDto> testPipelineDtoList = spiderConfigListService.testRun(entity);
            commonResponse.put("data",testPipelineDtoList);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace().toString());
            commonResponse.fail("配置信息出现错误");
        }
        return commonResponse;
    }
    @PostMapping("testDataBase.html")
    @ResponseBody
    public CommonResponse testDataBase(@Valid SpiderConfigDatabaseDto databaseDto, Errors errors)
    {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
          if (errors.hasErrors())
          {
              commonResponse.fail("请检查以下出错字段",errors.getFieldErrors());
          }else {
              commonResponse.put("connect", spiderConfigListService.testDataBase(databaseDto));
          }
        return commonResponse;
    }

}
