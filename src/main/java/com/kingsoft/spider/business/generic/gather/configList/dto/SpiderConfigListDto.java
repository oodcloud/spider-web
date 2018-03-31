package com.kingsoft.spider.business.generic.gather.configList.dto;

/**
 * Created by wangyujie on 2018/1/30.
 */
public class SpiderConfigListDto {
    private Long id;
    private String groupName;
    private String itemName;
    private String modificationTime;
    private String commonUrl;
    private String uuid;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }
}
