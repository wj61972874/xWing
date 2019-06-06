package com.xwin.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 *	获取短信验证码类
 *	GetPhoneMessage
 *	@author houzhiyang
 *	@version 1.0.0
 */

public class GetPhoneMessage {

    //用户ID
    public static final String ACCOUNT_SID = "61972874";
    //密钥
    public static final String AUTH_TOKEN = "wj123456789";
    //请求地址前半部分
    public static final String BASE_URL = "http://api.smsbao.com/sms";
    //随机数
    public static String randNum = RandomUtil.getRandom();
    //短信内容
    public static String smsContent = "【未来科技】您的验证码为"+randNum+"，请于5分钟内正确输入，如非本人操作，请忽略此短信。";

    public static String getPram(String toPhone){
        StringBuffer httpArg = new StringBuffer();
        httpArg.append("u=").append(ACCOUNT_SID).append("&");
        httpArg.append("p=").append(md5(AUTH_TOKEN)).append("&");
        httpArg.append("m=").append(toPhone).append("&");
        httpArg.append("c=").append(encodeUrlString(smsContent, "UTF-8"));

        String result = request(BASE_URL, httpArg.toString());
        return result;
    }

    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = reader.readLine();
            if (strRead != null) {
                sbf.append(strRead);
                while ((strRead = reader.readLine()) != null) {
                    sbf.append("\n");
                    sbf.append(strRead);
                }
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String md5(String plainText) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public static String encodeUrlString(String str, String charset) {
        String strret = null;
        if (str == null)
            return str;
        try {
            strret = java.net.URLEncoder.encode(str, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strret;
    }
}


