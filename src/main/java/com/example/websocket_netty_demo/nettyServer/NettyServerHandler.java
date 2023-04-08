package com.example.websocket_netty_demo.nettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    public static ConcurrentHashMap<String,ChannelHandlerContext> channelList=new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive");
        //todo add channel to a static container
        channelList.put(ctx.channel().remoteAddress().toString(),ctx);

        //String command="AA 07 02 00 01 02 00 5A A5 0B";
        //byte[] resetCommand=HexUtils.parseHex2Byte(command);
        byte[] resetCommand=new byte[]{
                (byte) 0xAA,
                0x07,
                0x02,
                0x00,
                0x01,
                0x02,
                0x00,
                0x5A,
                (byte) 0xA5,
                0x0B,
        };
        ByteBuf buf = Unpooled.buffer(resetCommand.length);
        buf.writeBytes(resetCommand);
        ctx.writeAndFlush(buf);
    }


    /**
     * 读取客户端发送来的数据
     * @param ctx ChannelHandler的上下文对象 有管道 pipeline 通道 channel 和 请求地址 等信息
     * @param msg 客户端发送的具体数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("客户端请求到了..." + ctx.channel().remoteAddress());
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] message=new byte[buf.readableBytes()];
//        buf.readBytes(message);
//        String content = HexUtils.parseByte2HexStr(message);
        //System.out.println("客户端发送的数据是:" +content);
    }

    /**
     * 读取客户端发送数据完成后的方法
     *    在本方法中可以发送返回的数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush 是组合方法
        //System.out.println("message read");
        //ctx.writeAndFlush(Unpooled.copiedBuffer("你好啊，客户端....^_^",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn(cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){

    }
}

