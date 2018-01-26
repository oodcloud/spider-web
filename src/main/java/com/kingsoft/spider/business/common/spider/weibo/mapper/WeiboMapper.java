package com.kingsoft.spider.business.common.spider.weibo.mapper;

import com.kingsoft.spider.business.common.spider.weibo.dao.WeiboDto;
import com.kingsoft.spider.core.common.mybatis.Mapper;

import java.util.List;

/**
 * Created by wangyujie on 2017/12/26.
 */
@Mapper
public interface WeiboMapper {
    void xmqzAdd(List<WeiboDto> list);
    void extopiaAdd(List<WeiboDto> list);
    void matAdd(List<WeiboDto> list);
    void producerAdd(List<WeiboDto> list);


    void xmqzSingleAdd(WeiboDto dto);
    void extopiaSingleAdd(WeiboDto dto);
    void matSingleAdd(WeiboDto dto);
    void producerSingleAdd(WeiboDto dto);

}
