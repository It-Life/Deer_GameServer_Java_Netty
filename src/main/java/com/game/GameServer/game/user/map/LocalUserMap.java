package com.game.GameServer.game.user.map;

import com.game.GameServer.game.user.object.ConcreteRole;
import io.netty.channel.Channel;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

@Data
public class LocalUserMap {
    /**
     * key:userId
     * value:channel
     */
    private static volatile Map<Integer, Channel> userChannelMap = Maps.newConcurrentMap();
    /**
     * key:userId
     * value:ConcreteRole
     */
    private static volatile Map<Integer, ConcreteRole> userRoleMap = Maps.newConcurrentMap();

    /**
     * key:channel
     * value:userId
     */
    private static volatile Map<Channel, Integer> channelUserMap = Maps.newConcurrentMap();


    public static Map<Integer,Channel> getUserChannelMap(){
        return userChannelMap;
    }

    public static Map<Integer, ConcreteRole> getUserRoleMap(){
        return userRoleMap;
    }

    public static Map<Channel,Integer> getChannelUserMap(){
        return channelUserMap;
    }
}
