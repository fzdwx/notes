package cn.like.code.config.support.custom.client;

import cn.like.code.config.HttpMessageConverterConfiguration;
import cn.like.code.config.support.response.SecurityResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 自定义的 {@link WebResponseExceptionTranslator}<br>
 * 自定义 {@link OAuth2Exception} 重写其序列化方案, 最终达到访问 /oauth/token 端点异常信息自定义的目的.<br>
 * 同时需要在 {@link HttpMessageConverterConfiguration#httpMessageConvertConfigurer()}  } 中排除自定义的 {@link OAuth2Exception}.
 *
 * @author LiKe
 * @version 1.0.0
 * @date 2020-06-22 15:20
 * @see org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator
 * @see org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator
 */
@Slf4j
@Component
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        log.debug("Custom WebResponseExceptionTranslator triggered. Determine http status ...");

        final Class<? extends Exception> exceptionClass = e.getClass();
        final String exceptionMessage = e.getMessage();

        // AuthenticationException -> 401
        if (AuthenticationException.class.isAssignableFrom(exceptionClass)) {
            return handleOAuth2Exception(exceptionMessage, HttpStatus.UNAUTHORIZED);
        }

        // OAuth2Exception -> 403
        if (OAuth2Exception.class.isAssignableFrom(exceptionClass)) {
            return handleOAuth2Exception(exceptionMessage, HttpStatus.FORBIDDEN);
        }

        return handleOAuth2Exception(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(String exceptionMessage, HttpStatus httpStatus) {
        return new ResponseEntity<>(new CustomOAuth2Exception(exceptionMessage, httpStatus), httpStatus);
    }

    /**
     * Description: 自定义 {@link OAuth2Exception}
     *
     * @author LiKe
     * @date 2020-06-22 16:42:05
     */
    // @com.fasterxml.jackson.databind.annotation.JsonSerialize(using = CustomOAuth2ExceptionJackson2Serializer.class)
    // 如果需要自定义序列化
    public static final class CustomOAuth2Exception extends OAuth2Exception {

        private final HttpStatus httpStatus;

        public CustomOAuth2Exception(String msg, HttpStatus httpStatus) {
            super(msg);
            this.httpStatus = httpStatus;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }
    }

    // ~ Serializer
    // -----------------------------------------------------------------------------------------------------------------

    private static final class CustomOAuth2ExceptionJackson2Serializer extends StdSerializer<CustomOAuth2Exception> {

        private static final String SERIALIZED_PLAIN_OBJECT = "{}";

        protected CustomOAuth2ExceptionJackson2Serializer() {
            super(CustomOAuth2Exception.class);
        }

        @Override
        public void serialize(CustomOAuth2Exception e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField(SecurityResponse.FIELD_HTTP_STATUS, e.getHttpStatus().value());
            jsonGenerator.writeObjectField(SecurityResponse.FIELD_TIMESTAMP, LocalDateTime.now().format(DateTimeFormatter
                    .ofPattern(SecurityResponse.TIME_PATTERN, Locale.CHINA)));
            jsonGenerator.writeObjectField(SecurityResponse.FIELD_MESSAGE, e.getMessage());
            jsonGenerator.writeObjectField(SecurityResponse.FIELD_DATA, SERIALIZED_PLAIN_OBJECT);
            jsonGenerator.writeEndObject();
        }
    }
}