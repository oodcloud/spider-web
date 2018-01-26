package com.kingsoft.spider.business.common.spider.weibo;

import com.kingsoft.spider.business.common.spider.weibo.dao.WeiboDto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HandleData {

    public HandleData() {
    }

    void checkIsWriteDB(String time, List<WeiboDto> weiboDtos) {
        Iterator<WeiboDto> weiboDtoIterator = weiboDtos.iterator();
        while (weiboDtoIterator.hasNext()) {
            WeiboDto dto = weiboDtoIterator.next();
            if (time != null && !"".equals(time)) {
                Long date = Long.valueOf(time);
                if (date > dto.getTime()) {
                    weiboDtos.remove(dto);
                }
            }
        }
    }

}