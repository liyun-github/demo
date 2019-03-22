package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.example.demo.util.WxUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wx")
public class WxController {

    /**
     * 路由控制
     *
     * @param code
     * @return
     */
//    @RequestMapping("/auth")
//    public Response auth(HttpServletRequest request,
//                         String code) {
//        Map<String, String> data = new HashMap();
//        Map<String, String> result = WxUtil.getUserInfoAccessToken(code);//通过这个code获取access_token
//        String openId = result.get("openid");
//        if (StringUtils.isNotEmpty(openId)) {
//            System.out.println("try getting user info. [openid={"+openId+"}]");
//            Map<String, String> userInfo = WxUtil.getUserInfo(result.get("access_token"), openId);//使用access_token获取用户信息
//            System.out.println("received user info. [result={"+ JSONUtils.toJSONString(userInfo)+"}]");
//
//            return forward("auth", userInfo);
//        }
//
//        return Response.ok("openid为空").build();
//    }

    @RequestMapping("/getOpenid")
    public String getUserInfo(@RequestParam(name = "code") String code) throws Exception {
        System.out.println("code=====>" + code);

        //获取用户opendId和accessToken
        String jsonStr = WxUtil.getAccessTokenByCode(code);
        System.out.println("获取用户opendId和accessToken............");
        String openid = JSON.parseObject(jsonStr).getString("openid");
        String accessToken = JSON.parseObject(jsonStr).getString("access_token");
        System.out.println("openid=>" + openid);
        System.out.println("accessToken=>" + accessToken);

        //获取用户信息
        System.out.println("获取用户信息............");
        jsonStr = WxUtil.getUserinfo(openid, accessToken);
        System.out.println(jsonStr);

        //判断accessToken是否有效
        System.out.println("判断accessToken是否有效............");
        jsonStr = WxUtil.verifyAccessToken(openid, accessToken);
        System.out.println(jsonStr);

        //刷新accessToken
        System.out.println("刷新accessToken............");
        jsonStr = WxUtil.refreshToken(accessToken);
        System.out.println(jsonStr);



        System.out.println("跳转登录页........");

        return "login";
    }

    @RequestMapping(value = "/wx.do")
    public String welcome(String signature, String timestamp, String nonce, String openid) throws Exception {

        System.out.println("========welcome========= ");

        System.out.println("请求进来了...");

        System.out.println("signature=>" + signature);
        System.out.println("timestamp=>" + timestamp);
        System.out.println("nonce=>" + nonce);
        System.out.println("openid=>" + openid);


//        PrintWriter out = response.getWriter();
//
//        out.print(echostr);
//        out.close();

        return "login";
    }

}
