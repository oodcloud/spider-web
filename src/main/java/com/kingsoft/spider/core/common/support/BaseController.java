package com.kingsoft.spider.core.common.support;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangyujie on 2017/12/20.
 */
public class BaseController {
    protected  String thymeleaf(String url){
        RequestMapping requestMapping=  this.getClass().getDeclaredAnnotation(RequestMapping.class);
       return requestMapping.value()[0]+"/"+url.concat(".html");
    }
}
