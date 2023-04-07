package com.example.websocket_netty_demo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import java.util.Objects;
import java.util.Set;

import static com.example.websocket_netty_demo.websocket.webSocketStarter.startWS;


@ClientEndpoint
@Slf4j
@Component
public class webSocketClientHandler{

    @OnOpen
    public void onOpen(Session session) {
        webSocketStarter.session = session;
    }

    @OnMessage
    public void processMessage(String message) {
        log.info("websocket 接收推送消息 "+message);
    }

    @OnError
    public void processError(Throwable t) {
        webSocketStarter.session = null;
//        try {
//            Thread.sleep(5000);
//            startWS();
//        } catch (InterruptedException e) {
//            log.error("---websocket processError InterruptedException---", e);
//        }
        log.error("---websocket processError error---", t);
    }

    @OnClose
    public void processClose(Session session, CloseReason closeReason) {
        log.error(session.getId() + closeReason.toString());
    }

    public void send(String sessionId, String message) {
        try {
            log.info("send Msg:" + message);
            if (Objects.nonNull(webSocketStarter.session)) {
                webSocketStarter.session.getBasicRemote().sendText(message);
            } else {
                log.info("---websocket error----");
            }

        } catch (Exception e) {
            log.error("---websocket send error---", e);
        }

    }
}
