package com.kingsoft.spider.business.spidercore.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.math.NumberUtils;


import java.util.*;

/**
 * 配置信息转换
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
        String headers = jsonObject.getString("headers");
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


    public SpiderConfigEntity switchToEntity(SpiderConfigInfoDto dto) {
        SpiderConfigEntity entity = new SpiderConfigEntity();
        entity.setCharset(dto.getCharset());
        entity.setCommonUrl(dto.getCommonUrl());
        entity.setCookies(dto.getCookies());
        entity.setDomain(dto.getDomain());
        entity.setEndNum(Integer.valueOf(dto.getEndNum()));
        entity.setGroupName(dto.getGroupName());
        entity.setGrowthPattern(Integer.valueOf(dto.getGrowthPattern()));
        entity.setHeaders(dto.getHeaders());
        entity.setItemName(dto.getItemName());
        entity.setMatchFields(JSON.toJSONString(dto.getMatchFields()));
        entity.setSiteName(dto.getSiteName());
        entity.setSleepTime(Integer.valueOf(dto.getSleepTime()));
        entity.setStartNum(Integer.valueOf(dto.getStartNum()));
        entity.setThread(Integer.valueOf(dto.getThread()));
        entity.setTimeOut(Integer.valueOf(dto.getTimeOut()));
        entity.setUrlRule(Integer.valueOf(dto.getUrlRule()));
        entity.setUserAgent(dto.getUserAgent());
        entity.setGeneratedTime(dto.getGeneratedTime());
        return entity;
    }

    public List<SpiderConfigEntity> switchToEntity(List<SpiderConfigInfoDto> list) {
        List<SpiderConfigEntity> entityList = new ArrayList<>();
        for (SpiderConfigInfoDto dto : list) {
            SpiderConfigEntity entity = switchToEntity(dto);
            entityList.add(entity);
        }
        return entityList;
    }


    public SpiderConfigInfoDto switchToDto(SpiderConfigEntity dto) {
        SpiderConfigInfoDto infoDto = new SpiderConfigInfoDto();
        infoDto.setCharset(dto.getCharset());
        infoDto.setCommonUrl(dto.getCommonUrl());
        infoDto.setCookies(dto.getCookies());
        infoDto.setDomain(dto.getDomain());
        infoDto.setEndNum(String.valueOf(dto.getEndNum()));
        infoDto.setGroupName(dto.getGroupName());
        infoDto.setGrowthPattern(String.valueOf(dto.getGrowthPattern()));
        infoDto.setHeaders(dto.getHeaders());
        infoDto.setItemName(dto.getItemName());
        infoDto.setMatchFields(JSON.parseArray(dto.getMatchFields(), SpiderConfigInfoDto.MatchField.class));
        infoDto.setSiteName(dto.getSiteName());
        infoDto.setSleepTime(String.valueOf(dto.getSleepTime()));
        infoDto.setStartNum(String.valueOf(dto.getStartNum()));
        infoDto.setThread(String.valueOf(dto.getThread()));
        infoDto.setTimeOut(String.valueOf(dto.getTimeOut()));
        infoDto.setUrlRule(String.valueOf(dto.getUrlRule()));
        infoDto.setUserAgent(dto.getUserAgent());
        infoDto.setGeneratedTime(dto.getGeneratedTime());
        return infoDto;
    }

    public List<SpiderConfigInfoDto> switchToDto(List<SpiderConfigEntity> list) {
        List<SpiderConfigInfoDto> entityList = new ArrayList<>();
        for (SpiderConfigEntity dto : list) {
            SpiderConfigInfoDto entity = switchToDto(dto);
            entityList.add(entity);
        }
        return entityList;
    }

}
