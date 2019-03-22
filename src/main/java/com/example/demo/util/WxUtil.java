package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.UnsupportedEncodingException;

public class WxUtil {

    //从微信后台拿到APPID和APPSECRET 并封装为常量
    public static final String APPID = "wxf2d40284c4d3e069";
    public static final String APPSECRET = "dbba666836ce687304dd2999b29d27f0";


    /**
     * 生成用于获取access_token的Code的Url
     *应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo
     *弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息
     * @param redirectUrl
     * @return
     */
    public static String getRequestCodeUrl(String redirectUrl) {
        return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
                WxUtil.APPID, redirectUrl, "snsapi_userinfo", "STATE");
    }


    /**
     * 通过code换取网页授权access_token
     * @param code
     * @return
     */
    public static String getAccessTokenByCode(String code) throws UnsupportedEncodingException {

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + APPSECRET + "&code="+code+"&grant_type=authorization_code";

        String response = sendHttpGet(url);

        JSONObject jsonObject = JSON.parseObject(response);

        System.out.println("openid==>" + jsonObject.getString("openid"));

        return response;
    }


    /**
     * 刷新access_token（如果需要）
     * @return
     */
    public static String refreshToken(String accessToken) throws UnsupportedEncodingException {

        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + APPID + "&grant_type=refresh_token&refresh_token=" + accessToken;

        String response = sendHttpGet(url);

        return response;
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     * @return
     */
    public static String getUserinfo(String openId, String accessToken) throws UnsupportedEncodingException {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
        String response = sendHttpGet(url);
        return response;
    }

    /**
     * 检验授权凭证（access_token）是否有效
     * @return
     */
    public static String verifyAccessToken(String openId, String accessToken) throws UnsupportedEncodingException {
        String url = "https://api.weixin.qq.com/sns/auth?access_token=" + accessToken + "&openid=" + openId;
        String response = sendHttpGet(url);
        return response;
    }

    /**
     * 根据appid和appsecret获取accessToken
     * @return
     */
    public static String getAccessTokenByAppId(String appId, String appSecret) throws UnsupportedEncodingException {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId + "&secret=" + appSecret;
        String response = sendHttpGet(url);
        return response;
    }


    public static String sendHttpGet(String url) throws UnsupportedEncodingException {
        byte[] res = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
            res = IOUtils.toByteArray(response.getEntity().getContent());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpget != null) {
                httpget.abort();
            }
            httpclient.getConnectionManager().shutdown();
        }

        return new String(res, "utf-8");
    }



    public static void main(String...strings) throws Exception {
        System.out.println(getRequestCodeUrl("http://0cc98ea6.ngrok.io/wx/getOpenid"));
        //System.out.println(getAccessTokenByAppId(APPID,APPSECRET));
    }
}
