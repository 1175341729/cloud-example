package com.springcloud.example.common.filter;

import com.springcloud.example.common.wrapper.BodyReaderHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/***
 *  @Author dengwei
 *  @Description: 必须 必须  必须  通过filter注册clone流 否则会发生异常
 *  @Date 2019/6/3 17:00
 */
@WebFilter(filterName = "filter", urlPatterns = "/*")
@Slf4j
@Configuration
public class SignFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
        // GET 请求参数
        String queryString = requestWrapper.getQueryString();
        log.info("{}", queryString);
        // JSON 请求参数
        String body = IOUtils.toString(requestWrapper.getInputStream());
        log.info("{}", body);
        // FORM-DATA
        Map<String, String[]> parameterMap = requestWrapper.getParameterMap();
        log.info("{}", parameterMap);

        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }
}
