package com.example.websocket_netty_demo;

import com.example.websocket_netty_demo.websocket.webSocketStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync//注意这里，这个注解启用了线程池
public class WebsocketNettyDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebsocketNettyDemoApplication.class, args);
        webSocketStarter.startWS("ws://127.0.0.1:8082/Node/hello-kitty/1234");
    }

}
