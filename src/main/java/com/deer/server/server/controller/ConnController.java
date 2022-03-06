package com.deer.server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class ConnController {
    @Autowired
    //private ConnChannelGroup channels;

    @GetMapping("/send")
    public String send(String id){
/*        MessageBase.Message message = new MessageBase.Message()
                .toBuilder().setCmd(MessageBase.Message.CommandType.NORMAL)
                .setContent("hello netty client,i'm server")
                .setContent("how are you")
                .setRequestId(UUID.randomUUID().toString()).build();
        channels.writeAndFlushById(id,message);*/
        return "send client ok";
    }
}
