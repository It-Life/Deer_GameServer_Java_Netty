package com.game.GameServer.server.tcp;

import com.google.protobuf.Message;
import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConnChannelGroup extends DefaultChannelGroup {
    public ConnChannelGroup() {
        super(GlobalEventExecutor.INSTANCE);
    }
    public boolean register(Channel channel, Message msg){
        if (msg != null) {
            Attribute<Object> attribute = channel.attr(AttributeKey.valueOf("terminal"));
            Terminal terminal = new Terminal();
/*            if (msg.getCmd() == MessageBase.Message.CommandType.NORMAL) {
                terminal.setMac(msg.getSn());
                terminal.setAppVersion(msg.getVer());
                terminal.setIccid(msg.getIccid());
                terminal.setConnectTime(new Timestamp(System.currentTimeMillis()));
                terminal.setName(channel.id().toString());
                attribute.set(terminal);
*//*                 if (cmd == 0x01) {
                    terminal.setMac(sn);
                    terminal.setAppVersion(ver);
                    terminal.setIccid(iccid);
                    terminal.setConnectTime(new Timestamp(System.currentTimeMillis()));
                    attribute.set(terminal);
                    if (this.size() >= Parameter.MaxConnection) {
                        log.info("[{}] --- 超过最大连接", sn);
                        channel.close();
                        return false;
                    }

                    onlineProcesser.produce(channel);
                    //log.info("[{}] --- 连接成功", sn);
                    return super.add(channel);
                }*//*
                return super.add(channel);
            } else {
                log.info("terminal not register but first cmd is not 1:{}", msg.getContent());
            }*/
        }
        //log.info("[{}] --- 连接失败", dataG[0]);
        channel.close();
        return false;
    }

    public boolean writeAndFlushById(String id, Message msg) {
        for (Channel c : this) {
            Attribute<Object> attribute = c.attr(AttributeKey.valueOf("terminal"));
            Terminal terminal = (Terminal) attribute.get();
            if (terminal.getName().equals(id)) {
/*                log.info("({})send:{}", terminal.getMac(), msg);
                c.writeAndFlush(msg);*/
                return true;
            }
        }
        return false;
    }
}
