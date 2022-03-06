package com.game.GameServer.game.user.handler;

import com.game.GameServer.server.protobuf.proto.DPLogin;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
@Slf4j
public class DPUserLoginInfoReqHandler extends SimpleChannelInboundHandler<DPLogin.DPUserLoginInfoReq> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DPLogin.DPUserLoginInfoReq msg) throws Exception {
        log.info("收到客户端发来的登录消息：{}", msg.toString());
        ctx.writeAndFlush(DPLogin.DPAccountVerifyResultResp.newBuilder()
                .setSzAccount("11")
                .setNResult(DPLogin.AccountLoginVerifyResult.ACC_VERIFY_RET_ACCOUNT_OR_PASSWORD_ERROR)
                .setSzLoginGuid("22")
                .setSzServerIP("123123")
                .setNPort(8888)
                .setNServerID(1)
                .setNWaitNum(111)
                .build()
        );
    }
}