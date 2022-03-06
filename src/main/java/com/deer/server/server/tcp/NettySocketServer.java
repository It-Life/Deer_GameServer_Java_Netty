package com.deer.server.server.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NettySocketServer extends Thread implements CommandLineRunner
{
    @Autowired
    private NettySocketInitializer serverInitializer;
    @Value("${netty.port}")
    private int port;
    @Override
    public void run(String... args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//.handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(serverInitializer)
                    .localAddress(port);
            log.info("conn server start:{}", port);
            b.bind(port).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
