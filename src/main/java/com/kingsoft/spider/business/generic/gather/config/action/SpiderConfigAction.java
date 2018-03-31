package com.kingsoft.spider.business.generic.gather.config.action;

import com.kingsoft.spider.business.generic.gather.config.dto.CommonUrlTestRequest;
import com.kingsoft.spider.business.generic.gather.configList.dto.SpiderConfigDatabaseDto;
import com.kingsoft.spider.business.spidercore.SpiderBoot;
import com.kingsoft.spider.business.spidercore.common.*;
import com.kingsoft.spider.business.generic.gather.config.service.SpiderConfigService;
import com.kingsoft.spider.core.common.support.BaseController;
import com.kingsoft.spider.core.common.support.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by wangyujie on 2017/12/29.
 */
@RequestMapping("/generic/gather/config")
@Controller
public class SpiderConfigAction extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(SpiderConfigAction.class);
    @Autowired
    private SpiderConfigService spiderConfigService;

    /**
     * 爬虫配置页面
     *
     * @return
     */
    @RequestMapping("index.html")
    public String startIndex() {
        return thymeleaf("index");
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
        SpiderHelper convertDo = new SpiderHelper();
        commonResponse.setData(convertDo.getCommonUrlList(request));
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
        SpiderConfigEntity entity = transition.getSpiderConfigEntity(content);
        try {
            spiderConfigService.save(entity);
            commonResponse.success();
        } catch (Exception e) {
            logger.error(e.fillInStackTrace().toString());
            commonResponse.fail("保存失败");
        }
        return commonResponse;
    }

    @PostMapping("testRun.html")
    @ResponseBody
    public CommonResponse testRun(String content) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        SpiderConfigInfoTransition transition = new SpiderConfigInfoTransition();
        SpiderConfigInfoDto entity = transition.getSpiderConfigInfoDto(content);
        try {
            List<TestPipelineDto> testPipelineDtoList = spiderConfigService.testRun(entity);
            commonResponse.put("data",testPipelineDtoList);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace().toString());
            commonResponse.fail("程序出现错误");
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
            commonResponse.put("connect", spiderConfigService.testDataBase(databaseDto));
        }
        return commonResponse;
    }


}
