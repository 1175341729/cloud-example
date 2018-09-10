package com.springcloud.example.dynamic.controller;

import com.alibaba.fastjson.JSON;
import com.springcloud.example.common.exception.GlobalException;
import com.springcloud.example.common.message.MessageRsp;
import com.springcloud.example.common.message.MessageUtil;
import com.springcloud.example.common.message.PageMessage;
import com.springcloud.example.common.util.RedisUtil;
import com.springcloud.example.dynamic.model.SaleAreas;
import com.springcloud.example.dynamic.service.SaleAreasService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/***
 *  @Author dengwei
 *  @Description: 测试
 *  @Date 2018/9/7 14:58
 */
@RestController
@Validated
@Slf4j
public class TestController {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SaleAreasService saleAreasService;

    @GetMapping("/test")
    public MessageRsp test() {
        redisUtil.set("test", "邓伟", null);
        MessageRsp<String> success = MessageUtil.success(redisUtil.get("test"));
        if (true)
        throw new GlobalException("1234");
        return success;
    }

    @GetMapping("/test2")
    public MessageRsp test(@NotEmpty(message = "name不能为空！") String name) {
        redisUtil.setHash("hash","name","邓伟");
        MessageRsp<String> success = MessageUtil.success(redisUtil.getHashByField("hash","name"));
        return success;
    }

    @GetMapping("/page")
    public MessageRsp page(@RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "0") Integer pageSize){
        PageMessage pageMessage = saleAreasService.listSaleAreas(pageNum, pageSize);

        List<SaleAreas> list = pageMessage.getList();
        log.info(JSON.toJSONString(list));
        return MessageUtil.success(pageMessage);
    }
}
