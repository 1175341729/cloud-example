package com.springcloud.example.common.util;

import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 *  @Author dengwei
 *  @Description: 工具类
 *  @Date 2018/9/7 13:32
 */
public class CommonUtil {
    public static <T> List convertList(T... element){
        List<T> list = new ArrayList<>();
        if (!ArrayUtils.isEmpty(element)){
            list.addAll(Arrays.asList(element));
        }
        return list;
    }
}
