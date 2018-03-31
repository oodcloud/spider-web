package com.kingsoft.spider.business.spidercore.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingsoft.spider.business.generic.gather.configList.dto.SpiderConfigSaveDto;
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
        String targetUrl = jsonObject.getString("targetUrl");
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
        String sleepTime=jsonObject.getString("sleepTime");
        String retryTimes = jsonObject.getString("retryTimes");
        String timeOut = jsonObject.getString("timeOut");
        String thread = jsonObject.getString("thread");
        String headers = jsonObject.getString("headers");
        String dbType = jsonObject.getString("dbType");
        String dbAddress = jsonObject.getString("dbAddress");
        String dbName = jsonObject.getString("dbName");
        String dbUserName = jsonObject.getString("dbUserName");
        String dbPassWord = jsonObject.getString("dbPassWord");
        String dbTable=jsonObject.getString("dbTable");
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
                field.setDefaultValue(jsonObject.getString("defaultValue"+num));
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
        spiderConfigInfoDto.setTargetUrl(targetUrl);
        spiderConfigInfoDto.setDbType(dbType);
        spiderConfigInfoDto.setDbAddress(dbAddress);
        spiderConfigInfoDto.setDbName(dbName);
        spiderConfigInfoDto.setDbUserName(dbUserName);
        spiderConfigInfoDto.setDbPassWord(dbPassWord);
        spiderConfigInfoDto.setDbTable(dbTable);
        spiderConfigInfoDto.setRetryTimes(retryTimes);
        return spiderConfigInfoDto;
    }

    public SpiderConfigEntity getSpiderConfigEntity(String contents) {
        SpiderConfigEntity entity = new SpiderConfigEntity();
        SpiderConfigInfoDto spiderConfigInfoDto = getSpiderConfigInfoDto(contents);
        entity.setCharset(spiderConfigInfoDto.getCharset());
        entity.setCommonUrl(spiderConfigInfoDto.getCommonUrl());
        entity.setCookies(spiderConfigInfoDto.getCookies());
        entity.setTargetUrl(spiderConfigInfoDto.getTargetUrl());
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
        entity.setDbType(spiderConfigInfoDto.getDbType());
        entity.setDbAddress(spiderConfigInfoDto.getDbAddress());
        entity.setDbName(spiderConfigInfoDto.getDbName());
        entity.setDbUserName(spiderConfigInfoDto.getDbUserName());
        entity.setDbPassWord(spiderConfigInfoDto.getDbPassWord());
        entity.setDbTable(spiderConfigInfoDto.getDbTable());
        entity.setRetryTimes(spiderConfigInfoDto.getRetryTimes());
        return entity;
    }

    public SpiderConfigSaveDto getSpiderConfigSaveDto(String contents) {
        SpiderConfigSaveDto entity = new SpiderConfigSaveDto();
        SpiderConfigInfoDto spiderConfigInfoDto = getSpiderConfigInfoDto(contents);
        entity.setCharset(spiderConfigInfoDto.getCharset());
        entity.setCommonUrl(spiderConfigInfoDto.getCommonUrl());
        entity.setCookies(spiderConfigInfoDto.getCookies());
        entity.setTargetUrl(spiderConfigInfoDto.getTargetUrl());
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
        entity.setDbType(spiderConfigInfoDto.getDbType());
        entity.setDbAddress(spiderConfigInfoDto.getDbAddress());
        entity.setDbName(spiderConfigInfoDto.getDbName());
        entity.setDbUserName(spiderConfigInfoDto.getDbUserName());
        entity.setDbPassWord(spiderConfigInfoDto.getDbPassWord());
        entity.setDbTable(spiderConfigInfoDto.getDbTable());
        entity.setRetryTimes(spiderConfigInfoDto.getRetryTimes());
        entity.setModificationTime(new Date());
        JSONObject jsonObject = JSONObject.parseObject(contents);
        String id = jsonObject.getString("id");
        entity.setId(Long.valueOf(id));
        return entity;
    }

    public SpiderConfigEntity switchToEntity(SpiderConfigInfoDto dto) {
        SpiderConfigEntity entity = new SpiderConfigEntity();
        entity.setCharset(dto.getCharset());
        entity.setCommonUrl(dto.getCommonUrl());
        entity.setCookies(dto.getCookies());
        entity.setTargetUrl(dto.getTargetUrl());
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
        entity.setDbType(dto.getDbType());
        entity.setDbAddress(dto.getDbAddress());
        entity.setDbName(dto.getDbName());
        entity.setDbUserName(dto.getDbUserName());
        entity.setDbPassWord(dto.getDbPassWord());
        entity.setDbTable(dto.getDbTable());
        entity.setRetryTimes(dto.getRetryTimes());
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
        infoDto.setTargetUrl(dto.getTargetUrl());
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
        infoDto.setDbType(dto.getDbType());
        infoDto.setDbAddress(dto.getDbAddress());
        infoDto.setDbName(dto.getDbName());
        infoDto.setDbUserName(dto.getDbUserName());
        infoDto.setDbPassWord(dto.getDbPassWord());
        infoDto.setDbTable(dto.getDbTable());
        infoDto.setRetryTimes(dto.getRetryTimes());
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
