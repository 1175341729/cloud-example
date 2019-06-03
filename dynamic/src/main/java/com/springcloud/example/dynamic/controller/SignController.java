package com.springcloud.example.dynamic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2019/6/3 15:52
 */
@RequestMapping("/sign")
@RestController
@Slf4j
public class SignController {
    @GetMapping("/getSign")
    public String getSign(String name,String age){
        return "getSign";
    }

    @PostMapping("/postSignFormData")
    public String postSignFormData(Map<String,Object> param){
        return "postSignFormData";
    }

    @PostMapping("/postSignJson")
    public String postSignJson(@RequestBody Map<String,Object> param){
        log.info("{}",param);
        return "postSignJson";
    }
}
