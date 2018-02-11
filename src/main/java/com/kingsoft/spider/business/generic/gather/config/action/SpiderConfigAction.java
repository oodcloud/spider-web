package com.kingsoft.spider.business.generic.gather.config.action;

import com.kingsoft.spider.business.generic.gather.config.dto.CommonUrlTestRequest;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigEntity;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoTransition;
import com.kingsoft.spider.business.generic.gather.config.service.SpiderConfigService;
import com.kingsoft.spider.business.spidercore.common.SpiderInfoConvert;
import com.kingsoft.spider.core.common.support.BaseController;
import com.kingsoft.spider.core.common.support.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        SpiderInfoConvert convertDo=new SpiderInfoConvert();
        commonResponse.setData(convertDo.getTargetUrlList(request));
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
        }catch (Exception e)
        {
         logger.error(e.fillInStackTrace().toString());
            commonResponse.fail("保存失败");
        }
        return commonResponse;
    }

}
