package com.springcloud.example.common.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.InputStream;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2018/9/20 18:01
 */
public class HttpClientUtil {
    public static InputStream getInputStream(String url) throws Exception {
        InputStream ins;
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        try {
            ins = null;
            int statusCode;
            httpClient = HttpClients.createDefault();
            RequestConfig config = RequestConfig.custom().setSocketTimeout(200000).setConnectionRequestTimeout(200000).build();
            httpGet = new HttpGet(url);
            httpGet.setConfig(config);
            HttpResponse response = httpClient.execute(httpGet);
            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200){
                ins = response.getEntity().getContent();
            }
            httpGet.releaseConnection();
        } finally {
            if (httpGet != null){
//                httpGet.releaseConnection();
            }
        }
        return ins;
    }
}
