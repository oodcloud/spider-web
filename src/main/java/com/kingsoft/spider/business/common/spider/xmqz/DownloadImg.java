package com.kingsoft.spider.business.common.spider.xmqz;

import com.kingsoft.spider.core.common.support.HttpClientUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

/**
 * Created by wangyujie on 2017/12/27.
 */
public class DownloadImg {

    public static void save(String path, String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String urlPath = url.split("&tp=")[0];
        HttpGet httpGet = new HttpGet(urlPath);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        InputStream inputStream = response1.getEntity().getContent();
        String[] urlSplite = urlPath.split("wx_fmt=");
        String suffix = null;
        if (urlSplite.length == 2) {
            suffix = urlPath.split("wx_fmt=")[1];
        }
        if (suffix==null)
        {
            suffix="png";
        }
        File urlRoute = new File(path);
        if (!urlRoute.exists()) {
            urlRoute.mkdirs();
        }
        File file = new File(path + File.separator + System.currentTimeMillis() + "." + suffix);
        if (!file.exists()) {

            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        byte[] bytes = new byte[2048];
        int len = 0;
        while ((len = inputStream.read(bytes)) != -1) {
            bufferedOutputStream.write(bytes, 0, len);
        }
        bufferedOutputStream.close();

    }

    public static void main(String[] args) {

        try {
            DownloadImg.save("/xmqz/内/", "https://mmbiz.qpic.cn/mmbiz_png/0Wj2GZgfNCyBm4cQx2CdZGIaFMkh6DgIOibgsqmJl538F1VpN5M1GG8J3fBsiaA7epCbgS4iaVcOiaSN8XAkNxTcJw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
