package com.deer.server.server.protobuf.encoder;

import com.deer.server.server.protobuf.message.MessageType;
import com.deer.server.utils.ByteUtil;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 参考ProtobufVarint32LengthFieldPrepender 和 ProtobufEncoder
 */
@Sharable
public class CustomProtobufEncoder extends MessageToByteEncoder<MessageLite> {

/*    HangqingEncoder hangqingEncoder;

    public CustomProtobufEncoder(HangqingEncoder hangqingEncoder)
    {
        this.hangqingEncoder = hangqingEncoder;
    }*/

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageLite msg, ByteBuf out) throws Exception {

        byte[] body = msg.toByteArray();
        byte[] header = encodeHeader(msg, body.length);

        out.writeBytes(header);
        out.writeBytes(body);
        return;
    }

    private byte[] encodeHeader(MessageLite msg, Integer bodyLength) {
        short dataType = 0x0f;
        // 根据协议对象查找对应的协议号
        if (msg != null){
            dataType = (short) MessageType.getProtoCodeFromType(msg);
        }
        byte[] headerBodyLength = ByteUtil.intToByte(bodyLength);
        byte[] headerDataType = ByteUtil.shortToByte(dataType);
        byte[] header = ByteUtil.byteMergerAll(headerBodyLength,headerDataType);
        return header;
    }
}