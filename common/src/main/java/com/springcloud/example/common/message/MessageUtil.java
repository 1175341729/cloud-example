package com.springcloud.example.common.message;

import com.springcloud.example.common.enums.CommonEnum;
import org.apache.commons.lang.StringUtils;

public class MessageUtil {
    public static <T> MessageRsp success(T data){
        MessageRsp<T> rsp = new MessageRsp();
        CommonEnum.Message success = CommonEnum.Message.SUCCESS;
        rsp.setCode(success.getCode());
        rsp.setMessage(success.getMessage());
        rsp.setData(data);
        return rsp;
    }

    public static MessageRsp error(String message){
        MessageRsp rsp = new MessageRsp();
        CommonEnum.Message success = CommonEnum.Message.ERROR;
        rsp.setCode(success.getCode());
        if (StringUtils.isNotBlank(message)){
            rsp.setMessage(message);
        } else {
            rsp.setMessage(success.getMessage());
        }
        return rsp;
    }

    public static <T> MessageRsp error(T data,String message){
        MessageRsp rsp = new MessageRsp();
        CommonEnum.Message success = CommonEnum.Message.ERROR;
        rsp.setCode(success.getCode());
        rsp.setData(data);
        if (StringUtils.isNotBlank(message)){
            rsp.setMessage(message);
        } else {
            rsp.setMessage(success.getMessage());
        }
        return rsp;
    }

    public static MessageRsp error(Integer code,String message){
        MessageRsp rsp = new MessageRsp();
        rsp.setCode(code);
        rsp.setMessage(message);
        return rsp;
    }
}
