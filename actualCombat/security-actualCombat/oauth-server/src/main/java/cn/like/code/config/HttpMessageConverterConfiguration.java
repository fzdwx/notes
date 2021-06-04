package cn.like.code.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 * 用  Jackson 作为 {@link HttpMessageConverter}
 *
 * @author LiKe
 * @version 1.0.0
 * @date 2020-05-25 15:43
 */
@Configuration
public class HttpMessageConverterConfiguration {

    @Bean
    public HttpMessageConverter<?> httpMessageConvertConfigurer() {

        // ~ JsonConfig
        final ObjectMapper config = new ObjectMapper()
                .setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN))
                .enable(SerializationFeature.WRITE_NULL_MAP_VALUES);

        final CustomFastJsonHttpMessageConverter messageConverter = new CustomFastJsonHttpMessageConverter();
        messageConverter.setObjectMapper(config);
        messageConverter.setDefaultCharset(StandardCharsets.UTF_8);
        messageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        return messageConverter;
    }

    /**
     * 自定义的 {@link MappingJackson2HttpMessageConverter}<br>
     * 　默认的 {@link MappingJackson2HttpMessageConverter} 的 supports 方法始终返回 true, 对于某些逻辑可能不需要应用到此 Converter
     *
     * @author LiKe
     * @date 2020-06-23 09:51:00
     */
    @Slf4j
    private static final class CustomFastJsonHttpMessageConverter extends MappingJackson2HttpMessageConverter {

        /**
         * 不支持的列表
         */
        private final Set<Class<?>> excludes;

        public CustomFastJsonHttpMessageConverter(Class<?>... clazzArr) {
            this.excludes = Sets.newHashSet(Arrays.asList(clazzArr));
        }

        @Override
        protected boolean supports(@NotNull Class<?> clazz) {
            final boolean supports = !excludes.contains(clazz);
            log.debug("Custom FastJsonHttpMessageConverter#supports :: {} - {}", clazz.getCanonicalName(), supports);

            return supports;
        }
    }
}
