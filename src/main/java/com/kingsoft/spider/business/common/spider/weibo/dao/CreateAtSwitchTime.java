package com.kingsoft.spider.business.common.spider.weibo.dao;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangyujie on 2017/12/21.
 */
public class CreateAtSwitchTime {
    public static Date convert(String createAt)  {

        String[] c = createAt.split("-");
        String time = null;
        switch (c.length) {
            case 2:
                time = Calendar.getInstance().getWeekYear() + "-" + createAt;
                break;
            case 3:
                time = createAt;
                break;
        }
        if (c.length == 1) {
            if (createAt.contains("小时")) {
                int value = Integer.parseInt(createAt.substring(0,createAt.lastIndexOf("小时")));
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, -1 * value);
                return calendar.getTime();
            } else if (createAt.contains("分钟") || createAt.contains("秒")) {
                return Calendar.getInstance().getTime();
            }else if (createAt.contains("昨天")){
                Calendar calendar= Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH,-1);
                return calendar.getTime();
            }
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(-28800);
    }
}
