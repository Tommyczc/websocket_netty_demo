package com.example.websocket_netty_demo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;

/**
 * @author ghh
 * @date 2019-08-16 16:02
 */
@Component
@Slf4j
public class webSocketStarter {
    public static Session session;


    public static void startWS(String uri) {
        try {
            if (webSocketStarter.session != null) {
                webSocketStarter.session.close();
            }
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            //设置消息大小最大为10M
            container.setDefaultMaxBinaryMessageBufferSize(10*1024*1024);
            container.setDefaultMaxTextMessageBufferSize(10*1024*1024);
            // 客户端，开启服务端websocket。
            //String uri = "ws://192.168.0.108:8082/webSocket/1";
            Session session = container.connectToServer(webSocketStarter.class, URI.create(uri));
            webSocketStarter.session = session;
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }
}