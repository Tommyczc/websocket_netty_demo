package com.example.websocket_netty_demo;

import com.example.websocket_netty_demo.nettyServer.nettyStarter;
import com.example.websocket_netty_demo.websocket.webSocketStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsocketNettyDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebsocketNettyDemoApplication.class, args);
        nettyStarter.starter();
        webSocketStarter.startWS("ws://127.0.0.1:8082/websocket/hello-kitty/1234");
    }

}
