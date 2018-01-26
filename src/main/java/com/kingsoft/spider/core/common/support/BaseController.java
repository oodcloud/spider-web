package com.kingsoft.spider.core.common.support;

/**
 * Created by wangyujie on 2017/12/20.
 */
public class BaseController {
    protected  String thymeleaf(String url){
       return url.concat(".html");
    }
}
