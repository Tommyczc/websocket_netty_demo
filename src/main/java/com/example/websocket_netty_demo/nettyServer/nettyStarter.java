package com.example.websocket_netty_demo.nettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;

@Component//当成组件处理
@Order(value = 1)//这里表示启动顺序
@Slf4j
public class nettyStarter implements CommandLineRunner {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel channel;

    public void start(){
        // 创建对应的 线程池
        // 创建Boss group
        bossGroup = new NioEventLoopGroup(1);
        // 创建 workgroup
        workerGroup = new NioEventLoopGroup();
        // 创建对应的启动类
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            // 设置相关的配置信息
            bootstrap.group(bossGroup, workerGroup) // 设置对应的线程组
                    .channel(NioServerSocketChannel.class) // 设置对应的通道
                    .option(ChannelOption.SO_BACKLOG, 1024) // 设置线程的连接个数
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 设置
                        /**
                         * 给pipeline 设置处理器
                         * @param socketChannel
                         * @throws Exception
                         */
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            log.info("Netty server starting....");
            // 绑定端口  启动服务
            ChannelFuture channelFuture = bootstrap.bind(9000).sync();
            channel = channelFuture.channel().closeFuture().sync().channel();
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.info("Caught an exception {}", e.getMessage());
        } finally {
            // 优雅停服
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @PreDestroy
    public void stop() {
        if (channel != null) {
            log.info("Netty Server close");
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Async//注意这里，组件启动时会执行run，这个注解是让线程异步执行，这样不影响主线程
    @Override
    public void run(String... args) {
        start();
    }
}
