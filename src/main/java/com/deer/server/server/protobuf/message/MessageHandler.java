package com.deer.server.server.protobuf.message;

import com.deer.server.game.user.handler.DPUserLoginInfoReqHandler;
import com.deer.server.server.protobuf.proto.DeerProtoBase;
import com.google.protobuf.MessageLite;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
@Component
public class MessageHandler {
    private final static Map<Integer, SimpleChannelInboundHandler<? extends MessageLite>> handlerMap = new HashMap<>();
    @Autowired
    DPUserLoginInfoReqHandler dpUserLoginInfoReqHandler;
    MessageHandler() {}
    @PostConstruct
    public void injectData(){
        handlerMap.put(DeerProtoBase.ProtocolID.PTC_C2G_LOGININFO.getNumber(), dpUserLoginInfoReqHandler);
    }
    public static SimpleChannelInboundHandler<? extends MessageLite> getHandlerByProtoId(Integer protoId)
    {
        return handlerMap.get(protoId);
    }
}
