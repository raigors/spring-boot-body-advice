package com.github.springbootbodyadvice.advice;

import com.github.springbootbodyadvice.annotation.Decrypt;
import com.github.springbootbodyadvice.annotation.Encrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 创建时间为 下午10:44 2019/11/4
 * 项目名称 spring-boot-body-advice
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

@Slf4j
@RestControllerAdvice
public class DataRequestBodyAdvice implements RequestBodyAdvice {

    private static final Base64 BASE64 = new Base64();

    @Override
    public boolean supports(@NotNull MethodParameter methodParameter,
                            @NotNull Type targetType,
                            @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(Decrypt.class);
    }

    @NotNull
    @Override
    public HttpInputMessage beforeBodyRead(@NotNull HttpInputMessage inputMessage,
                                           @NotNull MethodParameter parameter,
                                           @NotNull Type targetType,
                                           @NotNull Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        String result = IOUtils.toString(inputMessage.getBody(), StandardCharsets.UTF_8);
        // 在这里解密(整个body数据被加密了在这里解密)
        result = new String(BASE64.decode(result));
        InputStream targetStream = IOUtils.toInputStream(result, StandardCharsets.UTF_8.name());
        log.info("beforeBodyRead:{}", result);
        return new DecodeHttpInputMessage(inputMessage.getHeaders(), targetStream);
    }

    @NotNull
    @Override
    public Object afterBodyRead(@NotNull Object body,
                                @NotNull HttpInputMessage inputMessage,
                                @NotNull MethodParameter parameter,
                                @NotNull Type targetType,
                                @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        // 单个字段解密在这里进行
        log.info("afterBodyRead");
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body,
                                  @NotNull HttpInputMessage inputMessage,
                                  @NotNull MethodParameter parameter,
                                  @NotNull Type targetType,
                                  @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("handleEmptyBody");
        return body;
    }

    static class DecodeHttpInputMessage implements HttpInputMessage {

        private final HttpHeaders headers;

        @Nullable
        private final InputStream body;


        @Contract(pure = true)
        DecodeHttpInputMessage(HttpHeaders headers, @Nullable InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        @NotNull
        @Override
        public InputStream getBody() {
            assert body != null;
            return body;
        }

        @NotNull
        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }


}
