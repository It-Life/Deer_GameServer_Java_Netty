package com.game.GameServer.server.protobuf.decoder;

import com.game.GameServer.server.protobuf.message.MessageType;
import com.game.GameServer.server.protobuf.proto.DeerGameBase;
import com.game.GameServer.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.google.protobuf.MessageLite;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 参考ProtobufVarint32FrameDecoder 和 ProtobufDecoder
 */
@Slf4j
public class CustomProtobufDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() > 6) { // 如果可读长度小于包头长度，退出。
            in.markReaderIndex();
            //消息长度4个字节
            byte[] lengthArray = new byte[4];
            for (int i = 0; i < lengthArray.length; i++) {
                lengthArray[i] = in.readByte();
            }
            //消息类型2个字节
            byte[] typeArray = new byte[2];
            for (int i = 0; i < typeArray.length; i++) {
                typeArray[i] = in.readByte();
            }
            int length = ByteUtil.bytesToInt(lengthArray);
            short dataType = ByteUtil.byteToShort(typeArray);
            // 如果可读长度小于body长度，恢复读指针，退出。
            if (in.readableBytes() < length) {
                in.resetReaderIndex();
                return;
            }
            // 读取body
            ByteBuf bodyByteBuf = in.readBytes(length);
            byte[] array;
            int offset;
            int readableLen= bodyByteBuf.readableBytes();
            if (bodyByteBuf.hasArray()) {
                array = bodyByteBuf.array();
                offset = bodyByteBuf.arrayOffset() + bodyByteBuf.readerIndex();
            } else {
                array = new byte[readableLen];
                bodyByteBuf.getBytes(bodyByteBuf.readerIndex(), array, 0, readableLen);
                offset = 0;
            }
            ReferenceCountUtil.release(bodyByteBuf);
            ReferenceCountUtil.release(lengthArray);
            ReferenceCountUtil.release(typeArray);
            //反序列化
            MessageLite result = decodeBody((int)dataType, array, offset, readableLen);
            if (result!=null)
            {
                out.add(result);
            }else {
                result = DeerGameBase.CSHeartBeat.newBuilder().setDwTime(1).getDefaultInstanceForType();
                out.add(result);
                log.warn(String.format("decode is null, protoId is %s",dataType));
            }
        }
    }

    public MessageLite decodeBody(Integer dataType, byte[] array, int offset, int length) throws Exception {
        if (MessageType.getProtoInstanceByProtoId(dataType) !=null)
        {
            return MessageType.getProtoInstanceByProtoId(dataType).getParserForType().parseFrom(array, offset, length); // or throw exception
        }
        return null;
    }
}
