package com.springcloud.example.common.exception;

import com.springcloud.example.common.enums.CommonEnum;
import com.springcloud.example.common.message.MessageRsp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 *  全局异常处理
 */
@ControllerAdvice
@Slf4j
public class HandlerException {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public MessageRsp handle(Exception e, HttpServletResponse response) {
        log.error("error", e);
        MessageRsp message = new MessageRsp();
        if (e instanceof GlobalException) {//自定义异常
            GlobalException exception = (GlobalException) e;
            message.setCode(exception.getCode());
            message.setMessage(exception.getMessage());
        } else if (e instanceof ConstraintViolationException) { // Validate参数验证异常
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<?> item : violations) {
                sb.append(item.getMessage()).append("、");
            }
            message.setCode(-1);
            message.setMessage(StringUtils.removeEnd(sb.toString(), "、"));
        } else if (e instanceof NoHandlerFoundException) {// 资源请求路径异常
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            message.setCode(CommonEnum.Message.NOT_FOUND.getCode());
            message.setMessage(CommonEnum.Message.NOT_FOUND.getMessage() + e.getMessage());
        } else if (e instanceof HttpRequestMethodNotSupportedException) {// 请求类型不对
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            message.setCode(CommonEnum.Message.METHOD_NOT_ALLOWD.getCode());
            message.setMessage(CommonEnum.Message.METHOD_NOT_ALLOWD.getMessage() + e.getMessage());
        } else {// 其它异常
            message.setCode(-1);
            message.setMessage("未知异常！");
        }
        return message;
    }
}
