package com.kingsoft.spider.business.generic.gather.config.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.math.NumberUtils;


import java.util.*;

/**
 * Created by wangyujie on 2018/1/23.
 */
public class SpiderConfigInfoTransition {
    public SpiderConfigInfoDto getSpiderConfigInfoDto(String contents) {
        JSONObject jsonObject = JSONObject.parseObject(contents);
        String commonUrl = jsonObject.getString("commonUrl");
        String urlRule = jsonObject.getString("urlRule");
        String growthPattern = jsonObject.getString("growthPattern");
        String startNum = jsonObject.getString("startNum");
        String endNum = jsonObject.getString("endNum");
        String groupName = jsonObject.getString("groupName");
        String itemName = jsonObject.getString("itemName");
        String siteName = jsonObject.getString("siteName");
        String domain = jsonObject.getString("domain");
        String charset = jsonObject.getString("charset");
        String userAgent = jsonObject.getString("userAgent");
        String cookies = jsonObject.getString("cookies");
        String sleepTime = jsonObject.getString("retryTimes");
        String timeOut = jsonObject.getString("timeOut");
        String thread = jsonObject.getString("thread");
        String headers=jsonObject.getString("headers");
        Set<Map.Entry<String, Object>> set = jsonObject.entrySet();
        Iterator<Map.Entry<String, Object>> iterators = set.iterator();
        List<SpiderConfigInfoDto.MatchField> list = new ArrayList<>();
        while (iterators.hasNext()) {
            Map.Entry<String, Object> entry = iterators.next();
            String fieldName = entry.getKey();
            if (fieldName.contains("fieldName")) {
                int num = Integer.parseInt(fieldName.split("fieldName")[1]);
                SpiderConfigInfoDto.MatchField field = new SpiderConfigInfoDto.MatchField();
                field.setFieldName(entry.getValue().toString());
                field.setFieldEnglishName(jsonObject.getString("fieldEnglishName" + num));
                field.setRegex(jsonObject.getString("regex" + num));
                field.setXpath(jsonObject.getString("xpath" + num));
                list.add(field);
            }
        }
        SpiderConfigInfoDto spiderConfigInfoDto = new SpiderConfigInfoDto();
        spiderConfigInfoDto.setCommonUrl(commonUrl);
        spiderConfigInfoDto.setUrlRule(urlRule);
        spiderConfigInfoDto.setGrowthPattern(growthPattern);
        spiderConfigInfoDto.setStartNum(startNum);
        spiderConfigInfoDto.setEndNum(endNum);
        spiderConfigInfoDto.setGroupName(groupName);
        spiderConfigInfoDto.setItemName(itemName);
        spiderConfigInfoDto.setSiteName(siteName);
        spiderConfigInfoDto.setDomain(domain);
        spiderConfigInfoDto.setCharset(charset);
        spiderConfigInfoDto.setUserAgent(userAgent);
        spiderConfigInfoDto.setCookies(cookies);
        spiderConfigInfoDto.setSleepTime(sleepTime);
        spiderConfigInfoDto.setTimeOut(timeOut);
        spiderConfigInfoDto.setThread(thread);
        spiderConfigInfoDto.setMatchFields(list);
        spiderConfigInfoDto.setHeaders(headers);
        return spiderConfigInfoDto;
    }

    public SpiderConfigEntity getSpiderConfigEntity(String contents) {
        SpiderConfigEntity entity = new SpiderConfigEntity();
        SpiderConfigInfoDto spiderConfigInfoDto = getSpiderConfigInfoDto(contents);
        entity.setCharset(spiderConfigInfoDto.getCharset());
        entity.setCommonUrl(spiderConfigInfoDto.getCommonUrl());
        entity.setCookies(spiderConfigInfoDto.getCookies());
        entity.setDomain(spiderConfigInfoDto.getDomain());
        if (NumberUtils.isNumber(spiderConfigInfoDto.getEndNum())) {
            entity.setEndNum(Integer.valueOf(spiderConfigInfoDto.getEndNum()));
        }
        entity.setGroupName(spiderConfigInfoDto.getGroupName());
        if (NumberUtils.isNumber(spiderConfigInfoDto.getGrowthPattern())) {
            entity.setGrowthPattern(Integer.valueOf(spiderConfigInfoDto.getGrowthPattern()));
        }
        entity.setItemName(spiderConfigInfoDto.getItemName());
        entity.setMatchFields(JSON.toJSONString(spiderConfigInfoDto.getMatchFields()));
        entity.setSiteName(spiderConfigInfoDto.getSiteName());
        if (NumberUtils.isNumber(spiderConfigInfoDto.getSleepTime())) {
            entity.setSleepTime(Integer.valueOf(spiderConfigInfoDto.getSleepTime()));
        }
        if (NumberUtils.isNumber(spiderConfigInfoDto.getStartNum())) {
            entity.setStartNum(Integer.valueOf(spiderConfigInfoDto.getStartNum()));
        }
        if (NumberUtils.isNumber(spiderConfigInfoDto.getThread())) {
            entity.setThread(Integer.valueOf(spiderConfigInfoDto.getThread()));
        }
        if (NumberUtils.isNumber(spiderConfigInfoDto.getTimeOut())) {
            entity.setTimeOut(Integer.valueOf(spiderConfigInfoDto.getTimeOut()));
        }
        if (NumberUtils.isNumber(spiderConfigInfoDto.getUrlRule())) {
            entity.setUrlRule(Integer.valueOf(spiderConfigInfoDto.getUrlRule()));
        }
        entity.setUserAgent(spiderConfigInfoDto.getUserAgent());
        entity.setHeaders(spiderConfigInfoDto.getHeaders());
        entity.setGeneratedTime(System.currentTimeMillis());
       return entity;
    }

}
