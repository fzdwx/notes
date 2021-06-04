package cn.like.code.util.response;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * 通用返回
 *
 * @author like
 * @date 2021/06/04
 */
@Getter
@Setter
public final class SecurityResponse {

    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String FIELD_TIMESTAMP = "timestamp";

    public static final String FIELD_HTTP_STATUS = "status";

    public static final String FIELD_MESSAGE = "message";

    public static final String FIELD_DATA = "data";

    @JsonIgnore
    public static final Object PLAIN_OBJECT = new Object();

    /**
     * {@link HttpStatus}
     */
    @JsonProperty(defaultValue = "200", index = 1)
    private int httpStatus;

    /**
     * 时间戳
     */
    @JsonProperty(index = 2)
    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime timestamp;

    /**
     * 消息
     */
    @JsonProperty(index = 3)
    private String message;

    /**
     * 数据体
     */
    @JsonProperty(defaultValue = "{}", index = 4)
    private Object data;

    /**
     * Description: 序列化
     *
     * @return 序列化后的 JSON 字符串: { timestamp: '', status: '', message: '', data: {} }
     */
    @SneakyThrows
    @Override
    public String toString() {
        // 使用 LinkedHashMap 保证序列化顺序
        return new JSONObject(new LinkedHashMap<>(4))
                .putOnce(FIELD_TIMESTAMP, Optional.ofNullable(this.timestamp).orElse(LocalDateTime.now()).format(DateTimeFormatter.ofPattern(TIME_PATTERN, Locale.CHINA)))
                .putOnce(FIELD_HTTP_STATUS, this.httpStatus)
                .putOnce(FIELD_MESSAGE, this.message)
                .putOnce(FIELD_DATA, Optional.ofNullable(data).orElse(PLAIN_OBJECT))
                .toString();
    }

    /**
     * 构造器
     */
    public static final class Builder {

        private final SecurityResponse securityResponse = new SecurityResponse();

        /**
         * Description: 获取构造器
         *
         * @return c.c.d.s.s.o.a.c.authorization.server.domain.dto.SecurityResponse.Builder
         * @author LiKe
         * @date 2020-06-03 12:51:15
         */
        public static Builder of() {
            return new Builder();
        }

        public Builder httpStatus(HttpStatus httpStatus) {
            securityResponse.httpStatus = Optional.ofNullable(httpStatus).orElse(HttpStatus.OK).value();
            return this;
        }

        public Builder timestamp(LocalDateTime localDateTime) {
            securityResponse.timestamp = Optional.ofNullable(localDateTime).orElse(LocalDateTime.now());
            return this;
        }

        public Builder message(String message) {
            securityResponse.message = Optional.ofNullable(message).orElse(StringUtils.EMPTY);
            return this;
        }

        public Builder data(Object data) {
            securityResponse.data = Optional.ofNullable(data).orElse(PLAIN_OBJECT);
            return this;
        }

        /**
         * Description: 返回构建的 {@link SecurityResponse} 实例
         *
         * @return c.c.d.s.s.o.a.c.authorization.server.domain.dto.SecurityResponse
         * @author LiKe
         * @date 2020-06-03 13:19:32
         */
        public SecurityResponse build() {
            if (Objects.isNull(securityResponse.timestamp)) {
                securityResponse.timestamp = LocalDateTime.now();
            }
            return securityResponse;
        }

    }
}