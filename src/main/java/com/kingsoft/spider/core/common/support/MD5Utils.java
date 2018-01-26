package com.kingsoft.spider.core.common.support;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangyujie on 2017/12/28.
 */
public class MD5Utils {

    public static String encodeMd5(String content){
        String newContent="";

        try {
            MessageDigest digest=MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder=new BASE64Encoder();
            newContent=  base64Encoder.encode(digest.digest(content.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newContent;
    }

    public static void main(String[] args) {
        String aa=MD5Utils.encodeMd5("admin");
        System.out.println(aa);
    }
}
