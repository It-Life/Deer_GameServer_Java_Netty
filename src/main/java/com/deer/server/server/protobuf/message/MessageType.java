package com.deer.server.server.protobuf.message;

import com.deer.server.server.protobuf.proto.DPLogin;
import com.deer.server.server.protobuf.proto.DeerGameBase;
import com.deer.server.server.protobuf.proto.DeerProtoBase;
import com.google.protobuf.MessageLite;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageType {
    private final static Map<Integer, MessageLite> map = new HashMap<>();
    private final static Map<Class, Integer> messageMap = new HashMap<Class, Integer>();
    static {
        map.put(DeerProtoBase.ProtocolID.PTC_C2S_HeartBeat.getNumber(), DeerGameBase.CSHeartBeat.getDefaultInstance());
        map.put(DeerProtoBase.ProtocolID.PTC_S2C_HeartBeat.getNumber(), DeerGameBase.SCHeartBeat.getDefaultInstance());
        map.put(DeerProtoBase.ProtocolID.PTC_C2G_LOGININFO.getNumber(), DPLogin.DPUserLoginInfoReq.getDefaultInstance());
        map.put(DeerProtoBase.ProtocolID.PTC_G2C_ACC_VERIFY_RESULT.getNumber(), DPLogin.DPAccountVerifyResultResp.getDefaultInstance());
        map.put(DeerProtoBase.ProtocolID.PTC_C2S_LOGICLOGIN.getNumber(), DPLogin.DPGSLogicLoginReq.getDefaultInstance());
        map.put(DeerProtoBase.ProtocolID.PTC_S2C_LOGICLOIN_RET.getNumber(), DPLogin.DPGSLogicLoginRetResp.getDefaultInstance());
        map.put(DeerProtoBase.ProtocolID.PTC_G2C_ROLELIST_RESPONE.getNumber(), DPLogin.DPRoleListResponeResp.getDefaultInstance());
        map.put(DeerProtoBase.ProtocolID.PTC_C2G_GAMELOGIN_REQUEST.getNumber(), DPLogin.DPGameLoginRequestReq.getDefaultInstance());
        map.put(DeerProtoBase.ProtocolID.PTC_C2G_CREATE_ROLE.getNumber(), DPLogin.DPCreateRoleReq.getDefaultInstance());
        map.put(DeerProtoBase.ProtocolID.PTC_G2C_CREATEROLE_RESULT.getNumber(), DPLogin.DPCreateRoleResultResp.getDefaultInstance());
        map.put(DeerProtoBase.ProtocolID.PTC_G2C_KICKOUTCLIENT.getNumber(), DPLogin.DPKickOutClientResp.getDefaultInstance());
        map.put(DeerProtoBase.ProtocolID.PTC_C2S_EXITACCOUNT.getNumber(), DPLogin.DPGSExitAccountReq.getDefaultInstance());
        map.put(DeerProtoBase.ProtocolID.PTC_C2S_EXITROLE.getNumber(), DPLogin.DPGSExitRoleReq.getDefaultInstance());
    }
    MessageType()
    {
        readAllMessageType();
    }
    /**
     * ?????????????????? -> ????????????
     *
     * @param protoId
     * @return
     */
    public static MessageLite getProtoInstanceByProtoId(Integer protoId) throws Exception{
        return map.get(protoId);
    }
    /**
     * ????????? -> ??????
     *
     * @param messageLite
     * @return
     */
    public static final int getProtoCodeFromType(MessageLite messageLite){
        if (messageMap.get(messageLite.getClass())== null)
        {
            return 0;
        }
        return messageMap.get(messageLite.getClass());
    }
    /**
     * ????????????map
     */

    public static void readAllMessageType(){
        for (Map.Entry<Integer, MessageLite> entry : map.entrySet()) {
            messageMap.put(entry.getValue().getClass(), entry.getKey());
        }
    }
}
