package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liyun on 2018/8/15.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @RequestMapping("/orderList")
    public String orderList(String orderUserId, Model model){
        System.out.println("orderUserId=>" + orderUserId);
        model.addAttribute("orderUserId", orderUserId);
        return "order";
    }
}
