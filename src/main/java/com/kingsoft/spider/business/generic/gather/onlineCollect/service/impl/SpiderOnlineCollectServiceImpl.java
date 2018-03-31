package com.kingsoft.spider.business.generic.gather.onlineCollect.service.impl;

import com.kingsoft.spider.business.datadb.DbUseHandleUtils;
import com.kingsoft.spider.business.generic.gather.onlineCollect.dto.SpiderLogInfoDto;
import com.kingsoft.spider.business.generic.gather.onlineCollect.dto.SpiderOnlineCollectDto;
import com.kingsoft.spider.business.generic.gather.onlineCollect.dto.SpiderStatusDto;
import com.kingsoft.spider.business.generic.gather.onlineCollect.dto.SpiderStatusTimerDto;
import com.kingsoft.spider.business.generic.gather.onlineCollect.mapper.SpiderOnlineCollectMapper;
import com.kingsoft.spider.business.generic.gather.onlineCollect.service.SpiderOnlineCollectService;
import com.kingsoft.spider.business.spidercore.SpiderBoot;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigEntity;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoTransition;
import com.kingsoft.spider.business.spidercore.monitor.SpiderMonitorBootstrap;
import com.kingsoft.spider.business.spidercore.monitor.SpiderStatusBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyujie on 2018/1/30.
 */
@Service
public class SpiderOnlineCollectServiceImpl implements SpiderOnlineCollectService {
    @Autowired
    private SpiderOnlineCollectMapper spiderOnlineCollectMapper;

    @Override
    public List<SpiderOnlineCollectDto> getAll() {
        List<SpiderOnlineCollectDto> collectDtoList = spiderOnlineCollectMapper.getAll();
        SpiderMonitorBootstrap bootstrap = SpiderMonitorBootstrap.instance();
        List<SpiderStatusBean> spiderList = bootstrap.getSpiderStatuses();
        for (SpiderStatusBean bean : spiderList) {
            for (SpiderOnlineCollectDto collectDto : collectDtoList) {
                String uuid =collectDto.getGroupName()+collectDto.getItemName()+collectDto.getCommonUrl().hashCode();
                if (uuid.equals(bean.getUUid())) {
                    switch (bean.getStatus()) {
                        case "Init":
                            collectDto.setStatus("初始化");
                            break;
                        case "Running":
                            collectDto.setStatus("运行中");
                            break;
                        case "Stopped":
                            collectDto.setStatus("已停止");
                            break;
                        default:
                            collectDto.setStatus("未启动");
                    }
                }
            }
        }
        return collectDtoList;
    }

    @Override
    public SpiderStatusDto start(Integer id) {
        SpiderConfigEntity configEntity = spiderOnlineCollectMapper.getSpiderConfigById(id);
        SpiderConfigInfoTransition spiderConfigInfoTransition = new SpiderConfigInfoTransition();
        SpiderConfigInfoDto spiderConfigInfoDto = spiderConfigInfoTransition.switchToDto(configEntity);
        SpiderStatusDto spiderStatusDto = new SpiderStatusDto();
        SpiderMonitorBootstrap bootstrap = SpiderMonitorBootstrap.instance();
        List<SpiderStatusBean> spiderList = bootstrap.getSpiderStatuses();
        boolean isFirstRun = true;
        String uuid =spiderConfigInfoDto.getGroupName()+spiderConfigInfoDto.getItemName()+spiderConfigInfoDto.getCommonUrl().hashCode();


        Label54:
        for (SpiderStatusBean bean : spiderList) {
            if (bean.getUUid().equals(uuid)) {
                isFirstRun = false;
                switch (bean.getStatus()) {
                    case "Init":
                        bean.stop();
                        break Label54;
                    case "Running":
                        bean.stop();
                        break Label54;
                    case "Stopped":
                        //检查表结构是否改变
                        Integer dbTableColumnCount = DbUseHandleUtils.getColumnCount(spiderConfigInfoDto);
                        // 调用该id的数据的字段数量和matchfieldList匹配
                        if (dbTableColumnCount==spiderConfigInfoDto.getMatchFields().size()+4) //dbTableColumnCount从1开始 spiderConfigInfoDto.getMatchFields().size()从0开始 然后默认加入id，create_time,site_url
                        {
                            spiderOnlineCollectMapper.saveTime(id, System.currentTimeMillis());
                            bean.start();
                            spiderStatusDto.setCode(200);
                            spiderStatusDto.setMessage("启动成功");
                        }else {
                            spiderList.remove(bean);
                            isFirstRun=true;
                        }
                        break Label54;
                }
            }
        }
        if (isFirstRun)//该爬虫第一次运行走该代码，否则不走
        {
            SpiderBoot spiderBoot = new SpiderBoot();
            try {
                spiderOnlineCollectMapper.saveTime(id, System.currentTimeMillis());
                spiderBoot.start(spiderConfigInfoDto);
                spiderStatusDto.setCode(200);
                spiderStatusDto.setMessage("启动成功");
            } catch (Exception e) {
                spiderStatusDto.setCode(100);
                spiderStatusDto.setMessage("启动失败，请检查配置是否正确。");
                e.fillInStackTrace();
            }
        }
        return spiderStatusDto;
    }



    @Override
    public SpiderStatusDto stop(String uuid) {
        SpiderMonitorBootstrap bootstrap = SpiderMonitorBootstrap.instance();
        List<SpiderStatusBean> spiderList = bootstrap.getSpiderStatuses();
        SpiderStatusDto spiderStatusDto = new SpiderStatusDto();
        try {
            boolean flag = true;
            for (SpiderStatusBean bean : spiderList) {
                if (bean.getUUid().equals(uuid)) {
                    if (bean.getStatus().equals("Running")) {
                        bean.stop();
                        spiderStatusDto.setCode(200);
                        spiderStatusDto.setMessage("停止成功");
                        flag = false;
                        break;
                    } else {
                        spiderStatusDto.setCode(100);
                        spiderStatusDto.setMessage("该爬虫任务已经停止.");
                    }

                }
            }
            if (flag) {
                spiderStatusDto.setCode(100);
                spiderStatusDto.setMessage("未启动该爬虫任务");
            }
        } catch (Exception e) {
            spiderStatusDto.setCode(100);
            spiderStatusDto.setMessage("停止失败，原因请看错误日志.");
            e.fillInStackTrace();
        }

        return spiderStatusDto;
    }

    @Override
    public List<SpiderStatusTimerDto> getSpiderStutas() {
        List<SpiderStatusTimerDto> statusTimerDtos = new ArrayList<>();
        SpiderStatusTimerDto spiderStatusTimerDto = null;
        SpiderMonitorBootstrap bootstrap = SpiderMonitorBootstrap.instance();
        List<SpiderStatusBean> spiderList = bootstrap.getSpiderStatuses();
        for (SpiderStatusBean bean : spiderList) {
            spiderStatusTimerDto = new SpiderStatusTimerDto();
            spiderStatusTimerDto.setStatus(bean.getStatus());
            spiderStatusTimerDto.setUuid(bean.getUUid());
            statusTimerDtos.add(spiderStatusTimerDto);
        }
        return statusTimerDtos;
    }

    @Override
    public SpiderLogInfoDto getSpiderLogInfo(String uuid, Integer index) {
        SpiderMonitorBootstrap bootstrap = SpiderMonitorBootstrap.instance();
        List<SpiderStatusBean> spiderList = bootstrap.getSpiderStatuses();
        SpiderLogInfoDto logInfoDto=new SpiderLogInfoDto();
        boolean flag = true;
        for (SpiderStatusBean bean : spiderList) {
            if (bean.getUUid().equals(uuid)) {
                int count=0;
                int resultSize=bean.getAllResultList().size();
                if (resultSize%20==0)
                {
                    count=(resultSize+1)/20;
                }else {
                    count=(resultSize+1)/20+1;
                }
                if (bean.getStatus().equals("Running")||(bean.getStatus().equals("Stopped"))&&count>=index) {
                    flag = false;

                    if ((index-1)*20>bean.getTotalPageCount())
                    {
                        logInfoDto.setMsg("当前任务未在进行");
                        break;
                    }
                    List<String> showLog=new ArrayList<>(20);
                    for (;;)
                    {
                        List<String> logs = bean.getAllResultList();
                        int start=(index-1)*20;
                        if (logs.size()==20*index)
                        {

                            int end=index*20-1;
                            showLog.addAll(logs.subList(start,end));
                            logInfoDto.setNowLogs(showLog);
                            logInfoDto.setTotal(bean.getTotalPageCount());
                            logInfoDto.setNowSize(bean.getAllResultList().size()+1);
                            logInfoDto.setMsg("当前任务进行中");
                            break;
                        }
                        else if (logs.size()!=20*index &&  bean.getTotalPageCount()-logs.size()<=2&& (logs.size()-1)>start)
                        {
                            int end=logs.size()-1;
                            showLog.addAll(logs.subList(start,end));
                            logInfoDto.setNowLogs(showLog);
                            logInfoDto.setTotal(bean.getTotalPageCount());
                            logInfoDto.setNowSize(bean.getAllResultList().size()+1);
                            logInfoDto.setMsg("当前任务进行中");
                            break;
                        }
                    }

                }else {
                    logInfoDto.setMsg("当前任务未在进行");
                }
                break;
            }
        }
        if (spiderList.size()==0)
        {
            logInfoDto.setMsg("当前任务未在进行");
        }
        if (flag) {
            logInfoDto.setMsg("当前任务未在进行");
        }
        return logInfoDto;
    }
}
