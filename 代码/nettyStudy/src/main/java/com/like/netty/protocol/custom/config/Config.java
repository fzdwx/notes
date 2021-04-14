package com.like.netty.protocol.custom.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.setting.Setting;
import com.like.netty.protocol.custom.serializer.JSONMessageSerializerImpl;
import com.like.netty.protocol.custom.serializer.MessageSerializer;
import com.like.netty.protocol.custom.serializer.MessageSerializerAlgorithmType;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Create By like On 2021-04-13 21:15
 */
public class Config {
    private final static Logger log = getLogger(Config.class);

    public static final String configPath = "application.setting";
    private static final Setting setting;

    private String appName;
    private String serverPort;
    private String serverHost;

    private String messageSerializer;

    static {
        setting = new Setting(configPath);
        setting.autoLoad(true);
    }

    public static String getAppName() {
        return setting.get("app.name");
    }

    public static String getServerHost() {
        return setting.get("server.host");
    }

    public static String getServerPort() {
        return setting.get("server.port");
    }

    public static MessageSerializer getMessageSerializer() {
        String serializer = "";
        MessageSerializer messageSerializer = null;
        MessageSerializerAlgorithmType[] types = MessageSerializerAlgorithmType.values();
        try {
            serializer = setting.get("message.Serializer");
            for (MessageSerializerAlgorithmType type : types) {
                if (type.toString().equals(serializer)) {
                    messageSerializer = (MessageSerializer) type.clazz.newInstance();
                    log.info("#getMessageSerializer(..): 配置message序列化器:{}", type);
                }
            }
        } catch (InstantiationException | IllegalAccessException ignored) { }

        if (ObjectUtil.isNull(messageSerializer)) {
            log.warn("#getMessageSerializer(..): 配置消息序列化器失败，将使用默认消息序列化器-JSON, 供选择的序列化器：{}，您选择的错误的消息序列化器:{}", types, serializer);
            messageSerializer = new JSONMessageSerializerImpl();
        }
        return messageSerializer;
    }

    public static void flushConfig() {
        setting.store(configPath);
    }
}
