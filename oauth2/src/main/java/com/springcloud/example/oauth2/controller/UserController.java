package com.springcloud.example.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2019/3/20 17:30
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/info")
    public String user(){
        return "123";
    }
}
