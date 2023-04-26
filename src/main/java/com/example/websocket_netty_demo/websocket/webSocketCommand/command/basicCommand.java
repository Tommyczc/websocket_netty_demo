package com.example.websocket_netty_demo.websocket.webSocketCommand.command;

import com.example.websocket_netty_demo.websocket.webSocketClientHandler;

public class basicCommand {

    public static void sendMessage(String content){

        webSocketClientHandler.send(content);
    }
}
