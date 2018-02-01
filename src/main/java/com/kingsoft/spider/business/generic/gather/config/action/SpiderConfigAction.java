package com.kingsoft.spider.business.generic.gather.config.action;

import com.kingsoft.spider.business.generic.gather.config.dto.CommonUrlTestRequest;
import com.kingsoft.spider.business.generic.gather.config.dto.SpiderConfigEntity;
import com.kingsoft.spider.business.generic.gather.config.dto.SpiderConfigInfoTransition;
import com.kingsoft.spider.business.generic.gather.config.service.SpiderConfigService;
import com.kingsoft.spider.core.common.config.SpiderUrlRole;
import com.kingsoft.spider.core.common.support.BaseController;
import com.kingsoft.spider.core.common.support.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
        return thymeleaf("generic/gather/config/index");
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
        String commonUrl = request.getCommonUrl();
        int startNum = request.getStartNum();
        int endNum = request.getEndNum();
        int growthPattern = request.getGrowthPattern();
        int urlRule = request.getUrlRule();
        if (!commonUrl.contains("http://") && !commonUrl.contains("https://")) {
            commonUrl = "http://" + commonUrl;
        }
        String[] commonUrlArray = commonUrl.split("\\*");
        String commonUrlFrom = commonUrlArray[0];
        List<String> urls = new ArrayList<>();
        createUrlList(startNum, endNum, growthPattern, urlRule, commonUrlArray, commonUrlFrom, urls);
        commonResponse.setData(urls);
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

    private void createUrlList(int startNum, int endNum, int growthPattern, int urlRule, String[] commonUrlArray, String commonUrlFrom, List<String> urls) {
        switch (urlRule) {
            case SpiderUrlRole.GROWTHPATTERN_GEOMETRIC://等比
                if (growthPattern == 1) {
                    return;
                }
                for (int i = startNum; i <= endNum; i = i * growthPattern) {
                    if (commonUrlArray.length > 1) {
                        String commonUrlTo = commonUrlArray[1];
                        urls.add(commonUrlFrom + i + commonUrlTo);
                    } else {
                        urls.add(commonUrlFrom + i);
                    }
                }
                break;
            case SpiderUrlRole.GROWTHPATTERN_MONOTONE_INCREASING://单调递增
                for (int i = startNum; i <= endNum; i = i + growthPattern) {
                    if (commonUrlArray.length > 1) {
                        String commonUrlTo = commonUrlArray[1];
                        urls.add(commonUrlFrom + i + commonUrlTo);
                    } else {
                        urls.add(commonUrlFrom + i);
                    }
                }
                break;
            case SpiderUrlRole.GROWTHPATTERN_MONOTONE_DECREASING://单调递减
                for (int i = startNum; i <= endNum; i = i - growthPattern) {
                    if (commonUrlArray.length > 1) {
                        String commonUrlTo = commonUrlArray[1];
                        urls.add(commonUrlFrom + i + commonUrlTo);
                    } else {
                        urls.add(commonUrlFrom + i);
                    }
                }
                break;
        }
    }
}
