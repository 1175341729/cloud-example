package com.springcloud.example.common.advice.exception;

import com.springcloud.example.common.enums.CommonEnum;
import com.springcloud.example.common.message.MessageRsp;
import com.springcloud.example.common.message.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理
 */
@ControllerAdvice
@Slf4j
public class HandlerException {
    @ExceptionHandler(value = {Exception.class, Throwable.class})
    @ResponseBody
    public MessageRsp handle(Exception e, HttpServletResponse response) {
        log.error("error", e);
        return MessageUtil.error(-1, "未知异常！");
    }

    /**
     * 自定义异常
     *
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public MessageRsp globalException(GlobalException e, HttpServletResponse response) {
        log.error("error", e);
        return MessageUtil.error(e.getCode(), e.getMessage());
    }

    /**
     * 资源路径不正确
     *
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public MessageRsp noHandlerFoundException(NoHandlerFoundException e, HttpServletResponse response) {
        log.error("error", e);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return MessageUtil.error(CommonEnum.Message.NOT_FOUND.getCode(), CommonEnum.Message.NOT_FOUND.getMessage() + e.getMessage());
    }

    /**
     * 请求方法不对
     *
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public MessageRsp httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletResponse response) {
        log.error("error", e);
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return MessageUtil.error(CommonEnum.Message.METHOD_NOT_ALLOWD.getCode(), CommonEnum.Message.METHOD_NOT_ALLOWD.getMessage() + e.getMessage());
    }

    /**
     * 参数验证
     * @param exs
     * @param response
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public MessageRsp constraintViolationException(ConstraintViolationException exs, HttpServletResponse response) {
        log.error("error", exs);
        Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> item : violations) {
            sb.append(item.getMessage()).append("、");
        }
        return MessageUtil.error(CommonEnum.Message.PARAM_ERROR.getCode(), StringUtils.removeEnd(sb.toString(), "、"));
    }

    /**
     * 参数验证
     * @param e
     * MethodArgumentNotValidException @RequestBody
     * BindException 普通form表单
     * @param response
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class,BindException.class})
    @ResponseBody
    public MessageRsp methodArgumentNotValidException(Exception e, HttpServletResponse response) {
        log.error("error", e);
        BindingResult bindingResult;
        if (e instanceof MethodArgumentNotValidException){
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        } else {
            bindingResult = ((BindException) e).getBindingResult();
        }
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : allErrors) {
            String defaultMessage = error.getDefaultMessage();
            sb.append(defaultMessage).append("、");
        }

        return MessageUtil.error(CommonEnum.Message.PARAM_ERROR.getCode(), StringUtils.removeEnd(sb.toString(), "、"));
    }
}
