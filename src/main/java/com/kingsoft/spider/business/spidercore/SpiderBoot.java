package com.kingsoft.spider.business.spidercore;

import com.alibaba.fastjson.JSON;
import com.kingsoft.spider.business.spidercore.common.SpiderConfigInfoDto;
import com.kingsoft.spider.business.spidercore.common.SpiderHelper;
import com.kingsoft.spider.business.spidercore.common.TestPipelineDto;
import com.kingsoft.spider.business.spidercore.db.DataBaseBoot;
import com.kingsoft.spider.business.spidercore.monitor.SpiderMonitorBootstrap;
import com.kingsoft.spider.business.spidercore.monitor.SpiderStatusBean;
import com.kingsoft.spider.business.spidercore.run.*;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.util.*;

/**
 * Created by wangyujie on 2018/2/10.
 */
public class SpiderBoot {
    private CommonPageProcessor processor;
    private CommonPipeline pipeline;
    private SpiderHelper convert;
    private DataBaseBoot dataBaseBoot;
    private String[] urls;
    private SpiderWorkInfo workInfo;
    private TestPipeline testPipeline;


    private void init(SpiderConfigInfoDto dto) {
        convert = new SpiderHelper();
        processor = new CommonPageProcessor(convert.getSpiderRuleFieldList(dto), convert.getTargetUrl(dto), convert.getTargetSiteRequestParams(dto));
        dataBaseBoot = convert.getDataBase(dto);
        pipeline = new CommonPipeline(dataBaseBoot, convert.getSpiderRuleFieldList(dto), dto.getDbTable());
        urls = convert.getCommonUrlStrings(dto);
        workInfo = convert.getSpiderWorkInfo(dto);

    }

    public Spider react(SpiderConfigInfoDto dto) {
        init(dto);
        Spider spider = Spider.create(processor).addUrl(urls).setScheduler(new QueueScheduler()).addPipeline(pipeline);
        String uuid = workInfo.getUuid(); //uuid =dto.getGroupName()+dto.getItemName()+dto.getCommonUrl().hashCode()
        String threadNum = workInfo.getThread();
        if (threadNum != null) {
            spider.thread(Integer.parseInt(threadNum));
        }
        if (uuid != null) {
            spider.setUUID(uuid);
        }
        return spider;
    }

    public void closeDb(Spider spider) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (spider != null && "Stopped".equals(spider.getStatus().name()) && spider.isExitWhenComplete()) {
                    System.out.println("spider status is ...." + spider.getStatus().name());
                    System.out.println("关闭数据库");
                    dataBaseBoot.close();
                    timer.cancel();
                }
            }
        }, 2000, 200);
    }

    public SpiderMonitorBootstrap registerMonitor(Spider spider) {
        return SpiderMonitorBootstrap.instance().register(spider);
    }
    public  List<TestPipelineDto> runTestResult(SpiderConfigInfoDto dto)
    {
        List<TestPipelineDto> testPipelineDtos= Collections.synchronizedList(new ArrayList<>());
        init(dto);
        testPipeline=new TestPipeline();
        testPipeline.setPipelineCallBack(dto1 -> {
            testPipelineDtos.add(dto1);
            return testPipelineDtos;
        });
        Spider spider= Spider.create(processor).addPipeline(testPipeline);
        String uuid = workInfo.getUuid();
        String threadNum = workInfo.getThread();
        if (threadNum != null) {
            spider.thread(Integer.parseInt(threadNum));
        }
        if (uuid != null) {
            spider.setUUID(uuid);
        }
        spider.test(urls[0]);
        spider.run();

        while (true)
        {
            int index=testPipelineDtos.size();
            if (index!=0&&"Stopped".equals(spider.getStatus().name()))
            {
                spider.close();
                return testPipelineDtos;
            }else if (index==0&&"Stopped".equals(spider.getStatus().name()))
            {
                spider.close();
                TestPipelineDto pipelineDto=new TestPipelineDto();
                pipelineDto.setValue("爬取内容为空!请检查匹配规则");
                pipelineDto.setKey("error:");
                testPipelineDtos.add(new TestPipelineDto());
                return testPipelineDtos;
            }
        }
    }

    public void start(SpiderConfigInfoDto dto) {
        Spider spider = react(dto);
        spider.runAsync();
        registerMonitor(spider);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("正在获取爬虫info.......");
                List<SpiderStatusBean> statusBeanList = SpiderMonitorBootstrap.instance().getSpiderStatuses();

                System.out.println("数量：" + statusBeanList.size());
                for (SpiderStatusBean bean : statusBeanList) {
                    if (spider.getUUID().equals(bean.getUUid()))
                    {
                        if ("Running".equals(bean.getStatus())) {
                            System.out.println("===============================");
                            System.out.println("爬虫名字:" + bean.getName());
                            System.out.println("爬虫状态:" + bean.getStatus());
                            System.out.println("开始时间:" + bean.getStartTime());
                            System.out.println("当前爬虫线程数量:" + bean.getThread());
                            System.out.println("当前采集的url数量:" + bean.getTotalPageCount());
                            System.out.println("当前成功采集的url数量:" + bean.getSuccessPageCount());
                            System.out.println("当前失败采集的url数量:" + bean.getErrorPageCount());
                            System.out.println("===============================");
                        }else   if ("Stopped".equals(bean.getStatus())){
                                System.out.println("当前爬虫处于空闲状态，数量为:" + statusBeanList.size());
                                timer.cancel();
                                System.out.println("关闭定时任务");
                        }

                        break;
                    }
                }

                System.out.println("正在获取爬虫info.....结束");

            }
        }, 2000, 1000);
        closeDb(spider);
    }

    public static void main(String[] args) {
        SpiderBoot boot = new SpiderBoot();
        SpiderConfigInfoDto spiderConfigInfoDto = new SpiderConfigInfoDto();
        spiderConfigInfoDto.setCommonUrl("http://list.iqiyi.com/www/1/1-------------11-*-1-iqiyi--.html");
        spiderConfigInfoDto.setUrlRule("2");
        spiderConfigInfoDto.setGrowthPattern("1");
        spiderConfigInfoDto.setStartNum("1");
        spiderConfigInfoDto.setEndNum("1");
        spiderConfigInfoDto.setGroupName("电影");
        spiderConfigInfoDto.setItemName("华语");
        spiderConfigInfoDto.setSiteName("爱奇艺");
        spiderConfigInfoDto.setDomain("http://www.iqiyi.com");
        spiderConfigInfoDto.setCharset("utf-8");
        spiderConfigInfoDto.setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        spiderConfigInfoDto.setCookies("__uuid=fcef227c-25ff-976c-8933-17916f51e416; QC005=a70bfc0d560c4bdb50245fe2b3f55c5d; P00004=-635926317.1507694517.fcfda5d437; T00404=caa36853766284f67805512646c097a7; playspeedguide=1; QC021=%5B%7B%22key%22%3A%22%E4%B8%87%E7%95%8C%E4%BB%99%E8%B8%AA%22%7D%2C%7B%22key%22%3A%22%E6%AD%A6%E5%BA%9A%E7%BA%AA%22%7D%5D; QC124=1%7C0; QP001=1; QC118=%7B%22color%22%3A%22FFFFFF%22%2C%22channelConfig%22%3A0%2C%22hadTip%22%3A1%2C%22isOpen%22%3A1%7D; QC159=%7B%22color%22%3A%22FFFFFF%22%2C%22channelConfig%22%3A0%2C%22hadTip%22%3A1%2C%22isOpen%22%3A1%2C%22speed%22%3A13%2C%22density%22%3A30%2C%22opacity%22%3A86%2C%22isFilterColorFont%22%3A1%2C%22proofShield%22%3A0%2C%22forcedFontSize%22%3A24%2C%22isFilterImage%22%3A1%7D; jingdong=%7B%22rdNum%22%3A0%2C%22index%22%3A0%7D; Hm_lvt_53b7374a63c37483e5dd97d78d9bb36e=1517546736,1518064795,1518149270,1519363835; Hm_lpvt_53b7374a63c37483e5dd97d78d9bb36e=1519363840; QC008=1507694640.1519363834.1519363840.30; JSESSIONID=aaadjpWdylqvk_Nvkx1fw; QC007=DIRECT; QC006=6695c4fdd1d59e15ef6b1103b617dc6b; QC010=166554237; __dfp=a0a6f148dcde2249c6ada2a453ec2444afe48126a758608bb8e25c4b6f91aa7463@1521955846329@1519363846329; QC001=1; QILINPUSH=1; QY00001=1023108576");
        spiderConfigInfoDto.setSleepTime("800");
        spiderConfigInfoDto.setTimeOut("5000");
        spiderConfigInfoDto.setThread("2");

        spiderConfigInfoDto.setDbType("com.mysql.jdbc.Driver");
        spiderConfigInfoDto.setDbAddress("127.0.0.1:3306");
        spiderConfigInfoDto.setDbTable("test_aaai");
        spiderConfigInfoDto.setDbName("spider_web");
        spiderConfigInfoDto.setDbUserName("root");
        spiderConfigInfoDto.setDbPassWord("123456");
        List<SpiderConfigInfoDto.MatchField> matchFields = new ArrayList<>();
        SpiderConfigInfoDto.MatchField field = new SpiderConfigInfoDto.MatchField();
        field.setFieldEnglishName("title");
        field.setFieldName("标题");
        field.setXpath("//div[@class='mod-breadcrumb_left']//span[@id='widget-videotitle']/text()");
        field.setDefaultValue("");
        matchFields.add(field);

        SpiderConfigInfoDto.MatchField field1 = new SpiderConfigInfoDto.MatchField();
        field1.setFieldEnglishName("desca");
        field1.setFieldName("简介");
        field1.setXpath("//div[@class='vInfoSide_videoNew pt22 j_program_wrap']//span[@class='partDes j_param_des']/text()");
        field1.setDefaultValue("");
        matchFields.add(field1);

        SpiderConfigInfoDto.MatchField field2 = new SpiderConfigInfoDto.MatchField();
        field2.setFieldEnglishName("type");
        field2.setFieldName("type");
        field2.setDefaultValue("1");
        matchFields.add(field2);

        spiderConfigInfoDto.setMatchFields(matchFields);
        spiderConfigInfoDto.setTargetUrl("^http://www.iqiyi.com/v_\\w+.html[\\w|\\W]*$");


        //保存数据测试
        Spider spider = boot.react(spiderConfigInfoDto);
        spider.runAsync();
        boot.registerMonitor(spider);
        boot.closeDb(spider);

        //测试是否抓取成功
//        boot.runTestResult(spiderConfigInfoDto);


    }


}
