package com.example.websocket_netty_demo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;

/**
 * @author Tommy
 */
//@ClientEndpoint
@Component
@Slf4j
public class webSocketStarter {
    public static Session session;
    public static String uri;

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
            Session session = container.connectToServer(webSocketClientHandler.class, URI.create(uri));
            webSocketStarter.session = session;
            webSocketStarter.uri=uri;
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }
}