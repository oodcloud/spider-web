package com.kingsoft.spider.business.common.spider.bSite.dto;

import java.util.List;

/**
 * Created by wangyujie on 2017/12/25.
 */
public abstract class AbstractParse<M> {
    public abstract List<M> convert(String url);
}
