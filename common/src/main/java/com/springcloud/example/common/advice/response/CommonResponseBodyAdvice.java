package com.springcloud.example.common.advice.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.example.common.enums.CommonEnum;
import com.springcloud.example.common.message.MessageRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 通用返回拦截
 */
@Slf4j
@RestControllerAdvice
public class CommonResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        response.getHeaders().add("Content-type", "application/json");
        MessageRsp rsp = null;
        if (body == null) {
            rsp.setData(new Object());
        }
        if (body instanceof MessageRsp) {
            return body;
        }
        rsp = new MessageRsp(CommonEnum.Message.SUCCESS.getCode(), CommonEnum.Message.SUCCESS.getMessage(), body);
        if (body instanceof String) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(rsp);
            } catch (JsonProcessingException e) {
                log.error("error", e);
            }
        }
        return rsp;
    }

    @Override
    public boolean supports(MethodParameter param, Class<? extends HttpMessageConverter<?>> converter) {
        return true;
    }

}
