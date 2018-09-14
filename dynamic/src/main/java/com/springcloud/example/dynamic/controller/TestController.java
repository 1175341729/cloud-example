package com.springcloud.example.dynamic.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.springcloud.example.common.exception.GlobalException;
import com.springcloud.example.common.message.MessageRsp;
import com.springcloud.example.common.message.MessageUtil;
import com.springcloud.example.common.message.PageMessage;
import com.springcloud.example.common.util.RedisUtil;
import com.springcloud.example.dynamic.message.StudentReq;
import com.springcloud.example.dynamic.model.SaleAreas;
import com.springcloud.example.dynamic.service.SaleAreasService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
        Object lock = redisUtil.get("lock");
        if (lock != null){
            throw new GlobalException("请勿重复操作！");
        }
        redisUtil.set("lock",true,60L);
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

    @GetMapping("/validateGet")
    public MessageRsp validateGet(@NotBlank(message = "名称不能为空！") String name, @Min(value = 10,message = "age需大于{value}") @NotNull Integer age){
        return MessageUtil.success(name + "--" + age);
    }

    @PostMapping("/validatePost")
    public MessageRsp validatePost(@Validated @RequestBody(required = false) StudentReq req){
        return MessageUtil.success(JSON.toJSON(req));
    }
}
