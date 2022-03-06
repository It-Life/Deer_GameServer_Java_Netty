package com.deer.server.server.tcp;

import com.deer.server.game.user.map.LocalUserMap;
import com.deer.server.game.user.object.LocalUser;
import com.deer.server.game.user.threadpool.UserThreadPool;
import com.deer.server.server.protobuf.message.MessageHandler;
import com.deer.server.server.protobuf.message.MessageType;
import com.deer.server.server.protobuf.proto.DeerGameBase;
import com.deer.server.server.protobuf.proto.DeerProtoBase;
import com.deer.server.server.task.DispatcherTask;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@ChannelHandler.Sharable
public class NettySocketHandler extends SimpleChannelInboundHandler<MessageLite> {
    /*
     * channelAction
     *
     * channel 通道 action 活跃的
     *
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     *
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + " 通道已激活！");
    }
    /*
     * channelInactive
     *
     * channel 通道 Inactive 不活跃的
     *
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     *
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + " 通道不活跃！");
        // 关闭流

    }
    /**
     *
     * @author Taowd
     * TODO  此处用来处理收到的数据中含有中文的时  出现乱码的问题
     * 2017年8月31日 下午7:57:28
     * @param buf
     * @return
     */
    private String getMessage(ByteBuf buf) {
        byte[] con = new byte[buf.readableBytes()];
        buf.readBytes(con);
        try {
            return new String(con, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 功能：读取服务器发送过来的信息
     */
/*    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 第一种：接收字符串时的处理
*//*        ByteBuf buf = (ByteBuf) msg;
        String rev = getMessage(buf);
        System.out.println("客户端收到服务器数据:" + rev);*//*
    }*/
    /**
     * 功能：读取完毕客户端发送过来的数据之后的操作
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接收数据完毕..");
        // 第一种方法：写一个空的buf，并刷新写出区域。完成后关闭sock channel连接。
        //ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        // ctx.flush();
        // ctx.flush(); //
        // 第二种方法：在client端关闭channel连接，这样的话，会触发两次channelReadComplete方法。
        // ctx.flush().close().sync(); // 第三种：改成这种写法也可以，但是这中写法，没有第一种方法的好。
    }

    /**
     * 功能：服务端发生异常的操作
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }

    //ChannelOutboundBuffer.MessageProcessor messageProcessor = SpringUtil.getBean(ChannelOutboundBuffer.MessageProcessor.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageLite msg) throws Exception {
        int protoType = MessageType.getProtoCodeFromType(msg);
        if (protoType == DeerProtoBase.ProtocolID.PTC_C2S_HeartBeat.getNumber()) {
            log.info("收到客户端发来的心跳消息：{}", msg.toString());
            //回应pong
            ctx.writeAndFlush(DeerGameBase.SCHeartBeat.newBuilder()
                    .setDwTime(1)
                    .build());
        }else {
            //获取任务参数
            SimpleChannelInboundHandler<? extends MessageLite> simpleChannelInboundHandler = MessageHandler.getHandlerByProtoId(protoType);
            //创建任务
            DispatcherTask task = new DispatcherTask(simpleChannelInboundHandler,ctx,msg);
            long userId = LocalUser.getUserId();
            int threadIndex;
            if(userId<=0){
                //未登陆
                threadIndex = UserThreadPool.getThreadIndex(ctx.channel().id());
            }else{
                //已登陆
                Integer useId = LocalUserMap.getChannelUserMap().get(ctx.channel());
                threadIndex = UserThreadPool.getThreadIndex(useId);
            }
            //把任务放到线程池
            UserThreadPool.getThreadPool(threadIndex).submit(task);
        }
    }
}
