package com.example.websocket_netty_demo.websocket;

import com.alibaba.fastjson.JSONObject;
import com.example.websocket_netty_demo.nettyServer.NettyServerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.io.IOException;
import java.util.Objects;

import static com.example.websocket_netty_demo.websocket.webSocketStarter.startWS;


@ClientEndpoint
@Slf4j
@Component
public class webSocketClientHandler{

    private Thread refreshThread=null;
    private volatile boolean isStopped=false;

    @OnOpen
    public void onOpen(Session session) {
        webSocketStarter.session = session;
        refreshThread=new Thread(()->{
            while(!isStopped) {
                send(updateResult().toJSONString());
                try {
                    Thread.sleep(800);
                    log.info("[websocket] sending update command");
                } catch (InterruptedException e) {
                    log.warn("[websocket] error---", e);
                }
            }
        });
        refreshThread.start();
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("[websocket] 接收推送消息 "+message);

    }

    @OnError
    public void onError(Throwable t) {
        webSocketStarter.session = null;
        try {
            //isStopped=true;
            Thread.sleep(5000);
            if(webSocketStarter.uri!=null||!webSocketStarter.uri.trim().equals(""))
                startWS(webSocketStarter.uri);
        } catch (InterruptedException e) {
            log.error("[websocket] processError InterruptedException---", e);
        }
        log.error("[websocket] processError error---", t);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.error(session.getId() + closeReason.toString());
        try {
            session.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //todo stop thread
        this.isStopped=true;
    }

    private JSONObject updateResult(){
        JSONObject js=new JSONObject();
        js.put("order","update");
        js.put("chipList",NettyServerHandler.getAllChips());
        return js;
    }

    public static void send(String message) {
        try {
            log.info("send Msg:" + message);
            if (Objects.nonNull(webSocketStarter.session)) {
                webSocketStarter.session.getBasicRemote().sendText(message);
            } else {
                log.info("[websocket] error----");
            }

        } catch (Exception e) {
            log.error("[websocket] send error---", e);
        }

    }
}
