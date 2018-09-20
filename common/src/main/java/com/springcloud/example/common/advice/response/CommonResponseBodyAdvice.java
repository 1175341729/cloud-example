package com.springcloud.example.common.advice.response;

import com.springcloud.example.common.enums.CommonEnum;
import com.springcloud.example.common.message.MessageRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 通用返回拦截
 */
@Slf4j
@ControllerAdvice
public class CommonResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter param, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> converter, ServerHttpRequest req, ServerHttpResponse resp) {
        if (data != null && data.getClass().isAssignableFrom(MessageRsp.class)) {
            return data;
        }
        MessageRsp rsp = new MessageRsp(CommonEnum.Message.SUCCESS.getCode(), CommonEnum.Message.SUCCESS.getMessage(), data);
        if (data == null) {
            rsp.setData(new Object());
        }
        return rsp;
    }

    @Override
    public boolean supports(MethodParameter param, Class<? extends HttpMessageConverter<?>> converter) {
        return true;
    }

}
