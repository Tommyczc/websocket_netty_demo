package com.example.websocket_netty_demo.nettyServer.NettyCommand.enums;

public enum constantCommand {

    Time("Time","00");
    private String commandName;
    private String order;

    constantCommand(String commandName, String order) {
        this.commandName = commandName;
        this.order = order;
    }

    public static String getOrder(String name){
        for (constantCommand cc : constantCommand.values()) {
            if(cc.getCommandName().equals(name)){
                return cc.order;
            }
        }
        return null;
    }

    public static String getName(String order){
        for (constantCommand cc : constantCommand.values()) {
            if(cc.getOrder().equals(order)){
                return cc.commandName;
            }
        }
        return null;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }


}
