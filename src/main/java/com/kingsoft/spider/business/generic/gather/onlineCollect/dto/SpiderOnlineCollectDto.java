package com.kingsoft.spider.business.generic.gather.onlineCollect.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangyujie on 2018/1/30.
 */
public class SpiderOnlineCollectDto {
    private String groupName;
    private String itemName;
    private Long  lastSpiderTime;
    private String time;
    private String domain;
    private String status="未启动";
    private String commonUrl;
    private String uuid;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return groupName+itemName+commonUrl.hashCode();
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCommonUrl() {
        return commonUrl;
    }

    public void setCommonUrl(String commonUrl) {
        this.commonUrl = commonUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTime() {
        if (lastSpiderTime!=null||"".equals(lastSpiderTime))
        {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(new Date(lastSpiderTime));
        }
       return "无";
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getLastSpiderTime() {
        return lastSpiderTime;
    }

    public void setLastSpiderTime(Long lastSpiderTime) {
        this.lastSpiderTime = lastSpiderTime;
    }


}
