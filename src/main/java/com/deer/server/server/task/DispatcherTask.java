package com.deer.server.server.task;

import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
* @ClassName DispatcherTask
* @Description 分发任务
 * */
@Slf4j
public class DispatcherTask implements Runnable{
    /**
     * 处理器
     */
    private SimpleChannelInboundHandler<? extends MessageLite> simpleChannelInboundHandler;
    /**
     * ctx
     */
    private ChannelHandlerContext ctx;
    /**
     * 协议信息
     */
    private MessageLite msg;

    public DispatcherTask(SimpleChannelInboundHandler<? extends MessageLite> simpleChannelInboundHandler, ChannelHandlerContext ctx, MessageLite msg) {
        this.simpleChannelInboundHandler = simpleChannelInboundHandler;
        this.ctx = ctx;
        this.msg = msg;
    }
    @Override
    public void run() {
        try {
            simpleChannelInboundHandler.channelRead(ctx,msg);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("任务分发出现异常。。。。");
        }
    }
}
