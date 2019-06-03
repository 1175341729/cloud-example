package com.springcloud.example.common.wrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2019/6/3 15:44
 */
@Slf4j
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private byte[] requestBody = null;//用于将流保存下来

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            log.error("Wrap requestBody failed");
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        if (requestBody == null) {
            requestBody = new byte[0];
        }
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public void setReadListener(ReadListener listener) {
                // do nothing
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public boolean isFinished() {
                return false;
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
