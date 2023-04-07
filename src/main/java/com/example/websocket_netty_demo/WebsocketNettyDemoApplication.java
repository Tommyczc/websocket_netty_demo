package com.example.websocket_netty_demo;

import com.example.websocket_netty_demo.nettyServer.nettyStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsocketNettyDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebsocketNettyDemoApplication.class, args);
        nettyStarter.starter();
    }

}
