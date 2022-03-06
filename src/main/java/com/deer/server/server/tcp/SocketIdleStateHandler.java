package com.deer.server.server.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SocketIdleStateHandler extends IdleStateHandler {
    /**
     * 设置空闲检测时间为 30s
     */
    private static final int READER_IDLE_TIME = 30;
    public SocketIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }
    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        super.channelIdle(ctx, evt);
        if(evt.state() == IdleState.ALL_IDLE) {
            ctx.channel().close();
            log.info("{} 秒内没有读取到数据,关闭连接", READER_IDLE_TIME);
        }
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("from server:成功连接服务端");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("out line");
    }
}
