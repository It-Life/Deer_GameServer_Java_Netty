package com.deer.server.server.tcp;

import com.deer.server.server.protobuf.decoder.CustomProtobufDecoder;
import com.deer.server.server.protobuf.encoder.CustomProtobufEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NettySocketInitializer extends ChannelInitializer<SocketChannel> {
    private EventExecutorGroup businessGroup = new DefaultEventExecutorGroup(2);
    @Autowired
    private NettySocketHandler nettySocketHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println("报告");
        System.out.println("信息：有一客户端链接到本服务端");
        System.out.println("IP:" + socketChannel.localAddress().getHostName());
        System.out.println("Port:" + socketChannel.localAddress().getPort());
        System.out.println("报告完毕");
        socketChannel.pipeline()
                .addLast(new SocketIdleStateHandler())//空闲检测
                .addLast("decoder",new CustomProtobufDecoder())
                .addLast("encoder",new CustomProtobufEncoder())
                .addLast(businessGroup,nettySocketHandler);
    }
}
