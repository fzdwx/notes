package com.like.netty.protocol.custom.message.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.like.netty.protocol.custom.handler.LikeChannelMustPipeline;
import com.like.netty.protocol.custom.message.Message;
import com.like.netty.protocol.custom.serializer.MessageSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;

import java.nio.charset.Charset;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Create By like On 2021-04-11 14:41
 * 必须 配合该解码器使用 {@link LikeChannelMustPipeline#getLikeProtocolFrameDecoder()}
 * 必须传入 {@link MessageCodecSharable}
 */
@EqualsAndHashCode(callSuper = true)
@ChannelHandler.Sharable
@Data
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {

    /** 魔数 */
    public static final byte[] magicNumber = "LikeLove".getBytes();
    /** 日志 */
    private final static Logger log = getLogger(MessageCodecSharable.class);
    /** json 映射器 */
    private final ObjectMapper mapper = new ObjectMapper();

    private int version = 1;

    private MessageSerializer messageSerializer;

    public MessageCodecSharable(MessageSerializer messageSerializer) {
        this.messageSerializer = messageSerializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outList) throws Exception {
        final ByteBuf out = ctx.alloc().buffer();
        out.writeBytes(magicNumber);
        out.writeInt(version);  // TODO: 2021/4/13 暂时全为1
        out.writeByte(messageSerializer.algorithmType());
        out.writeByte(msg.getMessageType());
        out.writeInt(msg.getSequenceId());

        byte[] msgArray = messageSerializer.serialization(msg); // 序列化

        out.writeInt(msgArray.length); // 不可变 共22 个字节
        out.writeBytes(msgArray);

        outList.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String magicNumber = in.readBytes(8).toString(Charset.defaultCharset());
        if (!"LikeLove".equals(magicNumber)) return;

        int version = in.readInt();
        byte serializationType = in.readByte();
        byte messageType = in.readByte();
        int seqId = in.readInt();
        int msgArrayLen = in.readInt();

        byte[] msg = new byte[msgArrayLen];
        in.readBytes(msg, 0, msgArrayLen);

        out.add(messageSerializer.deserialization(Message.getMessageClass(messageType), msg));  // 反序列化
    }
}
