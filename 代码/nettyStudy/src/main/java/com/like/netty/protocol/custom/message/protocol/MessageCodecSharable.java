package com.like.netty.protocol.custom.message.protocol;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.like.netty.protocol.custom.handler.LikeChannelMustPipeline;
import com.like.netty.protocol.custom.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.slf4j.Logger;

import java.nio.charset.Charset;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Create By like On 2021-04-11 14:41
 * 必须 配合该解码器使用 {@link LikeChannelMustPipeline#getLikeProtocolFrameDecoder()}
 */
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {
    private final ObjectMapper mapper = new ObjectMapper();
    public static final byte[] magicNumber = "LikeLove".getBytes();
    public int version = 1;

    private MessageSerializer messageSerializer;

    public MessageCodecSharable() {
    }

    public MessageSerializer getMessageSerializer() {
        return messageSerializer;
    }

    public void setMessageSerializer(MessageSerializer messageSerializer) {
        this.messageSerializer = messageSerializer;
    }

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
        JSONUtil.toJsonStr(msg);

        byte[] msgArray = JSONSerialization(msg); ;

        out.writeInt(msgArray.length);
        // 不可变 共22 个字节
        // 7.写入消息
        out.writeBytes(msgArray);

        outList.add(out);
    }

    /**
     * json serialization
     *
     * @param msg message
     * @return {@link byte[]}
     */
    private byte[] JSONSerialization(Message msg) throws JsonProcessingException {
        return mapper.writeValueAsString(msg).getBytes();
        // return JSONUtil.toJsonStr(msg).getBytes();
    }

    /**
     * json deserialization
     *
     * @param msg message
     * @return {@link Message}
     */
    private Message JSONDeserialization(byte[] msg) throws JsonProcessingException {
        // return mapper.readValue(StrUtil.str(msg, Charset.defaultCharset()), Message.class);
        String json = StrUtil.str(msg, Charset.defaultCharset());
        return (Message) JSONUtil.toBean(json, Message.getMessageClass(JSONUtil.parse(json).getByPath("messageType", Integer.class)));
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


        Message message = JSONDeserialization(msg);
        out.add(message);
    }

    private final static Logger log = getLogger(MessageCodecSharable.class);
}
