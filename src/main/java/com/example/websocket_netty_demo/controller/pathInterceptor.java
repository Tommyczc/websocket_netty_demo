package com.example.websocket_netty_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class pathInterceptor {
    @RequestMapping(value={"/","/index"})
    public String index(){
        return"index";
    }
}
