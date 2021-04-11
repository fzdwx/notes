package com.like.netty.protocol.custom.protocol;

import com.like.netty.protocol.custom.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Create By like On 2021-04-11 12:42
 * 自定义协议
 * <p>
 * 魔数（8）：用来在第一时间判断是否无效数据包
 * 版本号（4）：可以支持协议的升级
 * 序列化算法（1）：消息正文到底采用哪种序列化反序列化方式
 * JSON protobuf  hessian jdk
 * 指令类型（1）：是登录、注册、单聊、群聊... 跟业务相关
 * 请求序号（4）：为了双工通信，提供异步能力
 * 正文长度（4）：
 * 消息正文（xx）：
 *
 * 示例：
 * |00000000| 4c 69 6b 65 4c 6f 76 65 00 00 00 01 00 00 00 00 |LikeLove........|
 * |00000010| 00 00 00 00 00 ec                               |......           |
 *
 * 固定长度 22
 */
public class MessageCodec extends ByteToMessageCodec<Message> {
    public static final byte[] magicNumber = "LikeLove".getBytes();
    public int version = 1;
    public int serializationType = 0;

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        // 1. 8 魔数
        out.writeBytes(magicNumber);
        // 2. 4 版本号
        out.writeInt(version);
        // 3. 1 序列化算法  0 jdk 1 json
        out.writeByte(serializationType);
        // 4. 1 指令类型
        out.writeByte(msg.getMessageType());
        // 5. 4 请求序列号
        out.writeInt(msg.getSequenceId());
        // 消息转成字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(msg);
        byte[] msgArray = baos.toByteArray();
        // 6. 4 消息内容正文长度
        out.writeInt(msgArray.length);
        // 不可变 共22 个字节
        // 7.写入消息
        out.writeBytes(msgArray);
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String magicNumber = in.readBytes(8).toString(Charset.defaultCharset());
        // if (!"LikeLove".equals(magicNumber)) return;

        int version = in.readInt();
        byte serializationType = in.readByte();
        byte messageType = in.readByte();
        int seqId = in.readInt();
        int msgArrayLen = in.readInt();
        byte[] msg = new byte[msgArrayLen];
        in.readBytes(msg, 0, msgArrayLen);

        // if (0 == serializationType) {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(msg));
        Message message = (Message) ois.readObject();
        // }
        log.info("#decode(..):magicNumber:{}", magicNumber);
        log.info("#decode(..):version:{}", version);
        log.info("#decode(..):serializationType:{}", serializationType);
        log.info("#decode(..):messageType:{}", messageType);
        log.info("#decode(..):seqId:{}", seqId);
        log.info("#decode(..):msgArrayLen:{}", msgArrayLen);
        log.info("#decode(..):message:{}", message);

        out.add(message);
    }

    private final static Logger log = getLogger(MessageCodec.class);
}
