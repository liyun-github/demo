package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liyun on 2018/8/15.
 */
@Controller
public class MyController {

    @RequestMapping("/index")
    public String test(){
        return "index";
    }

    @RequestMapping("/login")
    public String demo(){
        return "login";
    }

    @RequestMapping("/table")
    public String table(){
        return "order";
    }

    @RequestMapping("/wxpay")
    public String wxpay(){
        return "wxpay";
    }

}
