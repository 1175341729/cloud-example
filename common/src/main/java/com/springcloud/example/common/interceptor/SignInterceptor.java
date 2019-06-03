package com.springcloud.example.common.interceptor;

import com.springcloud.example.common.wrapper.BodyReaderHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 *  @Author dengwei
 *  @Description: 参数验证 在signFilter中
 *  @Date 2019/6/3 15:28
 */
@Component
@Slf4j
public class SignInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
//        String queryString = requestWrapper.getQueryString();
//        log.info("{}", queryString);

//        String body = IOUtils.toString(requestWrapper.getInputStream());
//        log.info("{}",body);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
