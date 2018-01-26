package com.kingsoft.spider.business.common.spider;

import com.kingsoft.spider.core.common.support.HttpClientUtil;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangyujie on 2018/1/2.
 */
public class check {
    String url = "https://search.bilibili.com/video?keyword=反恐行动";
    String ip = "123.185.131.249";
    int port = 8118;

    public void checkProxyIp() throws IOException {

        HttpClientBuilder build = HttpClients.custom();
        CloseableHttpClient client = build.build();
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
        request.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("Accept-Encoding", "gzip, deflate");
        CloseableHttpResponse response = client.execute(request);
        System.out.println(response.getEntity().toString());
//        int code = response.getStatusLine().getStatusCode();
//        if (code == 200) {
//            System.out.println("success:" + url + ":" + port);
//        } else {
//            System.out.println("failed:" + url + ":" + port);
//        }
    }


    public static boolean checkProxy(String ip, Integer port) {
        try {
            //http://1212.ip138.com/ic.asp 可以换成任何比较快的网页
            Jsoup.connect("http://1212.ip138.com/ic.asp")
                    .timeout(2 * 1000)
                    .proxy(ip, port)
                    .get();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
//        check a=new check();
//        a.checkProxyIp();
//        boolean aa = checkProxy("122.114.31.177", 808);
//       String aa= HttpClientUtil.doGet("https://search.bilibili.com/video?keyword=反恐行动");
//        System.out.println(aa);
        Pattern pattern = Pattern.compile("com.kingsoft.spider.*.mapper.*");
        Matcher matcher = pattern.matcher("com.kingsoft.spider.business.common.spider.bSite.mapper");
        while (matcher.find())
        {
            System.out.println(matcher.group());
        }
    }


}
