package com.github.springbootbodyadvice.advice;

import com.github.springbootbodyadvice.annotation.Encrypt;
import com.github.springbootbodyadvice.pojo.DataVO;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <p>
 * 创建时间为 下午10:07 2019/11/4
 * 项目名称 spring-boot-body-advice
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

@Slf4j
@RestControllerAdvice
public class DataResponseBodyAdvice implements ResponseBodyAdvice<DataVO> {

    @Override
    public boolean supports(@NotNull MethodParameter methodParameter, Class aClass) {
        return methodParameter.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public DataVO beforeBodyWrite(@NotNull DataVO body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        body.setData("Encrypt----" + body.getData());
        return body;
    }
}
